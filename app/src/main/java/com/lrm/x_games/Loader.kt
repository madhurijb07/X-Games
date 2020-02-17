package com.lrm.x_games

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

class Loader(private val c:Context) {
    val view = LayoutInflater.from(c).inflate(R.layout.loader,null)

    var alert = AlertDialog.Builder(c, R.style.CustomDialog).setView(view).show()

    fun Show(){
        alert.show()
    }

    fun Dismisser(){
        alert.dismiss()
    }
}