package vn.edu.poly.duanquanlysach.model;

import java.util.Date;

public class Hoadonclass {
    public String mahoadon;
    public String ngaymuahang;

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getNgaymuahang() {
        return ngaymuahang;
    }

    public void setNgaymuahang(String ngaymuahang) {
        this.ngaymuahang = ngaymuahang;
    }

    public Hoadonclass() {
    }

    public Hoadonclass(String mahoadon, String ngaymuahang) {
        this.mahoadon = mahoadon;
        this.ngaymuahang = ngaymuahang;
    }
}
