package com.flowerbell.masschat.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flowerbell.masschat.domain.LoginUser;
import com.flowerbell.masschat.service.database.DatabaseHelper;
import com.flowerbell.masschat.service.database.UserBeanTable;
import com.flowerbell.masschat.utils.AppManager;

/**
 * Created by wsli on 2017/7/25.
 */

public final class DatabaseManager {
    static DatabaseManager _instance;

    public  static synchronized DatabaseManager getInstance(){
        if (null == _instance){
            _instance = new DatabaseManager();
        }
        return _instance;
    }

    DatabaseHelper _databaseHelper;
    private DatabaseManager(){
        _databaseHelper = new DatabaseHelper(AppManager.getAppManager().currentActivity());
    }

    public LoginUser loadSignedInUser() {
        SQLiteDatabase db = _databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(
                UserBeanTable.UserBeanENTRY.TABLE_NAME,                     // The table to query
                UserBeanTable.projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        LoginUser bean = new LoginUser();
        if (cursor.moveToFirst()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(UserBeanTable.UserBeanENTRY._ID)
            );

            bean.setRowId(itemId);
            bean.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserBeanTable.UserBeanENTRY.name)));
            bean.setWechatName(cursor.getString(cursor.getColumnIndexOrThrow(UserBeanTable.UserBeanENTRY.WECHAT_NAME)));
            bean.setTittle(cursor.getString(cursor.getColumnIndexOrThrow(UserBeanTable.UserBeanENTRY.tittle)));
            bean.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(UserBeanTable.UserBeanENTRY.company)));
            bean.setCompanyLink(cursor.getString(cursor.getColumnIndexOrThrow(UserBeanTable.UserBeanENTRY.company_link)));

        }else{

            bean.setName("昵称");
            bean.setTittle("职位");
            bean.setCompany("公司名称");
            bean.setCompanyLink("公司主页");
        }

        return bean;
    }
}
