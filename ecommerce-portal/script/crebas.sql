alter table t_cart_info
   drop primary key;

drop table if exists t_cart_info;

alter table t_category
   drop primary key;

drop table if exists t_category;

alter table t_manufacturer
   drop primary key;

drop table if exists t_manufacturer;

alter table t_order
   drop primary key;

drop table if exists t_order;

drop table if exists t_prod_enshrine_log;

alter table t_product
   drop primary key;

drop table if exists t_product;

alter table t_product_inst
   drop primary key;

drop table if exists t_product_inst;

alter table t_shopping_cart
   drop primary key;

drop table if exists t_shopping_cart;

alter table t_sys_hat_area
   drop primary key;

drop table if exists t_sys_hat_area;

alter table t_sys_hat_city
   drop primary key;

drop table if exists t_sys_hat_city;

alter table t_sys_hat_province
   drop primary key;

drop table if exists t_sys_hat_province;

alter table t_user
   drop primary key;

drop table if exists t_user;

alter table t_user_address
   drop primary key;

drop table if exists t_user_address;

/*==============================================================*/
/* Table: t_cart_info                                           */
/*==============================================================*/
create table t_cart_info
(
   ID                    char(32) not null,
   CART_ID               char(32),
   PRODUCT_ID            char(32),
   CREATED_DATE         datetime
);

alter table t_cart_info
   add primary key (ID);

/*==============================================================*/
/* Table: t_category                                            */
/*==============================================================*/
create table t_category
(
   ID                    char(32) not null,
   NAME                  varchar(100),
   `DESC`                varchar(1000),
   STATUS                char(1),
   PARENT_ID             char(32),
   `ORDER`              int(11),
   COVER                 varchar(255),
   LEVEL                int(1),
   UPDATED_TIME         datetime,
   CREATED_TIME         datetime,
   CREATED_BY            varchar(50),
   UPDATED_BY            varchar(50)
);

alter table t_category
   add primary key (ID);

/*==============================================================*/
/* Table: t_manufacturer                                        */
/*==============================================================*/
create table t_manufacturer
(
   ID                    char(32) not null,
   NAME                  varchar(50) not null,
   `DESC`                varchar(1000),
   WEBSITE               varchar(255),
   PHONE                 varchar(20),
   FAX                   varchar(20),
   EMAIL                 varchar(50),
   STATUS                char(1) comment 'A-启用，B-下架，D-已经删除',
   CREATED_TIME         datetime,
   CREATED_BY            varchar(50),
   UPDATED_TIME         datetime,
   UPDATED_BY            varchar(50)
);

alter table t_manufacturer
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
   NAME                 varchar(50),
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
   t_p_ID               char(32),
   USER_ID              char(32),
   PRODUCT_ID           char(32),
   CREATED_DATE         datetime
);

/*==============================================================*/
/* Table: t_product                                             */
/*==============================================================*/
create table t_product
(
   ID                    char(32) not null,
   MANUF_ID              char(32),
   CATEGORY_ID           char(32),
   NAME                  varchar(120),
   MANUF_NAME            varchar(50),
   STATUS                char(1) not null,
   PRODUCT_IMG1          varchar(255),
   PRODUCT_IMG2          varchar(255),
   PRODUCT_IMG3          varchar(255),
   PRODUCT_IMG4          varchar(255),
   PRODUCT_IMG5          varchar(255),
   PRODUCT_URL_IMG1      varchar(200),
   PRODUCT_URL_IMG2      varchar(200),
   PRODUCT_URL_IMG3      varchar(200),
   PRODUCT_URL_IMG4      varchar(200),
   PRODUCT_URL_IMG5      varchar(200),
   `DESC`                text,
   MODEL_NO              varchar(100),
   WEIGHT               double(10,2),
   WEIGHT_UNIT           varchar(10),
   YEAR_MADE            int(11),
   SERIAL_NO             varchar(20),
   UNIT_PRICE           double(10,2),
   SELL_PRICE           double(10,2),
   IS_DISCOUNT          int(11) default 0,
   STOCK                int(11),
   SKU                   varchar(10),
   APPROVED_BY           varchar(20),
   APPROVED_DATE        datetime,
   CREATED_BY            varchar(50),
   CREATED_TIME         timestamp,
   UPDATED_BY            varchar(50),
   UPDATED_TIME         timestamp,
   TOKEN_OFF_BY         varchar(50),
   TOKEN_OFF_TIME       datetime
);

alter table t_product
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
   PROD_NAME            varchar(120),
   COUNT                int(11),
   PROD_DESC            text,
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
create table t_shopping_cart
(
   ID                    char(32) not null,
   USER_ID               char(32),
   STATUS                char(1),
   UPDATED_TIME         datetime
);

alter table t_shopping_cart
   add primary key (ID);

/*==============================================================*/
/* Table: t_sys_hat_area                                        */
/*==============================================================*/
create table t_sys_hat_area
(
   ID                  int not null,
   _CODE                char(6),
   _NAME                varchar(40),
   _CITY_ID             char(6)
);

alter table t_sys_hat_area
   add primary key (_ID);

/*==============================================================*/
/* Table: t_sys_hat_city                                        */
/*==============================================================*/
create table t_sys_hat_city
(
   ID                  int not null,
   _CODE                char(6),
   _NAME                varchar(40),
   _PROVINCE_ID         char(6)
);

alter table t_sys_hat_city
   add primary key (_ID);

/*==============================================================*/
/* Table: t_sys_hat_province                                    */
/*==============================================================*/
create table t_sys_hat_province
(
   ID                  int not null,
   _CODE                char(6),
   _NAME                varchar(40)
);

alter table t_sys_hat_province
   add primary key (_ID);

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   ID                    char(32) not null,
   STATUS                char(1) comment 'A-未激活，B-已经激活，D-已删除',
   FIRST_NAME            varchar(30),
   LAST_NAME             varchar(30),
   EMAIL                 varchar(50) not null,
   CELL_PHONE            varchar(20),
   USER_NAME             varchar(50) not null,
   PASSWORD              varchar(20) not null,
   LAST_ACCESS_IP        varchar(50),
   CREATED_BY            varchar(50),
   CREATED_TIME         timestamp,
   UPDATED_BY            varchar(50),
   UPDATED_TIME         timestamp,
   USER_ADMIN           tinyint(1) default 0
);

alter table t_user
   add primary key (ID);

/*==============================================================*/
/* Table: t_user_address                                        */
/*==============================================================*/
create table t_user_address
(
   ID                   char(32) not null,
   USER_ID              char(32),
   NAME                 varchar(50),
   PROVENCE             varchar(32),
   CITY                 varchar(32),
   AREA                 varchar(32),
   STREET               varchar(200),
   CELL_PHONE           varchar(20),
   PHONE                varchar(20),
   IS_DEFAULT           boolean,
   CREATED_TIME         datetime,
   UPDATED_TIME         datetime
);

alter table t_user_address
   add primary key (ID);

alter table t_cart_info add constraint FK_Reference_1 foreign key (CART_ID)
      references t_shopping_cart (ID) on delete restrict on update restrict;

alter table t_cart_info add constraint FK_Reference_2 foreign key (PRODUCT_ID)
      references t_product (ID) on delete restrict on update restrict;

alter table t_category add constraint FK_Reference_7 foreign key (PARENT_ID)
      references t_category (ID) on delete restrict on update restrict;

alter table t_order add constraint FK_Reference_5 foreign key (USER_ID)
      references t_user (ID) on delete restrict on update restrict;

alter table t_prod_enshrine_log add constraint FK_Reference_8 foreign key (ID)
      references t_user (ID) on delete restrict on update restrict;

alter table t_prod_enshrine_log add constraint FK_Reference_9 foreign key (t_p_ID)
      references t_product (ID) on delete restrict on update restrict;

alter table t_product add constraint FK_Reference_3 foreign key (MANUF_ID)
      references t_manufacturer (ID) on delete restrict on update restrict;

alter table t_product add constraint FK_Reference_4 foreign key (CATEGORY_ID)
      references t_category (ID) on delete restrict on update restrict;

alter table t_product_inst add constraint FK_Reference_10 foreign key (ORDER_ID)
      references t_order (ID) on delete restrict on update restrict;

alter table t_product_inst add constraint FK_Reference_11 foreign key (PRODUCT_ID)
      references t_product (ID) on delete restrict on update restrict;

alter table t_product_inst add constraint FK_Reference_12 foreign key (USER_ID)
      references t_user (ID) on delete restrict on update restrict;

alter table t_shopping_cart add constraint FK_Reference_13 foreign key (USER_ID)
      references t_user (ID) on delete restrict on update restrict;

alter table t_user_address add constraint FK_Reference_6 foreign key (USER_ID)
      references t_user (ID) on delete restrict on update restrict;
