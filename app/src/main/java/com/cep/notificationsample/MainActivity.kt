package com.cep.notificationsample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cep.notificationsample.activities.IntentNotificationActivity
import com.cep.notificationsample.utils.getBitmap
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val CHANNEL_ID = "NOTIFICATION_CHANNEL"

        const val notificationId = 101
        const val notificationId02 = 102
        const val notificationId03 = 103
        const val notificationId04 = 104
        const val notificationId05 = 105

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        createNotificationChannel()

        /***
         * Just Notification
         */
        val textTitle = "Hello Notification"
        val textContent = "I am a new notification"

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that can't fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        btnJustNotification.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId, builder.build())
            }
        }

        /***
         * Notification with Intent
         */
        val intent = Intent(this, IntentNotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent, 0)

        val builder2 = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        btnIntentNotification.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId02, builder2.build())
            }
        }

        /***
         * Notification with Actions
         */
        val intent02 = Intent(this, IntentNotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val pendingIntent02: PendingIntent = PendingIntent.getActivity(this, 0, intent02, 0)

        val builder03 = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setContentIntent(pendingIntent02)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(android.R.drawable.star_off, "Snooze", pendingIntent02)

        btnActionNotification.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId03, builder03.build())
            }
        }

        /***
         * Notification with Progress
         */
        val builder04 = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Video Download")
            setContentText("Download In Progress")
            setSmallIcon(android.R.drawable.stat_sys_download)
        }

        val PROGRESS_MAX = 100
        val CURRENT_PROGRESS = 10

        NotificationManagerCompat.from(this).apply{
            builder04.setProgress(PROGRESS_MAX, CURRENT_PROGRESS, false)

            builder04.setContentText("Download Complete")
                .setProgress(0,0,false)

            btnProgressNotification.setOnClickListener {
                notify(notificationId04, builder04.build())
            }
        }

        /***
         * Notification with Image
         */
        val builder05 = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("imageTitle")
            .setContentText("ImageDescription")
            .setLargeIcon(getBitmap(this, R.mipmap.ic_launcher))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(getBitmap(this, R.mipmap.ic_launcher_background))
                .bigLargeIcon(getBitmap(this, R.mipmap.ic_launcher))
            )

        btnBigImageNotification.setOnClickListener {
            with(NotificationManagerCompat.from(this)){
                notify(notificationId05, builder05.build())
            }

        }


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification"
            val descriptionText = "Sample Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}