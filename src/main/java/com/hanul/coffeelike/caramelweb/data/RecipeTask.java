package com.hanul.coffeelike.caramelweb.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public abstract class RecipeTask{
	public abstract String getType();

	public static class Timer extends RecipeTask{
		private int seconds;
		private Purpose purpose;

		public Timer(int seconds, Purpose purpose){
			this.seconds = seconds;
			this.purpose = purpose;
		}

		@Override public String getType(){
			return "timer";
		}

		public int getSeconds(){
			return seconds;
		}
		public void setSeconds(int seconds){
			this.seconds = seconds;
		}
		public Purpose getPurpose(){
			return purpose;
		}
		public void setPurpose(Purpose purpose){
			this.purpose = purpose;
		}

		public enum Purpose{
			COOK,
			WAIT;

			@Override public String toString(){
				return name().toLowerCase();
			}
		}

		public enum Json implements JsonSerializer<Timer>{
			INSTANCE;

			@Override
			public JsonElement serialize(Timer src,
			                             Type typeOfSrc,
			                             JsonSerializationContext context){
				JsonObject o = new JsonObject();
				o.addProperty("type", src.getType());
				o.addProperty("seconds", src.getSeconds());
				o.addProperty("purpose", src.getPurpose().toString());
				return o;
			}
		}
	}
}
