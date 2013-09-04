package com.quweizu.modal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.util.SparseArray;

import com.quweizu.view.PlazaListView;

/**
 * 应用中blog管理器，所有的blog都应该缓存在这个里面
 * @author yibuyisheng
 *
 */
public class Blogs{
	private static Blogs blogs = null;
	private Blogs(){}
	public static Blogs getInstance(){
		if(blogs == null){
			blogs = new Blogs();
		}
		return blogs;
	}
	
	private SparseArray<List<Blog>> plazaListView_blogList_map = new SparseArray<List<Blog>>();
	private Map<Long,Blog> id_blog_map = new HashMap<Long,Blog>();
	
	private void _add_to_plazaListView_blogList_map(PlazaListView plazaListView,Blog blog){
		List<Blog> blogs = plazaListView_blogList_map.get(plazaListView.hashCode());
		if(blogs == null){
			blogs = new LinkedList<Blog>();
			plazaListView_blogList_map.put(plazaListView.hashCode(), blogs);
		}
		blogs.add(blog);
	}
	private void _add_id_blog_map(Blog blog){
		this.id_blog_map.put(blog.getId(), blog);
	}
	public Blogs append(Blog blog,PlazaListView plazaListView){
		if(blog == null) return this;
		this._add_id_blog_map(blog);
		if(plazaListView != null) this._add_to_plazaListView_blogList_map(plazaListView, blog);
		return this;
	}
	
	public Blog getById(long id){
		return this.id_blog_map.get(id);
	}
	
	public Blog getByIndex(PlazaListView plazaListView,int index){
		if(plazaListView == null) return null;
		List<Blog> blogs = this.plazaListView_blogList_map.get(plazaListView.hashCode());
		if(blogs == null) return null;
		return blogs.get(index);
	}
	
	public int getPlazaBlogSize(PlazaListView plazaListView){
		if(plazaListView == null) return 0;
		List<Blog> blogs = this.plazaListView_blogList_map.get(plazaListView.hashCode());
		if(blogs == null) return 0;
		return blogs.size();
	}
	
	public void clearPlazaBlogs(PlazaListView plazaListView){
		if(plazaListView == null) return;
		List<Blog> blogs = this.plazaListView_blogList_map.get(plazaListView.hashCode());
		if(blogs == null) return;
		blogs.clear();
	}
	
	public int getIndexById(PlazaListView plazaListView,long blogId){
		if(plazaListView == null) return -1;
		List<Blog> blogs = this.plazaListView_blogList_map.get(plazaListView.hashCode());
		if(blogs == null) return -1;
		
		int index = -1;
		for(int i=0,il=blogs.size();i<il;i++){
			if(blogs.get(i).getId() == blogId){
				index = i;
				break;
			}
		}
		return index;
	}
	
}
