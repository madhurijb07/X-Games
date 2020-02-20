package com.lrm.x_games.System

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lrm.x_games.R
import com.lrm.x_games.Resources.Loader
import com.lrm.x_games.Resources.StaticProfileData

import kotlinx.android.synthetic.main.content_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    var prg: Loader? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




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
                            StaticProfileData.eid = response.getString("playerId")

                            editor.putString("name", response.getString("fullName"))
                            StaticProfileData.name = response.getString("fullName")

                            editor.putString("team", response.getString("teamId"))
                            StaticProfileData.team = response.getString("teamId")

                            editor.putString("email", response.getString("email"))
                            StaticProfileData.email = response.getString("email")

                            editor.putString("score", response.getString("score"))
                            StaticProfileData.score = response.getString("score")

                            if(response.getString("role").toLowerCase().equals("Owner")) {
                                editor.putInt("owner", 1)
                                StaticProfileData.owner = 1
                            }

                            if(response.getString("role").toLowerCase().equals("admin")) {
                                editor.putInt("admin", 1)
                                StaticProfileData.admin = 1
                            }
                            if(response.getString("role").toLowerCase().equals("Captain")) {
                                editor.putInt("captain", 1)
                                StaticProfileData.captain = 1
                            }

                            if(response.getString("role").toLowerCase().equals("OC")) {
                                editor.putInt("owner",1)
                                editor.putInt("captain",1)

                                StaticProfileData.owner = 1
                                StaticProfileData.captain = 1
                            }


                            //update accordingly

                            editor.commit()
                            if(pass.equals("Reset@123")){

                                //newuser
                                prg!!.Dismisser()
                                var i = Intent(this, SettingsActivity::class.java)
                                i.putExtra("newuser","1")
                                i.putExtra("name",StaticProfileData.name)
                                startActivity(i)
                                finish()
                            }else {

                                startActivity(Intent(this, MainActivity::class.java))

                                finish()
                            }


                        }catch (e:Exception){
                            Toast.makeText(this,"Something isnt right here",Toast.LENGTH_LONG).show()
                        }

                    },
                    Response.ErrorListener {
                        System.out.println(it)
                        Toast.makeText(this,"Invalid Username or password ",Toast.LENGTH_LONG).show()
                        prg!!.Dismisser()

                    })


                queue.add(request)


            }
        }

    }

}
