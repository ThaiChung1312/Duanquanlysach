package vn.edu.poly.duanquanlysach.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.model.Sachclass;
import vn.edu.poly.duanquanlysach.model.Theloaiclass;

public class Sachsql {
    SQLiteDatabase db;
    SqliteHelper dbhelper;
    public static final String TABLE_NAME3 = "Sachtable";
    public static final String CREATE_TABLE_SACH = "CREATE TABLE Sachtable (masach text primary key,matheloai text,tensach text,nxb text,soluong number,giabia double)";
    public static final String COLUMN_MASACH = "masach";


    public Sachsql(Context context) {
        dbhelper = new SqliteHelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public long insertsach(Sachclass s) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("masach", s.getMasach());
        contentValues.put("matheloai", s.getMatheloai());
        contentValues.put("tensach", s.getTensach());
        contentValues.put("nxb", s.getNxb());
        contentValues.put("soluong", s.getSoluong());
        contentValues.put("giabia", s.getGiabia());
        long result = db.insert(TABLE_NAME3, null, contentValues);
        return result;
    }

    public long xoasach(Sachclass s) {
        long result = db.delete(TABLE_NAME3, COLUMN_MASACH + "=?", new String[]{s.getMasach()});
        return result;
    }

    public List<Sachclass> getallsach() {
        List<Sachclass> list = new ArrayList<>();
        String SElECT = " SELECT * FROM " + TABLE_NAME3;
        //XIN QUYEN DOC

        Cursor cursor = db.rawQuery(SElECT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String masach = cursor.getString(cursor.getColumnIndex("masach"));
                String matheloai = cursor.getString(cursor.getColumnIndex("matheloai"));
                String tensach = cursor.getString(cursor.getColumnIndex("tensach"));
                String nxb = cursor.getString(cursor.getColumnIndex("nxb"));
                String soluong = cursor.getString(cursor.getColumnIndex("soluong"));
                String giabia = cursor.getString(cursor.getColumnIndex("giabia"));
                Sachclass s = new Sachclass();
                s.setMasach(masach);
                s.setMatheloai(matheloai);
                s.setTensach(tensach);
                s.setNxb(nxb);
                s.setSoluong(soluong);
                s.setGiabia(giabia);


                list.add(s);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public long updatesach(Sachclass s) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("masach", s.getMasach());
        contentValues.put("matheloai", s.getMatheloai());
        contentValues.put("tensach", s.getTensach());
        contentValues.put("nxb", s.getNxb());
        contentValues.put("soluong", s.getSoluong());
        contentValues.put("giabia", s.getGiabia());
        long resut = db.update(TABLE_NAME3, contentValues, COLUMN_MASACH + "=?", new String[]{s.getMasach()});
//        db.close();
        return resut;

    }

    public Sachclass getSachByID(String maSach) {
        Sachclass s = null;
//WHERE clause
        String selection = "masach=?";
//WHERE clause arguments
        String[] selectionArgs = {maSach};
        Cursor c = db.query(TABLE_NAME3, null, selection, selectionArgs, null, null, null);
        Log.d("getSachByID", "===>" + c.getCount());
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            s = new Sachclass();
            s.setMasach(c.getString(0));
            s.setMatheloai(c.getString(1));
            s.setTensach(c.getString(2));
            s.setNxb(c.getString(3));
            s.setSoluong(c.getString(4));
            s.setGiabia(c.getString(5));
            break;
        }
        c.close();
        return s;
    }

    public List<Sachclass> getSachTop10(String month) {
        List<Sachclass> dsSach = new ArrayList<>();
//        if (Integer.parseInt(month) < 10) {
//            month = "0" + month;
//        }
        Log.e("SQL", month + "");
        String sSQL = "SELECT masach, SUM(soluong) as soluong " +
                "FROM Hdcttable " +
                "INNER JOIN Hoadontable ON Hdcttable.mahoadon = Hoadontable.mahoadon " +
                "WHERE Hoadontable.ngaymua like '%/" + (month) + "/%' " +
                "GROUP BY masach " +
                "ORDER BY soluong DESC LIMIT 10";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Log.d("//=====", c.getString(0));
            Sachclass s = new Sachclass();
            s.setMasach(c.getString(0));
            s.setMatheloai("");
            s.setTensach("");
            s.setNxb("");
            s.setSoluong(c.getString(1));
            s.setGiabia("");

            dsSach.add(s);
            c.moveToNext();
        }
        c.close();
        return dsSach;
    }

}

