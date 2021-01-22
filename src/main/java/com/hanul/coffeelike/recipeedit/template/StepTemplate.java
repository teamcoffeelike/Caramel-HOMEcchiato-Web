package com.hanul.coffeelike.recipeedit.template;

import org.springframework.lang.Nullable;

public final class StepTemplate{
	@Nullable public Integer originalIndex;
	public final AttachmentTemplate image = new AttachmentTemplate();
	public String text;

	public StepTemplate(){}
	public StepTemplate(int originalIndex){
		this.originalIndex = originalIndex;
	}
}
