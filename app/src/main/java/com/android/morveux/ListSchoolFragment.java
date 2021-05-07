package com.android.morveux;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.morveux.entities.School;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListSchoolFragment extends Fragment {
    MutableLiveData<List<School>> mutableLiveDataSchools;
    private RecyclerView recyclerView;

    private void loadData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://declare-ton-morveux.herokuapp.com/api/schools",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<School> schools = new ArrayList<>();
                        for(int i=0; i<response.length(); i++) {


                            JSONObject schoolJson = null;
                            try {
                                schoolJson = (JSONObject) response.get(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            School school = new School();
                            school.setId(schoolJson.optLong("id"));
                            school.setName(schoolJson.optString("name"));
                            schools.add(school);



                        }
                        mutableLiveDataSchools.setValue(schools);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(this.getContext()).add(jsonArrayRequest);
    }

    private void initListView() {
        this.mutableLiveDataSchools.observe(this, new Observer<List<School>>() {
            @Override
            public void onChanged(List<School> schools) {
                if(schools != null) {
                    recyclerView.setAdapter(new ListSchoolRecyclerViewAdapter(schools, ListSchoolFragment.this.getContext()));
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mutableLiveDataSchools = new MutableLiveData<>();
        this.loadData();
        this.printNumberOfSickAndContagiousBySchoolId(1L);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_school, container, false);
        Context context = root.getContext();
        this.recyclerView = (RecyclerView) root;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.initListView();
    }



    private void printNumberOfSickBySchoolId(Long schoolId) {
        String url = "https://declare-ton-morveux.herokuapp.com/api/children/countIsSickBySchool/" + schoolId;
        StringRequest numberOfSickBySchoolIdRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response + "************************");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        Volley.newRequestQueue(ListSchoolFragment.this.getContext()).add(numberOfSickBySchoolIdRequest);
    }

    private void printNumberOfSickAndContagiousBySchoolId(Long schoolId) {
        String url = "https://declare-ton-morveux.herokuapp.com/api/children/countIsSickAndIsContagiousBySchool/" + schoolId;
        StringRequest numberOfSickBySchoolIdRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response + "************************");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        Volley.newRequestQueue(ListSchoolFragment.this.getContext()).add(numberOfSickBySchoolIdRequest);
    }
}
