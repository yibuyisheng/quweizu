package com.quweizu.view.dialog;

import zl.android.http.ZLHttpParameters;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quweizu.R;
import com.quweizu.activity.BaseActivity;
import com.quweizu.config.UrlConfig;
import zl.android.log.Logger;
import com.quweizu.modal.Blog;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.service.UserService;
public class CommentDialog extends Dialog implements View.OnClickListener{

	EditText commentEditText;
	LinearLayout contentContainer;
	
	Button okButton;
	Button cancelButton;
	TextView commentLoadingText;
	
	BaseActivity context;
	
	Blog blog;
	
	public CommentDialog(BaseActivity context,Blog blog) {
		// TODO Auto-generated constructor stub
		super(context);
		this.context = context;
		this.blog = blog;
		
		_init();
	}
	
	private void _init(){
		LayoutInflater inflater = this.getLayoutInflater();
		contentContainer = (LinearLayout)inflater.inflate(R.layout.comment_dialog, (ViewGroup)context.getRootView(),false);
		this.setContentView(contentContainer,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		this.setTitle(R.string.comment);
		
		Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width=LayoutParams.MATCH_PARENT;
        
        this.okButton = (Button)contentContainer.findViewById(R.id.ok);
        this.cancelButton = (Button)contentContainer.findViewById(R.id.cancel);
        commentEditText = (EditText)contentContainer.findViewById(R.id.comment);
        commentLoadingText = (TextView)contentContainer.findViewById(R.id.comment_loading);
        
        this.setCancelable(false);
        
        this.okButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);
	}
	
	public void show() {
		super.show();
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==this.okButton){
			String commentStr = String.valueOf(commentEditText.getText());
			if(commentStr.equals("null") || commentStr.equals("")){
				Toast.makeText(getContext(), "请输入评论内容！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			ZLHttpParameters ap = new ZLHttpParameters();
			ap.put("type", String.valueOf(6));
			ap.put("content", commentStr);
			ap.put("target_id", String.valueOf(this.blog.getId()));
			ap.put("target_user_id", String.valueOf(this.blog.getUid()));
			ap.put("refer_id", String.valueOf(this.blog.getId()));
			ap.put("refer_user_id", String.valueOf(this.blog.getUid()));
			
			Logger.info("----comment params:"+ap.toString());
			
			((View)this.okButton.getParent()).setVisibility(View.GONE);
			commentLoadingText.setVisibility(View.VISIBLE);
			ServiceFactory.createService(UserService.class).reply(UrlConfig.REPLY, ap, new UserService.ReplyCallback() {
				@Override
				public void onComplete(int status, String msg) {
					if(Service.noticeExceptSuccess(CommentDialog.this.getContext(), status, msg)){
						((View)okButton.getParent()).setVisibility(View.VISIBLE);
						commentLoadingText.setVisibility(View.GONE);
						return;
					}
					Toast.makeText(CommentDialog.this.getContext(), "评论成功！", Toast.LENGTH_SHORT).show();
					CommentDialog.this.dismiss();
				}
			});
		}else if(v==this.cancelButton){
			this.dismiss();
		}
	}
}
