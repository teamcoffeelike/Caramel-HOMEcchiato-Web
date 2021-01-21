package com.hanul.coffeelike.caramelweb.util.recipeedit;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import org.springframework.web.multipart.MultipartFile;

public class RecipeWriter implements RecipeEditorFunctionVisitor{
	private final RecipeTemplate t = new RecipeTemplate();

	private int selectedStepIndex = -1;

	@Override public void setCategory(RecipeCategory category) throws RecipeEditorException{
		if(t.category!=null) throw new RecipeEditorException("레시피 카테고리가 두 번 이상 설정됨");
		t.category = category;
	}
	@Override public void setTitle(String title) throws RecipeEditorException{
		if(t.title!=null) throw new RecipeEditorException("레시피 타이틀이 두 번 이상 설정됨");
		t.title = title;
	}
	@Override public void setCoverImage(MultipartFile coverImage) throws RecipeEditorException{
		if(t.coverImage!=null) throw new RecipeEditorException("레시피 표지 사진이 두 번 이상 설정됨");
		t.coverImage = coverImage;
	}
	@Override public void setTotalStepCount(int totalStepCount) throws RecipeEditorException{
		if(t.steps!=null) throw new RecipeEditorException("총 페이지 수가 두 번 이상 설정됨");
		t.steps = new StepTemplate[totalStepCount];
	}
	@Override public void newStep(int step) throws RecipeEditorException{
		expectStepIndexInRange(step);
		if(t.steps[step]!=null) throw new RecipeEditorException("두 번 이상의 페이지 "+step+" 설정");
		t.steps[step] = new StepTemplate();
		selectedStepIndex = step;
	}
	@Override public void selectStep(int step) throws RecipeEditorException{
		throw new RecipeEditorException("새 레시피 작성 중 기존 레시피 참조");
	}
	@Override public void moveStep(int prevIndex, int newIndex) throws RecipeEditorException{
		throw new RecipeEditorException("새 레시피 작성 중 기존 레시피 참조");
	}
	@Override public void removeStep(int step) throws RecipeEditorException{
		throw new RecipeEditorException("새 레시피 작성 중 기존 레시피 참조");
	}
	@Override public void setStepImage(MultipartFile image) throws RecipeEditorException{
		StepTemplate step = selectedStep();
		if(step.image!=null) throw new RecipeEditorException("중복된 "+selectedStepIndex+" 페이지 첨부 이미지 설정");
		step.image = image;
	}
	@Override public void removeStepImage() throws RecipeEditorException{
		throw new RecipeEditorException("새 레시피 작성 중 기존 레시피 참조");
	}
	@Override public void setStepText(String text) throws RecipeEditorException{
		StepTemplate step = selectedStep();
		if(step.text!=null) throw new RecipeEditorException("중복된 "+selectedStepIndex+" 페이지 내용 설정");
		step.text = text;
	}

	protected void expectStepCountDefined() throws RecipeEditorException{
		if(t.steps==null) throw new RecipeEditorException("총 페이지 갯수 설정 이전에 페이지 참조");
	}
	protected void expectStepIndexInRange(int step) throws RecipeEditorException{
		expectStepCountDefined();
		if(step<0||step>=t.steps.length) throw new RecipeEditorException("범위를 벗어난 페이지 "+step+"/"+t.steps.length+" 참조");
	}

	protected StepTemplate selectedStep() throws RecipeEditorException{
		expectStepCountDefined();
		if(this.selectedStepIndex==-1) throw new RecipeEditorException("현재 설정된 페이지 없음");
		return t.steps[selectedStepIndex];
	}

	public RecipeTemplate compile() throws RecipeEditorException{
		if(t.category==null) throw new RecipeEditorException("카테고리 설정되지 않음");
		if(t.title==null) throw new RecipeEditorException("타이틀 설정되지 않음");
		if(t.coverImage==null) throw new RecipeEditorException("표지 사진 설정되지 않음");
		if(t.steps==null) throw new RecipeEditorException("총 페이지 수 설정되지 않음");
		for(int i = 0; i<t.steps.length; i++){
			if(t.steps[i]==null) throw new RecipeEditorException("페이지 "+i+" 설정되지 않음");
			if(t.steps[i].text==null) throw new RecipeEditorException("페이지 "+i+"의 내용 설정되지 않음");
		}
		return t;
	}

}
