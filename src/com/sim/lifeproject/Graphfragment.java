package com.sim.lifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class Graphfragment extends Fragment {
	
	View view = null;
	TextView info_graph;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		if (Engine.ISTABLET==true) {
			view = inflater.inflate(R.layout.graphtablet, null);
		} else {
			view = inflater.inflate(R.layout.graph, null);
		}
		info_graph=(TextView) view.findViewById(R.id.show_infograph);
		
		return view; 
	}
	
	

}
