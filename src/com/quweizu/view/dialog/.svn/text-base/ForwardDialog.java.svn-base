package com.quweizu.view.dialog;

import zl.android.http.ZLHttpParameters;

import com.quweizu.R;
import com.quweizu.activity.BaseActivity;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Blog;
import com.quweizu.modal.Group;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.service.UserService;
import com.quweizu.view.GroupListView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ForwardDialog extends Dialog implements View.OnClickListener{

	GroupListView groupList;
	Button cancelButton;
	LinearLayout contentContainer;
	
	BaseActivity context;
	Blog blog;
	public ForwardDialog(BaseActivity context,Blog blog) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.blog = blog;
		this._init();
	}
	private void _init(){
		LayoutInflater inflater = this.getLayoutInflater();
		contentContainer = (LinearLayout)inflater.inflate(R.layout.forward_dialog, (ViewGroup)this.context.getRootView(),false);
		
		groupList = (GroupListView)contentContainer.findViewById(R.id.group_list);
		cancelButton  = (Button)contentContainer.findViewById(R.id.cancel);
		
		this.cancelButton.setOnClickListener(this);
		
		Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width=LayoutParams.MATCH_PARENT;
        
        this.setCancelable(false);
        this.setTitle("转发");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        this.setContentView(contentContainer);
        
        groupList.setOnGroupLoadComplete(new GroupListView.OnGroupLoadComplete() {
			@Override
			public void onComplete() {
				contentContainer.findViewById(R.id.group_load_progress).setVisibility(View.GONE);
			}
		});
        groupList.load();
        
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				final Group group = groupList.getGroupByIndex(position);
				AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
				builder.setTitle("确认");
				TextView noticeText = new TextView(getContext());
				noticeText.setTextSize(20);
				noticeText.setText("是否转发至\""+(group.getName()==null||group.getName().equals("")?"默认组":group.getName())+"\"小组？");
				builder.setView(noticeText);
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						_forward(group);
						dialog.dismiss();
					}
				});
				builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==cancelButton){
			this.dismiss();
		}
	}
	private void _forward(Group group){
		ZLHttpParameters params = new ZLHttpParameters();
		params.put("type", "5");
		params.put("target_id", String.valueOf(blog.getId()));
		params.put("target_user_id", String.valueOf(blog.getUid()));
		params.put("refer_id", String.valueOf(blog.getId()));
		params.put("refer_user_id", String.valueOf(blog.getUid()));
		params.put("qid", group.getId());
		ServiceFactory.createService(UserService.class).forward(UrlConfig.REPLY, params, new UserService.ForwardCallback() {
			@Override
			public void onComplete(int status, String msg) {
				// TODO Auto-generated method stub
				if(Service.noticeExceptSuccess(getContext(), status, msg)) return;
				Toast.makeText(getContext(), "转发成功！", Toast.LENGTH_SHORT).show();
				dismiss();
			}
		});
	}
}
