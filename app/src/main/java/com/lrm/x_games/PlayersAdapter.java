package com.lrm.x_games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

    Context c;
    ArrayList<Players> list;
    PlayersAdapter(Context c, ArrayList<Players> list){
        this.c  = c;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.playerlist,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //update values
        holder.name.setText(list.get(position).getName());
        holder.score.setText(list.get(position).getScore());
        holder.team.setText(list.get(position).getTeam());

        if(position%2==0){
            holder.profile_image.setImageResource(R.drawable.profile);
        }else
            holder.profile_image.setImageResource(R.drawable.profiledemo);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "We are editing this player", Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, team,score;
        ImageView edit;
        CircleImageView profile_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name  = itemView.findViewById(R.id.tv_name);
            team  = itemView.findViewById(R.id.tv_team);
            score  = itemView.findViewById(R.id.tv_score);

            edit = itemView.findViewById(R.id.iv_edit);
            profile_image = itemView.findViewById(R.id.iv_profile_image);
        }
    }
}
