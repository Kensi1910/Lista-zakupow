package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kensi on 05/05/2018.
 */

public class FavFragment extends Fragment {

    View v;
    public FavFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fav_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
