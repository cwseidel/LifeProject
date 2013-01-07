package com.sim.lifeproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class Specie2fragment extends Fragment {
	
	View view = null;
	EditText specie2_edit1,specie2_edit2,specie2_edit3,specie2_edit4,specie2_edit5,specie2_edit6,specie2_edit7,specie2_edit8;
	Button specie2_update;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		if (Engine.ISTABLET==true) {
			view = inflater.inflate(R.layout.specie2tablet, null);
		} else {
			view = inflater.inflate(R.layout.specie2, null);
		}
		specie2_edit1=(EditText) view.findViewById(R.id.specie2_edit1);
        specie2_edit2=(EditText) view.findViewById(R.id.specie2_edit2);
        specie2_edit3=(EditText) view.findViewById(R.id.specie2_edit3);
        specie2_edit4=(EditText) view.findViewById(R.id.specie2_edit4);
        specie2_edit5=(EditText) view.findViewById(R.id.specie2_edit5);
        specie2_edit6=(EditText) view.findViewById(R.id.specie2_edit6);
        specie2_edit7=(EditText) view.findViewById(R.id.specie2_edit7);
        specie2_edit8=(EditText) view.findViewById(R.id.specie2_edit8);
        specie2_update = (Button) view.findViewById(R.id.specie2_update);
        specie2_edit1.setText(Integer.toString(Engine.SPECIE2_CHANCES_TO_BORN));
        specie2_edit2.setText(Integer.toString(Engine.SPECIE2_ENERGY_NEEDED));
        specie2_edit3.setText(Integer.toString(Engine.SPECIE2_MAX_AGE));
        specie2_edit4.setText(Integer.toString(Engine.SPECIE2_X_START));
        specie2_edit5.setText(Integer.toString(Engine.SPECIE2_Y_START));
        specie2_edit6.setText(Integer.toString(Engine.SPECIE2_MINIMUM_AGE_TO_REPRODUCE));
        specie2_edit7.setText(Integer.toString(Engine.SPECIE2_MINIMUM_ENERGY_TO_REPRODUCE));
        specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        specie2_update.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		if (specie2_edit1.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE2_CHANCES_TO_BORN=Integer.parseInt(specie2_edit1.getText().toString());
        		} else {
        			specie2_edit1.setText(Integer.toString(Engine.SPECIE2_CHANCES_TO_BORN));
        		}
        		if (specie2_edit2.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE2_ENERGY_NEEDED=Integer.parseInt(specie2_edit2.getText().toString());
        		} else {
        			specie2_edit2.setText(Integer.toString(Engine.SPECIE2_ENERGY_NEEDED));
        		}
        		if (specie2_edit3.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE2_MAX_AGE=Integer.parseInt(specie2_edit3.getText().toString());
        		} else {
        			specie2_edit3.setText(Integer.toString(Engine.SPECIE2_MAX_AGE));
        		}
        		if (specie2_edit4.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE2_X_START=Integer.parseInt(specie2_edit4.getText().toString());
        		} else {
        			specie2_edit4.setText(Integer.toString(Engine.SPECIE2_X_START));
        		}
        		if (specie2_edit5.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE2_Y_START=Integer.parseInt(specie2_edit5.getText().toString());
        		} else {
        			specie2_edit5.setText(Integer.toString(Engine.SPECIE2_Y_START));
        		}
        		if (specie2_edit6.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE2_MINIMUM_AGE_TO_REPRODUCE=Integer.parseInt(specie2_edit6.getText().toString());
        		} else {
        			specie2_edit6.setText(Integer.toString(Engine.SPECIE2_MINIMUM_AGE_TO_REPRODUCE));
        		}
        		if (specie2_edit7.getText().toString().trim().equals("")==false) {
        			Engine.SPECIE2_MINIMUM_ENERGY_TO_REPRODUCE=Integer.parseInt(specie2_edit7.getText().toString());
        		} else {
        			specie2_edit7.setText(Integer.toString(Engine.SPECIE2_MINIMUM_ENERGY_TO_REPRODUCE));
        		}
        		if (specie2_edit8.getText().toString().trim().equals("")==false) {
        			Engine.PROCESSINGLIMIT=Integer.parseInt(specie2_edit8.getText().toString());
        			//specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		} else {
        			//specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        			specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		}
        	   }
           });
		
		return view; 
	}
	
	

}
