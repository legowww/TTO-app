package com.example.myapplication

import android.R
import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat


class AlarmSoundService : Service() {

    val CHANNEL_ID = "10000"
    val CHANNEL_NAME = "TimeToOut Channel"
    private var ringtone: Ringtone? = null


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Toast.makeText(this, "알람이 울립니다.", Toast.LENGTH_SHORT).show()
        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        this.ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone?.play()
        createNotificationChannel()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(2, buildNotification())
        return super.onStartCommand(intent, flags, startId)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun buildNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("나갈시간 입니다.")
                .setContentText("알람을 종료하시려면 눌러주세요.")
                .setSmallIcon(R.drawable.alert_light_frame)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            return builder
        } else {
            val builder = NotificationCompat.Builder(this)
                .setContentTitle("나갈시간 입니다.")
                .setContentText("알람을 종료하시려면 눌러주세요.")
                .setSmallIcon(R.drawable.alert_light_frame)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            return builder
        }
    }

    override fun onDestroy() {
        ringtone?.stop()
    }
}