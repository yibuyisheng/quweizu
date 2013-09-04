package com.quweizu.view.user;

import com.quweizu.R;
import com.quweizu.activity.PlazaActivity;
import com.quweizu.modal.User;
import com.quweizu.view.PlazaListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

/**
 * 广场上的“用户按钮”
 * @author yibuyisheng
 *
 */
public class PlazaListUserButton extends Button implements View.OnClickListener{
	public PlazaListUserButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		_init();
	}
	public PlazaListUserButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		_init();
	}
	private void _init(){
	}
	
	private User user;
	public void setUser(User user){
		this.user = user;
		
		this.setText(this.user.getNick_name());
		this.setOnClickListener(this);
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
