package com.sim.lifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Specie1fragment extends Fragment {
	
	View view = null;
	EditText specie1_edit1,specie1_edit2,specie1_edit3,specie1_edit4,specie1_edit5,specie1_edit6,specie1_edit7,specie1_edit8;
	Button specie1_update;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		if (Engine.ISTABLET==true) {
			view = inflater.inflate(R.layout.specie1tablet, null);
		} else {
			view = inflater.inflate(R.layout.specie1, null);
		}
		specie1_edit1=(EditText) view.findViewById(R.id.specie1_edit1);
        specie1_edit2=(EditText) view.findViewById(R.id.specie1_edit2);
        specie1_edit3=(EditText) view.findViewById(R.id.specie1_edit3);
        specie1_edit4=(EditText) view.findViewById(R.id.specie1_edit4);
        specie1_edit5=(EditText) view.findViewById(R.id.specie1_edit5);
        specie1_edit6=(EditText) view.findViewById(R.id.specie1_edit6);
        specie1_edit7=(EditText) view.findViewById(R.id.specie1_edit7);
        specie1_edit8=(EditText) view.findViewById(R.id.specie1_edit8);
        specie1_update = (Button) view.findViewById(R.id.specie1_update);
        specie1_edit1.setText(Integer.toString(Engine.SPECIE1_CHANCES_TO_BORN));
        specie1_edit2.setText(Integer.toString(Engine.SPECIE1_ENERGY_NEEDED));
        specie1_edit3.setText(Integer.toString(Engine.SPECIE1_MAX_AGE));
        specie1_edit4.setText(Integer.toString(Engine.SPECIE1_X_START));
        specie1_edit5.setText(Integer.toString(Engine.SPECIE1_Y_START));
        specie1_edit6.setText(Integer.toString(Engine.SPECIE1_MINIMUM_AGE_TO_REPRODUCE));
        specie1_edit7.setText(Integer.toString(Engine.SPECIE1_MINIMUM_ENERGY_TO_REPRODUCE));
        specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        specie1_update.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		if (specie1_edit1.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE1_CHANCES_TO_BORN=Integer.parseInt(specie1_edit1.getText().toString());
        		} else {
        			specie1_edit1.setText(Integer.toString(Engine.SPECIE1_CHANCES_TO_BORN));
        		}
        		if (specie1_edit2.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE1_ENERGY_NEEDED=Integer.parseInt(specie1_edit2.getText().toString());
        		} else {
        			specie1_edit2.setText(Integer.toString(Engine.SPECIE1_ENERGY_NEEDED));
        		}
        		if (specie1_edit3.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE1_MAX_AGE=Integer.parseInt(specie1_edit3.getText().toString());
        		} else {
        			specie1_edit3.setText(Integer.toString(Engine.SPECIE1_MAX_AGE));
        		}
        		if (specie1_edit4.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE1_X_START=Integer.parseInt(specie1_edit4.getText().toString());
        		} else {
        			specie1_edit4.setText(Integer.toString(Engine.SPECIE1_X_START));
        		}
        		if (specie1_edit5.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE1_Y_START=Integer.parseInt(specie1_edit5.getText().toString());
        		} else {
        			specie1_edit5.setText(Integer.toString(Engine.SPECIE1_Y_START));
        		}
        		if (specie1_edit6.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE1_MINIMUM_AGE_TO_REPRODUCE=Integer.parseInt(specie1_edit6.getText().toString());
        		} else {
        			specie1_edit6.setText(Integer.toString(Engine.SPECIE1_MINIMUM_AGE_TO_REPRODUCE));
        		}
        		if (specie1_edit7.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE1_MINIMUM_ENERGY_TO_REPRODUCE=Integer.parseInt(specie1_edit7.getText().toString());
        		} else {
        			specie1_edit7.setText(Integer.toString(Engine.SPECIE1_MINIMUM_ENERGY_TO_REPRODUCE));
        		}
        		if (specie1_edit8.getText().toString().trim().equals("")==false) {
        			Engine.PROCESSINGLIMIT=Integer.parseInt(specie1_edit8.getText().toString());
        			// COM COMUNICAR FRAGMENTS??
        			//specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		} else {
        			specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        			//specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		}
        	   }
           });
		return view; 
	}
	
	

}
