package com.lrm.x_games.Resources

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_splash.*
import android.content.pm.PackageManager
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lrm.x_games.R
import com.lrm.x_games.System.LoginActivity
import com.lrm.x_games.System.MainActivity
import com.lrm.x_games.System.SettingsActivity
import com.lrm.x_games.System.prg
import kotlinx.android.synthetic.main.menu.*
import org.json.JSONObject


class Splash : AppCompatActivity() {

    var istarter:Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash)
        val imageView = findViewById<View>(R.id.iv_logo)
        val animation = AnimationUtils.loadAnimation(applicationContext,
            R.anim.fade
        )
        try {
            val pInfo = this.getPackageManager().getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            tv_version.setText("Version: "+version)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        splash_anim.startAnimation(animation)

        Handler().postDelayed({
            mainthread()
        }, 2100)

    }
    fun mainthread(){


        var queuepass = Volley.newRequestQueue(this)
        if(StaticProfileData.eid!=null) {
            var ceid = StaticProfileData.eid
            if (ceid != null) {
                var url =
                    getResources().getString(R.string.host) + "/xgames/player/getPlayerBy/" + StaticProfileData.eid

                val request = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener<JSONObject> { response ->

                        try {

                            if (response.getString("empPassword").equals("Reset@123")) {

                                //newuser
                                var i = Intent(this, SettingsActivity::class.java)
                                i.putExtra("name", StaticProfileData.name)
                                i.putExtra("newuser","1")
                                startActivity(i)
                                finish()
                            } else {
                                setistarter();
                            }


                        } catch (e: Exception) {
                            Toast.makeText(this, "Something isnt right here", Toast.LENGTH_LONG)
                                .show()
                        }

                    },
                    Response.ErrorListener {
                        System.out.println(it)
                        val view = LayoutInflater.from(this).inflate(R.layout.nonet, null)
                        val tryagain: Button = view.findViewById<Button>(R.id.btn_tryagain)
                        val close = view.findViewById<ImageView>(R.id.iv_close)

                        tryagain.setOnClickListener {
                            finish()
                            startActivity(getIntent())

                        }
                        close.setOnClickListener {
                            finish()
                        }
                        var nonet = AlertDialog.Builder(this).setCancelable(false).setView(view)
                        try {
                            nonet!!.show()
                        }catch (e:Exception){

                        }

                    })


                queuepass.add(request)

            } else {
                setistarter()
            }
        }

        if(StaticProfileData.eid==null){
            setistarter()
        }
    }

    fun setistarter(){
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
            istarter = Intent(   this, LoginActivity::class.java)
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


                istarter = Intent(   this, MainActivity::class.java)
            }else{
                istarter = Intent(   this, LoginActivity::class.java)
            }
        }
        startActivity(Intent(istarter))
        finish()
    }

}
