package com.lrm.x_games.System

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.lrm.x_games.R

import kotlinx.android.synthetic.main.content_my_notifications.*

class MyNotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.setStatusBarColor(ContextCompat.getColor(this, R.color.grey))
        setContentView(R.layout.activity_my_notifications)

        back.setOnClickListener {
            finish()
        }

    }

}
