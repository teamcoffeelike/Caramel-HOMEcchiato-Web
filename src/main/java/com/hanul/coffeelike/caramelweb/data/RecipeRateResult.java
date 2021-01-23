package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public final class RecipeRateResult{
	@Nullable private final String error;

	public RecipeRateResult(){
		this(null);
	}
	public RecipeRateResult(@Nullable String error){
		this.error = error;
	}

	@Nullable public String getError(){
		return error;
	}
}
