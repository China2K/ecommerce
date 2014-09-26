package com.dk.core.common.constant;

public interface IConstants {

	public static final String SYS_CONFIG_PASSWORD_MAX_FAIL_TIMES = "10000001";
	// 商户默认角色的code
	public static final String MERCHANT_DEFAULT_ROLE_CODE = "10000002";

	public static final String SYS_CONFIG_PASSWORD_ERROR_UNLOCK_MINS = "10000003";

	// url starts with http or https
	public static final String HTTP_HTTPS_REG = "^(http|https)://.+";

	public interface CATEGORY_STATUS {
		//A-激活的;I-初始状态;D-已删除
		public final static String ACTIVE = "A";
		public final static String INITIAL = "I";
		public final static String DELETED = "D";
	}
	
	public interface PRODUCT_STATUS {
		//A-上架;A-下架;I-初始状态;D-已删除
		public final static String INITIAL = "I";
		public final static String ACTIVE = "A";
		public final static String TOKENOFF = "B";
		public final static String DELETED = "D";

	}
	
	public interface BRAND_STATUS {
		//A-启用，B-下架，I-初始状态,D-已经删除
		public final static String ACTIVE = "A";
		public final static String TOKENOFF = "B";
		public final static String INITIAL = "I";
		public final static String DELETED = "D";

	}

	public interface USER_STATUS {
		public final static String ACTIVE = "A";
		public final static String INACTIVE = "I";
		public final static String DELETED = "D";
	}
	
	public interface USER_ADDRESS_STATUS {
		public final static String ACTIVE = "A";
		public final static String DELETED = "D";
	}
	
	
	public interface ORDER_STATUS {
		//A-用户已生成未发货，B-系统已发货，C-用户已付款，D-用户已确认收货,订单完成，E-用户已退货，订单取消,Z-删除
		
		public final static String INITIAL = "A";
		public final static String SENDED = "B";
		public final static String PAID = "C";
		public final static String SUCCESS = "D";
		public final static String CANCELED = "E";
		public final static String DELETED = "Z";

	}
}
