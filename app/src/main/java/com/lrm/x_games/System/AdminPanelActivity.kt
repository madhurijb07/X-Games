    package com.lrm.x_games.System

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lrm.x_games.Profile.PlayerData
import com.lrm.x_games.R
import com.lrm.x_games.Resources.Loader
import com.lrm.x_games.Resources.Players
import com.lrm.x_games.Profile.PlayersAdapter
import kotlinx.android.synthetic.main.content_admin_panel.*
import org.json.JSONArray
import org.json.JSONObject


    var prg: Loader? = null
    class AdminPanelActivity : AppCompatActivity() {

    var list = ArrayList<Players>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        var tid = intent.getStringExtra("tid")
        var tname = intent.getStringExtra("tname")
        var tscore = intent.getStringExtra("tscore")




        back.setOnClickListener {
            finish()
        }

        prg = Loader(this)
        prg!!.Show()

        val PlayersAdapter = PlayersAdapter(this, list);

        var queue = Volley.newRequestQueue(this)
        var url = getResources().getString(R.string.host)+"/xgames/team/getTeamById/"+tid

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                prg!!.Dismisser()

                Toast.makeText(this,response.getString("teamName"),Toast.LENGTH_LONG).show()
                var jArray = response.getJSONArray("players")
                for(i in 0 until jArray.length()) {
                    var player = jArray.getJSONObject(i)
                    var name = player.getString("fullName")
                    var score = player.getString("score");
                    var id = player.getString("playerId")
                    list.add(Players(id,name,tname,score))

                    PlayersAdapter.notifyDataSetChanged()

                }

            },
            Response.ErrorListener {
                System.out.println(it)
                Toast.makeText(this,"Invalid Username or password ", Toast.LENGTH_LONG).show()
                prg!!.Dismisser()

            })

        queue.add(request)

/*

        list!!.add(Players("144","Pritam Pawade","404 Warriors","450"))
        list!!.add(Players("144","Anushree Zawar","404 Warriors","450"))
        list!!.add(Players("144","Anushree Zawar","404 Warriors","450"))
        list!!.add(Players("144","Anushree Zawar","404 Warriors","450"))
        list!!.add(Players("144","Pritam Pawade","404 Warriors","450"))
        list!!.add(Players("144","Pritam Pawade","404 Warriors","450"))*/

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_players.layoutManager = layoutManager



        rv_players.adapter = PlayersAdapter


    }


}
