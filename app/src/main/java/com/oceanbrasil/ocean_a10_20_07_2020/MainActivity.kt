package com.oceanbrasil.ocean_a10_20_07_2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btEnviarNotificacao.setOnClickListener {
            NotificationCreator.create(
                this,
                "Título de Teste",
                "Corpo da notificação"
            )
        }
    }
}
