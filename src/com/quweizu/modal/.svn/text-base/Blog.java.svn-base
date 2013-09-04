package com.quweizu.modal;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import zl.android.utils.StringHelper;

import android.os.Bundle;

public class Blog {
	private String title = null;
	private String content = null;
	private ArrayList<String> imgs = new ArrayList<String>();
	private long id = 0;
	private String head;
	private String name;
	private long uid=0;
	private String qid;
	private String qname;
	private int price;
	private int like_no;
	private String url;
	private long time;
	private long pid;
	private String nick_name;
	private long create_timestamp;
	
	private String video;
	private String video_thumbnail;
	private String video_title;
	private String video_url;
	
	private boolean is_viewed=false;//这篇blog是否被看过
	
	
	public boolean isIs_viewed() {
		return is_viewed;
	}
	public void setIs_viewed(boolean is_viewed) {
		this.is_viewed = is_viewed;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getVideo_thumbnail() {
		return video_thumbnail;
	}
	public void setVideo_thumbnail(String video_thumbnail) {
		this.video_thumbnail = video_thumbnail;
	}
	public String getVideo_title() {
		return video_title;
	}
	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public long getPid() {
		return pid;
	}
	public long getCreate_timestamp() {
		return create_timestamp;
	}
	public void setCreate_timestamp(long create_timestamp) {
		this.create_timestamp = create_timestamp;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLike_no() {
		return like_no;
	}
	public void setLike_no(int like_no) {
		this.like_no = like_no;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public long getId() {
		return id;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getQname() {
		return qname;
	}
	public void setQname(String qname) {
		this.qname = qname;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<String> getImgs() {
		return imgs;
	}
	public void setImgs(ArrayList<String> imgs) {
		this.imgs = imgs;
	}
	
	
	public Group getGroup(){
		Group group = new Group();
		User creator = new User();
		creator.setHead(head);
		creator.setNick_name(nick_name);
		creator.setId(uid);
		group.setCreator(creator);
		group.setId(qid);
		group.setName(qname);
		return group;
	}
	public User getCreator(){
		User creator = new User();
		creator.setHead(head);
		creator.setNick_name(nick_name);
		creator.setId(uid);
		return creator;
	}
	
	public static Blog parseBlogReturn(String json)throws Exception{
		JSONObject jsonObject = new JSONObject(json);
		if(jsonObject.getInt("status") == 1){
			jsonObject = jsonObject.getJSONObject("data").getJSONObject("blog");
			return parseFromJSON(jsonObject.toString());
		}else{
			throw new Exception(jsonObject.getString("msg"));
		}
	}
	public static Blog parseFromJSON(String json) throws Exception{
		Blog blog = null;
		JSONObject jsonObject = new JSONObject(json);
		blog = new Blog();
		if(jsonObject.has("title")) blog.setTitle(jsonObject.getString("title"));
		if(jsonObject.has("content")) blog.setContent(jsonObject.getString("content"));
		if(jsonObject.has("id")) blog.setId(jsonObject.getInt("id"));
		
		if(jsonObject.has("images")&&jsonObject.getString("images")!=null&&!jsonObject.getString("images").equals("")&&!jsonObject.getString("images").equals("null")){
			JSONArray imgsJSON = jsonObject.getJSONArray("images");
			for(int i=0;i<imgsJSON.length();i++){
				blog.getImgs().add(imgsJSON.getString(i));
			}
		}
		
		if(jsonObject.has("head")) blog.setHead(jsonObject.getString("head"));
		if(jsonObject.has("name")) blog.setName(jsonObject.getString("name"));
		if(jsonObject.has("qid")) blog.setQid(jsonObject.getString("qid"));
		if(jsonObject.has("uid")) blog.setUid(jsonObject.getLong("uid"));
		if(jsonObject.has("user_id")) blog.setUid(jsonObject.getLong("user_id"));
		if(jsonObject.has("qname")) blog.setQname(jsonObject.getString("qname"));
		if(jsonObject.has("price")) blog.setPrice(jsonObject.getInt("price"));
		if(jsonObject.has("ltimes")) blog.setLike_no(jsonObject.getInt("ltimes"));
		if(jsonObject.has("url")) blog.setUrl(jsonObject.getString("url"));
		if(jsonObject.has("time")) blog.setTime(jsonObject.getLong("time"));
		if(jsonObject.has("pid")&&StringHelper.isLong(jsonObject.getString("pid"))) blog.setPid(jsonObject.getLong("pid"));
		if(jsonObject.has("nick_name")) blog.setNick_name(jsonObject.getString("nick_name"));
		if(jsonObject.has("name")) blog.setNick_name(jsonObject.getString("name"));
		if(jsonObject.has("create_timestamp")) blog.setCreate_timestamp(jsonObject.getLong("create_timestamp"));
		if(jsonObject.has("video")) blog.setVideo(jsonObject.getString("video"));
		if(jsonObject.has("video_thumbnail")) blog.setVideo_thumbnail(jsonObject.getString("video_thumbnail"));
		if(jsonObject.has("video_title")) blog.setVideo_title(jsonObject.getString("video_title"));
		if(jsonObject.has("video_url")) blog.setVideo_url(jsonObject.getString("video_url"));
		return blog;
	}
	
	public static ArrayList<Blog> parseBlogListReturn(String json) throws Exception{
		ArrayList<Blog> blogList = null;
		JSONObject jsonObject = new JSONObject(json);
		if(jsonObject.getInt("status") == 1){
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			if(jsonArray == null) return null;
			
			blogList = new ArrayList<Blog>();
			for(int i=0;i<jsonArray.length();i++){
				jsonObject = jsonArray.getJSONObject(i);
				blogList.add(Blog.parseFromJSON(jsonObject.toString()));
			}
		}else{
			throw new Exception(jsonObject.getString("msg"));
		}
		return blogList;
	}
	public static ArrayList<Blog> parseBlogListFromJson(String json)throws Exception{
		ArrayList<Blog> blogs = new ArrayList<Blog>();
		JSONArray jsonArray = new JSONArray(json);
		for(int i=0,il=jsonArray.length();i<il;i++){
			blogs.add(parseFromJSON(jsonArray.getString(i)));
		}
		return blogs;
	}
	public Bundle toBundle(){
		Bundle bundle = new Bundle();
		bundle.putString("title",title);
		bundle.putString("content", content);
		bundle.putStringArrayList("imgs", this.imgs);
		bundle.putLong("id",id);
		bundle.putString("head", this.head);
		bundle.putString("name", this.name);
		bundle.putString("qid", this.qid);
		bundle.putString("qname", this.qname);
		bundle.putInt("price", price);
		bundle.putInt("like_no", this.like_no);
		bundle.putString("url", this.url);
		bundle.putLong("uid", this.uid);
		bundle.putLong("time", this.time);
		bundle.putLong("time", this.pid);
		bundle.putString("nick_name", nick_name);
		bundle.putLong("create_timestamp", this.create_timestamp);
		bundle.putString("video", this.video);
		bundle.putString("video_thumbnail", this.video_thumbnail);
		bundle.putString("video_title", this.video_title);
		bundle.putString("video_url", this.video_url);
		bundle.putBoolean("is_viewed", this.is_viewed);
		return bundle;
	}
	public static Blog parseBlogFromBundle(Bundle bundle){
		Blog blog = new Blog();
		blog.setTitle(bundle.getString("title"));
		blog.setContent(bundle.getString("content"));
		blog.setImgs(bundle.getStringArrayList("imgs"));
		blog.setId(bundle.getLong("id"));
		blog.setHead(bundle.getString("head"));
		blog.setName(bundle.getString("name"));
		blog.setQid(bundle.getString("qid"));
		blog.setQname(bundle.getString("qname"));
		blog.setPrice(bundle.getInt("price"));
		blog.setLike_no(bundle.getInt("like_no"));
		blog.setUrl(bundle.getString("url"));
		blog.setUid(bundle.getLong("uid"));
		blog.setTime(bundle.getLong("time"));
		blog.setTime(bundle.getLong("pid"));
		blog.setNick_name(bundle.getString("nick_name"));
		blog.setCreate_timestamp(bundle.getLong("create_timestamp"));
		blog.setVideo(bundle.getString("video"));
		blog.setVideo_thumbnail(bundle.getString("video_thumbnail"));
		blog.setVideo_title(bundle.getString("video_title"));
		blog.setVideo_url(bundle.getString("video_url"));
		blog.setIs_viewed(bundle.getBoolean("is_viewed"));
		return blog;
	}
}
