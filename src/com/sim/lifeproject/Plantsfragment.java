package com.sim.lifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Plantsfragment extends Fragment {
	
	View view = null;
	EditText grow_edit1,grow_edit2;
	EditText rain_edit1,rain_edit2,rain_edit3;
	Button plants_update;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		if (Engine.ISTABLET==true) {
			view = inflater.inflate(R.layout.plantstablet, null);
		} else {
			view = inflater.inflate(R.layout.plants, null);
		}
		grow_edit1=(EditText) view.findViewById(R.id.grow_edit1);
        grow_edit2=(EditText) view.findViewById(R.id.grow_edit2);
        rain_edit1=(EditText) view.findViewById(R.id.rain_edit1);
        rain_edit2=(EditText) view.findViewById(R.id.rain_edit2);
        rain_edit3=(EditText) view.findViewById(R.id.rain_edit3);
        plants_update = (Button) view.findViewById(R.id.plants_update);
        rain_edit1.setText(Integer.toString(Engine.PLANTS_ONRAIN_GROWTH_RATIO));
        rain_edit2.setText(Integer.toString(Engine.MAX_RAINING_TIME));
        rain_edit3.setText(Integer.toString(Engine.CHANCES_OF_RAIN));
        grow_edit1.setText(Integer.toString(Engine.PLANTS_NORMAL_GROWTH_RATIO));
        grow_edit2.setText(Integer.toString(Engine.PLANTS_ENHANCED_GROWTH_RATIO));
        plants_update.setOnClickListener(new Button.OnClickListener() {
        	   public void onClick(View v) {
        		  if (rain_edit1.getText().toString().trim().equals("")==false) {
        			   Engine.PLANTS_ONRAIN_GROWTH_RATIO=Integer.parseInt(rain_edit1.getText().toString());
        		  } else {
        			   rain_edit1.setText(Integer.toString(Engine.PLANTS_ONRAIN_GROWTH_RATIO));
        		  }
        		  if (rain_edit2.getText().toString().trim().equals("")==false) {
        			  Engine.MAX_RAINING_TIME=Integer.parseInt(rain_edit2.getText().toString());
        		  } else {
        			  rain_edit2.setText(Integer.toString(Engine.MAX_RAINING_TIME));
        		  }
        		  if (rain_edit3.getText().toString().trim().equals("")==false) {
        			  Engine.CHANCES_OF_RAIN=Integer.parseInt(rain_edit3.getText().toString());
        		  } else {
        			  rain_edit3.setText(Integer.toString(Engine.CHANCES_OF_RAIN));
        		  }
        		  if (grow_edit1.getText().toString().trim().equals("")==false) {
        			  Engine.PLANTS_NORMAL_GROWTH_RATIO=Integer.parseInt(grow_edit1.getText().toString());
        		  } else {
        			  grow_edit1.setText(Integer.toString(Engine.PLANTS_NORMAL_GROWTH_RATIO));
        		  }
        		  if (grow_edit2.getText().toString().trim().equals("")==false) {
        			  Engine.PLANTS_ENHANCED_GROWTH_RATIO=Integer.parseInt(grow_edit2.getText().toString());
        		  } else {
        			  grow_edit2.setText(Integer.toString(Engine.PLANTS_ENHANCED_GROWTH_RATIO));
        		  }
        	   }
           });
		return view; 
	}
	

}
