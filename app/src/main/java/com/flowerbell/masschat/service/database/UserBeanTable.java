package com.flowerbell.masschat.service.database;

import android.provider.BaseColumns;

/**
 * Created by wsli on 2017/7/25.
 */

public final class UserBeanTable {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserBeanTable() {}

    /* Inner class that defines the table contents */
    public static class UserBeanENTRY implements BaseColumns {

        public static final String TABLE_NAME   = "userinfos";

        public static final String name         = "name";
        public static final String avatar       = "avatar";
        public static final String phone        = "phone";
        public static final String email        = "email";
        public static final String sex          = "sex";
        public static final String age          = "age";
        public static final String tittle       = "title";
        public static final String company      = "company";
        public static final String industry     = "industry";
        public static final String company_address    = "company_address";
        public static final String company_link = "company_link";
        public static final String WECHAT_ID    = "wechat_id";
        public static final String WECHAT_NAME  = "wechat_name";
        public static final String user_id_type = "user_id_type";
        public static final String create_time  = "create_time";
        public static final String update_time  = "update_time";
    }


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserBeanENTRY.TABLE_NAME + " (" +
                    UserBeanENTRY._ID + " INTEGER PRIMARY KEY," +
                    UserBeanENTRY.name          + " TEXT," +
                    UserBeanENTRY.avatar        + " BLOB," +
                    UserBeanENTRY.phone         + " TEXT," +
                    UserBeanENTRY.email         + " TEXT," +
                    UserBeanENTRY.sex           + " TEXT," +
                    UserBeanENTRY.age           + " INTEGER," +
                    UserBeanENTRY.tittle        + " TEXT," +
                    UserBeanENTRY.company       + " TEXT," +
                    UserBeanENTRY.industry      + " TEXT," +
                    UserBeanENTRY.company_address + " TEXT," +
                    UserBeanENTRY.company_link  + " TEXT," +
                    UserBeanENTRY.WECHAT_ID     + " TEXT," +
                    UserBeanENTRY.WECHAT_NAME   + " TEXT," +
                    UserBeanENTRY.user_id_type  + " INTEGER," +
                    UserBeanENTRY.create_time   + " INTEGER," +
                    UserBeanENTRY.update_time   + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserBeanENTRY.TABLE_NAME;

    public static String[] projection = {
            UserBeanENTRY._ID,
            UserBeanENTRY.name,
            UserBeanENTRY.avatar,
            UserBeanENTRY.phone,
            UserBeanENTRY.email,
            UserBeanENTRY.sex,
            UserBeanENTRY.age,
            UserBeanENTRY.tittle,
            UserBeanENTRY.company,
            UserBeanENTRY.industry,
            UserBeanENTRY.company_address,
            UserBeanENTRY.company_link,
            UserBeanENTRY.WECHAT_ID,
            UserBeanENTRY.WECHAT_NAME,
            UserBeanENTRY.user_id_type,
            UserBeanENTRY.create_time,
            UserBeanENTRY.update_time
    };
}
