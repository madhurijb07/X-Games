package com.lrm.x_games

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_admin_panel.*
import kotlinx.android.synthetic.main.content_login.*
import org.json.JSONArray
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    var prg:Loader? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


            splash.setVisibility(View.GONE)
            cv.setVisibility(View.VISIBLE)

        bt_login.setOnClickListener {

            prg = Loader(this)


            var eid = et_eid.getText().toString()
            var pass = et_epass.getText().toString()

            if(eid.length<=2)
                et_eid.setError("Invalid Employee ID");
            if(pass.length<9)
                et_epass.setError("Invalid Password Detected")

            if(eid.length>2&&pass.length>=9) {
                StaticProfileData.eid = eid
                //check eid in backend
                prg!!.Show()

                var queue = Volley.newRequestQueue(this)
                var url = getResources().getString(R.string.host)+"/xgames/user/login/"+eid+"/"+pass
                val request = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    Response.Listener<JSONObject> { response ->

                        try {
                            val sharedPreference = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
                            var editor = sharedPreference.edit()

                            editor.putString("username", response.getString("playerId"))
                            editor.putString("name", response.getString("fullName"))

                            editor.putString("team", response.getString("teamId"))
                            editor.putString("email", response.getString("email"))

                            editor.putString("score", response.getString("score"))

                            if(response.getString("role").equals("Owner"))
                                editor.putInt("owner", 1)
                            if(response.getString("role").equals("Admin"))
                                editor.putInt("admin", 1)
                            if(response.getString("role").equals("Captain"))
                                editor.putInt("captain", 1)

                            if(response.getString("role").equals("OC")) {
                                editor.putInt("owner",1)
                                editor.putInt("captain",1)
                            }


                            //update accordingly

                            editor.commit()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()


                        }catch (e:Exception){
                            Toast.makeText(this,"Something isnt right here",Toast.LENGTH_LONG).show()
                        }



                    },
                    Response.ErrorListener {
                        System.out.println(it)
                        Toast.makeText(this,"Invalid Username or password ",Toast.LENGTH_LONG).show()
                        prg!!.Dismisser()

                    })

                /*back.setOnClickListener {
                    finish()
                }*/

                queue.add(request)
                //On Success


                //If first time open help us know you better

            }
        }

    }

}
