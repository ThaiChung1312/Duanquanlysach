package vn.edu.poly.duanquanlysach.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.duanquanlysach.model.HoaDonChiTietClass;
import vn.edu.poly.duanquanlysach.model.Hoadonclass;
import vn.edu.poly.duanquanlysach.model.Sachclass;

public class Hdctsqlite {
    private SQLiteDatabase db;
    private SqliteHelper dbHelper;

    public static final String TABLE_NAME5 = "Hdcttable";
    public static final String SQL_HOA_DON_CHI_TIET = "CREATE TABLE Hdcttable (maHDCT TEXT PRIMARY KEY , " +
            "mahoadon text, masach text, soluong INTEGER);";
    public static final String TAG = "Hdcttable";

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Hdctsqlite(Context context) {
        dbHelper = new SqliteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //insert
    public long inserHoaDonChiTiet(HoaDonChiTietClass h) {
        ContentValues values = new ContentValues();
        values.put("maHDCT", h.getMahdct());
        values.put("mahoadon", h.getHoadonclass().getMahoadon());
        values.put("masach", h.getSachclass().getMasach());
        values.put("soluong", h.getSoluong());
        long result = db.insert(TABLE_NAME5, null, values);

        return result;
    }

    //getAll
    public List<HoaDonChiTietClass> getAllHoaDonChiTiet() {
        List<HoaDonChiTietClass> dsHoaDonChiTiet = new ArrayList<>();

        String sSQL = "SELECT maHDCT, hoadonclass.mahoadon, hoadonclass.ngaymuahang, " +
                "sachclass.masach, sachclass.matheloai, sachclass.tensach, " +
                "sachclass.nxb, sachclass.giabia, sachclass.soluong, " +
                "Hdcttable.soluong " +
                "FROM Hdcttable " +
                "INNER JOIN Hoadontable on Hdcttable.mahoadon = Hoadontable.mahoadon " +
                "INNER JOIN Sachtable on Sachtable.masach = Hdcttable.masach";

        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false) {
                HoaDonChiTietClass ee = new HoaDonChiTietClass();
                ee.setMahdct(c.getString(0));
                ee.setHoadonclass(new Hoadonclass(c.getString(1), c.getString(2)));
                ee.setSachclass(
                        new Sachclass(
                                c.getString(3),
                                c.getString(4),
                                c.getString(5),
                                c.getString(6),
                                c.getString(7),
                                c.getString(8)

                        )
                );
                ee.setSoluong(c.getInt(9));
                dsHoaDonChiTiet.add(ee);
                Log.d("//=====", ee.toString());
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return dsHoaDonChiTiet;
    }

    //getAll
    public List<HoaDonChiTietClass> getAllHoaDonChiTietByID(String mahoadon) {
        List<HoaDonChiTietClass> dsHoaDonChiTiet = new ArrayList<>();

        String sSQL = "SELECT maHDCT, Hoadontable.mahoadon, Hoadontable.ngaymua, " +
                "Sachtable.masach, Sachtable.matheloai, Sachtable.tensach, " +
                "Sachtable.nxb, Sachtable.giabia, Sachtable.soluong, " +
                "Hdcttable.soluong " +
                "FROM Hdcttable " +
                "INNER JOIN Hoadontable on Hdcttable.mahoadon = Hoadontable.mahoadon " +
                "INNER JOIN Sachtable on Sachtable.masach = Hdcttable.masach " +
                "WHERE Hoadontable.mahoadon='" + mahoadon + "'";


        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false) {
                HoaDonChiTietClass ee = new HoaDonChiTietClass();
                ee.setMahdct(c.getString(0));
                ee.setHoadonclass(new Hoadonclass(c.getString(1), c.getString(2)));
                ee.setSachclass(
                        new Sachclass(
                                c.getString(c.getColumnIndex("masach")),
                                c.getString(c.getColumnIndex("matheloai")),
                                c.getString(c.getColumnIndex("tensach")),
                                c.getString(c.getColumnIndex("nxb")),
                                c.getString(c.getColumnIndex("soluong")),
                                c.getString(c.getColumnIndex("giabia"))
                        )
                );

                Log.e("số lượng",c.getString(c.getColumnIndex("soluong")));
                Log.e("giá bìa",c.getString(c.getColumnIndex("giabia")));
                ee.setSoluong(c.getInt(9));
                dsHoaDonChiTiet.add(ee);
                Log.d("//=====", ee.toString());
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return dsHoaDonChiTiet;
    }

//    //update
//    public int updateHoaDonChiTiet(ChiTietHoaDon hd) {
//        ContentValues values = new ContentValues();
//        Log.e("AAAAAAAAAAAA", hd.getMaHDCT()+"");
//        values.put("maHDCT", hd.getMaHDCT());
//        values.put("mahoadon", hd.getHoaDon().getMaHoaDon());
//        values.put("maSach", hd.getSach().getMaSach());
//        values.put("soLuong", hd.getSoLuongMua());
//        int result = db.update(TABLE_NAME, values, "maHDCT=?", new
//                String[]{String.valueOf(hd.getMaHDCT())});
//        if (result == 0) {
//            return -1;
//        }
//        return 1;
//    }

//    //delete
//    public int deleteHoaDonChiTietByID(String maHDCT) {
//        int result = db.delete(TABLE_NAME, "maHDCT=?", new String[]{maHDCT});
//        if (result == 0)
//            return -1;
//        return 1;
//    }

    //check
    public boolean checkHoaDon(String maHoaDon) {
        //SELECT
        String[] columns = {"maHoaDon"};
        //WHERE clause
        String selection = "maHoaDon=?";
        //WHERE clause arguments
        String[] selectionArgs = {maHoaDon};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME5, columns, selection, selectionArgs, null, null,
                    null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if (i <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getDoanhThuTheoNgay() {
        double doanhThu = 0;

        String sSQL = "SELECT SUM(tongtien) " +
                "from (" +
                "SELECT SUM(Sach.giaBia * HoaDonChiTiet.soLuong) as 'tongtien' " +
                "FROM HoaDon " +
                "INNER JOIN HoaDonChiTiet on HoaDon.maHoaDon = HoaDonChiTiet.maHoaDon " +
                "INNER JOIN Sach on HoaDonChiTiet.maSach = Sach.maSach " +
//                "where HoaDon.ngayMua = date('now') " +
                "GROUP BY HoaDonChiTiet.maSach" +
                ")tmp";

        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public int deleteHoaDonChiTietByID(String maHDCT) {
        int result = db.delete(TABLE_NAME5, "maHDCT=?", new String[]{maHDCT});
        if (result == 0)
            return -1;
        return 1;
    }

    public double getDoanhThuTheoThang() {
        double doanhThu = 0;

        String sSQL = "SELECT SUM(tongtien) " +
                "from (" +
                "SELECT SUM(Sach.giaBia * HoaDonChiTiet.soLuong) as 'tongtien' " +
                "FROM HoaDon " +
                "INNER JOIN HoaDonChiTiet on HoaDon.maHoaDon = HoaDonChiTiet.maHoaDon " +
                "INNER JOIN Sach on HoaDonChiTiet.maSach = Sach.maSach " +
                "where strftime('%m',HoaDon.ngayMua) = strftime('%m','now') " +
                "GROUP BY HoaDonChiTiet.maSach" +
                ")tmp";

        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoNam() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) " +
                "from (" +
                "SELECT SUM(Sach.giaBia * HoaDonChiTiet.soLuong) as 'tongtien' " +
                "FROM HoaDon " +
                "INNER JOIN HoaDonChiTiet on HoaDon.maHoaDon = HoaDonChiTiet.maHoaDon " +
                "INNER JOIN Sach on HoaDonChiTiet.maSach = Sach.maSach " +
                "where strftime('%Y',HoaDon.ngayMua) = strftime('%Y','now') " +
                "GROUP BY HoaDonChiTiet.maSach" +
                ")tmp";

        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

}
