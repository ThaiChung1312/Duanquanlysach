package vn.edu.poly.duanquanlysach.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.model.Nguoidungclass;

public class Nguoidungsql {
    SQLiteDatabase db;
    SqliteHelper dbhelper;
    public static final String TABLE_NAME1 = "Nguoidungtable";
    public static final String CREATE_TABLE_NGUOIDUNG = "CREATE TABLE Nguoidungtable (ten text,tentk text primary key,mk text,sdt text,chucvu text)";
    public static final String COLUMN_TENTK ="tentk";



    public Nguoidungsql(Context context) {
        dbhelper = new SqliteHelper(context);
        db = dbhelper.getWritableDatabase();
    }
    public long insertnguoidung(Nguoidungclass n) {
        db = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten",n.getName());
        contentValues.put("tentk",n.getTentk());
        contentValues.put("mk",n.getMatkhau());
        contentValues.put("sdt",n.getSdt());
        contentValues.put("chucvu",n.getChucvu());

        long result = db.insert(TABLE_NAME1, null, contentValues);
        return result;
    }
    public long xoanguoidung(Nguoidungclass n){
        long result=db.delete(TABLE_NAME1,COLUMN_TENTK+"=?",new String[]{n.getTentk()});
        return result;
    }
    public Nguoidungclass getUserByID(String tentk) {
        Nguoidungclass user = null;
//WHERE clause
        String selection = "tentk=?";
//WHERE clause arguments
        String[] selectionArgs = {tentk};
        Cursor c = db.query(TABLE_NAME1, null, selection, selectionArgs, null, null, null);
        Log.d("getUserByID", "===>" + c.getCount());
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            user = new Nguoidungclass();
            user.setName(c.getString(0));
            user.setTentk(c.getString(1));
            user.setMatkhau(c.getString(2));
            user.setSdt(c.getString(3));
            user.setChucvu(c.getString(4));
            break;
        }
        c.close();
        return user;
    }

    public List<Nguoidungclass> getallnguoidung() {
        List<Nguoidungclass> list = new ArrayList<>();
        String SElECT = " SELECT * FROM " + TABLE_NAME1;
        //XIN QUYEN DOC

        Cursor cursor = db.rawQuery(SElECT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String ten = cursor.getString(cursor.getColumnIndex("ten"));
                String tentk = cursor.getString(cursor.getColumnIndex("tentk"));
                String mk = cursor.getString(cursor.getColumnIndex("mk"));
                String sdt = cursor.getString(cursor.getColumnIndex("sdt"));
                String chucvu = cursor.getString(cursor.getColumnIndex("chucvu"));
           Nguoidungclass nguoidungclass=new Nguoidungclass();
           nguoidungclass.name=ten;
           nguoidungclass.tentk=tentk;
           nguoidungclass.matkhau=mk;
           nguoidungclass.sdt=sdt;
           nguoidungclass.chucvu=chucvu;

                list.add(nguoidungclass);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
    public long updatenguoidung(Nguoidungclass n){

        ContentValues contentValues = new ContentValues();
        contentValues.put("ten",n.getName());
        contentValues.put("tentk",n.getTentk());
        contentValues.put("mk",n.getMatkhau());
        contentValues.put("sdt",n.getSdt());
        contentValues.put("chucvu",n.getChucvu());
        long resutl = db.update(TABLE_NAME1,contentValues,COLUMN_TENTK+"=?",new String[]{n.getTentk()});
//        db.close();
        return resutl;

    }
    public boolean checkUser(String tentk, String mk) {
        String selection = "tentk = ?  AND mk = ?";

        String[] selectionArgs = {tentk, mk};

        Cursor cursor = db.query(TABLE_NAME1, //Table to query
                null,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
