package com.dk.core.service.user.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dk.core.common.constant.IConstants;
import com.dk.core.common.exception.CoreException;
import com.dk.core.common.exception.PortalErrorCode;
import com.dk.core.common.utils.EncryptionUtils;
import com.dk.core.common.utils.GeneralUtils;
import com.dk.core.common.utils.search.SearchBean;
import com.dk.core.common.base.dao.IGenericDao;
import com.dk.core.common.base.service.support.GenericService;
import com.dk.core.common.exception.impl.PortalException;
import com.dk.core.common.exception.impl.PwdNotCorrectException;
import com.dk.core.common.log.LogUtils;
import com.dk.core.dao.user.IUserDao;
import com.dk.core.model.bean.User;
import com.dk.core.service.user.IUserService;

@Service
public class UserServiceImpl extends GenericService<User, String> implements
		IUserService {

	@Resource
	private IUserDao userDao;

	@Override
	public IGenericDao<User, String> getDao() {
		return userDao;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String register(User user) throws CoreException {
		LogUtils.getInstance().debugSystem(LogUtils.MODULE_PORTAL,
				"Execute registering user ", user);

		// 校验输入有效性
		this.checkValid(user);

		LogUtils.getInstance().debugSystem(LogUtils.MODULE_PORTAL,
				"Begin to insert user data to DB", user);

		user.setStatus(IConstants.USER_STATUS.ACTIVE);
		user.setCreatedTime(new Date());
		user.setCreatedBy("register");

		user.setIsAdmin(false);

		user.setPassword(EncryptionUtils.encryptBasedMd5(user.getPassword()));

		// 保存商户信息
		String id = userDao.save(user);

		LogUtils.getInstance().debugSystem(LogUtils.MODULE_PORTAL,
				"Insert user data successful!", user);
		return id;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> pageFind(SearchBean[] searchBeans, Pageable pageable)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public User find(String loginName) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void active(String loginName) throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String updatePassword(String loginName, String oldPassword,
			String newPassword) throws PortalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public User loginCheck(String loginName, String password)
			throws PortalException, PwdNotCorrectException {
		User user = userDao.findUniqueBy("userName", loginName);

		// 校验登录名是否存在
		if (GeneralUtils.isNull(user)) {

			LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
					"Login name not found error!", "Login name", loginName);

			throw new PortalException(
					PortalErrorCode.USER_LOGIN_NAME_NOT_EXIST_ERROR,
					"Login name not found error!");
		}

		// 校验密码是否正确
		if (!user.getPassword().equals(
				EncryptionUtils.encryptBasedMd5(password))) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
					"Password incorrect error!");
			throw new PwdNotCorrectException(
					PortalErrorCode.USER_PASSWORD_NOT_CORRECT_ERROR,
					"Password incorrect error!");
		}
		
		if(!user.getStatus().equals(IConstants.USER_STATUS.ACTIVE)){
			throw new PwdNotCorrectException(
					PortalErrorCode.USER_NOT_ACTIVE_ERROR,
					"user not active errorerror!");
		}
		return user;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateSelf(User user) throws CoreException {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void inactive(String loginName) throws PortalException {
		// TODO Auto-generated method stub

	}

	/**
	 * 注册或者更新时，是否有重复的值
	 * 
	 * @param user
	 * @throws PortalException
	 */
	private void checkValid(User user) throws PortalException {

		/**
		 * 1. 为空校验
		 */
		// 登录名为空校验
		if (GeneralUtils.isNull(user.getUserName())) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
					"Check user info error,login name is empty!",
					user.getUserName());
			throw new PortalException(
					PortalErrorCode.USER_LOGIN_NAME_EMPTY_ERROR,
					"Check user info error,login name is empty!");
		}

		// 密码为空校验
		if (GeneralUtils.isNull(user.getPassword())) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
					"Check user info error,password is empty!",
					user.getUserName());
			throw new PortalException(
					PortalErrorCode.USER_PASSWORD_EMPTY_ERROR,
					"Check user info error,password is empty!");
		}

		// 邮箱为空校验
		if (GeneralUtils.isNull(user.getEmail())) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
					"Check user info error,email is empty!", user.getEmail());
			throw new PortalException(PortalErrorCode.USER_EMAIL_EMPTY_ERROR,
					"Check user info error,email is empty!");
		}

		/**
		 * 2. 重复校验
		 */
		// 判断登录名是否重复
		if (userDao.isNoDeleteExist("userName", user.getUserName(),
				user.getId())) {

			LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
					"Check user info error,login name exists!",
					user.getUserName());
			throw new PortalException(
					PortalErrorCode.USER_LOGIN_NAME_EXIST_ERROR,
					"Check user info error,login name exists!");
		}

		// 判断邮箱是否重复

		if (userDao.isNoDeleteExist("email", user.getEmail(), user.getId())) {

			LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
					"Check user info error,email exists!", user.getUserName());
			throw new PortalException(PortalErrorCode.USER_EMAIL_EXIST_ERROR,
					"Check user info error,email exists!");
		}

		if (GeneralUtils.isNotNull(user.getCellPhone())) {
			// 判断手机号码是否重复
			if (userDao.isNoDeleteExist("cellPhone", user.getCellPhone(),
					user.getId())) {

				LogUtils.getInstance().errorSystem(LogUtils.MODULE_PORTAL,
						"Check user info error,phone exists!",
						user.getUserName());
				throw new PortalException(
						PortalErrorCode.USER_MOBILE_PHONE_EXIST_ERROR,
						"Check user info error,phone exists!");
			}
		}
	}

}
