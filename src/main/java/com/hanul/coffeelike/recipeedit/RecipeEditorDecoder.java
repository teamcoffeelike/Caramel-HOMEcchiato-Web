package com.hanul.coffeelike.recipeedit;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditMode;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditorAST;
import com.hanul.coffeelike.recipeedit.ast.RemoveStep;
import com.hanul.coffeelike.recipeedit.ast.RemoveStepImage;
import com.hanul.coffeelike.recipeedit.ast.SetCategory;
import com.hanul.coffeelike.recipeedit.ast.SetCoverImage;
import com.hanul.coffeelike.recipeedit.ast.SetStepImage;
import com.hanul.coffeelike.recipeedit.ast.SetStepText;
import com.hanul.coffeelike.recipeedit.ast.SetTitle;
import com.hanul.coffeelike.recipeedit.ast.SetTotalStepCount;
import com.hanul.coffeelike.recipeedit.ast.StepSelector;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.exception.compile.InvalidRecipeCategory;
import com.hanul.coffeelike.recipeedit.exception.compile.InvalidResourceReference;
import com.hanul.coffeelike.recipeedit.exception.compile.MalformedInstruction;
import com.hanul.coffeelike.recipeedit.exception.compile.UnknownFunction;
import com.hanul.coffeelike.recipeedit.exception.compile.UnknownMode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntFunction;

/**
 * <pre>
 * RecipeEditor
 *   : Mode (Function NULL)* EOF
 *   ;
 *
 * Mode
 *   : MODE_WRITE   # 레시피 새로 작성
 *   | MODE_EDIT I  # 레시피 변경
 *   ;
 *
 * Function
 *   : SET_CATEGORY S
 *   | SET_TITLE S
 *   | SET_COVER_IMAGE B
 *   | SET_TOTAL_STEP_COUNT I
 *   | NEW_STEP I
 *   | SELECT_STEP I
 *   | MOVE_STEP I I
 *   | REMOVE_STEP I
 *   | SET_STEP_IMAGE B
 *   | REMOVE_STEP_IMAGE
 *   | SET_STEP_TEXT S
 *   ;
 *
 * S : (DataInput에서 정의된 Modified UTF-8 String)
 * B : (uint8)
 * I : (int32)
 * NULL : (uint8 0)
 *
 * # 바이트 상수
 * MODE_WRITE : '1';
 * MODE_EDIT : '2';
 * SET_CATEGORY : 'c';
 * SET_TITLE : 't';
 * SET_COVER_IMAGE : 'i';
 * SET_TOTAL_STEP_COUNT : 'q';
 * NEW_STEP : 'n';
 * SELECT_STEP : 's';
 * MOVE_STEP : 'm';
 * REMOVE_STEP : 'r';
 * SET_STEP_IMAGE : 'z';
 * REMOVE_STEP_IMAGE : 'x';
 * SET_STEP_TEXT : 'v';
 * </pre>
 * ㅅㅂㅋㅋ<br>
 * 레시피 수정을 위한 작은 domain specific regular language.<br>
 * 각각의 수정 작업에 해당되는 함수들의 1차원 배열.
 * 함수는 1바이트의 선택자를 따르는 임의 바이트의 데이터 패러미터를 칭합니다. 함수의 끝 부분은 null byte로 구분됩니다.<br>
 * <br>
 * <pre>
 * c..hot_coffee.
 *
 *
 * Indicator  Parameter   Terminator
 *         _ ____________ _
 *         c ..hot_coffee .
 * (공백 문자는 존재하지 않음. '.'은 해독 불가능한 character.)
 * </pre>
 * 문자열은 Java의 DataOutputStream에서 정의하는 Modified UTF-8 String으로 전달됩니다.
 * 이미지 등의 바이너리 리소스는 인코더 내부에서 인덱싱되어 별개의 멀티파트 body로 전달됩니다.
 * 리소스 간의 연결은 1바이트의 index 번호로 전달됩니다.<br>
 * <br>
 * step count는 무조건 제공되어야 하며, 모든 추가/삭제/유지되는 step은 각자 한 번씩 '선택'되어야 합니다.
 * 비어 있는 인덱스가 있거나 기존에 존재하던 step이 아무런 작업이 이루어지지 않았다면 불완전한 작업으로 간주하게 됩니다.<br>
 * step count는 step의 선택 이전에 제공되어야 하며, step 내부 값을 수정하는 작업(내용 수정, 이미지 수정/삭제)은 step의 선택 이후에 이루어져야 합니다.
 * 모든 step 수정 작업은 마지막으로 선택한 step을 대상으로 이루어집니다.<br>
 * <br>
 * 모든 수정 작업에 대해, 두 번 이상의 값 제공 또는 이미 선택된 step의 재선택은 작업 요청에서의 오류로 간주하여 에러를 일으킵니다.
 */
public final class RecipeEditorDecoder{
	private static final byte MODE_WRITE = '1';
	private static final byte MODE_EDIT = '2';

	private static final byte OP_SET_CATEGORY = 'c';
	private static final byte OP_SET_TITLE = 't';
	private static final byte OP_SET_COVER_IMAGE = 'i';
	private static final byte OP_SET_TOTAL_STEP_COUNT = 'q';
	private static final byte OP_NEW_STEP = 'n';
	private static final byte OP_SELECT_STEP = 's';
	private static final byte OP_MOVE_STEP = 'm';
	private static final byte OP_REMOVE_STEP = 'r';
	private static final byte OP_SET_STEP_IMAGE = 'z';
	private static final byte OP_REMOVE_STEP_IMAGE = 'x';
	private static final byte OP_SET_STEP_TEXT = 'v';

	private final DataInputStream reader;
	private final IntFunction<MultipartFile> resourceMap;

	public RecipeEditorDecoder(InputStream data, IntFunction<MultipartFile> resourceMap){
		this.reader = new DataInputStream(data);
		this.resourceMap = resourceMap;
	}

	public RecipeEditMode readMode() throws IOException, RecipeEditorException{
		byte mode = reader.readByte();
		switch(mode){
		case MODE_WRITE: return RecipeEditMode.WriteMode.INSTANCE;
		case MODE_EDIT: return new RecipeEditMode.EditMode(reader.readInt());
		default: throw new UnknownMode((char)mode);
		}
	}

	@Nullable public RecipeEditorAST readFunction() throws IOException, RecipeEditorException{
		if(reader.available()<=0) return null;
		int selector = reader.readUnsignedByte();
		RecipeEditorAST fun;
		switch(selector){
		case OP_SET_CATEGORY:{
			String read = reader.readUTF();
			RecipeCategory category = RecipeCategory.fromString(read);
			if(category==null) throw new InvalidRecipeCategory(read);
			fun = new SetCategory(category);
			break;
		}
		case OP_SET_TITLE:
			fun = new SetTitle(reader.readUTF());
			break;
		case OP_SET_COVER_IMAGE:
			fun = new SetCoverImage(readResource());
			break;
		case OP_SET_TOTAL_STEP_COUNT:
			fun = new SetTotalStepCount(reader.readInt());
			break;
		case OP_NEW_STEP:
			fun = new StepSelector.NewStep(reader.readInt());
			break;
		case OP_SELECT_STEP:
			fun = new StepSelector.SelectStep(reader.readInt());
			break;
		case OP_MOVE_STEP:
			fun = new StepSelector.MoveStep(reader.readInt(), reader.readInt());
			break;
		case OP_REMOVE_STEP:
			fun = new RemoveStep(reader.readInt());
			break;
		case OP_SET_STEP_IMAGE:
			fun = new SetStepImage(readResource());
			break;
		case OP_REMOVE_STEP_IMAGE:
			fun = RemoveStepImage.INSTANCE;
			break;
		case OP_SET_STEP_TEXT:
			fun = new SetStepText(reader.readUTF());
			break;
		default: throw new UnknownFunction((char)selector);
		}
		if(reader.readByte()!=0) throw new MalformedInstruction("Expected null terminator");
		return fun;
	}

	@NonNull private MultipartFile readResource() throws IOException, RecipeEditorException{
		int id = reader.readUnsignedByte();
		MultipartFile resource = resourceMap.apply(id);
		if(resource==null) throw new InvalidResourceReference(id);
		return resource;
	}
}
