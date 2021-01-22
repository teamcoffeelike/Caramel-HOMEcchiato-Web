package com.hanul.coffeelike.recipeedit.exception;

import com.hanul.coffeelike.caramelweb.util.JsonHelper;

public abstract class RecipeEditorException extends Exception{
	/////////// Compile Error ///////////

	public static final String MALFORMED_INSTRUCTION = "malformed_instruction";
	public static final String UNKNOWN_MODE = "unknown_mode";
	public static final String UNKNOWN_FUNCTION = "unknown_function";
	public static final String INVALID_RESOURCE_REFERENCE = "invalid_resource_reference";

	/////////// Runtime Error ///////////

	public static final String NO_RECIPE = "no_recipe";

	public static final String DUPLICATED_CATEGORY_SET = "duplicated_category_set";
	public static final String DUPLICATED_TITLE_SET = "duplicated_title_set";
	public static final String DUPLICATED_COVER_IMAGE_SET = "duplicated_cover_image_set";
	public static final String DUPLICATED_TOTAL_STEP_COUNT = "duplicated_total_step_count";

	public static final String DUPLICATED_STEP_SELECTION = "duplicated_step_selection";
	public static final String DUPLICATED_ORIGINAL_STEP_SELECTION = "duplicated_original_step_selection";
	public static final String DUPLICATED_STEP_IMAGE_SET = "duplicated_step_image_set";
	public static final String DUPLICATED_STEP_TEXT_SET = "duplicated_step_text_set";

	public static final String MODIFY_IN_WRITE_CONTEXT = "modify_in_write_context";
	public static final String STEP_SELECT_BEFORE_TOTAL_COUNT_SET = "step_select_before_total_count_set";
	public static final String STEP_OUT_OF_BOUNDS = "step_out_of_bounds";
	public static final String ORIGINAL_STEP_OUT_OF_BOUNDS = "original_step_out_of_bounds";
	public static final String NO_STEP_SELECTED = "no_step_selected";

	public static final String NO_CATEGORY_SET = "no_category_set";
	public static final String NO_TITLE_SET = "no_title_set";
	public static final String NO_COVER_IMAGE_SET = "no_cover_image_set";
	public static final String NO_TOTAL_STEPS_COUNT_SET = "no_total_steps_count_set";

	public static final String NO_STEP_SET = "no_step_set";
	public static final String NO_ORIGINAL_STEP_HANDLED = "no_original_step_handled";
	public static final String NO_STEP_TEXT_SET = "no_step_text_set";

	public static final String NO_IMAGE_TO_REMOVE_FROM_ORIGINAL_STEP = "no_image_to_remove_from_original_step";

	public RecipeEditorException(){}
	public RecipeEditorException(String message){
		super(message);
	}
	public RecipeEditorException(String message, Throwable cause){
		super(message, cause);
	}
	public RecipeEditorException(Throwable cause){
		super(cause);
	}
	public RecipeEditorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String toJson(){
		return JsonHelper.failure(jsonErrorMessage());
	}

	public abstract String jsonErrorMessage();
}
