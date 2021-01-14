package vn.edu.poly.duanquanlysach.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.model.Nguoidungclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;

public class Theloaisql {
    SQLiteDatabase db;
    SqliteHelper dbhelper;
    public static final String TABLE_NAME2 = "Theloaitable";
    public static final String CREATE_TABLE_THELOAI = "CREATE TABLE Theloaitable (matheloai text primary key,tentheloai text,mota text,vitri text)";
    public static final String COLUMN_MATL = "matheloai";


    public Theloaisql(Context context) {
        dbhelper = new SqliteHelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public long inserttheloai(Theloaiclass t) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matheloai", t.getMatheloai());
        contentValues.put("tentheloai", t.getTentheloai());
        contentValues.put("mota", t.getMota());
        contentValues.put("vitri", t.getVitri());
        long result = db.insert(TABLE_NAME2, null, contentValues);
        return result;
    }

    public long xoatheloai(Theloaiclass t) {
        long result = db.delete(TABLE_NAME2, COLUMN_MATL + "=?", new String[]{t.getMatheloai()});
        return result;
    }

    public List<Theloaiclass> getalltheloai() {
        List<Theloaiclass> list = new ArrayList<>();
        String SElECT = " SELECT * FROM " + TABLE_NAME2;
        //XIN QUYEN DOC

        Cursor cursor = db.rawQuery(SElECT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String matl = cursor.getString(cursor.getColumnIndex("matheloai"));
                String tentl = cursor.getString(cursor.getColumnIndex("tentheloai"));
                String mota = cursor.getString(cursor.getColumnIndex("mota"));
                String vitri = cursor.getString(cursor.getColumnIndex("vitri"));
                Theloaiclass theloaiclass = new Theloaiclass();
                theloaiclass.matheloai = matl;
                theloaiclass.tentheloai = tentl;
                theloaiclass.mota = mota;
                theloaiclass.vitri = vitri;


                list.add(theloaiclass);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public long updatetheloai(Theloaiclass t) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("matheloai", t.getMatheloai());
        contentValues.put("tentheloai", t.getTentheloai());
        contentValues.put("mota", t.getMota());
        contentValues.put("vitri", t.getVitri());
        long resutl = db.update(TABLE_NAME2, contentValues, COLUMN_MATL + "=?", new String[]{t.getMatheloai()});
//        db.close();
        return resutl;

    }
}

