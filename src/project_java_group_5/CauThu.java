package project_java_group_5;

public class CauThu extends NhanVien {
	// properties:
	private String viTriThiDau;
	private int soLuotTranThamGia;
	private int banThang;
	private double luongThoaThuan;

	// constructors:
	public CauThu() {
	}

	public CauThu(String ten, String quocTich, char gioiTinh, String ngaySinh, String ngayVaoLam,
			String viTriThiDau, int soLuotTranThamGia, int banThang, double luongThoaThuan) {
		super(ten, quocTich, gioiTinh, ngaySinh, ngayVaoLam);
		this.viTriThiDau = viTriThiDau;
		this.soLuotTranThamGia = soLuotTranThamGia;
		this.banThang = banThang;
		this.luongThoaThuan = luongThoaThuan;
	}

	// get and set:
	public String getViTriThiDau() {
		return viTriThiDau;
	}

	public void setViTriThiDau(String viTriThiDau) {
		this.viTriThiDau = viTriThiDau;
	}

	public int getSoLuotTranThamGia() {
		return soLuotTranThamGia;
	}

	public void setSoLuotTranThamGia(int soLuotTranThamGia) {
		this.soLuotTranThamGia = soLuotTranThamGia;
	}

	public int getBanThang() {
		return banThang;
	}

	public void setBanThang(int banThang) {
		this.banThang = banThang;
	}

	public double getLuongThoaThuan() {
		return luongThoaThuan;
	}

	public void setLuongThoaThuan(double luongThoaThuan) {
		this.luongThoaThuan = luongThoaThuan;
	}

	// methods:
	// tinh luong:
	public double tinhLuong() {
		return this.luongThoaThuan * super.hesoLuong(super.getNgayVaoLam());
	}
}
