package com.quweizu.view;

import java.util.ArrayList;
import java.util.Hashtable;

import zl.android.http.ZLHttpParameters;
import zl.android.view.listview.TouchLoadListView;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.quweizu.R;
import com.quweizu.application.ZLApplication;
import com.quweizu.config.UrlConfig;
import com.quweizu.modal.Blog;
import com.quweizu.modal.Blogs;
import com.quweizu.modal.Group;
import com.quweizu.modal.User;
import com.quweizu.service.BlogService;
import com.quweizu.service.Service;
import com.quweizu.service.ServiceFactory;
import com.quweizu.view.plaza.PlazaListItem;

public class PlazaListView extends TouchLoadListView{
	private ZLApplication app;
	
	private Blogs blogs = Blogs.getInstance();
	
	private ListViewAdapter adapter;
	
	private String id=String.valueOf(System.currentTimeMillis());/*plazaView的唯一标识*/
	private static Hashtable<String,PlazaListView> plazaViews = new Hashtable<String,PlazaListView>();
	
	public PlazaListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_init();
		plazaViews.put(id, this);
	}
	public PlazaListView(Context context) {
		super(context);
		_init();
		plazaViews.put(id, this);
	}
	public static PlazaListView getPlazaViewById(String id){
		return plazaViews.get(id);
	}
	private void _init(){
		app = (ZLApplication)this.getContext().getApplicationContext();
	}	
	private void _addBlogs(ArrayList<Blog> blogs){
		for(Blog blog : blogs){
			this.blogs.append(blog,this);
		}
	}
	
	private final class ListViewAdapter extends BaseAdapter{
		private LayoutInflater inflater = LayoutInflater.from(getContext());
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				if(showKind==WORDS_FIRST)convertView = inflater.inflate(R.layout.plaza_list_item, PlazaListView.this,false);
				else if(showKind==IMAGE_FIRST)convertView = inflater.inflate(R.layout.plaza_list_item_image, PlazaListView.this,false);
			}
			Blog blog = blogs.getByIndex(PlazaListView.this,position);
			((PlazaListItem)convertView).setBlog(blog,showKind);
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PlazaListView.this.performItemClick(v, position, 0);
				}
			});
			return convertView;
		}

		public long getItemId(int position) {
			//return position;
			return 0;
		}

		public Object getItem(int position) {
			return blogs.getByIndex(PlazaListView.this, position);
		}

		public int getCount() {
			return blogs.getPlazaBlogSize(PlazaListView.this);
		}
	}
	
	/**
	 * 看文式
	 */
	public static final int WORDS_FIRST=1;
	/**
	 * 看图式
	 */
	public static final int IMAGE_FIRST=2;
	/**
	 * 默认是看文式
	 */
	private int showKind=WORDS_FIRST;
	
	/**
	 * 广场-最新
	 */
	public static final int PLAZA_NEW=1;
	/**
	 * 广场-热门
	 */
	public static final int PLAZA_HOT=2;
	/**
	 * 广场-分类
	 */
	public static final int PLAZA_CATEGORY=3;
	/**
	 * 广场-子分类
	 */
	public static final int PLAZA_SUB_CATEGORY=4;
	/**
	 * 广场-喜欢
	 */
	public static final int PLAZA_LIKE=5;
	/**
	 * 广场-关注
	 */
	public static final int PLAZA_FAVOR=6;
	/**
	 * 搜索
	 */
	public static final int PLAZA_SEARCH=7;
	/**
	 * 广场-元素
	 */
	public static final int PLAZA_ELE=8;
	/**
	 * 其他人的分享
	 */
	public static final int PLAZA_OTHER=9;
	/**
	 * 组的分享
	 */
	public static final int PLAZA_GROUP=10;
	
	private int plazaKind;
	private boolean isStarted = false;
	public boolean isStarted(){
		return this.isStarted;
	}
	/**
	 * 开始加载blog
	 */
	String url=null;
	ZLHttpParameters httpParams=null;
	
	/**
	 * 开始加载blog，每一个对象只能调用一次该方法。
	 * @param plazaKind
	 * @param showKind
	 * @param bundleParams
	 */
	public void startLoadBlogs(int plazaKind,int showKind,Bundle bundleParams){
		isStarted = true;
		
		this.showKind = showKind;
		httpParams = new ZLHttpParameters();
		this.plazaKind = plazaKind;
		if(this.plazaKind == PLAZA_NEW){
			url = UrlConfig.PLAZA_NEW;
			httpParams.put("skip", "1");
		}else if(this.plazaKind == PLAZA_HOT){
			url = UrlConfig.PLAZA_HOT;
			httpParams.put("create_timestamp", String.valueOf(System.currentTimeMillis()));
		}else if(this.plazaKind == PLAZA_CATEGORY){
			url = UrlConfig.CATEGORY_HOT;
			httpParams.put("cat", bundleParams.getString("uname"));
			httpParams.put("create_timestamp", String.valueOf(System.currentTimeMillis()));
		}else if(this.plazaKind == PLAZA_SUB_CATEGORY){
			url = UrlConfig.CATEGORY_HOT+"/"+bundleParams.getString("uname")+"/"+bundleParams.getString("sub_uname");
			httpParams.put("skip", "1");
		}else if(this.plazaKind==PLAZA_LIKE){
			url = UrlConfig.PLAZA_UC;
			Object selfObj = app.getAttribute("self");
			if(selfObj == null || !(selfObj instanceof User)) return;
			User self = (User)selfObj;
			httpParams.put("user_id", String.valueOf(self.getId()));
			httpParams.put("create_timestamp", String.valueOf(System.currentTimeMillis()));
			httpParams.put("type", "3");
		}else if(this.plazaKind==PLAZA_FAVOR){
			url = UrlConfig.PLAZA_FAVOR;
			Object selfObj = app.getAttribute("self");
			if(selfObj == null || !(selfObj instanceof User)) return;
			httpParams.put("id",String.valueOf(System.currentTimeMillis()));
		}else if(this.plazaKind==PLAZA_SEARCH){
			url = UrlConfig.PLAZA_SEARCH;
			httpParams.put("k", bundleParams.getString("keywords"));
			httpParams.put("page_no", "1");
		}else if(this.plazaKind==PLAZA_ELE){
			url = UrlConfig.CATEGORY_HOT+"/"+bundleParams.getString("uname");
			httpParams.put("skip", "1");
			httpParams.put("ele", bundleParams.getString("ele"));
		}else if(this.plazaKind==PLAZA_OTHER){
			url = UrlConfig.PLAZA_UC;
			User user = User.parseFromBundle(bundleParams.getBundle("user"));
			if(user == null) return;
			httpParams.put("user_id", String.valueOf(user.getId()));
			httpParams.put("create_timestamp", String.valueOf(System.currentTimeMillis()));
			httpParams.put("type", "0");
		}else if(this.plazaKind==PLAZA_GROUP){
			url = UrlConfig.PLAZA_GROUP;
			Group group = Group.parseFromBundle(bundleParams.getBundle("group"));
			if(group==null)return;
			httpParams.put("create_timestamp", String.valueOf(System.currentTimeMillis()));
			httpParams.put("qid", group.getId());
		}else{
			return;
		}
		
		adapter = new ListViewAdapter();
		this.setonRefreshListener(new TouchLoadListView.OnRefreshListener() {
			public void onRefresh() {
				if(PlazaListView.this.plazaKind==PLAZA_HOT || PlazaListView.this.plazaKind==PLAZA_CATEGORY ||
						PlazaListView.this.plazaKind==PLAZA_OTHER || PlazaListView.this.plazaKind==PLAZA_GROUP){
					httpParams.put("create_timestamp", String.valueOf(System.currentTimeMillis()));
				}else if(PlazaListView.this.plazaKind==PLAZA_FAVOR){
					httpParams.put("id", String.valueOf(System.currentTimeMillis()));
				}
				_loadBlogs(url,httpParams,new BlogService.GetBlogsCallback() {
					@Override
					public void onComplete(int status, String msg, ArrayList<Blog> blogs) {
						if(blogs!=null){
							PlazaListView.this.blogs.clearPlazaBlogs(PlazaListView.this);
							_addBlogs(blogs);
							adapter.notifyDataSetChanged();
						}
						PlazaListView.this.onRefreshComplete();
					}
				});
			}
		});
		_loadBlogs(url,httpParams,new BlogService.GetBlogsCallback() {
			@Override
			public void onComplete(int status, String msg, ArrayList<Blog> blogs) {
				if(blogs!=null){
					_addBlogs(blogs);
					adapter.notifyDataSetChanged();
				}
			}
		});
		setAdapter(adapter);
		this.setonLoadListener(new OnLoadListener(){
			@Override
			public void onLoad() {
				_loadBlogs(url,httpParams,new BlogService.GetBlogsCallback() {
					@Override
					public void onComplete(int status, String msg, ArrayList<Blog> blogs) {
						if(blogs!=null){
							_addBlogs(blogs);
							if(blogs.size()==0){
								Toast.makeText(getContext(), "没有更多了！", Toast.LENGTH_SHORT).show();
							}
						}
						PlazaListView.this.onLoadComplete();
					}
				});
			}
		});
		
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(view instanceof PlazaListItem){
					PlazaListItem item = (PlazaListItem)view;
					item.startBlogActivity();
				}
			}
		});
	}
	public void startLoadBlogs(int plazaKind,Bundle bundleParams){
		int showType = PlazaListView.WORDS_FIRST;
		if(plazaKind==PLAZA_HOT){
			if(app.getInfoFromDb("plaza_hot_show_type")!=null){
				showType = Integer.parseInt(app.getInfoFromDb("plaza_hot_show_type"));
			}
		}
		startLoadBlogs(plazaKind, showType, bundleParams);
	}
	
	private boolean isLoading = false;
	private void _loadBlogs(String url,ZLHttpParameters params,final BlogService.GetBlogsCallback cb){
		if(isLoading) return;
		isLoading = true;
		ServiceFactory.createService(BlogService.class).getBlogs(url, params, new BlogService.GetBlogsCallback() {
			@Override
			public void onComplete(int status, String msg, ArrayList<Blog> blogs) {
				isLoading = false;
				if(cb != null) cb.onComplete(status, msg, blogs);
				
				if(BlogService.noticeExceptSuccess(app, status, msg)) {
					app.removeAttribute("self");
					app.removeInfoFromDb("self");
					return;
				}
				
				if(blogs==null || blogs.size()==0){
					Toast.makeText(PlazaListView.this.getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(plazaKind==PLAZA_NEW || plazaKind==PLAZA_HOT || plazaKind==PLAZA_CATEGORY || 
						plazaKind==PLAZA_SUB_CATEGORY || plazaKind==PLAZA_ELE ||
						plazaKind==PLAZA_OTHER || plazaKind==PLAZA_GROUP){
					int index = PlazaListView.this.blogs.getPlazaBlogSize(PlazaListView.this)-1;
					Blog blog = PlazaListView.this.blogs.getByIndex(PlazaListView.this,index);
					if(blog != null)httpParams.put("create_timestamp", String.valueOf(blog.getTime()));
				}else if(plazaKind==PLAZA_LIKE){
					if(blogs.size()>0){
						httpParams.put("create_timestamp", String.valueOf(blogs.get(blogs.size()-1).getTime()));
					}
				}else if(plazaKind==PLAZA_SEARCH){
					if(blogs.size()>0) httpParams.put("page_no", String.valueOf(Integer.parseInt(httpParams.get("page_no"))+1));
				}else if(plazaKind==PLAZA_FAVOR){
					if(blogs.size()>0){
						httpParams.put("id", String.valueOf(blogs.get(blogs.size()-1).getTime()));
					}
				}
			}
		});
	}
	
	/**
	 * 获取该类广场的下一篇blog
	 * @author yibuyisheng
	 *
	 */
	public interface GetNextBlogByIdCallback{void onComplete(int status,String msg,Blog blog);}
	public void getNextBlogById(long blogId,final GetNextBlogByIdCallback cb){
		int index = blogs.getIndexById(this, blogId);
		if(index==-1 || index+1 > blogs.getPlazaBlogSize(this)-1){
			_loadBlogs(url,httpParams,new BlogService.GetBlogsCallback(){
				@Override
				public void onComplete(int status, String msg,ArrayList<Blog> blogs) {
					if(cb!=null) cb.onComplete(status,msg,blogs==null||blogs.size()==0?null:blogs.get(0));
				}
			});
		}else{
			if(cb!=null) cb.onComplete(Service.STATUS_SUCCESS,null,blogs.getByIndex(this, index));
		}
	}
	/**
	 * 获取该类广场的上一篇blog
	 * @param blogId
	 * @return
	 */
	public Blog getPreviousBlogByIndex(long blogId){
		int index = blogs.getIndexById(this, blogId);
		if(index<1 || index==-1){
			return null;
		}
		if(blogs.getPlazaBlogSize(this)-1 < index-1){
			return null;
		}
		return blogs.getByIndex(this, index-1);
	}
}