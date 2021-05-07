package com.android.morveux;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.morveux.services.TokenService;

import java.time.LocalDate;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    TextView dateToday;
    Button declareButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.dateToday = this.findViewById(R.id.date_today);
        this.dateToday.setText(LocalDate.now().toString());
        this.declareButton = this.findViewById(R.id.declare_button);
        this.declareButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == this.declareButton) {
            this.redirect();
        }
    }

    private void redirect() {
        if(TokenService.getInstance().getToken() == null) {
            Intent intent = new Intent(this, AuthActivity.class);
            this.startActivity(intent);
            this.finish();
        } else {
            Intent intent = new Intent(this, DeclareActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }
}