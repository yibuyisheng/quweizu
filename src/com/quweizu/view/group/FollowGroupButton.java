package com.quweizu.view.group;

import com.quweizu.R;
import com.quweizu.activity.LoginActivity;
import com.quweizu.application.ZLApplication;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Group;
import com.quweizu.modal.User;
import com.quweizu.service.GroupService;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 关注按钮
 * @author yibuyisheng
 *
 */
public class FollowGroupButton extends Button implements View.OnClickListener{
	public FollowGroupButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		_init();
	}
	public FollowGroupButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		_init();
	}
	private void _init(){
		this.setPadding(10, 15, 10, 15);
		
		app = (ZLApplication)this.getContext().getApplicationContext();
		self = (User)app.getAttribute("self");
	}
	
	private Group group;
	private User self=null;
	private ZLApplication app;
	public void setGroup(Group group){
		if(self==null) return;
		
		this.group = group;
		this.setVisibility(View.VISIBLE);
		
		this.setOnClickListener(this);
		
		self = (User)app.getAttribute("self");
		this.setVisibility(View.GONE);
		ServiceFactory.createService(GroupService.class).hasFollow(UrlConfig.HAS_FOLLOW, this.group.getId(), new GroupService.HasFollowCallback() {
			@Override
			public void onComplete(int status, String msg,boolean followed) {
				// TODO Auto-generated method stub
				setFollow(followed);
				FollowGroupButton.this.setVisibility(View.VISIBLE);
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(String.valueOf(this.getText()).equals("处理中...")) return;
		if(self==null)self = (User)app.getAttribute("self");
		if(self==null){
			Intent intent = new Intent();
			intent.setClass(this.getContext(), LoginActivity.class);
			this.getContext().startActivity(intent);
			return;
		}
		
		if(getResources().getString(R.string.follow_this_group).equals(String.valueOf(this.getText()))){
			this.setText("处理中...");
			ServiceFactory.createService(GroupService.class).followGroup(UrlConfig.ZU_FOLLOW, this.group.getId(), new GroupService.FollowGroupCallback() {
				@Override
				public void onComplete(int status, String msg) {
					// TODO Auto-generated method stub
					if(Service.noticeExceptSuccess(getContext(), status, msg)) {
						if(status==Service.STATUS_NOT_LOGIN){
							Intent intent = new Intent();
							intent.setClass(getContext(), LoginActivity.class);
							getContext().startActivity(intent);
						}
						setText(R.string.follow_this_group);
						return;
					}
					Toast.makeText(getContext(), "关注成功！", Toast.LENGTH_SHORT).show();
					setFollow(true);
				}
			});
		}else if(getResources().getString(R.string.unfollow_this_group).equals(String.valueOf(this.getText()))){
			this.setText("处理中...");
			ServiceFactory.createService(GroupService.class).unfollowGroup(UrlConfig.ZU_UNFOLLOW, this.group.getId(), new GroupService.UnFollowGroupCallback() {
				@Override
				public void onComplete(int status, String msg) {
					// TODO Auto-generated method stub
					if(Service.noticeExceptSuccess(getContext(), status, msg)) {
						if(status==Service.STATUS_NOT_LOGIN){
							Intent intent = new Intent();
							intent.setClass(getContext(), LoginActivity.class);
							getContext().startActivity(intent);
						}
						setText(R.string.unfollow_this_group);
						return;
					}
					Toast.makeText(getContext(), "取消关注成功！", Toast.LENGTH_SHORT).show();
					setFollow(false);
				}
			});
		}
	}
	
	public void setFollow(boolean isFollow){
		if(isFollow){
			this.setText(R.string.unfollow_this_group);
			this.setBackgroundResource(R.drawable.unfollow_group_button);
			this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.plus, 0, 0, 0);
		}else{
			this.setText(R.string.follow_this_group);
			this.setBackgroundResource(R.drawable.follow_group_button);
			this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.add, 0, 0, 0);
		}
	}
}
