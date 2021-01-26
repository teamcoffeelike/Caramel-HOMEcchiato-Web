package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Recipe;
import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import com.hanul.coffeelike.caramelweb.data.RecipeCover;
import com.hanul.coffeelike.caramelweb.data.RecipeStep;
import com.hanul.coffeelike.caramelweb.service.RecipeService;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.AttachmentURLConverter;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class RecipeController{
	@Autowired
	private RecipeService recipeService;

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
		RecipeCover cover = r.getCover();
		if(AttachmentFileResolver.doesRecipeCoverImageExists(cover.getCoverImage())){
			cover.setCoverImage(AttachmentURLConverter.recipeCoverImageFromId(cover.getId()));
		}else{
			cover.setCoverImage("imgs/post.png");
		}
		for(RecipeStep step : r.getSteps()){
			if(AttachmentFileResolver.doesRecipeStepImageExists(step.getImage())){
				step.setImage(AttachmentURLConverter.recipeStepImageFromId(step.getRecipe(), step.getStep()));
			}else{
				step.setImage("imgs/post.png");
			}
		}
		model.addAttribute("recipe", r);
		return "recipe/detail";
	}
}
