package vn.edu.poly.duanquanlysach.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.model.Hoadonclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;

public class Hoadonsql {
    SQLiteDatabase db;
    SqliteHelper dbhelper;
    public static final String TABLE_NAME4 = "Hoadontable";
    public static final String CREATE_TABLE_HOADON= "CREATE TABLE Hoadontable (mahoadon text primary key,ngaymua text)";
    public static final String COLUMN_MAHD = "mahoadon";


    public Hoadonsql(Context context) {
        dbhelper = new SqliteHelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public long insertHoaDon(Hoadonclass h) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mahoadon", h.getMahoadon());
        contentValues.put("ngaymua", h.getNgaymuahang());
        long result = db.insert(TABLE_NAME4, null, contentValues);
        return result;
    }

    public long xoatHoaDon(Hoadonclass h) {
        long result = db.delete(TABLE_NAME4, COLUMN_MAHD + "=?", new String[]{h.getMahoadon()});
        return result;
    }

    public List<Hoadonclass> getallHoaDon() {
        List<Hoadonclass> list = new ArrayList<>();
        String SElECT = " SELECT * FROM " + TABLE_NAME4;
        //XIN QUYEN DOC

        Cursor cursor = db.rawQuery(SElECT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String mahd = cursor.getString(cursor.getColumnIndex("mahoadon"));
                String ngaymua = cursor.getString(cursor.getColumnIndex("ngaymua"));

                Hoadonclass hoadonclass = new Hoadonclass();
                hoadonclass.setMahoadon(mahd);
                hoadonclass.setNgaymuahang(ngaymua);
                list.add(hoadonclass);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public long updateHoaDon(Hoadonclass h) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("mahoadon", h.getMahoadon());
        contentValues.put("ngaymua", h.getNgaymuahang());

        long resutl = db.update(TABLE_NAME4, contentValues, COLUMN_MAHD + "=?", new String[]{h.getMahoadon()});
//        db.close();
        return resutl;

    }
}

