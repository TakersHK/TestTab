package com.inspiritstudio.testtab;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelloFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/** Inflating the layout for this fragment **/
		View v = inflater.inflate(R.layout.mtr_fragment, null);
		return v;
	}
}
