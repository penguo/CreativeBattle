package com.afordev.creativebattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.afordev.creativebattle.Data.Card;
import com.afordev.creativebattle.Data.UserData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String APPVERSION = "develop 0.2.180204_07";

    private TextView tvVersion;
    private EditText etName, etServer;
    private Button btnJoin, btnReJoin;
    private UserData user;
    private ToggleButton toggleBtnSide;
    private FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = mFirebase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvVersion = findViewById(R.id.main_tv_version);
        etName = findViewById(R.id.main_et_name);
        etServer = findViewById(R.id.main_et_server);
        btnJoin = findViewById(R.id.main_btn_join);
        btnReJoin = findViewById(R.id.main_btn_rejoin);
        toggleBtnSide = findViewById(R.id.main_togglebtn_side);

        tvVersion.setText(APPVERSION);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String side;
                    if(toggleBtnSide.isChecked()){
                        side = "red";
                    }else{
                        side = "blue";
                    }
                    user = new UserData(1, etName.getText().toString(), 0);
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("side", side);
                    intent.putExtra("serverName", etServer.getText().toString());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Fatal error", e.toString());
                    Toast.makeText(MainActivity.this, "FATAL ERROR! Something is wrong.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnReJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Soon.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}