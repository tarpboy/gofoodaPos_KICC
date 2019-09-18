package app.payfun.com.gofoodapos.Push;

/**
 * Created by jaewooklee on 2017. 3. 20..
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import app.payfun.com.gofoodapos.ActivityMain;
import app.payfun.com.gofoodapos.R;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.e("Jonthaan", " remoteMessage :: " +remoteMessage.getData());

        //추가한것
        sendNotification(remoteMessage.getData());


    }

    private void sendNotification(Map<String, String> messageBody) {
        Intent intent = new Intent(this, ActivityMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        String title = messageBody.get("TITLE").toString();
        String msg = messageBody.get("MSG").toString();


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(messageBody.toString()))
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);



//        String[] texts = messageBody.split("=");
//        int qtdText = texts.length;
//        if(qtdText > 1){
//
//            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//            inboxStyle.setBigContentTitle("롯데렌탈 오토옥션");
//            for (int i=0; i < qtdText; i++) {
//                inboxStyle.addLine(texts[i]);
//            }
//            notificationBuilder.setStyle(inboxStyle);
//        }



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(FirebaseNotificationID.getID() /* ID of notification */, notificationBuilder.build());
    }



}
