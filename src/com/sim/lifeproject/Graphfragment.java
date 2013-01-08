package com.sim.lifeproject;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Graphfragment extends Fragment {
	
	View view = null;
	TextView info_graph;
	View grafic;
	Thread bucleprincipal;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		grafic = new Graph(getActivity().getApplicationContext()); 
		if (Engine.ISTABLET==true) {
			view = inflater.inflate(R.layout.graphtablet, null);
		} else {
			view = inflater.inflate(R.layout.graph, null);
		}
		info_graph=(TextView) view.findViewById(R.id.show_infograph);
		LinearLayout canvasgraph = (LinearLayout) view.findViewById(R.id.graficsimulacio);
	    canvasgraph.addView(grafic);
	    bucleprincipal = new Thread() {
        	public void run() {
        		while (true) {
        			Message msg = new Message();
        			msg.what= LifeProjectActivity.UPDATEID;
       				LifeProjectActivity.gestor.sendMessage(msg);
      					try {
      						Thread.sleep(400);
      					} catch (InterruptedException e) {
      						Engine.PLAY=false;
        			}
        		}
        	}
        };
        bucleprincipal.start();
		
		return view; 
	}
	
	

}
