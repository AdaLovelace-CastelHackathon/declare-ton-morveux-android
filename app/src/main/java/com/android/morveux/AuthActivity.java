package com.android.morveux;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.morveux.entities.Parent;
import com.android.morveux.services.TokenService;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username;
    EditText password;

    Button signInButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        this.username = this.findViewById(R.id.edit_username);
        this.password = this.findViewById(R.id.edit_password);
        this.signInButton = this.findViewById(R.id.signin_button);
        this.loginButton = this.findViewById(R.id.login_button);

        this.signInButton.setOnClickListener(this);
        this.loginButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        JSONObject parentJson = new JSONObject();
        try {
            parentJson.put("username", this.username.getText().toString());
            parentJson.put("password", this.password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(v == this.signInButton) {
            JsonObjectRequest stringRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "https://declare-ton-morveux.herokuapp.com/register",
                    parentJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String token = response.getString("token");
                                TokenService.getInstance().setToken(token);
                                System.out.println(TokenService.getInstance().getToken());
                                navigateToDeclareActivity(token);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }

            );
            Volley.newRequestQueue(this).add(stringRequest);
        } else if(v == this.loginButton) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "https://declare-ton-morveux.herokuapp.com/authenticate",
                    parentJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String token = response.getString("token");
                                TokenService.getInstance().setToken(token);
                                System.out.println(TokenService.getInstance().getToken());
                                navigateToDeclareActivity(token);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            Volley.newRequestQueue(this).add(jsonObjectRequest);
        }
    }

    private void navigateToDeclareActivity(String token) {
        Intent intent = new Intent(this, DeclareActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}