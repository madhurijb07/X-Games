package com.lrm.x_games

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


            splash.setVisibility(View.GONE)
            cv.setVisibility(View.VISIBLE)

        bt_login.setOnClickListener {
            var progress = ProgressDialog(this)
            progress.setTitle("Signing in")

            var prg = Loader(this)
            prg.Show()


            var eid = et_eid.getText().toString()
            var pass = et_epass.getText().toString()

            if(eid.length<=2)
                et_eid.setError("Invalid Employee ID");
            if(pass.length<9)
                et_epass.setError("Invalid Password Detected")

            if(eid.length>2&&pass.length>=9) {
                progress.show()
                StaticProfileData.eid = eid
                //check eid in backend

                //On Success
                progress.dismiss()

                //If first time open help us know you better
                val sharedPreference = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
                var editor = sharedPreference.edit()
                editor.putString("username", eid)
                editor.putString("name", "Pritam Pawade")
                editor.putString("team", "404 Warriors")
                editor.putString("email", "pritam.pawade@leftrightmind.com")
                editor.putString("score", "1055")

                editor.putInt("owner", 1)
                editor.putInt("admin", 1)
                editor.putInt("captain", 1)

                //update accordingly

                editor.commit()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }

}
