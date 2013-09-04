package com.quweizu.service;


import org.json.JSONObject;

import zl.android.http.ZLHttpCallback;
import zl.android.http.ZLHttpService;

import zl.android.log.Logger;
import com.quweizu.view.PlazaListView;

public class PlazaService extends Service {
	public interface GetPlazaShowTypeCallback{void onComplete(int status,String msg,int showType);}
	public void getPlazaShowType(String url,final GetPlazaShowTypeCallback cb){
		ZLHttpService.get(url, new ZLHttpCallback(){
			@Override
			public void onComplete(int status, String msg, Throwable t,
					byte[] data, String responseCookie) {
				super.onComplete(status, msg, t, data, responseCookie);
				
				if(cb==null) return;
				if(status==ZLHttpCallback.STATUS_CONNECT_ERROR || status==ZLHttpCallback.STATUS_REQUEST_TIMEOUT
						|| status==ZLHttpCallback.STATUS_INVALIDE_URL || status==ZLHttpCallback.STATUS_PROTOCOL_ERROR){
					cb.onComplete(Service.STATUS_NET_FAIL, msg,0);
				}else if(status==ZLHttpCallback.STATUS_SERVER_ERROR){
					cb.onComplete(Service.STATUS_SERVER_ERROR, msg, 0);
				}else if(status==ZLHttpCallback.STATUS_FAIL){
					cb.onComplete(Service.STATUS_NET_FAIL, msg, 0);
				}else if(status==ZLHttpCallback.STATUS_SUCCESS){
					int cbStatus = Service.STATUS_UNKNOWN_ERROR;
					String cbMsg = null;
					int cbShowType = 0;
					try{
						JSONObject jsonObject = new JSONObject(parseServerByteData(data, "{status:0,\"错误的服务器数据\"}"));
						if(jsonObject.getInt("status")!=1){
							cbStatus = Service.STATUS_DATA_FAIL;
							cbMsg = jsonObject.getString("msg");
						}else{
							cbStatus = Service.STATUS_SUCCESS;
							cbShowType = jsonObject.getInt("view_type");
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
