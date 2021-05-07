package com.android.morveux;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.morveux.entities.School;
import com.android.morveux.services.SchoolService;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class ListSchoolRecyclerViewAdapter extends RecyclerView.Adapter<ListSchoolRecyclerViewAdapter.ViewHolder> {

    private List<School> schools;

    private Context context;

    public ListSchoolRecyclerViewAdapter(List<School> schools, Context context) {
        this.schools = schools;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_school_item, parent, false);
        return new ListSchoolRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        School school = this.schools.get(position);
        holder.schoolName.setText(school.getName());

        String urlSick = "https://declare-ton-morveux.herokuapp.com/api/children/countIsSickBySchool/" + school.getId();
        StringRequest numberOfSickBySchoolIdRequest = new StringRequest(
                Request.Method.GET,
                urlSick,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response + "************************");
                        holder.numberOfSick.setText(holder.itemView.getContext().getString(R.string.sicks)+ ": " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        Volley.newRequestQueue(holder.itemView.getContext()).add(numberOfSickBySchoolIdRequest);

        String urlContagious = "https://declare-ton-morveux.herokuapp.com/api/children/countIsSickAndIsContagiousBySchool/" + school.getId();
        StringRequest numberOfSickAndContagiousBySchoolIdRequest = new StringRequest(
                Request.Method.GET,
                urlContagious,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response + "************************");
                        holder.numberOfContagious.setText(holder.itemView.getContext().getString(R.string.contagious) + ": " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        Volley.newRequestQueue(holder.itemView.getContext()).add(numberOfSickAndContagiousBySchoolIdRequest);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(school.getId() + " " + school.getName());
                SchoolService.getInstance().setSchool(school);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.schools.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView schoolName;
        TextView numberOfSick;
        TextView numberOfContagious;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.schoolName = itemView.findViewById(R.id.school_name);
            this.numberOfSick = itemView.findViewById(R.id.number_of_sick);
            this.numberOfContagious = itemView.findViewById(R.id.number_of_contagious);


        }

        private void printNumberOfSickAndContagiousBySchoolId(Long schoolId) {

        }


    }
}
