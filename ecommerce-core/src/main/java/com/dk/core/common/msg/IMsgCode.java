package com.dk.core.common.msg;

public interface IMsgCode
{
	
	/**信息代码**/
	
	/**********************success***********************/
	public static final String MSGCODE_SUCCESS ="0";
	
	public static final String MSGCODE_SUCCESS_ADD ="01";//增加成功

	public static final String MSGCODE_SUCCESS_DELETE ="02";//删除成功
	
	public static final String MSGCODE_SUCCESS_UPDATE ="03";//更新成功

	public static final String MSGCODE_SUCCESS_QUERY ="04";//查询成功
	
	
	/**********************error***********************/
	
	public static final String MSGCODE_ERROR ="-1";
	
	public static final String MSGCODE_ERROR_ADD ="-101";//增加失败

	public static final String MSGCODE_ERROR_DELETE ="-102";//删除失败
	
	public static final String MSGCODE_ERROR_UPDATE ="-103";//更新失败
	
	public static final String MSGCODE_ERROR_QUERY ="-104";//查询失败
	
	public static final String MSGCODE_ERROR_MSGDUPLICATE ="-105";//消息重复添加
	
	public static final String MSGCODE_ERROR_ENTITY_EXISTS="-200";//数据库已经存在实体
	
	public static final String MSGCODE_ERROR_ENTITY_NOTEXISTS="-201";//数据库不存在实体
	
	public static final String MSGCODE_ERROR_ENTITY_EXISTS_MORE_THAN_ONCE="-202";//数据库存在多条
	
	public static final String MSGCODE_ERROR_DB_CONNECTION="-203";//数据库连接异常
	
	public static final String MSGCODE_ERROR_USER_NOT_AUTHORIZED="-204";//用户未授权
	
	public static final String MSGCODE_ERROR_QUERYSTR_SPLITY="-205";//查询字段分离错误

	public static final String MSGCODE_ERROR_QUERYMAPPING="-206";//查询字段映射错误
	
	public static final String MSGCODE_ERROR_OTHER="-999"; //其它错误
	
}
