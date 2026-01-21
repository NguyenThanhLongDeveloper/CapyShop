package com.example.capyshop.admin.sanpham;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.R;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamThuocTinh;
import com.example.capyshop.common.danhmuc.DanhMuc;
import com.example.capyshop.common.sanpham.SanPham;
import com.example.capyshop.common.thuonghieu.ThuongHieu;
import com.example.capyshop.common.activity.BaseActivity;
import com.example.capyshop.common.chitietsanpham.ChiTietSanPhamAlbum;
import com.example.capyshop.common.retrofit.ApiAdmin;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminThemSanPhamActivity extends BaseActivity {

    Toolbar tbAdminQuanLySanPhamBieuMau;
    TextInputEditText etAdminQuanLySanPhamBieuMauTen, etAdminQuanLySanPhamBieuMauGia, etAdminQuanLySanPhamBieuMauTonKho, etAdminQuanLySanPhamBieuMauMoTa;
    Spinner spAdminQuanLySanPhamBieuMauDanhMuc, spAdminQuanLySanPhamBieuMauThuongHieu;
    LinearLayout llAdminQuanLySanPhamBieuMauHinhAnhKhungChua;
    RecyclerView rvAdminQuanLySanPhamBieuMauThuocTinh; // Replace layoutContainerThuocTinh
    LinearLayout llAdminQuanLySanPhamBieuMauThemAnhNutBam;
    TextView tvAdminQuanLySanPhamBieuMauTieuDe;
    ImageView ivAdminQuanLySanPhamBieuMauLuuThanhCongCu;
    AppCompatButton btAdminQuanLySanPhamBieuMauLuu, btAdminQuanLySanPhamBieuMauHuy;

    ApiAdmin apiAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<DanhMuc> listDanhMuc = new ArrayList<>();
    List<ThuongHieu> listThuongHieu = new ArrayList<>();
    List<ChiTietSanPhamAlbum> listAlbum = new ArrayList<>();
    List<ChiTietSanPhamThuocTinh> listThuocTinh = new ArrayList<>();
    AdminThuocTinhAdapter attributeAdapter; // New Adapter

    boolean isEdit = false;
    SanPham sanPhamEdit;

    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.admin_quanlysanpham_themsanpham_activity);
        super.onCreate(savedInstanceState);

        apiAdmin = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiAdmin.class);

        // Initialize Image Picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        if (uri != null) {
                            uploadImage(uri);
                        }
                    }
                });

        anhXaView();
        caiDatToolbar();

        // Init RecyclerView
        attributeAdapter = new AdminThuocTinhAdapter(this, listThuocTinh);
        rvAdminQuanLySanPhamBieuMauThuocTinh.setLayoutManager(new LinearLayoutManager(this));
        rvAdminQuanLySanPhamBieuMauThuocTinh.setAdapter(attributeAdapter);

        layDanhSachDanhMuc();
        xuLySuKien();

        checkIntent();
    }

    private void uploadImage(Uri uri) {
        Utils.taiAnhLenServer(this, uri, "sanpham", compositeDisposable, new Utils.OnUploadCallback() {
            @Override
            public void onThanhCong(String tenFileMoi) {
                // Server returns file name, we need full URL to display
                // Assuming images are stored in "common/images/" relative to base URL?
                // Or just filenames?
                // The API upload.php returns 'name' which is filename.
                // We will store the full URL in database logic (or assume a base image URL).
                // Let's assume for now we store the full URL or relative path.
                // The current app seems to store full URLs or valid paths.

                // Construct full URL (Temporary: modify if server structure differs)
                String fullUrl = Utils.BASE_URL + "common/images/" + tenFileMoi;
                listAlbum.add(new ChiTietSanPhamAlbum(fullUrl));
                addImageToLayout(fullUrl);
                Toast.makeText(AdminThemSanPhamActivity.this, "Upload thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onThatBai(String message) {
                Toast.makeText(AdminThemSanPhamActivity.this, "Upload thất bại: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXaView() {
        tbAdminQuanLySanPhamBieuMau = findViewById(R.id.tb_admin_quan_ly_san_pham_bieu_mau);
        etAdminQuanLySanPhamBieuMauTen = findViewById(R.id.et_admin_quan_ly_san_pham_bieu_mau_ten);
        etAdminQuanLySanPhamBieuMauGia = findViewById(R.id.et_admin_quan_ly_san_pham_bieu_mau_gia);
        etAdminQuanLySanPhamBieuMauTonKho = findViewById(R.id.et_admin_quan_ly_san_pham_bieu_mau_ton_kho);
        etAdminQuanLySanPhamBieuMauMoTa = findViewById(R.id.et_admin_quan_ly_san_pham_bieu_mau_mo_ta);
        spAdminQuanLySanPhamBieuMauDanhMuc = findViewById(R.id.sp_admin_quan_ly_san_pham_bieu_mau_danh_muc);
        spAdminQuanLySanPhamBieuMauThuongHieu = findViewById(R.id.sp_admin_quan_ly_san_pham_bieu_mau_thuong_hieu);

        ivAdminQuanLySanPhamBieuMauLuuThanhCongCu = findViewById(R.id.iv_admin_quan_ly_san_pham_bieu_mau_luu_thanh_cong_cu);

        llAdminQuanLySanPhamBieuMauHinhAnhKhungChua = findViewById(R.id.ll_admin_quan_ly_san_pham_bieu_mau_hinh_anh_khung_chua);
        rvAdminQuanLySanPhamBieuMauThuocTinh = findViewById(R.id.rv_admin_quan_ly_san_pham_bieu_mau_thuoc_tinh); // Bind RecyclerView

        llAdminQuanLySanPhamBieuMauThemAnhNutBam = findViewById(R.id.ll_admin_quan_ly_san_pham_bieu_mau_them_anh_nut_bam);
        // tvThemThuocTinh = findViewById(R.id.tv_them_thuoctinh);

        btAdminQuanLySanPhamBieuMauLuu = findViewById(R.id.bt_admin_quan_ly_san_pham_bieu_mau_luu);
        btAdminQuanLySanPhamBieuMauHuy = findViewById(R.id.bt_admin_quan_ly_san_pham_bieu_mau_huy);
        tvAdminQuanLySanPhamBieuMauTieuDe = findViewById(R.id.tv_admin_quan_ly_san_pham_bieu_mau_tieu_de);
    }

    private void caiDatToolbar() {
        setSupportActionBar(tbAdminQuanLySanPhamBieuMau);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        tbAdminQuanLySanPhamBieuMau.setNavigationOnClickListener(v -> finish());
    }

    private void checkIntent() {
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        if (isEdit) {
            tvAdminQuanLySanPhamBieuMauTieuDe.setText("Sửa Sản Phẩm");
            sanPhamEdit = (SanPham) getIntent().getSerializableExtra("sanpham");
            if (sanPhamEdit != null) {
                // Fill data
                etAdminQuanLySanPhamBieuMauTen.setText(sanPhamEdit.getTenSanPham());
                etAdminQuanLySanPhamBieuMauGia.setText(String.valueOf(sanPhamEdit.getGiaSanPham()));
                etAdminQuanLySanPhamBieuMauTonKho.setText(String.valueOf(sanPhamEdit.getSoLuongTon()));
                etAdminQuanLySanPhamBieuMauMoTa.setText(sanPhamEdit.getMoTaSanPham());

                // Images
                if (sanPhamEdit.getAlbum() != null) {
                    listAlbum.addAll(sanPhamEdit.getAlbum());
                    for (ChiTietSanPhamAlbum album : listAlbum)
                        addImageToLayout(album.getHinhAnh());
                } else if (sanPhamEdit.getHinhAnhSanPham() != null) {
                    // Add main image if list is empty
                    ChiTietSanPhamAlbum mainAlbum = new ChiTietSanPhamAlbum(sanPhamEdit.getHinhAnhSanPham());
                    listAlbum.add(mainAlbum);
                    addImageToLayout(sanPhamEdit.getHinhAnhSanPham());
                }

                // Attributes
                if (sanPhamEdit.getThuocTinh() != null) {
                    listThuocTinh.addAll(sanPhamEdit.getThuocTinh());
                    attributeAdapter.notifyDataSetChanged(); // Updates UI
                }
            }
            ivAdminQuanLySanPhamBieuMauLuuThanhCongCu.setVisibility(View.VISIBLE);
        } else {
            tvAdminQuanLySanPhamBieuMauTieuDe.setText("Thêm Sản Phẩm");
            ivAdminQuanLySanPhamBieuMauLuuThanhCongCu.setVisibility(View.GONE);
        }
    }

    private void layDanhSachDanhMuc() {
        compositeDisposable.add(apiAdmin.layDanhSachDanhMuc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                listDanhMuc = model.getResult();
                                List<String> tenDanhMuc = new ArrayList<>();
                                for (DanhMuc dm : listDanhMuc) {
                                    tenDanhMuc.add(dm.getTenDanhMuc());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                        android.R.layout.simple_spinner_item, tenDanhMuc);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Handle Item Selection
                                spAdminQuanLySanPhamBieuMauDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                                            long id) {
                                        // String selectedName = (String) parent.getItemAtPosition(position);
                                        // Better to use index since we have the list
                                        if (position >= 0 && position < listDanhMuc.size()) {
                                            int maDM = listDanhMuc.get(position).getMaDanhMuc();
                                            loadAttributesForCategory(maDM);
                                            loadBrandsForCategory(maDM);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });

                                spAdminQuanLySanPhamBieuMauDanhMuc.setAdapter(adapter);

                                // Set selected category if edit
                                if (isEdit && sanPhamEdit != null) {
                                    for (int i = 0; i < listDanhMuc.size(); i++) {
                                        if (listDanhMuc.get(i).getMaDanhMuc() == sanPhamEdit.getMaDanhMuc()) {
                                            spAdminQuanLySanPhamBieuMauDanhMuc.setSelection(i);
                                            break;
                                        }
                                    }
                                }
                            }
                        },
                        throwable -> {
                        }));
    }

    private void loadAttributesForCategory(int maDanhMuc) {
        compositeDisposable.add(apiAdmin.layDanhSachThuocTinhTheoDanhMuc(maDanhMuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                listThuocTinh.clear();
                                // layoutContainerThuocTinh.removeAllViews(); // Removed
                                attributeAdapter.notifyDataSetChanged();
                                List<ChiTietSanPhamThuocTinh> fetchedAttributes = model.getResult();
                                if (fetchedAttributes != null) {
                                    for (ChiTietSanPhamThuocTinh newAttr : fetchedAttributes) {
                                        newAttr.setGiaTri(new ArrayList<>()); // Init empty values

                                        // If in Edit mode and category matches, preserve values
                                        if (isEdit && sanPhamEdit != null && sanPhamEdit.getMaDanhMuc() == maDanhMuc) {
                                            if (sanPhamEdit.getThuocTinh() != null) {
                                                for (ChiTietSanPhamThuocTinh editAttr : sanPhamEdit
                                                        .getThuocTinh()) {
                                                    if (editAttr.getTenThuocTinh().equals(newAttr.getTenThuocTinh())) {
                                                        newAttr.setGiaTri(editAttr.getGiaTri());
                                                        break;
                                                    }
                                                }
                                            }
                                        }

                                        listThuocTinh.add(newAttr);
                                    }
                                    attributeAdapter.notifyDataSetChanged(); // Refresh RecyclerView
                                }
                            }
                        },
                        throwable -> Toast
                                .makeText(this, "Lỗi tải thuộc tính: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                .show()));
    }

    private void loadBrandsForCategory(int maDanhMuc) {
        compositeDisposable.add(apiAdmin.layDanhSachThuongHieuTheoDanhMuc(maDanhMuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                listThuongHieu = model.getResult();
                                List<String> tenThuongHieu = new ArrayList<>();
                                for (ThuongHieu th : listThuongHieu) {
                                    tenThuongHieu.add(th.getTenThuongHieu());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                        android.R.layout.simple_spinner_item, tenThuongHieu);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spAdminQuanLySanPhamBieuMauThuongHieu.setAdapter(adapter);

                                // If Editing, set selected brand
                                if (isEdit && sanPhamEdit != null && sanPhamEdit.getMaThuongHieu() > 0) {
                                    for (int i = 0; i < listThuongHieu.size(); i++) {
                                        if (listThuongHieu.get(i).getMaThuongHieu() == sanPhamEdit.getMaThuongHieu()) {
                                            spAdminQuanLySanPhamBieuMauThuongHieu.setSelection(i);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                // Clear if no brands or error
                                spAdminQuanLySanPhamBieuMauThuongHieu.setAdapter(null);
                            }
                        },
                        throwable -> {
                            // Handle error silently or show toast?
                            // Toast.makeText(this, "Lỗi tải thương hiệu: " + throwable.getMessage(),
                            // Toast.LENGTH_SHORT).show();
                        }));
    }

    private void xuLySuKien() {
        btAdminQuanLySanPhamBieuMauHuy.setOnClickListener(v -> finish());

        llAdminQuanLySanPhamBieuMauThemAnhNutBam.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        // Manual add attribute disabled per new requirement
        // tvThemThuocTinh.setVisibility(View.GONE); // Removed

        btAdminQuanLySanPhamBieuMauLuu.setOnClickListener(v -> postSanPham());
        ivAdminQuanLySanPhamBieuMauLuuThanhCongCu.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void showDeleteConfirmation() {
        Utils.thietLapBottomSheetDialog(this, "Xóa sản phẩm", "Bạn có chắc chắn muốn xóa sản phẩm này không?", "Xóa",
                () -> deleteProduct());
    }

    private void deleteProduct() {
        if (sanPhamEdit == null)
            return;
        compositeDisposable.add(apiAdmin.xoaSanPham(sanPhamEdit.getMaSanPham())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        model -> {
                            if (model.isSuccess()) {
                                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Toast.makeText(this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                .show()));
    }

    private void addImageToLayout(String url) {
        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
        params.setMargins(16, 0, 0, 0);
        iv.setLayoutParams(params);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(this).load(url).into(iv);

        llAdminQuanLySanPhamBieuMauHinhAnhKhungChua.addView(iv);
    }



    private void postSanPham() {
        String ten = etAdminQuanLySanPhamBieuMauTen.getText().toString().trim();
        String strGia = etAdminQuanLySanPhamBieuMauGia.getText().toString().trim();
        String strKho = etAdminQuanLySanPhamBieuMauTonKho.getText().toString().trim();
        String moTa = etAdminQuanLySanPhamBieuMauMoTa.getText().toString().trim();

        if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(strGia)) {
            Toast.makeText(this, "Vui lòng nhập tên và giá", Toast.LENGTH_SHORT).show();
            return;
        }

        long gia = Long.parseLong(strGia);
        int tonKho = strKho.isEmpty() ? 0 : Integer.parseInt(strKho);

        String selectedDanhMuc = spAdminQuanLySanPhamBieuMauDanhMuc.getSelectedItem() != null ? spAdminQuanLySanPhamBieuMauDanhMuc.getSelectedItem().toString()
                : "";
        int maDanhMuc = 0;
        for (DanhMuc dm : listDanhMuc) {
            if (dm.getTenDanhMuc().equals(selectedDanhMuc)) {
                maDanhMuc = dm.getMaDanhMuc();
                break;
            }
        }

        String selectedThuongHieu = spAdminQuanLySanPhamBieuMauThuongHieu.getSelectedItem() != null
                ? spAdminQuanLySanPhamBieuMauThuongHieu.getSelectedItem().toString()
                : "";
        int maThuongHieu = 0;
        for (ThuongHieu th : listThuongHieu) {
            if (th.getTenThuongHieu().equals(selectedThuongHieu)) {
                maThuongHieu = th.getMaThuongHieu();
                break;
            }
        }

        if (maDanhMuc == 0 && !listDanhMuc.isEmpty()) {
            // Fallback or error if user didn't select valid category
            maDanhMuc = listDanhMuc.get(0).getMaDanhMuc();
        }

        // Collect Attribute Data from UI
        // Data is now directly in listThuocTinh, updated by Adapter.
        List<ChiTietSanPhamThuocTinh> finalAttributes = new ArrayList<>();
        for (ChiTietSanPhamThuocTinh tt : listThuocTinh) {
            if (tt.getGiaTri() != null && !tt.getGiaTri().isEmpty()) {
                finalAttributes.add(tt);
            }
        }

        String mainImage = listAlbum.isEmpty() ? "" : listAlbum.get(0).getHinhAnh();
        String jsonAlbum = new Gson().toJson(listAlbum);
        String jsonThuocTinh = new Gson().toJson(finalAttributes);

        if (!isEdit) {
            compositeDisposable
                    .add(apiAdmin
                            .themSanPham(ten, mainImage, gia, moTa, tonKho, maDanhMuc, maThuongHieu, jsonAlbum,
                                    jsonThuocTinh)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    model -> {
                                        if (model.isSuccess()) {
                                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> Toast
                                            .makeText(this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                            .show()));
        } else {
            compositeDisposable.add(apiAdmin
                    .suaSanPham(sanPhamEdit.getMaSanPham(), ten, mainImage, gia, moTa, tonKho, maDanhMuc, maThuongHieu,
                            jsonAlbum,
                            jsonThuocTinh)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            model -> {
                                if (model.isSuccess()) {
                                    Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> Toast.makeText(this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT)
                                    .show()));
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
