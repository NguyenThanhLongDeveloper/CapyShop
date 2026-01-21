package com.example.capyshop.user.giohang;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capyshop.common.Interface.ImageClickListener;
import com.example.capyshop.R;
import com.example.capyshop.common.retrofit.ApiUser;
import com.example.capyshop.common.retrofit.RetrofitClient;
import com.example.capyshop.common.utils.Utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserGioHangAdapter extends RecyclerView.Adapter<UserGioHangAdapter.MyViewHolder> {
    Context context;
    List<UserGioHang> array;
    ApiUser apiUser;

    // Callback về Activity
    Runnable tinhTongTienCallback;
    Runnable capNhatCheckBoxChonTatCaCallback;
    // Hỗ trợ RxJava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public UserGioHangAdapter(Context context, List<UserGioHang> array, Runnable tinhTongTienCallback, Runnable capNhatCheckBoxChonTatCaCallback) {
        this.context = context;
        this.array = array;
        this.tinhTongTienCallback = tinhTongTienCallback;
        this.capNhatCheckBoxChonTatCaCallback = capNhatCheckBoxChonTatCaCallback;
        apiUser = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiUser.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_giohang_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserGioHang userGioHang = array.get(position);
        holder.tvTenGioHang.setText(userGioHang.getTenSanPham());
        holder.tvThuocTinh.setText(hienThiThuocTinh(userGioHang));

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.tvGiaGioHang.setText(formatter.format(userGioHang.getGiaSanPham()) + " đ");

        holder.tvSoLuongGioHang.setText(String.valueOf(userGioHang.getSoLuong()));
        Glide.with(context).load(userGioHang.getHinhAnhSanPham()).into(holder.ivHinhAnhGioHang);

        // Xử lý Checkbox chọn sản phẩm để tính tiền
        holder.cbItemGioHang.setOnCheckedChangeListener(null);
        holder.cbItemGioHang.setChecked(userGioHang.isTrangThaiCheckBox());

        holder.cbItemGioHang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            userGioHang.setTrangThaiCheckBox(isChecked);

            if (isChecked) {
                if (!Utils.mangMuaHang.contains(userGioHang)) {
                    Utils.mangMuaHang.add(userGioHang);
                }
            } else {
                Utils.mangMuaHang.remove(userGioHang);
            }

            tinhTongTienCallback.run();
            capNhatCheckBoxChonTatCaCallback.run();
        });

        // Xử lý sự kiện click
        holder.setImageClickListener((view, pos, isLongClick) -> {
            int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
            int maGioHang = array.get(pos).getMaGioHang();

            if (isLongClick == 0) { // Tăng số lượng
                if (array.get(pos).getSoLuong() < 10) {
                    int soLuongMoi = array.get(pos).getSoLuong() + 1;
                    capNhatSoLuongGioHang(maNguoiDung, maGioHang, soLuongMoi, pos, holder);
                }
            } else if (isLongClick == 1) { // Giảm số lượng
                if (array.get(pos).getSoLuong() > 1) {
                    int soLuongMoi = array.get(pos).getSoLuong() - 1;
                    capNhatSoLuongGioHang(maNguoiDung, maGioHang, soLuongMoi, pos, holder);
                } else {
                    xacNhanXoaSanPham(userGioHang);
                }
            } else if (isLongClick == 2) { // Nút Xóa trực tiếp
                xacNhanXoaSanPham(userGioHang);
            }
        });
    }

    // Hàm gọi API cập nhật số lượng
    private void capNhatSoLuongGioHang(int maNguoiDung, int maGioHang, int soLuongMoi, int pos, MyViewHolder holder) {
        compositeDisposable.add(apiUser.capNhatGioHang(maNguoiDung, maGioHang, soLuongMoi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {
                            if (gioHangModel.isSuccess()) {
                                array.get(pos).setSoLuong(soLuongMoi);
                                holder.tvSoLuongGioHang.setText(String.valueOf(soLuongMoi));
                                tinhTongTienCallback.run();
                            } else {
                                Toast.makeText(context, gioHangModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> Log.e("UpdateCartError", throwable.getMessage())
                ));
    }

    // Hàm hiển thị Dialog và gọi API xóa sản phẩm
    private void xacNhanXoaSanPham(UserGioHang userGioHang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng?");
        builder.setPositiveButton("Đồng ý", (dialog, which) -> {
            int maNguoiDung = Utils.userNguoiDung_Current.getMaNguoiDung();
            int maGioHang = userGioHang.getMaGioHang(); // Lấy trực tiếp từ đối tượng

            compositeDisposable.add(apiUser.xoaGioHang(maNguoiDung, maGioHang)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            gioHangModel -> {
                                if (gioHangModel.isSuccess()) {
                                    // 1. Tìm vị trí hiện tại của đối tượng trong mảng trước khi xóa
                                    int indexReal = array.indexOf(userGioHang);

                                    if (indexReal != -1) {
                                        // 2. Xóa khỏi danh sách mua hàng (nếu có)
                                        Utils.mangMuaHang.remove(userGioHang);

                                        // 3. Xóa khỏi danh sách hiển thị
                                        array.remove(indexReal);

                                        // 4. Cập nhật giao diện mượt mà hơn notifyDataSetChanged
                                        notifyItemRemoved(indexReal);
                                        notifyItemRangeChanged(indexReal, array.size());

                                        // 5. Tính lại tiền
                                        tinhTongTienCallback.run();
                                    }
                                }
                            },
                            throwable -> Log.e("DeleteCartError", throwable.getMessage())
                    ));
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private String hienThiThuocTinh(UserGioHang userGioHang) {
        if (userGioHang.getThuocTinh() == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (UserGioHangThuocTinh userGioHangThuocTinh : userGioHang.getThuocTinh()) {
            stringBuilder.append(userGioHangThuocTinh.getTenThuocTinh()).append(": ");
            for (int i = 0; i < userGioHangThuocTinh.getGiaTri().size(); i++) {
                stringBuilder.append(userGioHangThuocTinh.getGiaTri().get(i).getValue());
                if (i < userGioHangThuocTinh.getGiaTri().size() - 1) stringBuilder.append(" ");
            }
            stringBuilder.append(" | ");
        }
        return stringBuilder.toString();
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public void cleanUp() {
        compositeDisposable.clear();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cbItemGioHang;
        ImageView ivHinhAnhGioHang, ivTruGioHang, ivCongGioHang, ivXoaGioHang;
        TextView tvTenGioHang, tvGiaGioHang, tvSoLuongGioHang, tvThuocTinh;
        ImageClickListener imageClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cbItemGioHang = itemView.findViewById(R.id.cb_item_gio_hang);
            ivHinhAnhGioHang = itemView.findViewById(R.id.iv_item_hinh_anh_gio_hang);
            ivTruGioHang = itemView.findViewById(R.id.iv_item_tru_gio_hang);
            ivCongGioHang = itemView.findViewById(R.id.iv_item_cong_gio_hang);
            ivXoaGioHang = itemView.findViewById(R.id.iv_item_xoa_gio_hang);
            tvTenGioHang = itemView.findViewById(R.id.tv_item_ten_gio_hang);
            tvGiaGioHang = itemView.findViewById(R.id.tv_item_gia_gio_hang);
            tvSoLuongGioHang = itemView.findViewById(R.id.tv_item_so_luong_gio_hang);
            tvThuocTinh = itemView.findViewById(R.id.tv_item_thuoc_tinh_gio_hang);

            ivCongGioHang.setOnClickListener(this);
            ivTruGioHang.setOnClickListener(this);
            ivXoaGioHang.setOnClickListener(this);
        }

        public void setImageClickListener(ImageClickListener imageClickListener) {
            this.imageClickListener = imageClickListener;
        }

        @Override
        public void onClick(View view) {
            if (view == ivCongGioHang) {
                imageClickListener.onImageClick(view, getAdapterPosition(), 0);
            } else if (view == ivTruGioHang) {
                imageClickListener.onImageClick(view, getAdapterPosition(), 1);
            } else if (view == ivXoaGioHang) {
                imageClickListener.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}