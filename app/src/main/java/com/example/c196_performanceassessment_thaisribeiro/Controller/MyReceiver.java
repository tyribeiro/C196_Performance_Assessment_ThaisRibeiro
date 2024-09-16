package com.example.c196_performanceassessment_thaisribeiro.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.c196_performanceassessment_thaisribeiro.R;

/**
 * BroadcastReceiver implementation that handles broadcasted intents to create and display notifications.
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "test";
    private int notificationID = 0;

    /**
     * Called when the BroadcastReceiver receives a broadcasted intent.
     * This method creates a notification channel (if not already created) and displays a notification with the intent's extra data.
     *
     * @param context The context in which the receiver is running.
     * @param intent  The Intent that triggered the broadcast.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // Display a toast message with the value from the intent's "key" extra
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();

        // Create a notification channel
        createNotificationChannel(context, CHANNEL_ID);

        // Build and display the notification
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)  // Set notification icon
                .setContentText(intent.getStringExtra("key"))      // Set notification content text
                .setContentTitle("Degree Tracker")                // Set notification title
                .build();                                         // Build the notification

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification); // Display the notification
    }

    /**
     * Creates a notification channel with the specified channel ID if it does not already exist.
     *
     * @param context     The context in which the notification channel is created.
     * @param channelId   The ID of the notification channel to be created.
     */
    private void createNotificationChannel(Context context, String channelId) {
        CharSequence name = "mychannelname";  // Name of the notification channel
        String description = "mychanneldescription";  // Description of the notification channel
        int importance = NotificationManager.IMPORTANCE_DEFAULT;  // Importance level of the notification channel
        NotificationChannel channel = new NotificationChannel(channelId, name, importance);

        channel.setDescription(description);  // Set description of the notification channel

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);  // Create the notification channel
    }
}
