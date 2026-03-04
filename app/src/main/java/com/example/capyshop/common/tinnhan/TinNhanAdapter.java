package com.example.capyshop.common.tinnhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capyshop.R;

import java.util.List;

public class TinNhanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<TinNhan> array;
    private String maNguoiGuiTinNhan;
    private static final int TYPE_GUI = 1;
    private static final int TYPE_NHAN = 2;

    public TinNhanAdapter(Context context, List<TinNhan> array, String maNguoiGuiTinNhan) {
        this.context = context;
        this.array = array;
        this.maNguoiGuiTinNhan = maNguoiGuiTinNhan;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_GUI) {
            view = LayoutInflater.from(context).inflate(R.layout.common_tin_nhan_gui_item, parent, false);
            return new TinNhanGuiViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.common_tin_nhan_nhan_item, parent, false);
            return new TinNhanNhanViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_GUI) {
            ((TinNhanGuiViewHolder) holder).tvNoiDungTinNhanGui.setText(array.get(position).noiDungTinNhan);
            ((TinNhanGuiViewHolder) holder).tvThoiGianTinNhanGui.setText(array.get(position).ngayGuiTinNhan);
        } else {
            ((TinNhanNhanViewHolder) holder).tvNoiDungTinNhanNhan.setText(array.get(position).noiDungTinNhan);
            ((TinNhanNhanViewHolder) holder).tvThoiGianTinNhanNhan.setText(array.get(position).ngayGuiTinNhan);
        }

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (array.get(position).maNguoiDungGuiTinNhan.equals(maNguoiGuiTinNhan)) {
            return TYPE_GUI;
        } else {
            return TYPE_NHAN;
        }

    }

    class TinNhanGuiViewHolder extends RecyclerView.ViewHolder{
        TextView tvNoiDungTinNhanGui, tvThoiGianTinNhanGui;

        public TinNhanGuiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoiDungTinNhanGui = itemView.findViewById(R.id.tv_noi_dung_tin_nhan_gui);
            tvThoiGianTinNhanGui = itemView.findViewById(R.id.tv_thoi_gian_tin_nhan_gui);
        }
    }

    class TinNhanNhanViewHolder extends RecyclerView.ViewHolder{

        TextView tvNoiDungTinNhanNhan, tvThoiGianTinNhanNhan;

        public TinNhanNhanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoiDungTinNhanNhan = itemView.findViewById(R.id.tv_noi_dung_tin_nhan_nhan);
            tvThoiGianTinNhanNhan = itemView.findViewById(R.id.tv_thoi_gian_tin_nhan_nhan);
        }
    }
}
