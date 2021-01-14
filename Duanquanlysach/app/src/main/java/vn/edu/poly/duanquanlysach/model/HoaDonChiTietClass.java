package vn.edu.poly.duanquanlysach.model;

public class HoaDonChiTietClass {
    public String mahdct;
    public Hoadonclass hoadonclass;
    public Sachclass sachclass;
public  int soluong;
    public HoaDonChiTietClass() {
    }

    public HoaDonChiTietClass(String mahdct, Hoadonclass hoadonclass, Sachclass sachclass, int soluong) {
        this.mahdct = mahdct;
        this.hoadonclass = hoadonclass;
        this.sachclass = sachclass;
        this.soluong = soluong;
    }

    public String getMahdct() {
        return mahdct;
    }

    public void setMahdct(String mahdct) {
        this.mahdct = mahdct;
    }

    public Hoadonclass getHoadonclass() {
        return hoadonclass;
    }

    public void setHoadonclass(Hoadonclass hoadonclass) {
        this.hoadonclass = hoadonclass;
    }

    public Sachclass getSachclass() {
        return sachclass;
    }

    public void setSachclass(Sachclass sachclass) {
        this.sachclass = sachclass;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
