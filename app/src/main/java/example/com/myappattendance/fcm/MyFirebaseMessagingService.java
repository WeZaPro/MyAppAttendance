package example.com.myappattendance.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import example.com.myappattendance.utils.Constants;
import static com.android.volley.VolleyLog.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String msg;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check device get message (App เปิดอยู่)
        Log.d("getMsg","GET MESSAGE");

        msg = remoteMessage.getData().toString();
        Log.d("check", msg);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }

    private void handleNow() {
    }

    private void scheduleJob() {
    }

    @Override
    public void onNewToken(String token) {
        Log.d("token", "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // sharePreference ( save token)
        SharedPreferences sharedPref = getSharedPreferences(Constants.MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.TOKEN, token);
        editor.commit();

        Log.d("token", "send token: " + token);
    }
}
