package com.quweizu.view.blog;

import zl.android.http.image_load.BitmapLoader;
import zl.android.http.image_load.LightBitmapLoader;
import zl.android.movie.ZLMovie;
import zl.android.view.imageview.MemorySafeImageView;

import com.quweizu.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BlogVideoLinearLayout extends LinearLayout{

	public BlogVideoLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public BlogVideoLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	private MemorySafeImageView blogVideoImgView;
	private String video_thumbnail;
	private String video;
	/**
	 * 
	 * @param video_thumbnail 缩略图
	 * @param video 播放地址
	 */
	public void setVideo(String video_thumbnail,String video){
		this.video_thumbnail = video_thumbnail;
		this.video = video;
		
		blogVideoImgView = (MemorySafeImageView)this.findViewById(R.id.blog_video_image);
		LightBitmapLoader.instance().load(video_thumbnail, blogVideoImgView, new BitmapLoader.OnImageLoadedSuccess() {
			@Override
			public void onImageLoadedSuccess(String url, Bitmap bitmap, ZLMovie movie,int imageType, ImageView imgView) {
				// TODO Auto-generated method stub
				blogVideoImgView.setImageBitmap(bitmap);
				
				_bindEvent();
			}
		}, new BitmapLoader.OnImageLoadedFailed(){
			@Override
			public void onImageLoadedFailed(String url, ImageView imgView,
					String errorMsg, Exception e) {
				// TODO Auto-generated method stub
				Toast.makeText(BlogVideoLinearLayout.this.getContext(), "视频缩略图加载失败！", Toast.LENGTH_SHORT).show();
				
				_bindEvent();
			}
		});
	}
	
	private void _bindEvent(){
		blogVideoImgView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(video);
			    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			    intent.setType("video/*");
			    BlogVideoLinearLayout.this.getContext().startActivity(intent);
			}
		});
	}
}
