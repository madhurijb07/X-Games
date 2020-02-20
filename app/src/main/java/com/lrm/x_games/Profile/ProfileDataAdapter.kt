package com.lrm.x_games.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.lrm.x_games.R
import com.lrm.x_games.Resources.StaticProfileData
import kotlinx.android.synthetic.main.tval.view.*

class ProfileDataAdapter(context: Context, resource: Int, private val objects: ArrayList<ProfileData>) : BaseAdapter() {
    val resource = resource
    val context = context

    val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(resource,null)

        view.tv_title.text = objects.get(position).key
        view.tv_value.text = objects.get(position).value
        if(objects.get(position).key.equals("Team Name")&& StaticProfileData.owner ==1){
            view.ll.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
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