package com.oceanbrasil.ocean_a10_20_07_2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_action.*

class ActionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        btFecharTela.setOnClickListener {
            finish()
        }

//        finish()
    }
}
