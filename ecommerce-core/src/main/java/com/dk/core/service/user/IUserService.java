package com.dk.core.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dk.core.common.exception.CoreException;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.common.base.service.IGenericService;
import com.dk.core.common.exception.impl.PortalException;
import com.dk.core.common.exception.impl.PwdNotCorrectException;
import com.dk.core.model.bean.User;

public interface IUserService extends IGenericService<User, String>{

	/**
	 * 注册
	 * @param user
	 */
	String register(User user) throws CoreException;

	/**
	 * 分页查找
	 * 
	 * @param loginName
	 * @return
	 */
	Page<User> pageFind(SearchBean[] searchBeans,Pageable pageable) throws CoreException;

	/**
	 * 查询详细信息
	 * 
	 * @param loginName
	 * @return
	 */
	User find(String loginName) throws CoreException;

	/**
	 * 删除个人信息
	 * 
	 * @param loginName
	 */
	void delete(String loginName);

	/**
	 * 激活个人信息
	 * 
	 * @param loginName
	 */
	void active(String loginName) throws CoreException;

	/**
	 * 更新信密码
	 * 
	 * @param user
	 * @throws PortalException
	 */
	String updatePassword(String loginName, String oldPassword, String newPassword)
			throws PortalException;

	/**
	 * 登陆
	 * 
	 * @param loginName
	 * @return
	 */
	User loginCheck(String loginName, String password) throws PortalException,PwdNotCorrectException;
	
	
	/**
	 * 修改个人信息
	 * @param user
	 * @return
	 */
	void updateSelf(User user) throws CoreException;


	/**
	 * 停用个人信息
	 * 
	 * @param loginName
	 */
	void inactive(String loginName) throws PortalException;
}
