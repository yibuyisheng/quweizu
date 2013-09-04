package com.quweizu.view.user;

import zl.android.http.image_load.LightBitmapLoader;
import com.quweizu.R;
import com.quweizu.activity.PlazaActivity;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.User;
import com.quweizu.view.PlazaListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

/**
 * 用户的头像
 * @author yibuyisheng
 *
 */
public class UserHeadImage extends ImageView implements View.OnClickListener{
	public UserHeadImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		_init();
	}
	public UserHeadImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		_init();
	}
	private void _init(){
		
	}
	
	User user;
	public void setUser(User user){
		this.user=user;
		
		this.setOnClickListener(this);
		
		String head = user.getHead();
		if(head==null || head.equals("")){
			head = UrlConfig.HEAD_NONE;
		}
		LightBitmapLoader.instance().load(head, this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String titleStr = user.getNick_name()+"的分享";
		TextView titleText = (TextView)this.getRootView().findViewById(R.id.title_text);
		if(titleText!=null && titleText.getText().toString().equals(titleStr)){
			Toast.makeText(this.getContext(), "当前就是该用户的分享！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent = new Intent();
		intent.setClass(this.getContext(), PlazaActivity.class);
		intent.putExtra("title", titleStr);
		intent.putExtra("plazaKind", PlazaListView.PLAZA_OTHER);
		Bundle params = new Bundle();
		params.putBundle("user", this.user.toBundle());
		intent.putExtra("params", params);
		this.getContext().startActivity(intent);
	}
}
