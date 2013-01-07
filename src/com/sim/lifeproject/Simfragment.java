package com.sim.lifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class Simfragment extends Fragment {
	
	View view = null;
	TextView info_sim;
	View pantalla;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		 pantalla = new Simulator(this);
		if (Engine.ISTABLET==true) {
			view = inflater.inflate(R.layout.simtablet, null);
		} else {
			view = inflater.inflate(R.layout.sim, null);
		}
		info_sim=(TextView) view.findViewById(R.id.show_info);
		
		return view; 
	}
	
	

}
