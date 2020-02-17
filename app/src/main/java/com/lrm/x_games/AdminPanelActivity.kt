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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_admin_panel.*


class AdminPanelActivity : AppCompatActivity() {

    var list = ArrayList<Players>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        back.setOnClickListener {
            finish()
        }
        list!!.add(Players("144","Pritam Pawade","404 Warriors","450"))
        list!!.add(Players("144","Anushree Zawar","404 Warriors","450"))
        list!!.add(Players("144","Anushree Zawar","404 Warriors","450"))
        list!!.add(Players("144","Anushree Zawar","404 Warriors","450"))
        list!!.add(Players("144","Pritam Pawade","404 Warriors","450"))
        list!!.add(Players("144","Pritam Pawade","404 Warriors","450"))

        val PlayersAdapter = PlayersAdapter(this,list);
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_players.layoutManager = layoutManager


        rv_players.adapter = PlayersAdapter


    }


}
