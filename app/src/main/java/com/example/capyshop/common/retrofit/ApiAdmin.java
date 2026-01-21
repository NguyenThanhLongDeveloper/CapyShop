package com.example.capyshop.common.retrofit;

import com.example.capyshop.common.danhmuc.DanhMucModel;
import com.example.capyshop.common.donvivanchuyen.DonViVanChuyenModel;
import com.example.capyshop.admin.main.AdminMainTongDoanhThuModel;
import com.example.capyshop.common.nguoidung.NguoiDungModel;
import com.example.capyshop.common.quangcao.QuangCaoModel;
import com.example.capyshop.common.phuongthucthanhtoan.PhuongThucThanhToanModel;
import com.example.capyshop.common.donhang.DonHangModel;
import com.example.capyshop.common.sanpham.SanPhamModel;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamThuocTinhModel;
import com.example.capyshop.common.thuonghieu.ThuongHieuModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiAdmin {
        // Lấy tổng doanh thu
        @GET("admin/main/admin_lay_tongdoanhthu.php")
        Observable<AdminMainTongDoanhThuModel> layTongDoanhThu();

        // Quản lý danh mục
        @GET("admin/danhmuc/admin_lay_danhmuc.php")
        Observable<DanhMucModel> layDanhSachDanhMuc();

        @FormUrlEncoded
        @POST("admin/danhmuc/admin_lay_chitiet_danhmuc.php")
        Observable<DanhMucModel> layChiTietDanhMuc(
                @Field("maDanhMuc") int maDanhMuc);

        @FormUrlEncoded
        @POST("admin/danhmuc/admin_them_danhmuc.php")
        Observable<DanhMucModel> themDanhMuc(
                @Field("tenDanhMuc") String tenDanhMuc,
                @Field("hinhAnhDanhMuc") String hinhAnhDanhMuc,
                @Field("danhSachThuongHieu") String danhSachThuongHieu,
                @Field("danhSachThuocTinh") String danhSachThuocTinh);

        @FormUrlEncoded
        @POST("admin/danhmuc/admin_sua_danhmuc.php")
        Observable<DanhMucModel> suaDanhMuc(
                @Field("maDanhMuc") int maDanhMuc,
                @Field("tenDanhMuc") String tenDanhMuc,
                @Field("hinhAnhDanhMuc") String hinhAnhDanhMuc,
                @Field("danhSachThuongHieu") String danhSachThuongHieu,
                @Field("danhSachThuocTinh") String danhSachThuocTinh);

        @FormUrlEncoded
        @POST("admin/danhmuc/admin_xoa_danhmuc.php")
        Observable<DanhMucModel> xoaDanhMuc(
                @Field("maDanhMuc") int maDanhMuc);

        // Quản lý quảng cáo
        @GET("admin/quangcao/admin_lay_quangcao.php")
        Observable<QuangCaoModel> layDanhSachQuangCao();

        @FormUrlEncoded
        @POST("admin/quangcao/admin_them_quangcao.php")
        Observable<QuangCaoModel> themQuangCao(
                @Field("hinhAnhQuangCao") String hinhAnhQuangCao);

        @FormUrlEncoded
        @POST("admin/quangcao/admin_xoa_quangcao.php")
        Observable<QuangCaoModel> xoaQuangCao(
                @Field("maQuangCao") int maQuangCao);

        // Quản lý đơn vị vận chuyển
        @GET("admin/donvivanchuyen/admin_lay_donvivanchuyen.php")
        Observable<DonViVanChuyenModel> layDanhSachDonViVanChuyen();

        @FormUrlEncoded
        @POST("admin/donvivanchuyen/admin_them_donvivanchuyen.php")
        Observable<DonViVanChuyenModel> themDonViVanChuyen(
                @Field("tenDonViVanChuyen") String tenDonViVanChuyen,
                @Field("giaDonViVanChuyen") long giaDonViVanChuyen,
                @Field("trangThai") String trangThai);

        @FormUrlEncoded
        @POST("admin/donvivanchuyen/admin_xoa_donvivanchuyen.php")
        Observable<DonViVanChuyenModel> xoaDonViVanChuyen(
                @Field("maDonViVanChuyen") int maDonViVanChuyen);

        @FormUrlEncoded
        @POST("admin/donvivanchuyen/admin_update_donvivanchuyen.php")
        Observable<DonViVanChuyenModel> capNhatDonViVanChuyen(
                @Field("maDonViVanChuyen") int maDonViVanChuyen,
                @Field("tenDonViVanChuyen") String tenDonViVanChuyen,
                @Field("giaDonViVanChuyen") long giaDonViVanChuyen,
                @Field("trangThai") String trangThai);

        // Quản lý người dùng
        @GET("admin/nguoidung/admin_lay_danhsach_nguoidung.php")
        Observable<NguoiDungModel> layDanhSachNguoiDung();

        @FormUrlEncoded
        @POST("admin/nguoidung/admin_khoa_taikhoan.php")
        Observable<NguoiDungModel> khoaTaiKhoan(
                @Field("maNguoiDung") int maNguoiDung,
                @Field("trangThai") String trangThai);

        // Quản lý phương thức thanh toán
        @GET("admin/thanhtoan/admin_lay_phuongthucthanhtoan.php")
        Observable<PhuongThucThanhToanModel> layDanhSachPhuongThucThanhToan();

        @FormUrlEncoded
        @POST("admin/thanhtoan/admin_update_phuongthucthanhtoan.php")
        Observable<PhuongThucThanhToanModel> updateTrangThaiPhuongThucThanhToan(
                @Field("maPhuongThucThanhToan") int maPhuongThucThanhToan,
                @Field("trangThai") String trangThai);

        // Quản lý đơn hàng
        @FormUrlEncoded
        @POST("admin/donhang/admin_lay_donhang.php")
        Observable<DonHangModel> layDanhSachDonHang(
                @Field("trangThai") String trangThai);

        @FormUrlEncoded
        @POST("admin/donhang/admin_update_trangthai_donhang.php")
        Observable<DonHangModel> updateTrangThaiDonHang(
                @Field("maDonHang") int maDonHang,
                @Field("trangThai") String trangThai);

        // Quản lý sản phẩm
        @GET("admin/sanpham/admin_lay_sanpham.php")
        Observable<SanPhamModel> layDanhSachSanPham();

        @FormUrlEncoded
        @POST("admin/sanpham/admin_them_sanpham.php")
        Observable<SanPhamModel> themSanPham(
                @Field("tenSanPham") String tenSanPham,
                @Field("hinhAnhSanPham") String hinhAnhSanPham,
                @Field("giaSanPham") long giaSanPham,
                @Field("moTaSanPham") String moTaSanPham,
                @Field("soLuongTon") int soLuongTon,
                @Field("maDanhMuc") int maDanhMuc,
                @Field("maThuongHieu") int maThuongHieu,
                @Field("albumSanPham") String albumSanPham,
                @Field("thuocTinhSanPham") String thuocTinhSanPham);

        @FormUrlEncoded
        @POST("admin/sanpham/admin_sua_sanpham.php")
        Observable<SanPhamModel> suaSanPham(
                @Field("maSanPham") int maSanPham,
                @Field("tenSanPham") String tenSanPham,
                @Field("hinhAnhSanPham") String hinhAnhSanPham,
                @Field("giaSanPham") long giaSanPham,
                @Field("moTaSanPham") String moTaSanPham,
                @Field("soLuongTon") int soLuongTon,
                @Field("maDanhMuc") int maDanhMuc,
                @Field("maThuongHieu") int maThuongHieu,
                @Field("albumSanPham") String albumSanPham,
                @Field("thuocTinhSanPham") String thuocTinhSanPham);

        @FormUrlEncoded
        @POST("admin/sanpham/admin_xoa_sanpham.php")
        Observable<SanPhamModel> xoaSanPham(
                @Field("maSanPham") int maSanPham);

        @FormUrlEncoded
        @POST("admin/sanpham/admin_lay_thuoctinh_theo_danhmuc.php")
        Observable<ChiTietSanPhamThuocTinhModel> layDanhSachThuocTinhTheoDanhMuc(
                @Field("maDanhMuc") int maDanhMuc);

        @FormUrlEncoded
        @POST("admin/sanpham/admin_lay_thuonghieu_theo_danhmuc.php")
        Observable<ThuongHieuModel> layDanhSachThuongHieuTheoDanhMuc(
                @Field("maDanhMuc") int maDanhMuc);

        @FormUrlEncoded
        @POST("admin/chitietsanpham/admin_lay_chitiet_sanpham.php")
        Observable<SanPhamModel> layChiTietSanPham(
                @Field("maSanPham") int maSanPham);
}