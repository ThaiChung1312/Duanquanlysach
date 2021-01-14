package vn.edu.poly.duanquanlysach.model;

public class Sachclass {
    private String masach, matheloai, tensach, nxb, soluong,giabia;

    public Sachclass() {
    }

    public Sachclass(String masach, String matheloai, String tensach, String nxb, String soluong, String giabia) {
        this.masach = masach;
        this.matheloai = matheloai;
        this.tensach = tensach;
        this.nxb = nxb;
        this.soluong = soluong;
        this.giabia = giabia;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getGiabia() {
        return giabia;
    }

    public void setGiabia(String giabia) {
        this.giabia = giabia;
    }

    @Override
    public String toString() {
        return "Sachclass{" +
                "masach='" + masach + '\'' +
                ", matheloai='" + matheloai + '\'' +
                ", tensach='" + tensach + '\'' +
                ", nxb='" + nxb + '\'' +
                ", soluong='" + soluong + '\'' +
                ", giabia='" + giabia + '\'' +
                '}';
    }
}