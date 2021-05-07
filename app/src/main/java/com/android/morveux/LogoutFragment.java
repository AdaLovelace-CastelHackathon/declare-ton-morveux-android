package com.android.morveux;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.morveux.services.TokenService;

public class LogoutFragment extends Fragment implements View.OnClickListener {

    Button logoutButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_layout, container, false);
        this.logoutButton = root.findViewById(R.id.logout_button);
        this.logoutButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        TokenService.getInstance().removeToken();
        Intent intent = new Intent(this.getActivity(), HomeActivity.class);
        this.startActivity(intent);
        this.getActivity().finish();
    }
}
