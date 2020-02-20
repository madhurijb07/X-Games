package com.lrm.x_games.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lrm.x_games.R;
import com.lrm.x_games.Resources.Loader;
import com.lrm.x_games.Resources.Players;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

    AlertDialog alrts = null;
    AlertDialog a;
    Context c;
    ArrayList<Players> list;
    public PlayersAdapter(Context c, ArrayList<Players> list){
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

                ImageView close = view.findViewById(R.id.iv_close);

                tv_uname.setText(list.get(position).getName());
                tv_team.setText(list.get(position).getTeam());
                tv_score.setText(list.get(position).getScore());

                CircleImageView profile_image = view.findViewById(R.id.profile_image);

                String profurl =c.getResources().getString(R.string.hostnoport) + "/images/users/" + list.get(position).getEid() + ".jpg";
                Picasso.get().load(profurl).into(profile_image);




                btn_addscore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(c);
                        Loader loader = new Loader(c);
                        EditText addscore = view.findViewById(R.id.et_addscore);
                        String newscore = addscore.getText().toString();
                        String url = c.getResources().getString(R.string.host) + "/xgames/player/updateScore/"+
                                list.get(position).getEid()+"/"+newscore;

                        if(newscore.length()<1){
                            addscore.setError("Please enter valid score");
                        }else {


                            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null,
                                    new Response.Listener<JSONObject>() {


                                        // Takes the response from the JSON request
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {

                                                int result = response.getInt("statusCode");
                                                if(result==200){
                                                    a.dismiss();
                                                    loader.Dismisser();
                                                    int updated = Integer.parseInt(list.get(position).getScore())+Integer.parseInt(newscore);
                                                    tv_score.setText(""+updated);
                                                    notifyDataSetChanged();
                                                    AlertDialog.Builder al = new AlertDialog.Builder(c);
                                                    View views = LayoutInflater.from(c).inflate(R.layout.success,null);
                                                    al.setView(views);
                                                    Button close = views.findViewById(R.id.ok);
                                                    close.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            alrts.dismiss();
                                                        }
                                                    });
                                                    AlertDialog salert = al.show();
                                                    Button ok = views.findViewById(R.id.ok);
                                                    ok.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            salert.dismiss();
                                                        }
                                                    });

                                                    Toast.makeText(c, "Score Updated Successfully", Toast.LENGTH_SHORT).show();

                                                }else{
                                                    a.dismiss();
                                                    Toast.makeText(c, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                }

                                                View vv = LayoutInflater.from(c).inflate(R.layout.success,null);
                                                Button closesuccess = vv.findViewById(R.id.ok);
                                                closesuccess.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alrts.dismiss();
                                                    }
                                                });



                                                alrts.dismiss();


                                            }
                                            // Try and catch are included to handle any errors due to JSON
                                            catch (Exception e) {
                                                // If an error occurs, this prints the error to the log
                                                e.printStackTrace();
                                            }




                                        }
                                    },
                                    // The final parameter overrides the method onErrorResponse() and passes VolleyError
                                    //as a parameter
                                    new Response.ErrorListener() {
                                        @Override
                                        // Handles errors that occur due to Volley
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(c, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            );
                            queue.add(obreq);
                        }
                    }
                });

                alert.setView(view);
                a= alert.show();
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        a.dismiss();
                    }
                });
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
