package com.lrm.x_games;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TeamsAdapter extends ArrayAdapter<Teams> {
    Context context;
    public TeamsAdapter(@NonNull Context context, int resource, @NonNull List<Teams> objects) {
        super(context, resource, objects);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.teamslist,null);
        TextView tname = v.findViewById(R.id.tv_team_name);//as TextView
        TextView tscore = v.findViewById(R.id.tv_team_score);//as TextView
        ImageView tlogo = v.findViewById(R.id.iv_team_logo);//as ImageView
        if(position==2){
            LinearLayout ll = v.findViewById(R.id.ll);
            tname.setTextColor(Color.parseColor("#FFFFFF"));
            tscore.setTextColor(Color.parseColor("FFFFFF"));
            ll.setBackgroundColor(Color.parseColor("#FF8BC34A"));
        }

        Teams team = getItem(position);

        tname.setText(team.getName());
        tscore.setText(team.getScore());
        return v;
    }
}
