package com.lrm.x_games

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.scheduled_card.view.*
import kotlinx.android.synthetic.main.tval.view.*

class PlayerDataAdapter(context: Context, resource: Int, private val objects: ArrayList<PlayerData>) : BaseAdapter() {
    val resource = resource
    val context = context

    val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(resource,null)

        view.tv_title.text = objects.get(position).key
        view.tv_value.text = objects.get(position).value
        if(objects.get(position).key.equals("Team Name")&&StaticProfileData.owner==1){
            view.ll.setBackgroundColor(ContextCompat.getColor(context,R.color.grey))
        }
        return view;
    }

    override fun getItem(position: Int): Any {
        return objects.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return objects.size
    }

}