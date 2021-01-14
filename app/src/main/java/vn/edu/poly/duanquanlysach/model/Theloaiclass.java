package vn.edu.poly.duanquanlysach.model;

public class Theloaiclass {
    public String matheloai,tentheloai,mota,vitri;

    public Theloaiclass() {
    }

    public Theloaiclass(String matheloai, String tentheloai, String mota, String vitri) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.mota = mota;
        this.vitri = vitri;
    }

    @Override
    public String toString() {
        return getMatheloai();
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }
}
