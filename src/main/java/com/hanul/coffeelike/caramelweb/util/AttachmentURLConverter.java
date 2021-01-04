package com.hanul.coffeelike.caramelweb.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class AttachmentURLConverter{
	private AttachmentURLConverter(){}

	public static String profileImageFromId(int userId){
		return resolve("profileImage", "id="+userId);
	}

	public static String postImageFromId(int postId){
		return resolve("postImage", "id="+postId);
	}

	public static String recipeCoverImageFromId(int recipeId){
		return resolve("recipeImage/cover", "id="+recipeId);
	}

	public static String recipeStepImageFromId(int recipe, int index){
		return resolve("recipeImage/step", "recipe="+recipe+"&index="+index);
	}

	private static String resolve(String type, String query){
		return "http://"+AddressLazy.ADDRESS+"/caramelweb/images/"+type+"?"+query;
	}

	private static final class AddressLazy{
		private static final String ADDRESS; // TODO https://stackoverflow.com/q/38916213

		static{
			try{
				ADDRESS = InetAddress.getLocalHost().getHostAddress();
			}catch(UnknownHostException e){
				throw new IllegalStateException(e);
			}
		}
	}
}
