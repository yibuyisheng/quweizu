package com.quweizu.view.group;

import com.quweizu.R;
import com.quweizu.activity.PlazaActivity;
import com.quweizu.modal.Group;
import com.quweizu.view.PlazaListView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlazaListGroupButton extends Button implements View.OnClickListener{
	public PlazaListGroupButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		_init();
	}
	public PlazaListGroupButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		_init();
	}
	
	private void _init(){
	}
	
	
	private Group group;
	public void setGroup(Group group){
		this.group = group;
		
		this.setText(this.group.getName());
		this.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String titleStr = group.getName();
		TextView titleText = (TextView)this.getRootView().findViewById(R.id.title_text);
		if(titleText!=null && titleText.getText().toString().equals(titleStr)){
			Toast.makeText(this.getContext(), "您当前正在该小组！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent = new Intent();
		intent.setClass(this.getContext(), PlazaActivity.class);
		intent.putExtra("title", titleStr);
		intent.putExtra("plazaKind", PlazaListView.PLAZA_GROUP);
		Bundle params = new Bundle();
		params.putBundle("group", this.group.toBundle());
		intent.putExtra("params", params);
		this.getContext().startActivity(intent);
	}
}
