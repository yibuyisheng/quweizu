package com.quweizu.activity;

import com.quweizu.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 包含有webbrowser控件
 * @author yibuyisheng
 *
 */
public class WebActivity extends BaseActivity implements View.OnClickListener{
	WebView webView;
	TextView titleText;
	ProgressBar progressBar;
	Button goForwardButton;
	Button goBackButton;
	Button refreshButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.web_activity);
		
		this.titleText.setTextSize(14);
		this.backButton.setVisibility(View.GONE);
		
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		
		this.webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setInitialScale(25);
		this.webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				if(webView.canGoBack()){
					goBackButton.setTextColor(Color.parseColor("#F7F5F5"));
				}else{
					goBackButton.setTextColor(Color.parseColor("#666666"));
				}
				if(webView.canGoForward()){
					goForwardButton.setTextColor(Color.parseColor("#F7F5F5"));
				}else{
					goForwardButton.setTextColor(Color.parseColor("#666666"));
				}
			}
		});
		this.webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
				WebActivity.this.titleText.setText(title);
			}
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				progressBar.setProgress(newProgress);
			}
		});
		this.webView.loadUrl(url);
	}

	@Override
	protected void _findViews() {
		// TODO Auto-generated method stub
		webView = (WebView)findViewById(R.id.webView);
		titleText = (TextView)findViewById(R.id.title_text);
		progressBar = (ProgressBar)findViewById(R.id.progressbar);
		goForwardButton = (Button)findViewById(R.id.go_forward);
		goBackButton = (Button)findViewById(R.id.go_back);
		refreshButton = (Button)findViewById(R.id.refresh);
	}

	@Override
	protected void _bindEvent() {
		// TODO Auto-generated method stub
		goForwardButton.setOnClickListener(this);
		goBackButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==goForwardButton){
			if(webView.canGoForward()){
				webView.goForward();
			}
		}else if(v==goBackButton){
			if(webView.canGoBack()){
				webView.goBack();
			}
		}else if(v==refreshButton){
			webView.reload();
		}
	}
}
