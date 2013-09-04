package com.quweizu.view.category;

import java.util.ArrayList;

import com.quweizu.modal.Category;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CategoryLinearLayout extends LinearLayout {
	public CategoryLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CategoryLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setCategories(ArrayList<Category> categories){
		this.setOrientation(LinearLayout.VERTICAL);
		LinearLayout layout = null;
		LinearLayout.LayoutParams params;
		for(int i=0,il=categories.size();i<il;i++){
			if(i%2==0){
				layout = new LinearLayout(this.getContext());
				params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
				layout.setLayoutParams(params);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				this.addView(layout);
			}
			CategoryButton categoryButton = new CategoryButton(this.getContext());
			categoryButton.setCategory(categories.get(i));
			params = new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT);
			params.weight=1;
			params.leftMargin=1;
			params.rightMargin=1;
			params.topMargin=1;
			params.bottomMargin=1;
			categoryButton.setLayoutParams(params);
			layout.addView(categoryButton);
		}
	}
}
