package com.example.capyshop.common.thongbao;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.capyshop.R;
import com.example.capyshop.admin.main.AdminMainActivity;
import com.example.capyshop.common.utils.Utils;
import com.example.capyshop.user.main.UserMainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagerSevice extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            hienThiThongBao(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }
    private void hienThiThongBao(String tieuDe, String noiDung) {

        //  Tạo Intent để khi người dùng nhấn vào thông báo thì mở App
        Intent intent;
        if (Utils.userNguoiDung_Current == null) {
            return;
        }else if ("ADMIN".equalsIgnoreCase(Utils.userNguoiDung_Current.getVaiTro())) {
            intent = new Intent(this, AdminMainActivity.class);
        } else {
            intent = new Intent(this, UserMainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //  Tạo PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        //Đặt ID cho Channel
        String channelId = "capyshop";
        // Xây dựng thông báo bằng Builder mặc định
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher) // Icon hiển thị trên thanh trạng thái
                .setContentTitle(tieuDe)            // Tiêu đề mặc định
                .setContentText(noiDung)             // Nội dung mặc định
                .setAutoCancel(true)                // Tự xóa thông báo khi nhấn vào
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Hiển thị dạng Heads-up
                .setContentIntent(pendingIntent)    // Gán sự kiện nhấn
                .setDefaults(NotificationCompat.DEFAULT_ALL); // Chuông và rung mặc định
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //  Cấu hình Channel cho Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Thông báo hệ thống", NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        // Hiển thị thông báo
        if (notificationManager != null) {
            // Dùng System.currentTimeMillis() để các thông báo không bị ghi đè lên nhau
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }

}
