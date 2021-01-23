package com.hanul.coffeelike.recipeedit.exception.compile;

public class InvalidResourceReference extends MalformedInstruction{
	public InvalidResourceReference(int id){
		super("존재하지 않는 리소스 "+id+" 참조");
	}

	@Override public String jsonErrorMessage(){
		return INVALID_RESOURCE_REFERENCE;
	}
}
