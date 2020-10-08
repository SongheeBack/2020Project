package com.example.a2020project.DBConnects;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.a2020project.ForLogin.LoginActivity;
import com.example.a2020project.MainActivity;
import com.example.a2020project.R;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("nope", "From: " + remoteMessage.getFrom());
        //여기서 메세지의 두가지 타입(1. data payload 2. notification payload)에 따라 다른 처리를 한다.
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("nope", "Message data payload: " + remoteMessage.getData());

            //꺼진 화면을 깨운다. 그러나 이방법은 Deprecated 되었다. 더이상 사용되지 않는다는 것이다. 현재로 작동은 하지만 나중에 어떻게 될지 모른다.
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            wakeLock.acquire(3000);

            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            //Log.d(TAG, title + " - " + body);
            //String click_action = remoteMessage.getData().get("clickAction");
            sendNotification(title, body);

            Intent intent = new Intent("MyData");
            intent.putExtra("title",title);
            intent.putExtra("body",body);
            broadcaster.sendBroadcast(intent);
        }

        //Intent intent = new Intent("MyData");
        /*
        intent.putExtra("phone", remoteMessage.getData().get("DriverPhone"));
        intent.putExtra("lat", remoteMessage.getData().get("DriverLatitude"));
        intent.putExtra("lng", remoteMessage.getData().get("DriverLongitude"));
        */
        //broadcaster.sendBroadcast(intent);

        /*
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        Log.d("nope", "From: " + remoteMessage.getFrom());
        Log.d("nope", "Title: " + title);
        Log.d("nope", "Message: " + message);

        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.createNotification(title,message);
        */
    }

    private void sendNotification(String title, String body) {
        if (title == null){
            //제목이 없는 payload이면 php에서 보낼때 이미 한번 점검했음.
            title = "공지사항"; //기본제목을 적어 주자.
        }
        //전달된 액티비티에 따라 분기하여 해당 액티비티를 오픈하도록 한다.
        Intent intent = new Intent();
        if(MainActivity.b != true){
            intent = new Intent(this, LoginActivity.class);
        }

        //번들에 수신한 메세지를 담아서 메인액티비티로 넘겨 보자.
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("body", body);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            String channelId = "one-channel";
            String channelName = "My Channel One";
            String channelDescription = "My Channel One Description";
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);


            channel.setDescription(channelDescription);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300});
            manager.createNotificationChannel(channel);
            notificationBuilder = new NotificationCompat.Builder(this,channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_hana)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setVibrate(new long[]{1000, 1000})
                    .setLights(Color.BLUE, 1,1)
                    .setContentIntent(pendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


        } else {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_hana)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setVibrate(new long[]{1000, 1000})
                    .setLights(Color.BLUE, 1,1)
                    .setContentIntent(pendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(692 /* ID of notification */, notificationBuilder.build());
    }
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        //final String[] token = {""};
        String token;
        token = s;
        /*
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token[0] = task.getResult().getToken();

                    }
                });
         */
        // 생성등록된 토큰을 개인 앱서버에 보내 저장해 두었다가 추가 뭔가를 하고 싶으면 할 수 있도록 한다.
        //Log.d("test_onnewtoken", "Refreshed token: " + token[0]);
        //sendRegistrationToServer(token[0]);
        Log.d("onnewtoken", ""+token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        //request
        Request request = new Request.Builder()
                .url("http://218.150.183.181:280/fcm/register.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}