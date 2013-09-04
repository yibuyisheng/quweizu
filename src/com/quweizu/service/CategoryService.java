package com.quweizu.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import zl.android.http.ZLHttpCallback;
import zl.android.http.ZLHttpParameters;
import zl.android.http.ZLHttpService;

import zl.android.log.Logger;
import com.quweizu.modal.Category;
import com.quweizu.view.PlazaListView;

public class CategoryService extends Service{
	public interface GetCategoriesFromServerCallback{void onComplete(int status,String msg,String dataCategories,ArrayList<Category> categories);}
	public void getCategoriesFromServer(String url,final GetCategoriesFromServerCallback cb){
		ZLHttpService.get(url,new ZLHttpCallback() {
			@Override
			public void onComplete(int status, String msg, Throwable t,
					byte[] data, String responseCookie) {
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null)return;
				if(status==ZLHttpCallback.STATUS_CONNECT_ERROR || status==ZLHttpCallback.STATUS_REQUEST_TIMEOUT
						|| status==ZLHttpCallback.STATUS_INVALIDE_URL || status==ZLHttpCallback.STATUS_PROTOCOL_ERROR){
					cb.onComplete(Service.STATUS_NET_FAIL, msg, null,null);
				}else if(status==ZLHttpCallback.STATUS_SERVER_ERROR){
					cb.onComplete(Service.STATUS_SERVER_ERROR, msg, null,null);
				}else if(status==ZLHttpCallback.STATUS_FAIL){
					cb.onComplete(Service.STATUS_NET_FAIL, msg, null,null);
				}else if(status==ZLHttpCallback.STATUS_SUCCESS){
					int cbStatus = Service.STATUS_UNKNOWN_ERROR;
					String cbMsg = null;
					String cbDataCategories = null;
					ArrayList<Category> cbCategories = null;
					try{
						String dataStr = new String(data);
						cbStatus = Service.STATUS_SUCCESS;
						cbDataCategories = dataStr;
						cbCategories = Category.parseCategoryListFromJson(dataStr);
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg, cbDataCategories, cbCategories);
				}
			}
		});
	}
	
	public interface GetSubCategoryCallback{void onComplete(int status,String msg,ArrayList<Category> subCategories);}
	public void getSubCategory(String url,String categoryUname,final GetSubCategoryCallback cb){
		ZLHttpParameters params = new ZLHttpParameters();
		params.put("category", categoryUname);
		ZLHttpService.get(url,params, new ZLHttpCallback() {
			@Override
			public void onComplete(int status, String msg, Throwable t,
					byte[] data, String responseCookie) {
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null)return;
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
					ArrayList<Category> cbSubCategories = null;
					try{
						JSONObject jsonObject = new JSONObject(new String(data));
						if(jsonObject.getInt("status") != 1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbStatus = Service.STATUS_SUCCESS;
							cbSubCategories = Category.parseCategoryListFromJson(jsonObject.getString("data"));
						}
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg, cbSubCategories);
				}
			}
		});
	}

	public interface GetElesByCategoryCallback{void onComplete(int status,String msg,ArrayList<String> eles);}
	public void getElesByCategory(String url,ZLHttpParameters params,final GetElesByCategoryCallback cb){
		ZLHttpService.get(url, params, new ZLHttpCallback() {
			@Override
			public void onComplete(int status, String msg, Throwable t,
					byte[] data, String responseCookie) {
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null)return;
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
					ArrayList<String> eles = null;
					try{
						JSONObject jsonObject = new JSONObject(new String(data));
						if(jsonObject.getInt("status") != 1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbStatus = Service.STATUS_SUCCESS;
							JSONArray jsonArray = jsonObject.getJSONArray("data");
							eles = new ArrayList<String>();
							for(int i=0,il=jsonArray.length();i<il;i++){
								eles.add(jsonArray.getString(i));
							}
						}
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg, eles);
				}
			}
		});
	}
	
	public interface GetShowTypeCallback{void onComplete(int status,String msg,int showType);}
	public void getShowType(String url,final String uname,final GetShowTypeCallback cb){
		ZLHttpParameters params = new ZLHttpParameters();
		params.put("uname",uname);
		ZLHttpService.get(url, params, new ZLHttpCallback() {
			@Override
			public void onComplete(int status, String msg, Throwable t,
					byte[] data, String responseCookie) {
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null)return;
				if(status==ZLHttpCallback.STATUS_CONNECT_ERROR || status==ZLHttpCallback.STATUS_REQUEST_TIMEOUT
						|| status==ZLHttpCallback.STATUS_INVALIDE_URL || status==ZLHttpCallback.STATUS_PROTOCOL_ERROR){
					cb.onComplete(Service.STATUS_NET_FAIL, msg,0);
				}else if(status==ZLHttpCallback.STATUS_SERVER_ERROR){
					cb.onComplete(Service.STATUS_SERVER_ERROR, msg,0);
				}else if(status==ZLHttpCallback.STATUS_FAIL){
					cb.onComplete(Service.STATUS_NET_FAIL, msg,0);
				}else if(status==ZLHttpCallback.STATUS_SUCCESS){
					int cbStatus = Service.STATUS_UNKNOWN_ERROR;
					String cbMsg = null;
					int cbShowType = 0;
					try{
						JSONObject jsonObject = new JSONObject(new String(data));
						if(jsonObject.getInt("status") != 1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbStatus = Service.STATUS_SUCCESS;
							cbShowType = jsonObject.getInt("view_type");
							Logger.info("category "+uname+" show type:"+cbShowType);
							if(cbShowType==1){
								cbShowType = PlazaListView.WORDS_FIRST;
							}else if(cbShowType==0){
								cbShowType=PlazaListView.IMAGE_FIRST;
							}
						}
					}catch(Exception e){
						Logger.error(e);
						cbStatus = Service.STATUS_DATA_PARSE_FAIL;
						cbMsg = e.getMessage();
					}
					cb.onComplete(cbStatus, cbMsg, cbShowType);
				}
			}
		});
	}
}
