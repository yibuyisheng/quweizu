package com.quweizu.activity;

import zl.android.log.Logger;

import com.quweizu.R;
import com.quweizu.application.QuWeiZuActivityManager;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Group;
import com.quweizu.service.CategoryService;
import com.quweizu.service.GroupService;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.view.PlazaListView;
import com.quweizu.view.group.GroupInfoLinearLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

/**
 * 广场
 * @author yibuyisheng
 *
 */
public class PlazaActivity extends BaseActivity implements View.OnClickListener{
	
	public static final int RESULT_CODE_FOLLOW=1;
	public static final int RESULT_CODE_HOT=2;
	public static final int RESULT_CODE_CATEGORY=3;
	
	PlazaListView plazaListView;
	TextView titleText;
	
	Button followButton;
	Button hotButton;
	Button categoryButton;
	
	GroupInfoLinearLayout groupInfoContainer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.plaza_activity);
		
		titleText.setText(getIntent().getStringExtra("title"));
		final int plazaKind = getIntent().getIntExtra("plazaKind", 0);
		_resetToolBarButtonColor();
		
		int showType = PlazaListView.WORDS_FIRST;
		final Bundle bundleParams = getIntent().getBundleExtra("params");
		if(plazaKind==PlazaListView.PLAZA_GROUP){
			Group group = Group.parseFromBundle(bundleParams.getBundle("group"));
			this.groupInfoContainer.setGroup(group,true,new GroupService.GetZuInfoCallback() {
				@Override
				public void onComplete(int status, String msg, Group group) {
					// TODO Auto-generated method stub
					int showType = PlazaListView.WORDS_FIRST;
					Logger.info("group "+group.getName()+" view_type:"+group.getVtype());
					if(group.getVtype()==0){
						showType=PlazaListView.IMAGE_FIRST;
					}
					plazaListView.startLoadBlogs(plazaKind,showType, bundleParams);
				}
			});
		}else if(plazaKind==PlazaListView.PLAZA_CATEGORY){
			final String uname = bundleParams.getString("uname");
			if(app.getAttribute("plaza_category_"+uname+"_show_type")!=null){
				plazaListView.startLoadBlogs(plazaKind,Integer.parseInt(app.getAttribute("plaza_category_"+uname+"_show_type").toString()), bundleParams);
				Logger.info("locale plaza_category_"+uname+"_show_type:"+app.getAttribute("plaza_category_"+uname+"_show_type"));
			}else{
				ServiceFactory.createService(CategoryService.class).getShowType(UrlConfig.CATEGORY_HOT_SHOW_TYPE, uname, new CategoryService.GetShowTypeCallback() {
					@Override
					public void onComplete(int status, String msg, int showType) {
						// TODO Auto-generated method stub
						if(status==Service.STATUS_SUCCESS){
							plazaListView.startLoadBlogs(plazaKind,showType, bundleParams);
							app.setAttribute("plaza_category_"+uname+"_show_type", showType);
						}else{
							plazaListView.startLoadBlogs(plazaKind, bundleParams);
						}
					}
				});
			}
		}else{
			plazaListView.startLoadBlogs(plazaKind,showType, bundleParams);
		}
	}
	@Override
	protected void _findViews() {
		// TODO Auto-generated method stub
		plazaListView = (PlazaListView)findViewById(R.id.plaza_list_view);
		titleText = (TextView)findViewById(R.id.title_text);
		this.followButton = (Button)findViewById(R.id.follow);
		this.hotButton = (Button)findViewById(R.id.hot);
		this.categoryButton = (Button)findViewById(R.id.category);
		this.groupInfoContainer = (GroupInfoLinearLayout)findViewById(R.id.group_info_container);
	}

	@Override
	protected void _bindEvent() {
		// TODO Auto-generated method stub
		this.followButton.setOnClickListener(this);
		this.hotButton.setOnClickListener(this);
		this.categoryButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==this.followButton){
			QuWeiZuActivityManager.instance().backToMainActivity(1);
		}else if(v==this.hotButton){
			QuWeiZuActivityManager.instance().backToMainActivity(2);
		}else if(v==this.categoryButton){
			QuWeiZuActivityManager.instance().backToMainActivity(3);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==2){
			if(resultCode==RESULT_CODE_CATEGORY){
				this.categoryButton.performClick();
			}else if(resultCode==RESULT_CODE_FOLLOW){
				this.followButton.performClick();
			}else if(resultCode==RESULT_CODE_HOT){
				this.hotButton.performClick();
			}
		}
	}
	
	private void _resetToolBarButtonColor(){
		this.categoryButton.setTextColor(Color.LTGRAY);
		this.followButton.setTextColor(Color.LTGRAY);
		this.hotButton.setTextColor(Color.LTGRAY);
	}

}
