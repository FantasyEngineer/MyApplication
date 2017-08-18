package com.hjg.hjgapplife.activity.greenDao.db;

import com.hjg.hjgapplife.activity.greenDao.entry.DaoMaster;
import com.hjg.hjgapplife.activity.greenDao.entry.DaoSession;
import com.hjg.hjgapplife.application.MyApplication;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class GreenDaoUtils {
    /**
     * 是否加密
     */
    public static boolean ENCRYPTED = false;

    private static GreenDaoUtils greenDaoUtils;
    private Database db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoUtils() {
    }

    public static GreenDaoUtils getInstance() {
        if (greenDaoUtils == null) {
            greenDaoUtils = new GreenDaoUtils();
        }
        return greenDaoUtils;
    }

    /**
     * 初始化greenDao
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(MyApplication.getApplication(), ENCRYPTED ? "aiyiqi-db_encrypted" : "aiyiqi-db", null);
        //加密的数据库：未加密的数据库
        db = ENCRYPTED ? mHelper.getEncryptedWritableDb("password") : mHelper.getWritableDb();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 获取DaoSession
     *
     * @return
     */
    public DaoSession getmDaoSession() {
        if (mDaoMaster == null) {
            initGreenDao();
        }
        return mDaoSession;
    }

    /**
     * 获取getDb
     *
     * @return
     */
    public Database getDb() {
        if (db == null) {
            initGreenDao();
        }
        return db;
    }
}
