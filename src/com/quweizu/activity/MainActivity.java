package com.quweizu.activity;

import java.io.File;
import java.util.ArrayList;

import zl.android.local.sdcard.ZLLocalSDCard;
import zl.android.log.Logger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import android.view.View;

import com.quweizu.R;
import com.quweizu.application.QuWeiZuDestroyer;
import com.quweizu.application.QuWeiZuInitializer;
import com.quweizu.config.ActivityRequestCode;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Category;
import com.quweizu.modal.User;
import com.quweizu.service.CategoryService;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.view.PlazaListView;
import com.quweizu.view.category.CategoryLinearLayout;
import com.quweizu.view.dialog.SplashDialog;
import com.quweizu.activity.LoginActivity;
import com.quweizu.activity.MainActivity;
import com.quweizu.activity.PhotoUploadActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{
	
	FrameLayout contentContainer;
	
	Button followButton;
	Button hotButton;
	Button categoryButton;
	Button takePhotoButton;
	
	//TextView titleText;
	
	PlazaListView hotListView;
	PlazaListView followListView;
	ScrollView categoryContainer;
	
	ProgressBar categoryProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		hotListView = new PlazaListView(this);
		followListView = new PlazaListView(this);
		
		//final ProgressDialog dlg = ProgressDialog.show(this, null, "应用初始化中...",false,false);
		final SplashDialog dlg = new SplashDialog();
		dlg.show(this);
		new QuWeiZuInitializer().initialize(app, new QuWeiZuInitializer.OnInitializeComplete() {
			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				dlg.close();
				
				if(isUserInfoCached()){
					followButton.performClick();
				}else{
					hotButton.performClick();
				}
			}
		});
		
		//AppConnect.getInstance(this);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		new QuWeiZuDestroyer().destroy(app);
		
		//AppConnect.getInstance(this).finalize();
	}

	@Override
	protected void _findViews() {
		this.contentContainer = (FrameLayout)findViewById(R.id.contentContainer);
		this.followButton = (Button)findViewById(R.id.follow);
		this.hotButton = (Button)findViewById(R.id.hot);
		this.categoryButton = (Button)findViewById(R.id.category);
		categoryContainer = (ScrollView)findViewById(R.id.categoryContainer);
		categoryProgressBar = (ProgressBar)findViewById(R.id.category_load_progress);
		takePhotoButton = (Button)findViewById(R.id.take_photo);
	}

	@Override
	protected void _bindEvent() {
		this.hotButton.setOnClickListener(this);
		this.followButton.setOnClickListener(this);
		this.categoryButton.setOnClickListener(this);
		this.takePhotoButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==hotButton){
			_onHotButtonClick();
		}else if(v==followButton){
			_onFollowButtonClick();
		}else if(v==categoryButton){
			_onCategoryButtonClick();
		}else if(v == this.takePhotoButton){
			_onTakePhotoButtonClick();
		}
	}
	
	private void _onTakePhotoButtonClick(){
		if(!ZLLocalSDCard.instance().hasSDCard()){
			Toast.makeText(MainActivity.this,"请插入SD卡", Toast.LENGTH_LONG).show();
			return;
		}
		if(!isUserInfoCached()){
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LoginActivity.class);
			MainActivity.this.startActivity(intent);
			return;
		}
		
        String[] choices={"拍摄照片","从相册里选择"};  
        AlertDialog dialog = new AlertDialog.Builder(this)  
                 .setTitle("选择照片")  
                 .setItems(choices, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(which==0){
							Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				        	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(ZLLocalSDCard.instance().getRealPath("/quweizu/tmp.jpg"))));
				        	startActivityForResult(intent, ActivityRequestCode.TAKE_PHOTO_MAIN_ACTIVITY);
						}else{
							Intent intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);  
							startActivityForResult(intent, ActivityRequestCode.LOCAL_CHOOSE_MAIN_ACTIVITY); 
						}
						dialog.dismiss();
					}
				}).create();  
        dialog.show(); 
	}
	private void _resetToolBarButtonColor(){
		this.categoryButton.setTextColor(Color.LTGRAY);
		this.followButton.setTextColor(Color.LTGRAY);
		this.hotButton.setTextColor(Color.LTGRAY);
	}
	private void _onCategoryButtonClick(){
		_resetToolBarButtonColor();
		this.categoryButton.setTextColor(Color.WHITE);
		
		if(followListView.isStarted()) followListView.setVisibility(View.GONE);
		if(hotListView.isStarted()) hotListView.setVisibility(View.GONE);
		categoryContainer.setVisibility(View.VISIBLE);
		if(categoryContainer.getChildCount()==0){
			categoryProgressBar.setVisibility(View.VISIBLE);
			ServiceFactory.createService(CategoryService.class).getCategoriesFromServer(UrlConfig.CATEGORY, new CategoryService.GetCategoriesFromServerCallback() {
				@Override
				public void onComplete(int status, String msg, String dataCategories,ArrayList<Category> categories) {
					categoryProgressBar.setVisibility(View.GONE);
					if(Service.noticeExceptSuccess(MainActivity.this, status, msg)) return;
					CategoryLinearLayout categoryLinearLayout = new CategoryLinearLayout(MainActivity.this);
					categoryContainer.addView(categoryLinearLayout);
					categoryLinearLayout.setCategories(categories);
				}
			});
		}
	}
	private void _onHotButtonClick(){
		_resetToolBarButtonColor();
		this.hotButton.setTextColor(Color.WHITE);
		
		if(followListView.isStarted()) followListView.setVisibility(View.GONE);
		categoryContainer.setVisibility(View.GONE);
		if(!hotListView.isStarted()){
			contentContainer.addView(hotListView);
			hotListView.startLoadBlogs(PlazaListView.PLAZA_HOT, null);
		}else{
			hotListView.setVisibility(View.VISIBLE);
		}
	}
	private void _showFollow(){
		_resetToolBarButtonColor();
		this.followButton.setTextColor(Color.WHITE);
		
		if(hotListView.isStarted()) hotListView.setVisibility(View.GONE);
		categoryContainer.setVisibility(View.GONE);
		if(!followListView.isStarted()){
			contentContainer.addView(followListView);
			
			int showType = ((User)app.getAttribute("self")).getVtype();
			if(showType==0) showType = PlazaListView.IMAGE_FIRST;
			else if(showType==1) showType = PlazaListView.WORDS_FIRST;
			followListView.startLoadBlogs(PlazaListView.PLAZA_FAVOR,showType, null);
		}else{
			followListView.setVisibility(View.VISIBLE);
		}
	}
	private void _onFollowButtonClick(){
		if(!this.isUserInfoCached()){
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			this.startActivityForResult(intent, ActivityRequestCode.FORWARD_BUTTON_REQUEST_LOGIN_ACTIVITY);
		}else{
			_showFollow();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==ActivityRequestCode.FORWARD_BUTTON_REQUEST_LOGIN_ACTIVITY){
			/*follow LoginActivity*/
			if(resultCode==LoginActivity.LOGIN_STATUS_SUCCESS){
				_showFollow();
			}
		}else if(requestCode==ActivityRequestCode.PLAZA_ACTIVITY || requestCode==ActivityRequestCode.PLAZA_LIST_ITEM_BLOG_ACTIVITY){
			/*PlazaActivity*/
			if(resultCode==PlazaActivity.RESULT_CODE_CATEGORY){
				categoryButton.performClick();
			}else if(resultCode==PlazaActivity.RESULT_CODE_FOLLOW){
				followButton.performClick();
			}else if(resultCode==PlazaActivity.RESULT_CODE_HOT){
				hotButton.performClick();
			}
		}else if(requestCode == ActivityRequestCode.TAKE_PHOTO_MAIN_ACTIVITY){
			if(resultCode!=-1) return;
			Intent intent = new Intent();
			intent.setClass(this, PhotoUploadActivity.class);
			intent.putExtra("img_path", ZLLocalSDCard.instance().getRealPath("/quweizu/tmp.jpg"));
			this.startActivity(intent);
		}else if(requestCode == ActivityRequestCode.LOCAL_CHOOSE_MAIN_ACTIVITY){
			if(data==null) return;
			Uri uri = data.getData();  
            try {  
                String[] pojo = {MediaStore.Images.Media.DATA};  
                Cursor cursor = getContentResolver().query(uri, pojo, null, null,null);  
                if(cursor!=null){  
                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);  
                    cursor.moveToFirst();  
                    String path = cursor.getString(colunm_index); 
                    if(path.endsWith("jpg")||path.endsWith("png")){  
                    	Intent intent = new Intent();
            			intent.setClass(this, PhotoUploadActivity.class);
            			intent.putExtra("img_path", path);
            			this.startActivity(intent);
                    }else{
                    	Toast.makeText(MainActivity.this, "错误的图片格式！", Toast.LENGTH_SHORT).show();
                    }  
                }else{
                	Toast.makeText(MainActivity.this, "无法选择图片！", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {  
            	Logger.error(e);
            	Toast.makeText(MainActivity.this, "出现错误啦，无法选择图片！"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }  
		}
	}
	
	
	
	public Button getFollowButton() {
		return followButton;
	}
	public Button getHotButton() {
		return hotButton;
	}
	public Button getCategoryButton() {
		return categoryButton;
	}
}
