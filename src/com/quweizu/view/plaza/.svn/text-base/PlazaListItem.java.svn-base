package com.quweizu.view.plaza;

import zl.android.http.ZLHttpParameters;
import zl.android.view.textview.ZLHtmlTextView;

import com.quweizu.R;
import com.quweizu.activity.BaseActivity;
import com.quweizu.activity.BlogActivity;
import com.quweizu.activity.LoginActivity;
import com.quweizu.config.ActivityRequestCode;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Blog;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.service.UserService;
import com.quweizu.utils.QuWeiZuStringHelper;
import com.quweizu.view.PlazaListView;
import com.quweizu.view.blog.BlogImageView;
import com.quweizu.view.dialog.CommentDialog;
import com.quweizu.view.group.PlazaListGroupButton;
import com.quweizu.view.user.PlazaListUserButton;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 广场上的单个条目
 * @author yibuyisheng
 *
 */
public class PlazaListItem extends LinearLayout implements View.OnClickListener{

	public PlazaListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	Blog blog=null;
	public Blog getBlog(){
		return this.blog;
	}
	
	private void _setViewed(boolean isViewed){
		if(isViewed){
			((TextView)this.findViewById(R.id.blog_content)).setTextColor(Color.GRAY);
		}else{
			((TextView)this.findViewById(R.id.blog_content)).setTextColor(Color.BLACK);
		}
	}
	
	private ZLHtmlTextView blogContent = null;
	private ImageView blogImage = null;
	private PlazaListGroupButton groupButton = null;
	private PlazaListUserButton userButton = null;
	private LinearLayout comment = null;
	private LinearLayout like = null;
	private void _init(){
		if(blogContent == null) blogContent = (ZLHtmlTextView)this.findViewById(R.id.blog_content);
		if(blogImage == null) blogImage = (ImageView)this.findViewById(R.id.blog_image);
		if(groupButton == null) groupButton = (PlazaListGroupButton)this.findViewById(R.id.group_btn);
		if(userButton == null) userButton = (PlazaListUserButton)this.findViewById(R.id.user_btn);
		if(comment == null) comment = (LinearLayout)this.findViewById(R.id.comment);
		if(like == null) like = (LinearLayout)this.findViewById(R.id.like);
	}
	public void setBlog(Blog blog,int showKind){
		_init();
		
		this.blog = blog;
		_setViewed(this.blog.isIs_viewed());
		
		String content = "";
		boolean hasContent = true;
		if(blog.getContent() == null || blog.getContent().replace(" ", "").length() == 0) hasContent = false;
		if(blog.getTitle() != null && blog.getTitle().length() != 0){
			content += hasContent ? ("【"+blog.getTitle()+"】") : blog.getTitle();
		}
		if(hasContent){
			content += blog.getContent();
		}
		if(content.length() > 110){
			content = content.substring(0, 110)+"...";
		}
		blogContent.setHtmlText(content);
				
		blogImage.setImageResource(R.drawable.default_image);
		if(blog.getImgs().size()>0){
			if(showKind==PlazaListView.WORDS_FIRST){
				((PlazaImageView)blogImage).setImageUrl(QuWeiZuStringHelper.getImageUrl(blog.getImgs().get(0), 191));
			}else if(showKind==PlazaListView.IMAGE_FIRST){
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 150);
				blogImage.setLayoutParams(params);
				((BlogImageView)blogImage).setBitmap(QuWeiZuStringHelper.getImageUrl(blog.getImgs().get(0),190));
			}
			blogImage.setVisibility(View.VISIBLE);
		}else{
			blogImage.setVisibility(View.GONE);
		}
		
		groupButton.setGroup(this.blog.getGroup());
		userButton.setUser(blog.getCreator());
		
		this.comment.setOnClickListener(this);
		this.like.setOnClickListener(this);
	}
	
	public void startBlogActivity(){
		this.blog.setIs_viewed(true);
		_setViewed(this.blog.isIs_viewed());
		
		Intent intent = new Intent();
		intent.setClass(getContext(), BlogActivity.class);
		intent.putExtra("blog_id", blog.getId());
		((Activity)getContext()).startActivityForResult(intent,ActivityRequestCode.PLAZA_LIST_ITEM_BLOG_ACTIVITY);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		BaseActivity activity = (BaseActivity)this.getContext();
		if(!activity.isUserInfoCached()){
			Intent intent = new Intent();
			intent.setClass(activity, LoginActivity.class);
			activity.startActivity(intent);
		}else{
			if(arg0 == this.comment){
				CommentDialog dlg = new CommentDialog(activity,this.blog);
				dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						//_loadComments(blog.getId(), commentPageIndex);
					}
				});
				dlg.show();
			}else if(arg0 == this.like){
				ZLHttpParameters params = new ZLHttpParameters();
				params.put("blog_id", String.valueOf(this.blog.getId()));
				ServiceFactory.createService(UserService.class).likeIt(UrlConfig.LIKE_IT_URL, params, new UserService.LikeItCallback() {
					@Override
					public void onComplete(int status, String msg) {
						// TODO Auto-generated method stub
						if(Service.noticeExceptSuccess(PlazaListItem.this.getContext(), status, msg)) return;
						Toast.makeText(PlazaListItem.this.getContext(), "赞成功！", Toast.LENGTH_LONG).show();
					}
				});
			}
		}
	}
}
