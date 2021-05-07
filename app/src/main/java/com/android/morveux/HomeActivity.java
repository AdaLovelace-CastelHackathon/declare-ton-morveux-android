package com.android.morveux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    Button declareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        this.declareButton = this.findViewById(R.id.declare_button);
        this.declareButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == this.declareButton) {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }


}