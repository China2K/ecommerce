/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/9/6 20:27:06                            */
/*==============================================================*/


drop table if exists ecommerce.tmp_t_product;

rename table ecommerce.t_product to tmp_t_product;

drop table if exists ecommerce.tmp_t_user;

rename table ecommerce.t_user to tmp_t_user;

/*==============================================================*/
/* Table: t_brand                                               */
/*==============================================================*/
create table ecommerce.t_brand
(
   ID                   national char(32) not null,
   NAME                 national varchar(50) not null,
   `DESC`               national varchar(1000),
   LOGO                 varchar(200),
   WEBSITE              national varchar(255),
   PHONE                national varchar(20),
   FAX                  national varchar(20),
   EMAIL                national varchar(50),
   STATUS               national char(1) comment 'A-启用，B-下架，D-已经删除',
   CREATED_TIME         datetime,
   CREATED_BY           national varchar(50),
   UPDATED_TIME         datetime,
   UPDATED_BY           national varchar(50)
);

alter table ecommerce.t_brand
   add primary key (ID);

/*==============================================================*/
/* Table: t_cart_info                                           */
/*==============================================================*/
create table ecommerce.t_cart_info
(
   ID                   national char(32) not null,
   CART_ID              national char(32),
   PRODUCT_ID           national char(32),
   CREATED_DATE         datetime
);

alter table ecommerce.t_cart_info
   add primary key (ID);

/*==============================================================*/
/* Table: t_category                                            */
/*==============================================================*/
create table ecommerce.t_category
(
   ID                   national char(32) not null,
   NAME                 national varchar(100),
   `DESC`               national varchar(1000),
   STATUS               national char(1) comment 'A-激活的;I-初始状态;D-已删除',
   PARENT_ID            national char(32),
   `ORDER`              int(11),
   COVER                national varchar(255),
   LEVEL                int(1),
   UPDATED_TIME         datetime,
   CREATED_TIME         datetime,
   CREATED_BY           national varchar(50),
   UPDATED_BY           national varchar(50),
   Column_13            char(10)
);

alter table ecommerce.t_category
   add primary key (ID);

/*==============================================================*/
/* Table: t_order                                               */
/*==============================================================*/
create table t_order
(
   ID                   char(32) not null,
   SN                   varchar(50),
   USER_ID              char(32),
   ADDRESS              varchar(300),
   LEAVE_MESSAGE        varchar(300),
   ADDRESSEE_NAME       varchar(50),
   PHONE                varchar(20),
   STATUS               char(1) comment 'A-用户已生成未发货，B-系统已发货，C-用户已确认收货，D-用户已付款，订单完成，E-用户已退货，订单取消',
   TOTAL_PRICE          double,
   CREATED_TIME         datetime,
   SEND_TIME            datetime,
   DELIVERY_TIME        datetime
);

alter table t_order
   add primary key (ID);

/*==============================================================*/
/* Table: t_prod_enshrine_log                                   */
/*==============================================================*/
create table t_prod_enshrine_log
(
   ID                   char(32),
   USER_ID              char(32),
   PRODUCT_ID           char(32),
   CREATED_DATE         datetime
);

/*==============================================================*/
/* Table: t_product                                             */
/*==============================================================*/
create table ecommerce.t_product
(
   ID                   national char(32) not null,
   BRAND_ID             national char(32),
   CATEGORY_ID          national char(32),
   NAME                 national varchar(120),
   BRAND_NAME           national varchar(50),
   STATUS               national char(1) not null comment 'A-上架;A-下架;I-初始状态;D-已删除',
   PRODUCT_IMG1         national varchar(255),
   PRODUCT_IMG2         national varchar(255),
   PRODUCT_IMG3         national varchar(255),
   PRODUCT_IMG4         national varchar(255),
   PRODUCT_IMG5         national varchar(255),
   PRODUCT_URL_IMG1     national varchar(200),
   PRODUCT_URL_IMG2     national varchar(200),
   PRODUCT_URL_IMG3     national varchar(200),
   PRODUCT_URL_IMG4     national varchar(200),
   PRODUCT_URL_IMG5     national varchar(200),
   `DESC`               national text,
   MODEL_NO             national varchar(100),
   WEIGHT               double(10,2),
   WEIGHT_UNIT          national varchar(10),
   YEAR_MADE            int(11),
   SERIAL_NO            national varchar(20),
   UNIT_PRICE           double(10,2),
   SELL_PRICE           double(10,2),
   IS_DISCOUNT          int(11) default 0,
   STOCK                int(11),
   SKU                  national varchar(10),
   APPROVED_BY          national varchar(20),
   APPROVED_DATE        datetime,
   CREATED_BY           national varchar(50),
   CREATED_TIME         timestamp,
   UPDATED_BY           national varchar(50),
   UPDATED_TIME         timestamp,
   TOKEN_OFF_BY         varchar(50),
   TOKEN_OFF_TIME       datetime
);

insert into ecommerce.t_product (ID, BRAND_ID, CATEGORY_ID, NAME, BRAND_NAME, STATUS, PRODUCT_IMG1, PRODUCT_IMG2, PRODUCT_IMG3, PRODUCT_IMG4, PRODUCT_IMG5, PRODUCT_URL_IMG1, PRODUCT_URL_IMG2, PRODUCT_URL_IMG3, PRODUCT_URL_IMG4, PRODUCT_URL_IMG5, `DESC`, MODEL_NO, WEIGHT, WEIGHT_UNIT, YEAR_MADE, SERIAL_NO, UNIT_PRICE, SELL_PRICE, IS_DISCOUNT, STOCK, SKU, APPROVED_BY, APPROVED_DATE, CREATED_BY, UPDATED_BY, TOKEN_OFF_BY, TOKEN_OFF_TIME)
select ID, BRAND_ID, CATEGORY_ID, NAME, BRAND_NAME, STATUS, PRODUCT_IMG1, PRODUCT_IMG2, PRODUCT_IMG3, PRODUCT_IMG4, PRODUCT_IMG5, PRODUCT_URL_IMG1, PRODUCT_URL_IMG2, PRODUCT_URL_IMG3, PRODUCT_URL_IMG4, PRODUCT_URL_IMG5, `DESC`, MODEL_NO, WEIGHT, WEIGHT_UNIT, YEAR_MADE, SERIAL_NO, UNIT_PRICE, SELL_PRICE, IS_DISCOUNT, STOCK, SKU, APPROVED_BY, APPROVED_DATE, CREATED_BY, UPDATED_BY, TOKEN_OFF_BY, TOKEN_OFF_TIME
from ecommerce.tmp_t_product;

alter table ecommerce.t_product
   add primary key (ID);

/*==============================================================*/
/* Index: CATEGORY_ID                                           */
/*==============================================================*/
create index CATEGORY_ID on ecommerce.t_product
(
   CATEGORY_ID
);

/*==============================================================*/
/* Index: MANUF_ID                                              */
/*==============================================================*/
create index MANUF_ID on ecommerce.t_product
(
   BRAND_ID
);

/*==============================================================*/
/* Table: t_product_hot                                         */
/*==============================================================*/
create table t_product_hot
(
   ID                   char(32) not null,
   PRODUCT_ID           char(32),
   `ORDER`              int,
   TYPE                 char(1) comment 'A-主页列表中热卖产品，B-主页幻灯片中展示产品',
   PROD_IMG             varchar(200) comment 'type为B 时使用',
   CREATED_TIME         datetime,
   CREATED_BY           varchar(50)
);

alter table t_product_hot
   add primary key (ID);

/*==============================================================*/
/* Table: t_product_inst                                        */
/*==============================================================*/
create table t_product_inst
(
   ID                   char(32) not null,
   PRODUCT_ID           char(32),
   ORDER_ID             char(32),
   USER_ID              char(32),
   NAME                 varchar(120),
   COUNT                int(11),
   `DESC`               text,
   UNIT_PRICE           double,
   SELL_PRICE           double,
   PROD_COVER           varchar(200),
   CREATE_TIME          datetime
);

alter table t_product_inst
   add primary key (ID);

/*==============================================================*/
/* Table: t_shopping_cart                                       */
/*==============================================================*/
create table ecommerce.t_shopping_cart
(
   ID                   national char(32) not null,
   USER_ID              national char(32),
   STATUS               national char(1),
   UPDATED_TIME         datetime
);

alter table ecommerce.t_shopping_cart
   add primary key (ID);

/*==============================================================*/
/* Table: t_sys_hat_area                                        */
/*==============================================================*/
create table t_sys_hat_area
(
   ID                   int not null,
   _CODE                char(6),
   _NAME                varchar(40),
   _CITY_CODE           char(6)
);

alter table t_sys_hat_area
   add primary key (ID);

/*==============================================================*/
/* Table: t_sys_hat_city                                        */
/*==============================================================*/
create table t_sys_hat_city
(
   ID                   int not null,
   _CODE                char(6),
   _NAME                varchar(40),
   _PROVINCE_CODE       char(6)
);

alter table t_sys_hat_city
   add primary key (ID);

/*==============================================================*/
/* Table: t_sys_hat_province                                    */
/*==============================================================*/
create table t_sys_hat_province
(
   ID                   int not null,
   _CODE                char(6),
   _NAME                varchar(40)
);

alter table t_sys_hat_province
   add primary key (ID);

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table ecommerce.t_user
(
   ID                   national char(32) not null,
   STATUS               national char(1) comment 'A-未激活，B-已经激活，D-已删除',
   NAME                 national varchar(30),
   EMAIL                national varchar(50) not null,
   CELL_PHONE           national varchar(20),
   USER_NAME            national varchar(50) not null,
   PASSWORD             national varchar(20) not null,
   LAST_ACCESS_IP       national varchar(50),
   CREATED_BY           national varchar(50),
   CREATED_TIME         timestamp,
   UPDATED_BY           national varchar(50),
   UPDATED_TIME         timestamp,
   USER_ADMIN           tinyint(1) default 0
);

#WARNING: The following insert order will not restore columns: PASSWORD
insert into ecommerce.t_user (ID, STATUS, NAME, EMAIL, CELL_PHONE, USER_NAME, PASSWORD, LAST_ACCESS_IP, CREATED_BY, UPDATED_BY, USER_ADMIN)
select ID, STATUS, NAME, EMAIL, CELL_PHONE, USER_NAME, ?, LAST_ACCESS_IP, CREATED_BY, UPDATED_BY, USER_ADMIN
from ecommerce.tmp_t_user;

alter table ecommerce.t_user
   add primary key (ID);

/*==============================================================*/
/* Table: t_user_address                                        */
/*==============================================================*/
create table t_user_address
(
   ID                   char(32) not null,
   USER_ID              char(32),
   STATUS               char(1),
   NAME                 varchar(50),
   PROVENCE             varchar(6),
   CITY                 varchar(6),
   AREA                 varchar(6),
   STREET               varchar(200),
   CELL_PHONE           varchar(20),
   PHONE                varchar(20),
   IS_DEFAULT           boolean,
   CREATED_TIME         datetime,
   UPDATED_TIME         datetime
);

alter table t_user_address
   add primary key (ID);

alter table ecommerce.t_cart_info add constraint FK_Reference_1 foreign key (CART_ID)
      references ecommerce.t_shopping_cart (ID) on delete restrict on update restrict;

alter table ecommerce.t_cart_info add constraint FK_Reference_2 foreign key (PRODUCT_ID)
      references ecommerce.t_product (ID) on delete restrict on update restrict;

alter table ecommerce.t_category add constraint FK_Reference_7 foreign key (PARENT_ID)
      references ecommerce.t_category (ID) on delete restrict on update restrict;

alter table t_order add constraint FK_Reference_5 foreign key (USER_ID)
      references ecommerce.t_user (ID) on delete restrict on update restrict;

alter table t_prod_enshrine_log add constraint FK_Reference_8 foreign key (ID)
      references ecommerce.t_user (ID) on delete restrict on update restrict;

alter table t_prod_enshrine_log add constraint FK_Reference_9 foreign key (PRODUCT_ID)
      references ecommerce.t_product (ID) on delete restrict on update restrict;

alter table ecommerce.t_product add constraint FK_Reference_3 foreign key (BRAND_ID)
      references ecommerce.t_brand (ID) on delete restrict on update restrict;

alter table ecommerce.t_product add constraint FK_Reference_4 foreign key (CATEGORY_ID)
      references ecommerce.t_category (ID) on delete restrict on update restrict;

alter table t_product_hot add constraint FK_Reference_14 foreign key (PRODUCT_ID)
      references ecommerce.t_product (ID) on delete restrict on update restrict;

alter table t_product_inst add constraint FK_Reference_10 foreign key (ORDER_ID)
      references t_order (ID) on delete restrict on update restrict;

alter table t_product_inst add constraint FK_Reference_11 foreign key (PRODUCT_ID)
      references ecommerce.t_product (ID) on delete restrict on update restrict;

alter table t_product_inst add constraint FK_Reference_12 foreign key (USER_ID)
      references ecommerce.t_user (ID) on delete restrict on update restrict;

alter table ecommerce.t_shopping_cart add constraint FK_Reference_13 foreign key (USER_ID)
      references ecommerce.t_user (ID) on delete restrict on update restrict;

alter table t_user_address add constraint FK_Reference_6 foreign key (USER_ID)
      references ecommerce.t_user (ID) on delete restrict on update restrict;

