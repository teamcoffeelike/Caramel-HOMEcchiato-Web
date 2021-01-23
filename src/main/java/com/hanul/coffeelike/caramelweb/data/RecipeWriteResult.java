package com.hanul.coffeelike.caramelweb.data;

import org.springframework.lang.Nullable;

public final class RecipeWriteResult{
	@Nullable private final Integer id;
	@Nullable private final String error;

	public RecipeWriteResult(@Nullable Integer id){
		this.id = id;
		this.error = null;
	}
	public RecipeWriteResult(@Nullable String error){
		this.id = null;
		this.error = error;
	}

	@Nullable public Integer getId(){
		return id;
	}
	@Nullable public String getError(){
		return error;
	}
}
