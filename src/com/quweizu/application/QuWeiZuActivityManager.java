package com.quweizu.application;

import java.util.Stack;

import com.quweizu.activity.BaseActivity;
import com.quweizu.activity.MainActivity;

/**
 * 趣味组的所有activity管理器
 * @author yibuyisheng
 *
 */
public class QuWeiZuActivityManager {
	private QuWeiZuActivityManager(){}
	private static QuWeiZuActivityManager manager = null;
	public static final QuWeiZuActivityManager instance(){
		if(manager==null) manager = new QuWeiZuActivityManager();
		return manager;
	}
	
	/**
	 * activity栈
	 */
	private Stack<BaseActivity> activityStack = new Stack<BaseActivity>();
	public BaseActivity push(BaseActivity baseActivity){
		return this.activityStack.push(baseActivity);
	}
	public BaseActivity pop(){
		return this.activityStack.pop();
	}
	public BaseActivity peek(){
		return this.activityStack.peek();
	}
	
	
	/**
	 * 回到主Activity
	 * @param code
	 */
	public void backToMainActivity(int code){
		while(!activityStack.isEmpty()){
			BaseActivity ba = activityStack.peek();
			if(ba instanceof MainActivity){
				if(code==1){
					((MainActivity)ba).getFollowButton().performClick();
				}else if(code==2){
					((MainActivity)ba).getHotButton().performClick();
				}else if(code==3){
					((MainActivity)ba).getCategoryButton().performClick();
				}
				return;
			}else{
				activityStack.pop().finish();
			}
		}
	}
}
