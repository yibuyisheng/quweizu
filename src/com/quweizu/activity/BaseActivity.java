package com.quweizu.activity;

import com.quweizu.R;
import com.quweizu.application.QuWeiZuActivityManager;
import com.quweizu.application.ZLApplication;
import com.quweizu.modal.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

/**
 * 所有的activity都应该继承自该类
 * @author zhangli
 *
 */
public abstract class BaseActivity extends Activity {
	protected ZLApplication app;
	protected Button backButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ZLApplication)getApplication();
		QuWeiZuActivityManager.instance().push(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(QuWeiZuActivityManager.instance().peek() == this){
			QuWeiZuActivityManager.instance().pop();
		}
	}
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		backButton = (Button)this.findViewById(R.id.back_button);
		_findViews();
		_bindEvent();
		_init();
	}
	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(view);
		backButton = (Button)this.findViewById(R.id.back_button);
		_findViews();
		_bindEvent();
		_init();
	}
	@Override
	public void setContentView(View view, LayoutParams params) {
		// TODO Auto-generated method stub
		super.setContentView(view, params);
		backButton = (Button)this.findViewById(R.id.back_button);
		_findViews();
		_bindEvent();
		_init();
	}
	
	private void _init(){
		if(backButton!=null){
			backButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}
	/**找到view*/
	protected abstract void _findViews();
	/**给view绑定事件*/
	protected abstract void _bindEvent();
	
	public View getRootView(){
		return ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
	}
	
	public boolean isUserInfoCached(){
		Object selfObj = app.getAttribute("self");
		if(selfObj==null || !(selfObj instanceof User)){
			return false;
		}
		return true;
	}
	
}
