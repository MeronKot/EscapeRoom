package com.example.dell.escaperoom.Database;

import android.text.TextUtils;

import com.example.dell.escaperoom.Logic.Player;
import com.example.dell.escaperoom.Logic.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by yaelgersh on 25/03/2017.
 */

public class PlayerHandler {
    private static PlayerHandler instance = new PlayerHandler();
    private Player player;
    private DatabaseReference databasePlayers;
    private boolean loading = true;


    private PlayerHandler(){
        databasePlayers = FirebaseDatabase.getInstance().getReference("Players");//.child(player.getId());


    }


    public static PlayerHandler getInstance() {
        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final String id, final String name) {
        databasePlayers = FirebaseDatabase.getInstance().getReference("Players").child(id);

        databasePlayers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                player = dataSnapshot.getValue(Player.class);
                if(player == null){
                    player = new Player();
                    player.setName(name);
                    player.setId(id);
                }
                loading = false;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updatePlayer() {

        if(!loading) {
            databasePlayers.setValue(player);
        }
    }

}
