package com.quweizu.utils;

public class QuWeiZuStringHelper {
	public static final String getImageUrl(String src,int height){
		if(src==null || src.equals("")) return null;
		int index = src.indexOf('#');
		if(index==-1) return src += "_"+height;
		StringBuilder ret = new StringBuilder();
		ret.append(src.substring(0, index)).append("_").append(height);
		return ret.toString();
	}
}
