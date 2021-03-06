package com.hanul.coffeelike.caramelweb.controller.api;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeCoverListResult;
import com.hanul.coffeelike.caramelweb.data.RecipeRateResult;
import com.hanul.coffeelike.caramelweb.data.RecipeWriteResult;
import com.hanul.coffeelike.caramelweb.service.RecipeService;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.util.JsonHelper;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import com.hanul.coffeelike.caramelweb.util.Validate;
import com.hanul.coffeelike.recipeedit.RecipeEditorDecoder;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditMode;
import com.hanul.coffeelike.recipeedit.ast.RecipeEditorAST;
import com.hanul.coffeelike.recipeedit.exception.RecipeEditorException;
import com.hanul.coffeelike.recipeedit.visitor.RecipeJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpSession;
import java.io.EOFException;
import java.sql.Date;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

@RestController
public class RecipeApiController extends BaseExceptionHandlingController{
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserService userService;

	/**
	 * 레시피 리스트, 최신순 정렬<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   recipes: [
	 *     {
	 *       id: Integer
	 *       category: ( "hot_coffee" | "ice_coffee" | "tea" | "ade" | "smoothie" | "etc" )
	 *       title: String
	 *       [ coverImage ]: URL
	 *       author: {
	 *         id: Integer
	 *         name: String
	 *         [ profileImage ]: URL
	 *         [ isFollowingYou ]: Boolean
	 *         [ isFollowedByYou ]: Boolean
	 *       }
	 *       postDate: Date
	 *       [ lastEditDate ]: Date
	 *       ratings: Integer
	 *       [ averageRating ]: Number # 평가 평균
	 *       [ yourRating ]: Number # 로그인한 유저의 평가
	 *     }
	 *   ]
	 *   endOfList: Boolean
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * bad_category : 유효하지 않은 category<br>
	 * no_user : 존재하지 않는 author<br>
	 */
	@RequestMapping(value = "/api/recipeList", produces = "application/json;charset=UTF-8")
	public String recipeList(HttpSession session,
	                         @RequestParam(required = false) @Nullable Long since,
	                         @RequestParam(defaultValue = "10") int pages,
	                         @Nullable @RequestParam(required = false) String category,
	                         @Nullable @RequestParam(required = false) Integer author){
		if(category!=null&&RecipeCategory.fromString(category)==null){
			return JsonHelper.failure("bad_category");
		}
		if(author!=null&&!userService.checkIfUserExists(author)){
			return JsonHelper.failure("no_user");
		}

		AuthToken loginUser = SessionAttributes.getLoginUser(session);

		RecipeCoverListResult result = recipeService.list(
				loginUser==null ? null : loginUser.getUserId(),
				since==null ? null : new Date(since),
				pages,
				category,
				author);
		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 해당 레시피의 스텝을 포함한 모든 정보<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   id: Integer
	 *   category: ( "hot_coffee" | "ice_coffee" | "tea" | "ade" | "smoothie" | "etc" )
	 *   title: String
	 *   [ coverImage ]: URL
	 *   author: {
	 *     id: Integer
	 *     name: String
	 *     [ profileImage ]: URL
	 *     [ isFollowingYou ]: Boolean
	 *     [ isFollowedByYou ]: Boolean
	 *   }
	 *   postDate: Date
	 *   [ lastEditDate ]: Date
	 *   ratings: Integer
	 *   [ averageRating ]: Number # 평가 평균
	 *   [ yourRating ]: Number # 로그인한 유저의 평가
	 *   steps: [
	 *     {
	 *       index: Integer
	 *       [ image ]: URL
	 *       text: String
	 *     }
	 *   ]
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_recipe : 존재하지 않는 레시피 id<br>
	 */
	@RequestMapping(value = "/api/recipe", produces = "application/json;charset=UTF-8")
	public String recipe(HttpSession session,
	                     @RequestParam int id){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		Recipe recipe = recipeService.recipe(id, loginUser==null ? null : loginUser.getUserId());
		if(recipe==null) return JsonHelper.failure("no_recipe");
		return JsonHelper.GSON.toJson(recipe);
	}

	/**
	 * 레시피 작성<br>
	 * <br>
	 * <pre>{@code
	 * -> title: Plain Text
	 * -> coverImage: Image
	 * -> category: Plain Text ( "hot_coffee" | "ice_coffee" | "tea" | "ade" | "smoothie" | "etc" )
	 * -> steps: Integer
	 * [ N = 1 2 3...steps
	 *   [-> imageN]: Image
	 *   -> textN: Plain Text
	 * ]
	 * }</pre>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   id: Integer # 작성한 레시피 ID
	 * }
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 * bad_title : 유효하지 않은 타이틀<br>
	 * bad_category : 유효하지 않은 카테고리<br>
	 * bad_steps : 유효하지 않은 레시피 단계 갯수<br>
	 * bad_step_text : 유효하지 않은 레시피 단계 텍스트<br>
	 */
	@RequestMapping(value = "/api/writeRecipe", produces = "application/json;charset=UTF-8")
	public String writeRecipe(HttpSession session,
	                          @RequestParam Map<String, String> allRequestParams,
	                          MultipartRequest multipartRequest,
	                          @RequestParam("title") String title,
	                          @RequestParam("coverImage") MultipartFile coverImage,
	                          @RequestParam("category") String category,
	                          @RequestParam("steps") int steps){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		RecipeCategory recipeCategory = RecipeCategory.fromString(category);
		if(recipeCategory==null) return JsonHelper.failure("bad_category");

		if(steps<1||steps>Validate.MAX_RECIPE_STEPS){
			return JsonHelper.failure("bad_steps");
		}

		List<Entry<MultipartFile, String>> stepsList = new ArrayList<>();

		for(int i = 1; i<=steps; i++){
			MultipartFile imageN = multipartRequest.getFile("image"+i);
			String textN = allRequestParams.get("text"+i);
			if(textN==null) return JsonHelper.failure("bad_parameter");

			stepsList.add(new SimpleEntry<>(imageN, textN));
		}

		RecipeWriteResult result = recipeService.writeRecipe(loginUser.getUserId(), title, coverImage, recipeCategory, stepsList);

		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 레시피 작성/수정<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * {
	 *   id: Integer # 작성/수정한 레시피 ID
	 * }
	 * }</pre>
	 *
	 * <b>공통 에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 * <br>
	 * <b>수정 모드 에러: </b><br>
	 * no_recipe : 해당 ID의 레시피가 존재하지 않음<br>
	 * cannot_edit : 해당 글을 수정할 수 없음<br>
	 *
	 * @see RecipeEditorDecoder
	 */
	@RequestMapping(value = "/api/editRecipe", produces = "application/json;charset=UTF-8")
	public String editRecipe(HttpSession session,
	                         MultipartRequest multipartRequest){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		MultipartFile inst = multipartRequest.getFile("inst");
		try{
			RecipeEditorDecoder decoder = new RecipeEditorDecoder(Objects.requireNonNull(inst).getInputStream(),
					value -> multipartRequest.getFile(Integer.toString(value)));
			RecipeEditMode mode = decoder.readMode();
			List<RecipeEditorAST> functions = new ArrayList<>();
			while(true){
				RecipeEditorAST function = decoder.readFunction();
				if(function==null) break;
				functions.add(function);
			}

			logger.info(mode+" : "+new RecipeJoiner(", ").apply(functions).toString());

			RecipeWriteResult result = recipeService.editRecipe(loginUser.getUserId(), mode, functions);
			return JsonHelper.GSON.toJson(result);
		}catch(RecipeEditorException ex){
			logger.error("RecipeEditorException", ex);
			return ex.toJson();
		}catch(EOFException ex){
			logger.error("End of File", ex);
			return JsonHelper.failure(RecipeEditorException.MALFORMED_INSTRUCTION);
		}catch(Exception ex){
			logger.error("레시피 edit 중 오류 발생", ex);
			return JsonHelper.failure("unexpected");
		}
	}

	/**
	 * 레시피 삭제<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * no_recipe : 해당 ID의 레시피가 존재하지 않음<br>
	 * cannot_delete : 해당 글을 삭제할 수 없음 (비 로그인 상태 포함)<br>
	 */
	@RequestMapping(value = "/api/deleteRecipe", produces = "application/json;charset=UTF-8")
	public String deleteRecipe(HttpSession session,
	                           @RequestParam int recipe){
		RecipeCover cover = recipeService.getCover(recipe, null);
		if(cover==null) return JsonHelper.failure("no_recipe");

		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null||loginUser.getUserId()!=cover.getAuthor().getId())
			return JsonHelper.failure("cannot_delete");

		recipeService.delete(recipe);

		return "{}";
	}

	/**
	 * 레시피 평가<br>
	 * 기존 평가를 덮어쓸 수 있음<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 * bad_rating : 유효하지 않은 rating 인자<br>
	 * no_recipe : 해당 ID의 레시피가 존재하지 않음<br>
	 */
	@RequestMapping(value = "/api/rateRecipe", produces = "application/json;charset=UTF-8")
	public String rateRecipe(HttpSession session,
	                         @RequestParam int recipe,
	                         @RequestParam double rating){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		RecipeRateResult result = recipeService.rateRecipe(loginUser.getUserId(), recipe, rating);
		return JsonHelper.GSON.toJson(result);
	}

	/**
	 * 레시피 평가 삭제<br>
	 * 삭제할 평가가 없어도 OK처리<br>
	 * <br>
	 * <b>성공 시:</b><br>
	 * <pre>{@code
	 * 추가 데이터 없음
	 * }</pre>
	 *
	 * <b>에러: </b><br>
	 * not_logged_in : 로그인 상태가 아님<br>
	 * no_recipe : 해당 ID의 레시피가 존재하지 않음<br>
	 */
	@RequestMapping(value = "/api/deleteRecipeRating", produces = "application/json;charset=UTF-8")
	public String deleteRecipeRating(HttpSession session, @RequestParam int recipe){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return JsonHelper.failure("not_logged_in");

		if(!recipeService.deleteRecipeRating(loginUser.getUserId(), recipe)){
			return JsonHelper.failure("no_recipe");
		}

		return "{}";
	}
}
