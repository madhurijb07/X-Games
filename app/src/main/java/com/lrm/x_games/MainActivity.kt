package com.lrm.x_games

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.content.Intent
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import kotlinx.android.synthetic.main.menu.*
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import org.json.JSONArray
import java.util.*
import com.azoft.carousellayoutmanager.CenterScrollListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    var list:ArrayList<ScheduledData>?= ArrayList<ScheduledData>()

    var nonet: AlertDialog.Builder? = null
    var nalert:AlertDialog?=null

    var nnet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme



        var loader = Loader(this)
        val view = LayoutInflater.from(this).inflate(R.layout.nonet,null)
        val tryagain = view.findViewById<Button>(R.id.btn_tryagain)
        val close = view.findViewById<ImageView>(R.id.iv_close)
        close.setOnClickListener {
            finish()
        }
        tryagain.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        nonet = AlertDialog.Builder(this).setView(view)

       /* tv_uname.text =  StaticProfileData.name
        tv_score.text = StaticProfileData.score
*/

        iv_closer.setOnClickListener {
            drawerlayout.closeDrawer()
        }

        iv_notification.setOnClickListener {
            startActivity(Intent(this,MyNotificationsActivity::class.java))
        }
        iv_drawer.setOnClickListener {
            drawerlayout.openDrawer()
        }


        //Drawer Layout menu operations
        var drawerLayout = findViewById<DuoDrawerLayout>(R.id.drawerlayout);
        var drawerToggle = DuoDrawerToggle(this, drawerLayout, null,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)

        drawerLayout.setDrawerListener(drawerToggle)
        drawerToggle.syncState()


        my_profile.setOnClickListener {
            var intent = Intent(this,ProfileActivity::class.java)
            intent.putExtra("eid",StaticProfileData.eid)
            startActivity(intent)
        }


        //END of Drawer layout menu operations

        list!!.add(ScheduledData("• Live"))
        list!!.add(ScheduledData("• Live"))
        list!!.add(ScheduledData("Scheduled"))
        list!!.add(ScheduledData("Scheduled"))
        list!!.add(ScheduledData("Scheduled"))
        list!!.add(ScheduledData("Scheduled"))
        list!!.add(ScheduledData("Scheduled"))

        val ScheduledAdapter = ScheduledAdapter(this,list);
        var layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);

        rv_schedule.setLayoutManager(layoutManager);
        rv_schedule.setHasFixedSize(true);
        rv_schedule.addOnScrollListener(CenterScrollListener())
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener());

        rv_schedule.adapter = ScheduledAdapter




        val teams = ArrayList<Teams>()
        var queue = Volley.newRequestQueue(this)
        var url = getResources().getString(R.string.host)+"/xgames/team/all"

        val teamsAdapter = TeamsAdapter(this,R.layout.teamslist,teams);


        teams.add(Teams("404 Warriors","150","demo"))
        teams.add(Teams("PubGunners","200","demo"))
        teams.add(Teams("Chennai Super Kings","400","demo"))
        teams.add(Teams("Mumbai Indians","600","demo"))
        teams.add(Teams("Royal Challenger Bangalore","200","demo"))
        teams.add(Teams("Rising Pune Supergiants","300","demo"))
        teams.add(Teams("Delhi Derdevils","400","demo"))
        teams.add(Teams("Kings 11 Punjab","500","demo"))
        teams.add(Teams("Rajasthan Royals","0","demo"))


        teamsAdapter.notifyDataSetChanged()

        adminpanel.setOnClickListener {
            startActivity(Intent(this,AdminPanelActivity::class.java))
        }
        leaderboard.setOnClickListener {
            Snackbar.make(it,"Coming soon...",Snackbar.LENGTH_LONG).setTextColor(resources.getColor(R.color.colorAccent)).show()
        }
        dashboard.setOnClickListener {
            Snackbar.make(it,"Coming soon...",Snackbar.LENGTH_LONG).setTextColor(resources.getColor(R.color.colorAccent)).show()
        }
        val request = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                var jArray = JSONArray(response.toString())
                val i  = 0

                if(nalert!=null&&nnet==1)
                    nalert!!.dismiss()


                loader.Dismisser()
                for(i in 0 until jArray.length()) {
                    var team = jArray.getJSONObject(i)
                    var name = team.getString("teamName")
                    var id = team.getString("teamId");
                    teams.add(Teams(name,id,id))
                    teamsAdapter.notifyDataSetChanged()

                }

            },
            Response.ErrorListener {
                System.out.println(it)
                if(nalert==null)
                    nalert = nonet!!.show()
                    nnet = 1
            })


        queue.add(request)

        val lv = findViewById<ListView>(R.id.lv_teamlist)

        lv.adapter = teamsAdapter

        lv.setOnItemClickListener { parent, view, position, id ->
            var team = parent.getItemAtPosition(position) as Teams
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
                    startActivity(Intent(this,LoginActivity::class.java))
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



}
