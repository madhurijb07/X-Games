package com.lrm.x_games

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.content_main.*
import androidx.core.app.ActivityCompat.startActivityForResult
import android.content.Intent
import android.content.res.Configuration
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.tasks.Task
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.common.api.ApiException
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.material.internal.ContextUtils.getActivity
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()


    var mGoogleSignInClient:GoogleSignInClient?=null
    var account:GoogleSignInAccount? = null
    var list:ArrayList<ScheduledData>?= ArrayList<ScheduledData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //Checking if already signed in
        account = GoogleSignIn.getLastSignedInAccount(this);


        list!!.add(ScheduledData("Mallinath"))
        list!!.add(ScheduledData("Mallinath"))
        list!!.add(ScheduledData("Mallinath"))
        list!!.add(ScheduledData("Mallinath"))
        list!!.add(ScheduledData("Mallinath"))

        val ScheduledAdapter = ScheduledAdapter(this,list);
        rv_schedule.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_schedule.adapter = ScheduledAdapter


        val teams = ArrayList<Teams>()
        teams.add(Teams("PubGunners","150"))
        teams.add(Teams("PubGunners","150"))
        teams.add(Teams("PubGunners","150"))
        teams.add(Teams("PubGunners","150"))
        teams.add(Teams("PubGunners","150"))
        val teamsAdapter = TeamsAdapter(this,R.layout.teamslist,teams);
        teamsAdapter.notifyDataSetChanged()

        val lv = findViewById(R.id.lv_teamlist) as ListView

        lv.adapter = teamsAdapter

        accessAcc()

    }

    private fun signOut() {
        mGoogleSignInClient!!.signOut()
            .addOnCompleteListener(this) {
                finish()
            }
    }
    var personName:String? = null
    var personGivenName:String? = null
    var personFamilyName:String? = null
    var personEmail:String? = null
    var personId:String? = null
    var personPhoto: Uri? = null

    public fun accessAcc(){
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            personName = acct.getDisplayName()
            personGivenName = acct.getGivenName()
            personFamilyName = acct.getFamilyName()
            personEmail = acct.getEmail()
            personId = acct.getId()
            personPhoto = acct.getPhotoUrl()
            var pic = personPhoto.toString()


            Picasso.with(this).load(personPhoto.toString()).into(iv_profilepic)
           // Picasso.with(this).load(personPhoto.toString()).into(iv_prof)
          //  iv_prof.setImageURI(acct.photoUrl)
            retriever()
            tv_uname.setText(personName)

            if(!personEmail!!.contains("leftrightmind.com",true)) {
                Toast.makeText(this, "You are not the part of this championship", Toast.LENGTH_SHORT).show()
                signOut()
            }
        }else{

            val signInIntent:Intent = GoogleSignIn.getClient(this, gso).signInIntent
            startActivityForResult(signInIntent, 100)
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


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 100) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)


        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            accessAcc()
            Toast.makeText(this,"sign in success",Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this,"Sign in failed",Toast.LENGTH_SHORT).show()
            finish()
        }


    }
    public fun retriever(){

    }
}
