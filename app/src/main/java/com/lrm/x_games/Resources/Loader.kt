package com.lrm.x_games.Resources

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.lrm.x_games.R

class Loader(private val c:Context) {
    val view = LayoutInflater.from(c).inflate(R.layout.loader,null)

    var alert = AlertDialog.Builder(c, R.style.CustomDialog).setView(view).setCancelable(false).show()


    fun Show(){

        alert.show()
    }

    fun Dismisser(){
        if(alert.isShowing)
            try {
                alert.dismiss()
            }catch (e:Exception){

            }
    }
}