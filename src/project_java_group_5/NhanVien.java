package project_java_group_5;

import java.util.Calendar;

public abstract class NhanVien {
    // properties:

    private String ten;
    private String quocTich;
    private char gioiTinh;
    private String ngaySinh;
    private String ngayVaoLam;

    // constructor:
    public NhanVien() {
    }

    public NhanVien(String ten, String quocTich, char gioiTinh, String ngaySinh, String ngayVaoLam) {
        this.ten = ten;
        this.quocTich = quocTich;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.ngayVaoLam = ngayVaoLam;
    }

    // get and set:
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public char getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(char gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public int getTuoi(String ngaySinh) {
        //Lay nam hien tai
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        
        //Lay nam Nhan Vien sinh ra
        String[] parts = ngaySinh.trim().split("\\s*/\\s*");
        int year1 = Integer.parseInt(parts[2]); //Lay nam sinh
        
        return year1 - year;
    }
    
    public int hesoLuong(String ngayVaoLam){
        Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        
        //Lay nam Nhan Vien vao lam
        String[] parts = ngayVaoLam.trim().split("\\s*/\\s*");
        int year1 = Integer.parseInt(parts[2]); //Lay nam vao lam
        
        int nam = year1 - year;
        if(nam >= 0 && nam <10){ //Duoi 10 nam
            return 1;
        }
        else if(nam >= 10 && nam <20){
            return 2;
        }
        return 3;
    }
    // @req: Tinh luong:
    abstract double tinhLuong();
}
