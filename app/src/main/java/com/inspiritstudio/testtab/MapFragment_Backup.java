package com.inspiritstudio.testtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MapFragment_Backup extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View v =inflater.inflate(R.layout.map_fragment,container,false);
        View v =inflater.inflate(R.layout.activity_maps,container,false);
        return v;
    }
}
