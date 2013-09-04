package com.quweizu.config;

/**
 * 所有的用的到的后端接口
 * @author yibuyisheng
 *
 */
public class UrlConfig {
	public static final String HOST = "http://www.quweizu.com";
	
	/**
	 * 广场-最新
	 */
	public static final String PLAZA_NEW            	=HOST+"/plaza_ajax/back";
	/**
	 * 广场-热门
	 */
	public static final String PLAZA_HOT            	=HOST+"/plaza_ajax/hot";
	/**
	 * 用户分享
	 */
	public static final String PLAZA_UC           		=HOST+"/ucAjax/back";
	/**
	 * 用户关注
	 */
	public static final String PLAZA_FAVOR          	=HOST+"/favor/back";
	/**
	 * 搜索
	 */
	public static final String PLAZA_SEARCH         	=HOST+"/search/blog.json";
	/**
	 * 组分享
	 */
	public static final String PLAZA_GROUP        		=HOST+"/zuAjax/zu_blog";
	/**
	 * 所有分类信息
	 */
	public static final String CATEGORY             	=HOST+"/zuAjax/categories";
	/**
	 * 广场-热门分类
	 */
	public static final String CATEGORY_HOT         	=HOST+"/catAjax";
	/**
	 * blog的评论
	 */
	public static final String COMMENTS             	=HOST+"/blog_ajax/get_blog_comment";
	/**
	 * blog回复（评论）
	 */
	public static final String REPLY                	=HOST+"/page/reply";
	/**
	 * 登录
	 */
	public static final String LOGIN_URL            	=HOST+"/ouAuth/login_";
	/**
	 * 喜欢
	 */
	public static final String LIKE_IT_URL          	=HOST+"/page/like_it";
	/**
	 * 获取用户的组信息
	 */
	public static final String PUB_INFO_JSON        	=HOST+"/widget/pub_info.json";
	/**
	 * 获取用户的信息
	 */
	public static final String GET_SELF             	=HOST+"/ucAjax/user_info";
	public static final String SEARCH               	=HOST+"/search/blog.json";
	/**
	 * 无头像时显示的默认头像
	 */
	public static final String HEAD_NONE            	="http://static.cutool.com/site/images/no.jpg";
	/**
	 * 子分类信息
	 */
	public static final String SUB_CATEGORY         	=HOST+"/catAjax/sub/get";
	public static final String PUB_INFO             	=HOST+"/widget/pub_info";
	/**
	 * 注册
	 */
	public static final String REGISTE_URL          	=HOST+"/ouAuth/reg_";
	/**
	 * 验证码
	 */
	public static final String VCODE_URL            	=HOST+"/vcode";
	/**
	 * 所有元素
	 */
	public static final String ELES_GET             	=HOST+"/catAjax/eles/get";
	/**
	 * 获取某一骗blog信息
	 */
	public static final String BLOG_AJAX_GET        	=HOST+"/blog_ajax/get";
	/**
	 * 关注组
	 */
	public static final String ZU_FOLLOW        		=HOST+"/zuAjax/follow";
	/**
	 * 取消关注组
	 */
	public static final String ZU_UNFOLLOW				=HOST+"/zuAjax/unfollow";
	/**
	 * 用户是否关注某个组
	 */
	public static final String HAS_FOLLOW				=HOST+"/zuAjax/has_follow";
	/**
	 * 组信息
	 */
	public static final String ZU_INFO					=HOST+"/zuAjax/zu";
	/**
	 * 广场-热门的显示方式
	 */
	public static final String PLAZA_HOT_VIEW_TYPE		=HOST+"/plaza_ajax/hot/show_type";
	/**
	 * 广场-最新的显示方式
	 */
	public static final String PLAZA_NEW_VIEW_TYPE		=HOST+"/plaza_ajax/new/show_type";
	/**
	 * 热门分类显示方式
	 */
	public static final String CATEGORY_HOT_SHOW_TYPE	=HOST+"/catAjax/hot/show_type";
	/**
	 * 分享（发布）
	 */
	public static final String AJAX_SHARE				=HOST+"/page/ajax/share";
	/**
	 * 上传图片
	 */
	public static final String CUTOOL_IMAGE_UPLOAD	    ="http://img.quweizu.com/cutool_img/upload.json";
}
