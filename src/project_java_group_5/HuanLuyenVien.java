package project_java_group_5;

public class HuanLuyenVien extends NhanVien{
    //properties:
	private int soNamKinhNghiem;	
	private double phuCap;
	
	// constructors:
	public HuanLuyenVien(){}
	public HuanLuyenVien(String ten, String quocTich, char gioiTinh, String ngaySinh, String ngayVaoLam,
			int soNamKinhNghiem, double phuCap){
		super(ten, quocTich, gioiTinh, ngaySinh, ngayVaoLam);
		this.soNamKinhNghiem = soNamKinhNghiem;		
		this.phuCap = phuCap;
	}
	
	// get and set:
	public int getSoNamKinhNghiem() {
		return soNamKinhNghiem;
	}
	public void setSoNamKinhNghiem(int soNamKinhNghiem) {
		this.soNamKinhNghiem = soNamKinhNghiem;
	}
	public double getPhuCap() {
		return phuCap;
	}
	public void setPhuCap(double phuCap) {
		this.phuCap = phuCap;
	}
	
	//methods:
	// tinh phu cap tham nien:
	public double tinhLuong(){
		return (super.hesoLuong(super.getNgayVaoLam()) * 1050000) + this.phuCap;
	}
}
