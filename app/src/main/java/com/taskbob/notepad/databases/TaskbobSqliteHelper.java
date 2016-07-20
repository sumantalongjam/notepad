package com.taskbob.notepad.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.taskbob.notepad.Utilities.KeyIDS;

/**
 * Created by sumanta on 21/7/16.
 */
public class TaskbobSqliteHelper extends SQLiteOpenHelper implements KeyIDS {

    private static final String DATABASE_NAME = "taskbob.db";
    private static final int DATABASE_VERSION = 1;

    private final String CREATE_TABLE_TASK = "CREATE TABLE "
            + TASK_TABLE + "(" + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TASK_TITLE + " TEXT," + TASK_CONTENT + " TEXT);";


    private static TaskbobSqliteHelper sqliteHelper;

    private TaskbobSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static TaskbobSqliteHelper getSqliteHelper(Context context) {
        synchronized (TaskbobSqliteHelper.class) {
            if (sqliteHelper == null) {
                sqliteHelper = new TaskbobSqliteHelper(context);
            }
        }
        return sqliteHelper;
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        return getSqliteHelper(context).getWritableDatabase();
    }

    public static SQLiteDatabase getReadableDatabase(Context context) {
        return getSqliteHelper(context).getReadableDatabase();
    }

    public static void closeDatabase(Context context) {
        getSqliteHelper(context).close();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}