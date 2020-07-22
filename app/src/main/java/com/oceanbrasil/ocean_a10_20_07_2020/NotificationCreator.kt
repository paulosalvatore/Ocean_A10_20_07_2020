package com.oceanbrasil.ocean_a10_20_07_2020

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

object NotificationCreator {

    val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"

    fun create(context: Context, title: String, body: String) {
        // Obtenção do Serviço de Notificação do Android
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Criação do canal, apenas para API maior ou igual ao Android.OREO
        val channelId = "OCEAN_MAIN"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Ocean - Canal Principal"
            val channelDescription = "Ocean - Canal utilizado para as principais notícias do app."

            val channel = notificationManager.getNotificationChannel(channelId)

            if (channel == null) {
                val newChannel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                newChannel.description = channelDescription
                newChannel.enableVibration(true)
                newChannel.enableLights(true)
                newChannel.vibrationPattern = longArrayOf(300, 400, 500, 400, 300)

                notificationManager.createNotificationChannel(newChannel)
            }
        }

        // Preparar a intent da notificação
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val newActionIntent = Intent(context, ActionActivity::class.java).apply {
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val newActionPendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, newActionIntent, 0)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        // Criação da notificação
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
//            .addAction(android.R.drawable.ic_input_add, "Ação 1", newActionPendingIntent)

        // Reply Action
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            val KEY_TEXT_REPLY = "KEY_TEXT_REPLY"
            val replyLabel = "Responder"
            val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                setLabel(replyLabel)
                build()
            }

            // TODO: Adicionar broadcast para lidar com a intent de reply
            val replyIntent = Intent(context, ActionActivity::class.java).apply {
                putExtra(EXTRA_NOTIFICATION_ID, 0)
            }

            val replyPendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, replyIntent, 0)

            val replyAction: NotificationCompat.Action =
                NotificationCompat.Action.Builder(android.R.drawable.ic_dialog_info,
                    replyLabel, replyPendingIntent)
                    .addRemoteInput(remoteInput)
                    .build()

            builder.addAction(replyAction)
        }

        // Envio da notificação
        val notification = builder.build()
        val notificationId = 1
        notificationManager.notify(notificationId, notification)
    }
}
