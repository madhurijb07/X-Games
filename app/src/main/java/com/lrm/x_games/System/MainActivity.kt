package com.lrm.x_games.System

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.google.android.material.snackbar.Snackbar
import com.lrm.x_games.R
import com.lrm.x_games.Resources.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.menu.*
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {

    var list:ArrayList<ScheduledData>?= ArrayList<ScheduledData>()

    var lv:ListView?=null

    var queue:RequestQueue? = null
    var teams = ArrayList<Teams>()
   // var nonet: AlertDialog.Builder? = null
    var nalert:AlertDialog?=null
    var loader:Loader? = null

    var nnet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme

        loader  = Loader(this)

        lv = findViewById<ListView>(R.id.lv_teamlist)



       /* tv_uname.text =  StaticProfileData.name
        tv_score.text = StaticProfileData.score
*/

        iv_closer.setOnClickListener {
            drawerlayout.closeDrawer()
        }

        iv_notification.setOnClickListener {
            startActivity(Intent(this, MyNotificationsActivity::class.java))
        }

        iv_drawer.setOnClickListener {
            drawerlayout.openDrawer()

        }

        val profurl =getResources().getString(R.string.hostnoport) + "/images/users/" + StaticProfileData.eid + ".jpg"
        Picasso.get().load(profurl).into(iv_profile)
        tv_uname.setText(StaticProfileData.name)
        tv_points.setText(StaticProfileData.score +" Points")

        //Drawer Layout menu operations
        var drawerLayout = findViewById<DuoDrawerLayout>(R.id.drawerlayout);
        var drawerToggle = DuoDrawerToggle(this, drawerLayout, null,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.setDrawerListener(drawerToggle)
        drawerToggle.syncState()


        my_profile.setOnClickListener {
            var intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("eid", StaticProfileData.eid)
            startActivity(intent)
        }


        //END of Drawer layout menu operations

        list!!.add(ScheduledData("• Live"))


        val ScheduledAdapter = ScheduledAdapter(this, list);
        var layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);

        rv_schedule.setLayoutManager(layoutManager);
        rv_schedule.setHasFixedSize(true);
        rv_schedule.addOnScrollListener(CenterScrollListener())
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener());

        rv_schedule.adapter = ScheduledAdapter



        if(StaticProfileData.admin==1){
            adminpanel.visibility = View.VISIBLE
        }
        settings.setOnClickListener {
            var i =Intent(this,SettingsActivity::class.java)
            i.putExtra("name",StaticProfileData.name)
            startActivity(i)
            finish()
        }

        adminpanel.setOnClickListener {
            startActivity(Intent(this, UpdateScoreSelectTeamActivity::class.java))
        }
        leaderboard.setOnClickListener {
            Snackbar.make(it,"Coming soon...",Snackbar.LENGTH_LONG).setTextColor(resources.getColor(
                R.color.colorAccent
            )).show()
        }
        dashboard.setOnClickListener {
            Snackbar.make(it,"Coming soon...",Snackbar.LENGTH_LONG).setTextColor(resources.getColor(
                R.color.colorAccent
            )).show()
        }

        queue = Volley.newRequestQueue(this)



       setter();



        lv!!.setOnItemClickListener { parent, view, position, id ->
            var team = parent.getItemAtPosition(position) as Teams
            var i = Intent(this,OwnerPanelActivity::class.java)
            i.putExtra("tid",team.tid)
            i.putExtra("tname",team.name)
            i.putExtra("tscore",team.score)
            startActivity(i)
            Toast.makeText(this,team.getTid(),Toast.LENGTH_LONG).show()
        }

        logout.setOnClickListener {
            var alert = AlertDialog.Builder(this).setTitle("Are you sure??").setPositiveButton("Yes, sign me out",
                DialogInterface.OnClickListener { dialog, which ->
                    //clear preferences
                    val sharedPreference = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
                    var editor = sharedPreference.edit()
                    editor.clear()
                    editor.commit()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }).setNegativeButton("No, keep me signed in",null).show()

        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        if(!drawerlayout.isDrawerOpen) {
            AlertDialog.Builder(this).setTitle("Exit the app")
                .setMessage("Do you really want to close the app?")
                .setPositiveButton("yes", DialogInterface.OnClickListener { dialog, which ->
                    finish()
                }).setNegativeButton("No", null).show()
        }else{
            drawerlayout.closeDrawer()
        }
    }

    fun setter(){

        var url = getResources().getString(R.string.host)+"/xgames/team/all"


        var teamsAdapter =
            TeamsAdapter(this, R.layout.teamslist, teams);
        lv!!.adapter = teamsAdapter



        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                var jArray = JSONArray(response.toString())
                val i  = 0

                if(nalert!=null&&nnet==1)
                    nalert!!.dismiss()




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
                val view = LayoutInflater.from(this).inflate(R.layout.nonet,null)
                val tryagain:Button = view.findViewById<Button>(R.id.btn_tryagain)
                val close = view.findViewById<ImageView>(R.id.iv_close)

                tryagain.setOnClickListener {
                    finish()
                    startActivity(getIntent())

                }
                close.setOnClickListener {
                    finish()
                }
                var nonet = AlertDialog.Builder(this).setCancelable(false).setView(view)
                    nalert = nonet!!.show()

            })


        queue!!.add(request)
    }
}
