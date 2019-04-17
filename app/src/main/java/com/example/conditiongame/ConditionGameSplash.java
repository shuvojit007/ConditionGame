package com.example.conditiongame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ConditionGameSplash extends AppCompatActivity {
    TextView ready;


    int id, ListId;

    ImageView splash_watch, gear1, gear2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_condition_game_splash);



        id = getIntent().getIntExtra("id", 0);
        ListId = getIntent().getIntExtra("listId", 0);


        SetUpSplash();
    }

    private void SetUpSplash() {
        splash_watch = findViewById(R.id.splash_watch);
        gear1 = findViewById(R.id.gear1);
        gear2 = findViewById(R.id.gear2);

        Glide.with(ConditionGameSplash.this).load(R.drawable.grear).into(gear1);
        Glide.with(ConditionGameSplash.this).load(R.drawable.grear).into(gear2);
        Glide.with(ConditionGameSplash.this).load(R.drawable.conditional_splash_watch).into(splash_watch);

        ready = findViewById(R.id.ready);
        //ready.setVisibility(View.INVISIBLE);
        ready.setOnClickListener(v -> {
            startActivity(new Intent(ConditionGameSplash.this, ConditionGame.class)
                    .putExtra("id", id)
                    .putExtra("listId", ListId));
            finish();
        });


    }
}
