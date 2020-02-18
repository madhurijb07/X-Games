    package com.lrm.x_games

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_admin_panel.*
import androidx.recyclerview.widget.RecyclerView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ScrollView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.content_admin_panel.*
import org.json.JSONArray


    var prg:Loader? = null
    class AdminPanelActivity : AppCompatActivity() {

    var list = ArrayList<Players>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        back.setOnClickListener {
            finish()
        }

        prg = Loader(this)
        prg!!.Show()

        val PlayersAdapter = PlayersAdapter(this,list);

        var queue = Volley.newRequestQueue(this)
        var url = getResources().getString(R.string.host)+"/xgames/player/all"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                var jArray = JSONArray(response.toString())
                val i  = 0




                prg!!.Dismisser()
                for(i in 0 until jArray.length()) {
                    var player = jArray.getJSONObject(i)

                    var uname = player.getString("fullName")
                    var eid = player.getString("playerId")
                    var uteam = player.getString("teamId")
                    var score = player.getString("score")


                    list.add(Players(eid,uname,uteam,score))
                    PlayersAdapter.notifyDataSetChanged()


                }

            },
            Response.ErrorListener {
                System.out.println(it)
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
                finish()
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
