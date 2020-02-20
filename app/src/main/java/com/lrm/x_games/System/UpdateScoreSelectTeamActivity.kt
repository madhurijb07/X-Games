package com.lrm.x_games.System

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.lrm.x_games.R
import com.lrm.x_games.Resources.Loader
import com.lrm.x_games.Resources.Teams
import com.lrm.x_games.Resources.TeamsAdapter

import kotlinx.android.synthetic.main.activity_update_score_select_team.*
import kotlinx.android.synthetic.main.content_update_score_select_team.*
import org.json.JSONArray
import java.util.ArrayList

class UpdateScoreSelectTeamActivity : AppCompatActivity() {

    var loader:Loader?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_score_select_team)

        loader = Loader(this)
        var teams = ArrayList<Teams>()
        var queue = Volley.newRequestQueue(this)
        var url = getResources().getString(R.string.host)+"/xgames/team/all"

        back.setOnClickListener {
            finish()
        }
        var teamsAdapter =
            TeamsAdapter(this, R.layout.teamslist, teams);

        teamsAdapter.notifyDataSetChanged()

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                var jArray = JSONArray(response.toString())
                val i  = 0

                loader!!.Dismisser()
                for(i in 0 until jArray.length()) {
                    var team = jArray.getJSONObject(i)
                    var name = team.getString("teamName")
                    var score = team.getString("teamScore");
                    var id = team.getString("teamId")
                    teams.add(Teams(name, score, id))

                    teamsAdapter.notifyDataSetChanged()

                }

            },
            Response.ErrorListener {
                System.out.println(it)
            })


        queue.add(request)

        val lv = findViewById<ListView>(R.id.lv_updaterteamlist)

        lv.adapter = teamsAdapter

        lv.setOnItemClickListener { parent, view, position, id ->
            var team = parent.getItemAtPosition(position) as Teams
            var i = Intent(this,AdminPanelActivity::class.java)
            i.putExtra("tid",team.tid)
            i.putExtra("tname",team.name)
            i.putExtra("tscore",team.score)
            startActivity(i)
            Toast.makeText(this,team.getTid(), Toast.LENGTH_LONG).show()
        }
    }

}
