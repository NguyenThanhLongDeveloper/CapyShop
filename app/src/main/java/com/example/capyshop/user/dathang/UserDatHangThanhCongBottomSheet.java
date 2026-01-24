package com.example.capyshop.user.dathang;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capyshop.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UserDatHangThanhCongBottomSheet extends BottomSheetDialogFragment {

    private OnActionListener listener;

    public interface OnActionListener {
        void onContinueShopping();
        void onViewOrderDetails();
    }

    public UserDatHangThanhCongBottomSheet(OnActionListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_dathang_thanhcong_bottomsheet_layout, container, false);

        AppCompatButton btnContinueShopping = view.findViewById(R.id.btn_continue_shopping);
        AppCompatButton btnViewOrderDetails = view.findViewById(R.id.btn_view_order_details);


        btnContinueShopping.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.onContinueShopping();
            }
        });

        btnViewOrderDetails.setOnClickListener(v -> {
            dismiss();
            if (listener != null) {
                listener.onViewOrderDetails();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
        return dialog;
    }
}
