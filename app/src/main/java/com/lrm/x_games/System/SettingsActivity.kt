package com.lrm.x_games.System

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.Loader
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.lrm.x_games.R
import com.lrm.x_games.Resources.StaticProfileData
import kotlinx.android.synthetic.main.content_settings.*
import org.json.JSONObject

class SettingsActivity : AppCompatActivity() {

    var newuser:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tv_welcome.setText("Hey, "+intent.getStringExtra("name"))
        newuser = intent.getStringExtra("newuser")



        bt_update.setOnClickListener {
            var cpass = et_cpass.text.toString()
            var npass = et_npass.text.toString()
            var cnpass = et_cnpass.text.toString()

            if(npass.length<=7){
                et_npass.setError("Invalid password")
            }
            if(cpass.length<=7){
                et_cpass.setError("Invalid password")
            }

            if(npass.equals(cnpass)) {

                var loader = com.lrm.x_games.Resources.Loader(this)
                loader.Show()
                if(npass.equals("Reset@123")){
                    et_npass.setError("You can't set default password")
                }else{

                    var queue = Volley.newRequestQueue(this)
                    var url = getResources().getString(R.string.host)+"/xgames/user/resetPassword/"+
                            StaticProfileData.eid+"/"+cpass+"/"+npass

                    val request = JsonObjectRequest(
                        Request.Method.GET, url, null,
                        Response.Listener<JSONObject> { response ->
                            try {
                                loader.Dismisser()
                                var show:AlertDialog? = null
                                var status = response.getString("statusCode")
                                if(status.equals("400")){
                                    var dialog = AlertDialog.Builder(this)
                                    var view:View = LayoutInflater.from(this).inflate(R.layout.success,null)
                                    var ok = view.findViewById<Button>(R.id.ok)
                                    ok.setOnClickListener {
                                        startActivity(Intent(this,MainActivity::class.java))
                                        finish()
                                    }
                                    dialog.setView(view)
                                    dialog.setCancelable(false)
                                    dialog.show()

                                }

                            }catch (e:Exception){
                                Toast.makeText(this,"Something isnt right here",Toast.LENGTH_LONG).show()
                            }



                        },
                        Response.ErrorListener {
                            System.out.println(it)

                        })

                    /*back.setOnClickListener {
                        finish()
                    }*/

                    queue.add(request)
                }

            }else{
                Snackbar.make(it,"Passwords does not match",Snackbar.LENGTH_LONG).show()
            }



        }

        back.setOnClickListener {

            onBackPressed()

        }
    }

    override fun onBackPressed() {
        if(newuser==null) {
            finish()
            startActivity(Intent(this,MainActivity::class.java))
        }
        else
            Toast.makeText(this,"You need to update password first",Toast.LENGTH_LONG).show()

    }

}
