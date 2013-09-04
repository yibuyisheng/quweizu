package com.quweizu.view.group;

import com.quweizu.R;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Group;
import com.quweizu.service.GroupService;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 显示组信息
 * @author yibuyisheng
 *
 */
public class GroupInfoLinearLayout extends LinearLayout {
	public GroupInfoLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		_init();
	}
	public GroupInfoLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		_init();
	}
	private void _init(){
		
	}
	
	Group group;
	/**
	 * 
	 * @param group
	 * @param needRequest 是否需要到服务器获取全面的组信息
	 */
	public void setGroup(Group group,boolean needRequest,final GroupService.GetZuInfoCallback cb){
		if(needRequest){
			ServiceFactory.createService(GroupService.class).getZuInfo(UrlConfig.ZU_INFO, group.getId(), new GroupService.GetZuInfoCallback() {
				@Override
				public void onComplete(int status, String msg, Group group) {
					// TODO Auto-generated method stub
					if(Service.noticeExceptSuccess(getContext(), status, msg)) return;
					GroupInfoLinearLayout.this.group = group;
					_show();
					if(cb !=null) cb.onComplete(status, msg, group);
				}
			});
		}else{
			_show();
		}
	}
	
	private void _show(){
		this.setVisibility(View.VISIBLE);
		
		String text = "分享数："+group.getBlog_no()+"    粉丝数："+group.getFans_no();
		TextView groupBriefText = (TextView)this.findViewById(R.id.group_brief);
		groupBriefText.setText(text);
		
		FollowGroupButton followButton = (FollowGroupButton)this.findViewById(R.id.follow_group);
		followButton.setGroup(group);
	}
}
