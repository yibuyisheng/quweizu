package com.quweizu.application;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONObject;

import com.quweizu.config.UrlConfig;
import zl.android.log.Logger;
import com.quweizu.modal.User;
import com.quweizu.service.PlazaService;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.service.UserService;

import zl.android.exception.ZLGlobalExceptionCatch;
import zl.android.http.ZLHttpService;
import zl.android.local.ZLLocalPreferences;

/**
 * 趣味组app初始化
 * @author zhangli
 *
 */
public class QuWeiZuInitializer {
	private interface _OnAsyncComplete{void onComplete();}
	
	/**
	 * 各个初始化步骤的状态
	 */
	private Hashtable<String,Boolean> asyncInitializeReadyTable = new Hashtable<String,Boolean>();
	/**
	 * 检查是否所有的初始化步骤都已经完成
	 * @return
	 */
	private boolean _checkReady(){
		for(Enumeration<String> en=asyncInitializeReadyTable.keys();en.hasMoreElements();){
			String key = en.nextElement();
			if(!asyncInitializeReadyTable.get(key)){
				return false;
			}
		}
		asyncInitializeReadyTable.clear();
		return true;
	}
	
	/**
	 * 初始化完成后的回调
	 * @author yibuyisheng
	 *
	 */
	public interface OnInitializeComplete{void onComplete();}
	/**
	 * 初始化
	 * @param app 应用对象
	 * @param onComplete 回调
	 */
	public void initialize(ZLApplication app,final OnInitializeComplete onComplete){
		_appGlobalExceptionCatcher(app);
		_initializeCookies(app);
		_initializeUserInfo(app,onComplete==null?null:new _OnAsyncComplete() {
			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				if(_checkReady()){
					onComplete.onComplete();
				}
			}
		});
		_initializePlazaHotShowKind(app,onComplete==null?null:new _OnAsyncComplete() {
			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				if(_checkReady()){
					onComplete.onComplete();
				}
			}
		});
		
		if(_checkReady()){
			onComplete.onComplete();
		}
	}
	
	/**
	 * 初始化广场-热门的显示方式
	 * @param app
	 * @param cb
	 */
	private void _initializePlazaHotShowKind(final ZLApplication app,final _OnAsyncComplete cb){
		try{
			int showType = Integer.parseInt(app.getInfoFromDb("plaza_hot_show_type"));
			Logger.info("locale cache plaza_hot_show_type:"+showType);
		}catch(Exception e){
			asyncInitializeReadyTable.put("_initializePlazaHotShowKind", false);
			ServiceFactory.createService(PlazaService.class).getPlazaShowType(UrlConfig.PLAZA_HOT_VIEW_TYPE, new PlazaService.GetPlazaShowTypeCallback() {
				@Override
				public void onComplete(int status, String msg, int showType) {
					// TODO Auto-generated method stub
					if(status==Service.STATUS_SUCCESS){
						app.saveInfoToDb("plaza_hot_show_type", showType);
					}
					asyncInitializeReadyTable.put("_initializePlazaHotShowKind", true);
					if(cb!=null) cb.onComplete();
				}
			});
		}
	}
	/**
	 * 初始化用户信息，主要查看用户是否已经登录
	 * @param app
	 * @param onComplete
	 */
	private void _initializeUserInfo(final ZLApplication app,final _OnAsyncComplete onComplete){
		User self = null;
		try{
			self = User.parseFromJSON(app.getInfoFromDb("self"));
			app.setAttribute("self", self);
		}catch(Exception e){
			Logger.error(e);
		}
		if(self == null){
			this.asyncInitializeReadyTable.put("_initializeUserInfo", false);
			ServiceFactory.createService(UserService.class).getSelf(UrlConfig.GET_SELF, new UserService.GetSelfCallback() {
				@Override
				public void onComplete(int status, String msg, User self) {
					// TODO Auto-generated method stub
					if(status==Service.STATUS_SUCCESS){
						app.setAttribute("self", self);
						app.saveInfoToDb("self", self);
					}else{
						app.removeAttribute("self");
						app.removeInfoFromDb("self");
					}
					asyncInitializeReadyTable.put("_initializeUserInfo", true);
					if(onComplete!=null) onComplete.onComplete();
				}
			});
		}
	}
	
	/**
	 * 全局异常捕获
	 * @param app
	 */
	private void _appGlobalExceptionCatcher(ZLApplication app){
		ZLGlobalExceptionCatch globalExceptionCatch = ZLGlobalExceptionCatch.getInstance();
		globalExceptionCatch.init(app);
		Thread.setDefaultUncaughtExceptionHandler(globalExceptionCatch);
	}
	
	/**
	 * cookie初始化
	 * @param app
	 */
	private void _initializeCookies(ZLApplication app){
		ZLLocalPreferences.createInstance(app);
		try{
			JSONObject jsonObject = new JSONObject(ZLLocalPreferences.getInstance().getAttribute("cookies", "{}"));
			Logger.info("get cookies:"+jsonObject.toString());
			if(jsonObject != null){
				@SuppressWarnings("unchecked")
				Iterator<String> it = jsonObject.keys();
				while(it.hasNext()){
					String key = it.next();
					ZLHttpService.addCookie(key,jsonObject.getString(key));
				}
			}
		}catch(Exception e){
			Logger.error(e);
		}
	}
}
