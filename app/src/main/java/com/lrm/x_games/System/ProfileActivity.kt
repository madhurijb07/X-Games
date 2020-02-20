package com.lrm.x_games.System

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lrm.x_games.R
import com.lrm.x_games.Resources.Loader
import com.lrm.x_games.Profile.ProfileData
import com.lrm.x_games.Profile.ProfileDataAdapter
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.content_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {
    var eid: String? = null
    var adapter: ProfileDataAdapter? = null
    var list = ArrayList<ProfileData>()
    var loader:Loader? = null


    var prg: Loader? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        loader = Loader(this)

        loader!!.Show()
        adapter = ProfileDataAdapter(this, R.layout.tval, list)
        //get EID from Intent and load data each time

        eid = intent.getStringExtra("eid")
        if (eid != null)
            retriever();
        else {
            Toast.makeText(this, "User Id is invalid", Toast.LENGTH_LONG).show()
        }
    }

    fun retriever() {


        Picasso.get()
            .load(getResources().getString(R.string.hostnoport) + "/images/users/" + eid + ".jpg")
            .into(profile_image)

        var url = getResources().getString(R.string.host) + "/xgames/player/getPlayerBy/" + eid


        var queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->

                loader!!.Dismisser()
                try {

                    list.add(
                        ProfileData(
                            "Team Name",
                            response.getString("teamId")
                        )
                    )
                    list.add(
                        ProfileData(
                            "Sold value",
                            response.getString("auctionPrice")
                        )
                    )
                   // list.add(ProfileData("Email", response.getString("email")))
                    list.add(
                        ProfileData(
                            "Date of birth",
                            response.getString("dateOfBirth")
                        )
                    )
                    tv_profile_name.setText(response.getString("fullName"))

                    list.add(
                        ProfileData(
                            "Role",
                            response.getString("role")
                        )
                    )
                    list.add(
                        ProfileData(
                            "Gender",
                            response.getString("gender")
                        )
                    )

                    tv_score.setText(response.getString("score"))
                    list.add(ProfileData("Interests", "N/A"))
                    adapter!!.notifyDataSetChanged()

                } catch (e: Exception) {
                    System.out.println(e.toString())
                    Toast.makeText(this, "Something isnt right here", Toast.LENGTH_LONG).show()
                }


            },
            Response.ErrorListener {
                System.out.println(it)
                Toast.makeText(this, "Player not found", Toast.LENGTH_LONG).show()
                prg!!.Dismisser()

            })

        queue.add(request)
        lv_profiledata.adapter = adapter

        back.setOnClickListener {
            finish()
        }

    }

}
