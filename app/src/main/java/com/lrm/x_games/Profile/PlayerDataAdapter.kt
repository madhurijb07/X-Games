package com.lrm.x_games.Profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.lrm.x_games.R
import com.lrm.x_games.Resources.StaticProfileData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.menu.view.*
import kotlinx.android.synthetic.main.player.view.*
import kotlinx.android.synthetic.main.tval.view.*
import kotlinx.android.synthetic.main.tval.view.tv_title
import kotlinx.android.synthetic.main.tval.view.tv_value

class PlayerDataAdapter(context: Context, resource: Int, private val objects: ArrayList<PlayerData>) : BaseAdapter() {
    val resource = resource
    val context = context

    val inflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(resource,null)

        view.tv_title.text = objects.get(position).name
        view.tv_value.text = objects.get(position).score

        if(objects.get(position).role.toLowerCase().equals("owner")){
            view.iv_owner.visibility = View.VISIBLE
        }
        if(objects.get(position).role.toLowerCase().equals("captain")){
            view.captain.visibility = View.VISIBLE
        }

        Picasso.get()
            .load(context.getResources().getString(R.string.hostnoport) + "/images/users/" + objects.get(position).id + ".jpg")
            .into(view.iv_profile_pic)

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