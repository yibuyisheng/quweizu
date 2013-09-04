package com.quweizu.view.blog;

import zl.android.utils.ZLDateFormat;

import com.quweizu.R;
import com.quweizu.modal.Comment;
import com.quweizu.view.user.UserHeadImage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentItemLinearLayout extends LinearLayout {

	public CommentItemLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setComment(Comment comment){
		UserHeadImage userHeadImageView = (UserHeadImage)this.findViewById(R.id.user_head);
		userHeadImageView.setUser(comment.getUser());
		
		TextView nickNameTextView = (TextView)findViewById(R.id.nick_name);
		nickNameTextView.setText(comment.getUser().getNick_name());
		
		TextView commentTimeTextView = (TextView)findViewById(R.id.comment_time);
		commentTimeTextView.setText(ZLDateFormat.convertDate2Offset(comment.getCreate_timestamp(),System.currentTimeMillis()));
		
		TextView commentTextView = (TextView)findViewById(R.id.comment);
		commentTextView.setText(comment.getComment());
	}
}
