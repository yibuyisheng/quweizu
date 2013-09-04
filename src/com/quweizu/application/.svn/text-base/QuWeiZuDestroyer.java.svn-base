package com.quweizu.application;

import zl.android.log.Logger;

import zl.android.http.ZLHttpService;
import zl.android.http.image_load.BitmapCacheManager;
import zl.android.local.ZLLocalPreferences;

/**
 * 应用退出的时候，这个类做一些清理工作
 * @author yibuyisheng
 *
 */
public class QuWeiZuDestroyer {
	/**
	 * 销毁工作完成后的回调，暂未使用
	 * @author yibuyisheng
	 *
	 */
	public interface OnDestroyComplete{void onComplete();}
	/**
	 * 销毁
	 * @param app
	 */
	public void destroy(ZLApplication app){
		BitmapCacheManager.getBitmapManager().destroy();
		ZLLocalPreferences.getInstance().saveAttribute("cookies", ZLHttpService.getCookies());
		Logger.info("save cookies:"+ZLHttpService.getCookies());
		app.removeAllAttribute();
	}
}
