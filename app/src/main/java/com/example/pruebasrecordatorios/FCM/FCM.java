package com.example.pruebasrecordatorios.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.pruebasrecordatorios.LoadingActivity;
import com.example.pruebasrecordatorios.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FCM extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String de = remoteMessage.getFrom();
        if (remoteMessage.getData().size() > 0) {
            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("detalle");
            mayorQueCero(de,titulo, detalle);
        }
    }
    private void mayorQueCero(String de, String titulo,String detalle){
        String id = "msg";
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }
        final NotificationCompat.Builder builder1 = builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setContentText(detalle)
                .setSmallIcon(R.drawable.ic_calendar)
                .setContentIntent(clickPoti())
                .setContentInfo(de);
        Random random = new Random();
        int idNotify = random.nextInt(8000);
        assert nm != null;
        nm.notify(idNotify,builder.build());
    }
    public PendingIntent clickPoti(){
        Intent n = new Intent(getApplicationContext(), LoadingActivity.class);
        n.putExtra("color","azul");
        n.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,n,0);
    }
}