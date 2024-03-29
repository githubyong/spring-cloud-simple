package org.example.auth.service;

public interface SecurityConstants {
	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";

	/**
	 * oauth 相关前缀
	 */
	String OAUTH_PREFIX = "scs_oauth:";

	String CLIENT_TOKEN_PREFIX = "scs_oauth:client_token:";

	/**
	 * 内部
	 */
	String FROM_IN = "FROM_IN";

	/**
	 * 标志
	 */
	String FROM = "from";

	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";

	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";
	/**
	 * oauth_client_details 表的字段，不包括client_id、client_secret
	 */
	String CLIENT_FIELDS = "client_id,CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
		+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
		+ "refresh_token_validity, additional_information, autoapprove";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
		+ " from oauth_client_details";

	/**
	 * 默认的查询语句
	 */
	String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	/**
	 * 按条件client_id 查询
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

	/***
	 * 资源服务器默认bean名称
	 */
	String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

	/**
	 * 用户ID字段
	 */
	String DETAILS_USER_ID = "user_id";

	/**
	 * 用户名字段
	 */
	String DETAILS_USERNAME = "username";

	/**
	 * 权限集合
	 */
	String PFEATURES = "pfeatures";

	/**
	 * 数据权限集合
	 */
	String CONDITION_PAIRS = "condition_pairs";


	String CONDITION_MAP = "condition_map";

	/**
	 * 用户部门字段
	 */
	String DETAILS_DEPT_ID = "dept_id";

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";
}
