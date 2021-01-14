package vn.edu.poly.duanquanlysach.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.model.Theloaiclass;

import static vn.edu.poly.duanquanlysach.sqlite.Theloaisql.TABLE_NAME2;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Quanlisach";

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Nguoidungsql.CREATE_TABLE_NGUOIDUNG);
        sqLiteDatabase.execSQL(Theloaisql.CREATE_TABLE_THELOAI);
        sqLiteDatabase.execSQL(Sachsql.CREATE_TABLE_SACH);
        sqLiteDatabase.execSQL(Hoadonsql.CREATE_TABLE_HOADON);
        sqLiteDatabase.execSQL(Hdctsqlite.SQL_HOA_DON_CHI_TIET);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + Nguoidungsql.TABLE_NAME1);
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + Theloaisql.TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + Sachsql.TABLE_NAME3);
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + Hoadonsql.TABLE_NAME4);
        sqLiteDatabase.execSQL("DROP TABLE  IF EXISTS " + Hdctsqlite.TABLE_NAME5);
        onCreate(sqLiteDatabase);
    }


}
