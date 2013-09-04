package com.quweizu.service;

import java.util.ArrayList;

import org.json.JSONObject;

import zl.android.http.ZLHttpCallback;
import zl.android.http.ZLHttpParameters;
import zl.android.http.ZLHttpService;

import zl.android.log.Logger;
import com.quweizu.modal.Group;

public class GroupService extends Service{
	public interface GetMyGroupsCallback{void onComplete(int status,String msg,ArrayList<Group> groups);}
	public void getMyGroups(String url,final GetMyGroupsCallback cb){
		ZLHttpService.get(url, new ZLHttpCallback(){
			@Override
			public void onComplete(int status, String msg, Throwable t,
					byte[] data, String responseCookie) {
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null) return;
				if(status==ZLHttpCallback.STATUS_CONNECT_ERROR || status==ZLHttpCallback.STATUS_REQUEST_TIMEOUT
						|| status==ZLHttpCallback.STATUS_INVALIDE_URL || status==ZLHttpCallback.STATUS_PROTOCOL_ERROR){
					cb.onComplete(Service.STATUS_NET_FAIL, msg, null);
				}else if(status==ZLHttpCallback.STATUS_SERVER_ERROR){
					cb.onComplete(Service.STATUS_SERVER_ERROR, msg, null);
				}else if(status==ZLHttpCallback.STATUS_FAIL){
					cb.onComplete(Service.STATUS_NET_FAIL, msg, null);
				}else if(status==ZLHttpCallback.STATUS_SUCCESS){
					int cbStatus = Service.STATUS_UNKNOWN_ERROR;
					String cbMsg = null;
					ArrayList<Group> cbGroups = null;
					try{
						JSONObject jsonObject = new JSONObject(parseServerByteDataJsonp(data, "{status:0,\"错误的服务器数据\"}"));
						if(jsonObject.getInt("status")!=1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbStatus = Service.STATUS_SUCCESS;
							cbGroups = Group.parseGroupListFromJSON(jsonObject.getString("pub_zu"));
						}
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg, cbGroups);
				}
			}
		});
	}

	public interface FollowGroupCallback{void onComplete(int status,String msg);}
	public void followGroup(String url,String qid,final FollowGroupCallback cb){
		ZLHttpParameters params = new ZLHttpParameters();
		params.put("qid", qid);
		ZLHttpService.get(url, params, new ZLHttpCallback() {
			@Override
			public void onComplete(int status, String msg, Throwable t,byte[] data, String responseCookie) {
				// TODO Auto-generated method stub
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null) return;
				if(status==ZLHttpCallback.STATUS_CONNECT_ERROR || status==ZLHttpCallback.STATUS_REQUEST_TIMEOUT
						|| status==ZLHttpCallback.STATUS_INVALIDE_URL || status==ZLHttpCallback.STATUS_PROTOCOL_ERROR){
					cb.onComplete(Service.STATUS_NET_FAIL, msg);
				}else if(status==ZLHttpCallback.STATUS_SERVER_ERROR){
					cb.onComplete(Service.STATUS_SERVER_ERROR, msg);
				}else if(status==ZLHttpCallback.STATUS_FAIL){
					cb.onComplete(Service.STATUS_NET_FAIL, msg);
				}else if(status==ZLHttpCallback.STATUS_SUCCESS){
					int cbStatus = Service.STATUS_UNKNOWN_ERROR;
					String cbMsg = null;
					try{
						JSONObject jsonObject = new JSONObject(parseServerByteData(data, "{status:0,\"错误的服务器数据\"}"));
						if(jsonObject.getInt("status")==2){
							cbStatus=Service.STATUS_NOT_LOGIN;
						}else if(jsonObject.getInt("status")!=1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbStatus = Service.STATUS_SUCCESS;
						}
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg);
				}
			}
		});
	}
	
	public interface UnFollowGroupCallback{void onComplete(int status,String msg);}
	public void unfollowGroup(String url,String qid,final UnFollowGroupCallback cb){
		followGroup(url,qid,cb==null?null:new FollowGroupCallback() {
			@Override
			public void onComplete(int status, String msg) {
				// TODO Auto-generated method stub
				cb.onComplete(status, msg);
			}
		});
	}
	
	public interface HasFollowCallback{void onComplete(int status,String msg,boolean followed);}
	public void hasFollow(String url,String qid,final HasFollowCallback cb){
		ZLHttpParameters params = new ZLHttpParameters();
		params.put("qid", qid);
		ZLHttpService.get(url, params, new ZLHttpCallback() {
			@Override
			public void onComplete(int status, String msg, Throwable t,byte[] data, String responseCookie) {
				// TODO Auto-generated method stub
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null) return;
				if(status==ZLHttpCallback.STATUS_CONNECT_ERROR || status==ZLHttpCallback.STATUS_REQUEST_TIMEOUT
						|| status==ZLHttpCallback.STATUS_INVALIDE_URL || status==ZLHttpCallback.STATUS_PROTOCOL_ERROR){
					cb.onComplete(Service.STATUS_NET_FAIL, msg,false);
				}else if(status==ZLHttpCallback.STATUS_SERVER_ERROR){
					cb.onComplete(Service.STATUS_SERVER_ERROR, msg,false);
				}else if(status==ZLHttpCallback.STATUS_FAIL){
					cb.onComplete(Service.STATUS_NET_FAIL, msg,false);
				}else if(status==ZLHttpCallback.STATUS_SUCCESS){
					int cbStatus = Service.STATUS_UNKNOWN_ERROR;
					String cbMsg = null;
					boolean cbFollowed=false;
					try{
						JSONObject jsonObject = new JSONObject(parseServerByteData(data, "{status:0,\"错误的服务器数据\"}"));
						if(jsonObject.getInt("status")==2){
							cbStatus=Service.STATUS_NOT_LOGIN;
						}else if(jsonObject.getInt("status")!=1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbStatus = Service.STATUS_SUCCESS;
							if(jsonObject.getInt("followed")==1){
								cbFollowed=true;
							}
						}
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg,cbFollowed);
				}
			}
		});
	}
	
	public interface GetZuInfoCallback{void onComplete(int status,String msg,Group group);}
	public void getZuInfo(String url,String qid,final GetZuInfoCallback cb){
		ZLHttpParameters params = new ZLHttpParameters();
		params.put("qid", qid);
		ZLHttpService.get(url, params, new ZLHttpCallback() {
			@Override
			public void onComplete(int status, String msg, Throwable t,byte[] data, String responseCookie) {
				// TODO Auto-generated method stub
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null) return;
				if(status==ZLHttpCallback.STATUS_CONNECT_ERROR || status==ZLHttpCallback.STATUS_REQUEST_TIMEOUT
						|| status==ZLHttpCallback.STATUS_INVALIDE_URL || status==ZLHttpCallback.STATUS_PROTOCOL_ERROR){
					cb.onComplete(Service.STATUS_NET_FAIL, msg,null);
				}else if(status==ZLHttpCallback.STATUS_SERVER_ERROR){
					cb.onComplete(Service.STATUS_SERVER_ERROR, msg,null);
				}else if(status==ZLHttpCallback.STATUS_FAIL){
					cb.onComplete(Service.STATUS_NET_FAIL, msg,null);
				}else if(status==ZLHttpCallback.STATUS_SUCCESS){
					int cbStatus = Service.STATUS_UNKNOWN_ERROR;
					String cbMsg = null;
					Group cbGroup=null;
					try{
						JSONObject jsonObject = new JSONObject(parseServerByteData(data, "{status:0,\"错误的服务器数据\"}"));
						Logger.info("---------group:"+jsonObject);
						if(jsonObject.getInt("status")!=1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbGroup = Group.parseFromJSON(jsonObject.getString("data"));
							cbStatus = Service.STATUS_SUCCESS;
						}
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg,cbGroup);
				}
			}
		});
	}
}
