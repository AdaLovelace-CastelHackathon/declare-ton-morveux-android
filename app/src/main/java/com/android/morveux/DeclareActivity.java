package com.android.morveux;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.morveux.entities.Parent;
import com.android.morveux.services.TokenService;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeclareActivity extends AppCompatActivity implements View.OnClickListener {

    private final String url = "https://declare-ton-morveux.herokuapp.com/api/parents/me";
    private MutableLiveData<Parent> mutableLiveDataParent;

    TextView username;
    Button addChildButton;


    private void loadParent() {
        JsonObjectRequest getParentRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("*** parent: " + response.toString());
                        username.setText(response.optString("username"));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", "Bearer " + TokenService.getInstance().getToken());
                    return params;
                }
        };
        Volley.newRequestQueue(this).add(getParentRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        this.mutableLiveDataParent = new MutableLiveData<>();

        this.loadParent();


        this.username = this.findViewById(R.id.parent_username);
        this.addChildButton = this.findViewById(R.id.add_child_button);


        this.addChildButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == this.addChildButton) {
            Intent intent = new Intent(this, AddChildActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }
}