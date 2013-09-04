package com.quweizu.modal;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import zl.android.log.Logger;

/**
 * 用户
 * @author yibuyisheng
 *
 */
public class User {
	private long id;
	private String nick_name;
	private String email;
	private String head;
	private int email_verified;
	private byte vtype;
	public byte getVtype() {
		return vtype;
	}
	public void setVtype(byte vtype) {
		this.vtype = vtype;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public int getEmail_verified() {
		return email_verified;
	}
	public void setEmail_verified(int email_verified) {
		this.email_verified = email_verified;
	}
	
	public static User parseFromJSON(String json) throws JSONException{
		if(json == null || json.equals("")) return null;
		JSONObject jsonObject = new JSONObject(json);
		User user = new User();
		if(jsonObject.has("id")) user.setId(jsonObject.getInt("id"));
		if(jsonObject.has("nick_name")) user.setNick_name(jsonObject.getString("nick_name"));
		if(jsonObject.has("email")) user.setEmail(jsonObject.getString("email"));
		if(jsonObject.has("head")) user.setHead(jsonObject.getString("head"));
		if(jsonObject.has("email_verified")) user.setEmail_verified(jsonObject.getInt("email_verified"));
		if(jsonObject.has("vtype")) user.setVtype((byte)jsonObject.getInt("vtype"));
		return user;
	}
	public static ArrayList<User> parseFromJsonArray(String json) throws JSONException{
		if(json == null || json.equals("")) return null;
		JSONArray jsonArray = new JSONArray(json);
		ArrayList<User> users = new ArrayList<User>();
		for(int i=0,il=jsonArray.length();i<il;i++){
			users.add(parseFromJSON(jsonArray.getString(i)));
		}
		return users;
	}
	
	public Bundle toBundle(){
		Bundle bundle = new Bundle();
		bundle.putLong("id", id);
		bundle.putString("nick_name", nick_name);
		bundle.putString("email", email);
		bundle.putString("head", head);
		bundle.putInt("email_verified", email_verified);
		bundle.putByte("vtype", vtype);
		return bundle;
	}
	
	public static User parseFromBundle(Bundle bundle){
		if(bundle==null) return null;
		User user = new User();
		user.setId(bundle.getLong("id"));
		user.setEmail(bundle.getString("email"));
		user.setEmail_verified(bundle.getInt("email_verified"));
		user.setHead(bundle.getString("head"));
		user.setNick_name(bundle.getString("nick_name"));
		user.setVtype(bundle.getByte("vtype"));
		return user;
	}
	
	public JSONObject toJSONObject() throws JSONException{
		JSONObject ret = new JSONObject();
		ret.put("id", this.id);
		ret.put("nick_name", this.nick_name);
		ret.put("email", this.email);
		ret.put("head", this.head);
		ret.put("email_verified", this.email_verified);
		ret.put("vtype", vtype);
		return ret;
	}
	public String toString(){
		try {
			return this.toJSONObject().toString();
		} catch (JSONException e) {
			Logger.error(e);
		}
		return "";
	}
}
