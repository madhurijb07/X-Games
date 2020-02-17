package com.lrm.x_games

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_splash.*
import android.content.pm.PackageManager
import android.R.attr.versionName
import android.content.Context
import android.content.pm.PackageInfo
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.content_login.*


class Splash : AppCompatActivity() {

    var istarter:Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash)
        val imageView = findViewById<View>(R.id.iv_logo)
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade)
        try {
            val pInfo = this.getPackageManager().getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            tv_version.setText("Version: "+version)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        splash_anim.startAnimation(animation)
        val sharedPreference =  getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        val unm = sharedPreference.getString("username","na")

        val name = sharedPreference.getString("name","na")
        val team = sharedPreference.getString("team","na")
        val email =sharedPreference.getString("email","na")

        val admin =sharedPreference.getInt("admin",0)
        val captain =sharedPreference.getInt("captain",0)
        val owner =sharedPreference.getInt("owner",0)

        val score =sharedPreference.getString("score","na")

        if(unm.equals("na")||name.equals("na")||team.equals("na")||email.equals("na")||score.equals("na")){
            /*splash.setVisibility(View.GONE)
            cv.setVisibility(View.VISIBLE)*/
            istarter = Intent(   this,LoginActivity::class.java)
        }else{
            if (unm != null&&name != null&&team != null&&email != null) {
                StaticProfileData.eid = unm
                StaticProfileData.name = name
                StaticProfileData.team = team
                StaticProfileData.email = email
                StaticProfileData.score = score

                StaticProfileData.admin = admin
                StaticProfileData.captain = captain
                StaticProfileData.owner = owner


                istarter = Intent(   this,MainActivity::class.java)
            }else{
                istarter = Intent(   this,LoginActivity::class.java)
            }
        }
        Handler().postDelayed({
            startActivity(istarter)
            finish()
        }, 3000)
    }

}
