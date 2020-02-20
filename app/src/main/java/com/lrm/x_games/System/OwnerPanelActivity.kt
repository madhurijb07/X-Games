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
import com.lrm.x_games.Profile.PlayerData
import com.lrm.x_games.Profile.PlayerDataAdapter
import com.lrm.x_games.Resources.Loader
import com.lrm.x_games.Resources.StaticProfileData
import com.lrm.x_games.Resources.Teams
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.content_owner_panel.*
import kotlinx.android.synthetic.main.content_owner_panel.back
import kotlinx.android.synthetic.main.content_owner_panel.profile_image
import kotlinx.android.synthetic.main.content_profile.*
import org.json.JSONArray
import org.json.JSONObject

class OwnerPanelActivity : AppCompatActivity() {

    var loading: Loader? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_panel)

        loading = Loader(this)
        loading!!.Show()
        var tid = intent.getStringExtra("tid")
        var tname = intent.getStringExtra("tname")
        var tscore = intent.getStringExtra("tscore")

        tv_team_name.text = StaticProfileData.team
        var players = ArrayList<PlayerData>()
        var queue = Volley.newRequestQueue(this)

        var adapter = PlayerDataAdapter(this, R.layout.player, players)
        var url = getResources().getString(R.string.host)+"/xgames/team/getTeamById/"+tid

        Picasso.get()
            .load(getResources().getString(R.string.hostnoport) + "/images/teams/" + tid + ".jpg")
            .into(profile_image)

        tv_team_name.setText(tname)
        tv_team_score.setText(tscore)

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                loading!!.Dismisser()

                Toast.makeText(this,response.getString("teamName"),Toast.LENGTH_LONG).show()
                var jArray = response.getJSONArray("players")
                for(i in 0 until jArray.length()) {
                    var player = jArray.getJSONObject(i)
                    var name = player.getString("fullName")
                    var score = player.getString("score");
                    var id = player.getString("playerId")
                    var role = player.getString("role")
                    players.add(PlayerData(name, score, role,id))

                    adapter.notifyDataSetChanged()

                }

            },
            Response.ErrorListener {
                System.out.println(it)
                Toast.makeText(this,"Invalid Username or password ", Toast.LENGTH_LONG).show()
                prg!!.Dismisser()

            })


        queue.add(request)

        lv_players.adapter = adapter

        lv_players.setOnItemClickListener { parent, view, position, id ->
            var player = parent.getItemAtPosition(position) as PlayerData
            var intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("eid",player.id)
            startActivity(intent)
        }


        back.setOnClickListener {
            finish()
        }
    }

}
