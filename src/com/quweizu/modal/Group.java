package com.quweizu.modal;

import java.util.ArrayList;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zl.android.utils.StringHelper;

import android.os.Bundle;

/**
 * 组
 * @author yibuyisheng
 *
 */
public class Group{
	private String id;
	private String name;
	private byte type;
	private byte vtype;
	private User creator;
	private int active_no;
	private String bk_img;
	private int blog_no;
	private String category;
	private String desc;
	private String face_img;
	private int fans_no;
	private byte free_join;
	private ArrayList<User> manager;
	private int mem_no;
	private ArrayList<String> show_imgs;
	private int status;
	private long _super;
	private long time;
	private ArrayList<User> trust;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte getVtype() {
		return vtype;
	}
	public void setVtype(byte vtype) {
		this.vtype = vtype;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public int getActive_no() {
		return active_no;
	}
	public void setActive_no(int active_no) {
		this.active_no = active_no;
	}
	public String getBk_img() {
		return bk_img;
	}
	public void setBk_img(String bk_img) {
		this.bk_img = bk_img;
	}
	public int getBlog_no() {
		return blog_no;
	}
	public void setBlog_no(int blog_no) {
		this.blog_no = blog_no;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFace_img() {
		return face_img;
	}
	public void setFace_img(String face_img) {
		this.face_img = face_img;
	}
	public int getFans_no() {
		return fans_no;
	}
	public void setFans_no(int fans_no) {
		this.fans_no = fans_no;
	}
	public byte getFree_join() {
		return free_join;
	}
	public void setFree_join(byte free_join) {
		this.free_join = free_join;
	}
	public ArrayList<User> getManager() {
		return manager;
	}
	public void setManager(ArrayList<User> manager) {
		this.manager = manager;
	}
	public int getMem_no() {
		return mem_no;
	}
	public void setMem_no(int mem_no) {
		this.mem_no = mem_no;
	}
	public ArrayList<String> getShow_imgs() {
		return show_imgs;
	}
	public void setShow_imgs(ArrayList<String> show_imgs) {
		this.show_imgs = show_imgs;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getSuper() {
		return _super;
	}
	public void setSuper(long _super) {
		this._super = _super;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public ArrayList<User> getTrust() {
		return trust;
	}
	public void setTrust(ArrayList<User> trust) {
		this.trust = trust;
	}
	public static Group parseFromJSON(String json) throws JSONException{
		Group group = null;
		JSONObject jsonObject = new JSONObject(json);
		group = new Group();
		if(jsonObject.has("id")) group.setId(jsonObject.getString("id"));
		if(jsonObject.has("_id")) group.setId(jsonObject.getString("_id"));
		if(jsonObject.has("name")) group.setName(jsonObject.getString("name"));
		if(jsonObject.has("type")) group.setType((byte)jsonObject.getInt("type"));
		if(jsonObject.has("vtype")) group.setVtype((byte)jsonObject.getInt("vtype"));
		if(jsonObject.has("trust")) {
			String trustStr = jsonObject.getString("trust");
			if(!StringHelper.isInt(trustStr)){
				group.setTrust(User.parseFromJsonArray(trustStr));
			}
		}
		if(jsonObject.has("active_no")) group.setActive_no(jsonObject.getInt("active_no"));
		if(jsonObject.has("bk_img")) group.setBk_img(jsonObject.getString("bk_img"));
		if(jsonObject.has("blog_no")) group.setBlog_no(jsonObject.getInt("blog_no"));
		if(jsonObject.has("category")) group.setCategory(jsonObject.getString("category"));
		if(jsonObject.has("desc")) group.setDesc(jsonObject.getString("desc"));
		if(jsonObject.has("face_img")) group.setFace_img(jsonObject.getString("face_img"));
		if(jsonObject.has("fans_no")) group.setFans_no(jsonObject.getInt("fans_no"));
		if(jsonObject.has("free_join")) group.setFree_join((byte)jsonObject.getInt("free_join"));
		if(jsonObject.has("manager")) group.setManager(User.parseFromJsonArray(jsonObject.getString("manager")));
		if(jsonObject.has("mem_no")) group.setMem_no(jsonObject.getInt("mem_no"));
		if(jsonObject.has("show_imgs")) {
			ArrayList<String> show_imgs = new ArrayList<String>();
			JSONArray jsonArray = new JSONArray(jsonObject.getString("show_imgs"));
			for(int i=0,il=jsonArray.length();i<il;i++){
				show_imgs.add(jsonArray.getString(i));
			}
			group.setShow_imgs(show_imgs);
		}
		if(jsonObject.has("status")) group.setStatus(jsonObject.getInt("status"));
		if(jsonObject.has("super")) group.setSuper(jsonObject.getLong("super"));
		if(jsonObject.has("time")) group.setTime(jsonObject.getLong("time"));
		if(jsonObject.has("creator")) group.setCreator(User.parseFromJSON(jsonObject.getString("creator")));
		return group;
	}
	public static ArrayList<Group> parseGroupListFromJSON(String json) throws JSONException{
		ArrayList<Group> groups = new ArrayList<Group>();
		JSONArray jsonArray = new JSONArray(json);
		for(int i=0,il=jsonArray.length();i<il;i++){
			groups.add(parseFromJSON(jsonArray.getString(i)));
		}
		return groups;
	}
	
	private Bundle _userListToBundle(ArrayList<User> users){
		if(users==null) return null;
		Bundle bundle = new Bundle();
		for(int i=0,il=users.size();i<il;i++){
			bundle.putBundle(String.valueOf(i), users.get(i).toBundle());
		}
		return bundle;
	}
	public Bundle toBundle(){
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		bundle.putString("name",name);
		bundle.putByte("type", type);
		bundle.putByte("vtype",vtype);
		bundle.putBundle("creator",creator.toBundle());
		bundle.putInt("active_no", active_no);
		bundle.putString("bk_img",bk_img);
		bundle.putInt("blog_no", blog_no);
		bundle.putString("category", category);
		bundle.putString("desc", desc);
		bundle.putString("face_img", face_img);
		bundle.putInt("fans_no", fans_no);
		bundle.putByte("free_join", free_join);
		bundle.putBundle("manager", _userListToBundle(manager));
		bundle.putInt("mem_no", mem_no);
		bundle.putStringArrayList("show_imgs", show_imgs);
		bundle.putInt("status", status);
		bundle.putLong("super", _super);
		bundle.putLong("time",time);
		bundle.putBundle("trust", _userListToBundle(trust));
		return bundle;
	}
	private static ArrayList<User> _bundleToUserList(Bundle bundle){
		if(bundle==null) return null;
		Set<String> keySet = bundle.keySet();
		ArrayList<User> users = new ArrayList<User>();
		for(String key : keySet){
			users.add(User.parseFromBundle(bundle.getBundle(key)));
		}
		return users;
	}
	public static Group parseFromBundle(Bundle bundle){
		if(bundle==null) return null;
		Group group = new Group();
		if(bundle.containsKey("id")) group.setId(bundle.getString("id"));
		if(bundle.containsKey("name")) group.setName(bundle.getString("name"));
		if(bundle.containsKey("type")) group.setType(bundle.getByte("type"));
		if(bundle.containsKey("vtype")) group.setVtype(bundle.getByte("vtype"));
		if(bundle.containsKey("creator")) group.setCreator(User.parseFromBundle(bundle.getBundle("creator")));
		if(bundle.containsKey("active_no")) group.setActive_no(bundle.getInt("active_no"));
		if(bundle.containsKey("bk_img")) group.setBk_img(bundle.getString("bk_img"));
		if(bundle.containsKey("blog_no")) group.setBlog_no(bundle.getInt("blog_no"));
		if(bundle.containsKey("category")) group.setCategory(bundle.getString("category"));
		if(bundle.containsKey("desc")) group.setDesc(bundle.getString("desc"));
		if(bundle.containsKey("face_img")) group.setFace_img(bundle.getString("face_img"));
		if(bundle.containsKey("fans_no")) group.setFans_no(bundle.getInt("fans_no"));
		if(bundle.containsKey("free_join")) group.setFree_join(bundle.getByte("free_join"));
		if(bundle.containsKey("manager")) group.setManager(_bundleToUserList(bundle.getBundle("manager")));
		if(bundle.containsKey("mem_no")) group.setMem_no(bundle.getInt("mem_no"));
		if(bundle.containsKey("show_imgs")) group.setShow_imgs(bundle.getStringArrayList("show_imgs"));
		if(bundle.containsKey("status")) group.setStatus(bundle.getInt("status"));
		if(bundle.containsKey("super")) group.setSuper(bundle.getLong("super"));
		if(bundle.containsKey("time")) group.setTime(bundle.getLong("time"));
		if(bundle.containsKey("trust")) group.setTrust(_bundleToUserList(bundle.getBundle("trust")));
		return group;
	}
}
