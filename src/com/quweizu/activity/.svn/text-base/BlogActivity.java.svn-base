/**
 * 
 */
package com.quweizu.activity;

import java.util.ArrayList;

import zl.android.http.ZLHttpParameters;
import zl.android.utils.ZLDateFormat;

import com.quweizu.R;
import com.quweizu.config.ActivityRequestCode;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Blog;
import com.quweizu.modal.Blogs;
import com.quweizu.modal.Comment;
import com.quweizu.service.BlogService;
import com.quweizu.service.CommentService;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.service.UserService;
import com.quweizu.view.blog.BlogContentTextView;
import com.quweizu.view.blog.BlogImageView;
import com.quweizu.view.blog.BlogVideoLinearLayout;
import com.quweizu.view.blog.CommentItemLinearLayout;
import com.quweizu.view.dialog.CommentDialog;
import com.quweizu.view.dialog.ForwardDialog;
import com.quweizu.view.user.UserHeadImage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * blog详情
 * @author zhangli
 *
 */
public class BlogActivity extends BaseActivity implements OnClickListener{
	
	Blog blog;
	
	UserHeadImage userHeadImageView;
	TextView userNickNameTextView;
	TextView blogTimeTextView;
	
	LinearLayout blogImagesContainer;
	TextView blogTitleTextView;
	BlogContentTextView blogContentTextView;
	Button originalSiteButton;
	LinearLayout commentsContainer;
	
	LinearLayout commentButton;
	LinearLayout likeButton;
	LinearLayout forwardButton;
	
	Button commentPrePageButton;
	Button commentNextPageButton;
	
	BlogVideoLinearLayout blogVideoLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.blog_activity);
		
		Intent intent = getIntent();
		long blogId = intent.getLongExtra("blog_id", 0);
		blog = Blogs.getInstance().getById(blogId);
		_renderBlog(blog);
		_rerenderBlog(blog);
	}
	
	private void _renderBlog(Blog blog){
		BlogActivity.this.blog = blog;
		userHeadImageView.setUser(blog.getCreator());
		userNickNameTextView.setText(blog.getNick_name());
		blogTimeTextView.setText(ZLDateFormat.convertDate2Offset(blog.getCreate_timestamp(),System.currentTimeMillis()));
		if(blog.getTitle()==null||blog.getTitle().equals("")){
			blogTitleTextView.setVisibility(View.GONE);
		}else{
			blogTitleTextView.setText(blog.getTitle());
		}
		blogContentTextView.setHtmlText(blog.getContent());
		ArrayList<String> imgUrls = blog.getImgs();
		for(int i=0,il=imgUrls.size();i<il;i++){
			BlogImageView imgView = new BlogImageView(BlogActivity.this);
			blogImagesContainer.addView(imgView);
			imgView.setBitmap(imgUrls.get(i).split("#")[0]+"_200");
		}
	}
	private void _rerenderBlog(Blog blog){
		ServiceFactory.createService(BlogService.class).getBlogById(UrlConfig.BLOG_AJAX_GET, blog.getId(), new BlogService.GetBlogByIdCallback() {
			@Override
			public void onComplete(int status, String msg, Blog blog) {
				if(Service.noticeExceptSuccess(BlogActivity.this, status, msg)) return;
				blogContentTextView.setHtmlText(blog.getContent());
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		_loadComments(blog.getId(), commentPageIndex);
	}
	/* (non-Javadoc)
	 * @see com.quweizu.activity.BaseActivity#_findViews()
	 */
	@Override
	protected void _findViews() {
		// TODO Auto-generated method stub
		this.userHeadImageView = (UserHeadImage)findViewById(R.id.user_head);
		this.userNickNameTextView = (TextView)findViewById(R.id.user_nick_name);
		this.blogTimeTextView = (TextView)findViewById(R.id.blog_time);
		this.blogImagesContainer = (LinearLayout)findViewById(R.id.blog_images_container);
		this.blogTitleTextView = (TextView)findViewById(R.id.blog_title);
		this.blogContentTextView = (BlogContentTextView)findViewById(R.id.blog_content);
		originalSiteButton = (Button)findViewById(R.id.original_site);
		commentsContainer = (LinearLayout)findViewById(R.id.commentsContainer);
		commentButton = (LinearLayout)findViewById(R.id.comment);
		likeButton = (LinearLayout)findViewById(R.id.like);
		forwardButton = (LinearLayout)findViewById(R.id.forward);
		commentPrePageButton = (Button)findViewById(R.id.pre_page);
		commentNextPageButton = (Button)findViewById(R.id.next_page);
		//blogVideoLayout = (BlogVideoLinearLayout)findViewById(R.id.blog_video);
	}

	/* (non-Javadoc)
	 * @see com.quweizu.activity.BaseActivity#_bindEvent()
	 */
	@Override
	protected void _bindEvent() {
		// TODO Auto-generated method stub
		originalSiteButton.setOnClickListener(this);
		commentButton.setOnClickListener(this);
		likeButton.setOnClickListener(this);
		forwardButton.setOnClickListener(this);
		this.commentPrePageButton.setOnClickListener(this);
		this.commentNextPageButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==originalSiteButton){
			Intent intent = new Intent();
			intent.setClass(this, WebActivity.class);
			intent.putExtra("url", blog.getUrl());
			this.startActivity(intent);
		}else if(v==commentButton){
			_onCommentButtonClick();
		}else if(v==likeButton){
			_onLikeButtonClick();
		}else if(v==forwardButton){
			_onForwardButtonClick();
		}else if(v==commentPrePageButton){
			_onCommentPrePageButtonClick();
		}else if(v==commentNextPageButton){
			_onCommentNextPageButtonClick();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==ActivityRequestCode.COMMENT_BUTTON_REQUEST_LOGIN_ACTIVITY){
			/*comment LoginActivity*/
			if(resultCode==LoginActivity.LOGIN_STATUS_SUCCESS){
				_showCommentDlg();
			}
		}else if(requestCode == ActivityRequestCode.LIKE_BUTTON_REQUEST_LOGIN_ACTIVITY){
			/*like LoginActivity*/
			if(resultCode==LoginActivity.LOGIN_STATUS_SUCCESS){
				_like();
			}
		}else if(requestCode == ActivityRequestCode.FORWARD_BUTTON_REQUEST_LOGIN_ACTIVITY){
			/*forward LoginActivity*/
			if(resultCode==LoginActivity.LOGIN_STATUS_SUCCESS){
				_showForward();
			}
		}else if(requestCode==ActivityRequestCode.PLAZA_ACTIVITY){
			this.setResult(resultCode);
			this.finish();
		}
	}
	private void _showCommentDlg(){
		CommentDialog dlg = new CommentDialog(this,this.blog);
		dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				_loadComments(blog.getId(), commentPageIndex);
			}
		});
		dlg.show();
	}
	private void _onCommentButtonClick(){
		if(!this.isUserInfoCached()){
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			this.startActivityForResult(intent, ActivityRequestCode.COMMENT_BUTTON_REQUEST_LOGIN_ACTIVITY);
		}else{
			_showCommentDlg();
		}
	}
	private void _like(){
		ZLHttpParameters params = new ZLHttpParameters();
		params.put("blog_id", String.valueOf(this.blog.getId()));
		ServiceFactory.createService(UserService.class).likeIt(UrlConfig.LIKE_IT_URL, params, new UserService.LikeItCallback() {
			@Override
			public void onComplete(int status, String msg) {
				// TODO Auto-generated method stub
				if(Service.noticeExceptSuccess(BlogActivity.this, status, msg)) return;
				Toast.makeText(BlogActivity.this, "赞成功！", Toast.LENGTH_LONG).show();
			}
		});
	}
	private void _onLikeButtonClick(){
		if(!this.isUserInfoCached()){
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			this.startActivityForResult(intent, ActivityRequestCode.LIKE_BUTTON_REQUEST_LOGIN_ACTIVITY);
		}else{
			_like();
		}
	}
	private void _showForward(){
		ForwardDialog dlg = new ForwardDialog(this,this.blog);
		dlg.show();
	}
	private void _onForwardButtonClick(){
		if(!this.isUserInfoCached()){
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			this.startActivityForResult(intent, ActivityRequestCode.FORWARD_BUTTON_REQUEST_LOGIN_ACTIVITY);
		}else{
			_showForward();
		}
	}

	
	private int commentPageIndex = 1;
	private int commentPageSize = 15;
	private void _loadComments(long blogId,int page){
		ServiceFactory.createService(CommentService.class).getCommentListByPageAndBlogId(UrlConfig.COMMENTS, page, blogId, new CommentService.GetCommentListByPageAndBlogIdCallback() {
			@Override
			public void onComplete(int status, String msg, ArrayList<Comment> comments,int replyNo) {
				if(Service.noticeExceptSuccess(BlogActivity.this, status, msg)) return;
				_renderComments(comments);
				
				int totalPage = 0;
				if(replyNo % commentPageSize==0) totalPage = replyNo / commentPageSize;
				else totalPage = replyNo / commentPageSize + 1;
				
				if(commentPageIndex<=1){
					commentPrePageButton.setVisibility(View.GONE);
					if(totalPage>commentPageIndex) commentNextPageButton.setVisibility(View.VISIBLE);
				}else if(commentPageIndex < totalPage){
					commentPrePageButton.setVisibility(View.VISIBLE);
					commentNextPageButton.setVisibility(View.VISIBLE);
				}else{
					commentPrePageButton.setVisibility(View.VISIBLE);
					commentNextPageButton.setVisibility(View.GONE);
				}
			}
		});
	}
	private void _renderComments(ArrayList<Comment> comments){
		while(commentsContainer.getChildCount()>2){
			commentsContainer.removeViewAt(0);
		}
		
		LayoutInflater inflater = LayoutInflater.from(this);
		for(int i=comments.size()-1;i>=0;i--){
			Comment comment = comments.get(i);
			CommentItemLinearLayout item = (CommentItemLinearLayout)inflater.inflate(R.layout.comment_item, commentsContainer,false);
			commentsContainer.addView(item,0);
			item.setComment(comment);
		}
	}
	private void _onCommentPrePageButtonClick(){
		commentPageIndex--;
		_loadComments(blog.getId(), commentPageIndex);
	}
	private void _onCommentNextPageButtonClick(){
		commentPageIndex++;
		_loadComments(blog.getId(), commentPageIndex);
	}
}
