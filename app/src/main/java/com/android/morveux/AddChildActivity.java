package com.android.morveux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.morveux.services.SchoolService;
import com.android.morveux.services.TokenService;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddChildActivity extends AppCompatActivity implements View.OnClickListener {

    final String URL_CHILDREN = "https://declare-ton-morveux.herokuapp.com/api/children";

    EditText firstName;
    EditText schoolName;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        this.firstName = this.findViewById(R.id.edit_child_first_name);
        this.schoolName = this.findViewById(R.id.edit_child_shool_name);
        this.saveButton = this.findViewById(R.id.save_child_button);

        this.schoolName.setText(SchoolService.getInstance().getSchool().getName());
        this.saveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == this.saveButton) {
            this.save();

        }
    }

    private void save() {

        JSONObject childJson = new JSONObject();
        JSONObject schoolJson = new JSONObject();
        try {
            schoolJson.put("id", SchoolService.getInstance().getSchool().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            childJson.put("firstName", this.firstName.getText().toString());

            childJson.put("school", schoolJson);
            childJson.put("sick", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest addChildRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL_CHILDREN,
                childJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
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
        Volley.newRequestQueue(this).add(addChildRequest);

    }


}