package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeCoverListResult;
import com.hanul.coffeelike.caramelweb.service.RecipeService;
import com.hanul.coffeelike.caramelweb.service.UserService;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Controller
public class RecipeController{
	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

	@Autowired
	private RecipeService recipeService;
	@Autowired
	private UserService userService;

	@RequestMapping("/recipeList")
	public String list(HttpSession session,
	                   Model model,
	                   @RequestParam(required = false) @Nullable String category){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";

		RecipeCategory recipeCategory = category==null ? null : RecipeCategory.fromString(category);
		if(recipeCategory==null) return "redirect:recipeList?category=hot_coffee";
		model.addAttribute("category", recipeCategory);

		return "recipe/list";
	}

	@RequestMapping("/myRecipe")
	public String myRecipe(HttpSession session,
	                       Model model,
	                       @RequestParam int author){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";

		if(!userService.checkIfUserExists(author)){
			model.addAttribute("msg", "존재하지 않는 유저입니다.\n레시피 목록으로 돌아갑니다.");
			model.addAttribute("redirect", "recipeList");
			return "recipe/recipeError";
		}

		model.addAttribute("author", author);

		return "recipe/myRecipe";
	}

	@RequestMapping("/recipe")
	public String recipe(HttpSession session,
	                     Model model,
	                     @RequestParam int recipe){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";

		Recipe r = recipeService.recipe(recipe, loginUser.getUserId());
		if(r==null){
			model.addAttribute("msg", "존재하지 않는 레시피입니다.\n레시피 목록으로 돌아갑니다.");
			model.addAttribute("redirect", "recipeList");
			return "recipe/recipeError";
		}
		AttachmentFileResolver.resolve(r);
		RecipeCoverListResult result = recipeService.list(loginUser.getUserId(), null, 6, null, r.getCover().getAuthor().getId());
		List<RecipeCover> otherRecipes = result.getRecipes();
		if(otherRecipes==null){
			// 위 서비스 내에서 발생할 수 있는 에러는 모두 코드 내부 에러
			LOGGER.error("recipe 서비스 내부에서 예상치 못한 오류 발생");
		}else{
			for(Iterator<RecipeCover> it = otherRecipes.iterator(); it.hasNext();){
				RecipeCover next = it.next();
				if(next.getId()==recipe) it.remove();
				else AttachmentFileResolver.resolve(next);
			}
			model.addAttribute("otherRecipes", otherRecipes);
		}
		model.addAttribute("recipe", r);
		return "recipe/detail";
	}
}
