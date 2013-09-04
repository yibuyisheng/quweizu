package com.quweizu.view.category;

import com.quweizu.R;
import com.quweizu.activity.PlazaActivity;
import com.quweizu.config.ActivityRequestCode;
import com.quweizu.modal.Category;
import com.quweizu.view.PlazaListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

public class CategoryButton extends Button implements View.OnClickListener{

	public CategoryButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private Category category;
	
	public void setCategory(Category category){
		this.category = category;
		this.setText(this.category.getName());
		this.setBackgroundResource(R.drawable.category_button);
		this.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		Intent intent = new Intent();
		intent.setClass(getContext(), PlazaActivity.class);
		intent.putExtra("plazaKind", PlazaListView.PLAZA_CATEGORY);
		intent.putExtra("title", category.getName());
		Bundle params = new Bundle();
		params.putString("uname",category.getUname());
		intent.putExtra("params", params);
		if(this.getContext() instanceof Activity){
			((Activity)this.getContext()).startActivityForResult(intent, ActivityRequestCode.PLAZA_ACTIVITY);
		}else{
			this.getContext().startActivity(intent);
		}
	}
}
