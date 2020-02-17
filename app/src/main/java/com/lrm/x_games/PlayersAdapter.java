package com.lrm.x_games;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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


        String profurl =c.getResources().getString(R.string.hostnoport) + "/images/users/" + list.get(position).getEid() + ".jpg";
        Picasso.get().load(profurl).into(holder.profile_image);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(c).inflate(R.layout.editor,null);
                AlertDialog.Builder alert = new AlertDialog.Builder(c);
                TextView tv_uname = view.findViewById(R.id.tv_uname);
                TextView tv_team = view.findViewById(R.id.tv_team);
                TextView tv_score = view.findViewById(R.id.tv_score);
                Button btn_addscore = view.findViewById(R.id.btn_addscore);

                tv_uname.setText(list.get(position).getName());
                tv_team.setText(list.get(position).getTeam());
                tv_score.setText(list.get(position).getScore());

                CircleImageView profile_image = view.findViewById(R.id.profile_image);

                String profurl =c.getResources().getString(R.string.hostnoport) + "/images/users/" + list.get(position).getEid() + ".jpg";
                Picasso.get().load(profurl).into(profile_image);



                btn_addscore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(c, "update score here", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setView(view);
                alert.show();
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
