/*
    Copyright (C) 2012  Ferran Fàbregas

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.sim.lifeproject;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class LifeProjectActivity extends Activity {
    /** Called when the activity is first created. */
	Thread bucleprincipal;
	View pantalla;
	LinearLayout upper;
	protected static final int UPDATEID=0x101;
	static Handler gestor;
	TabHost tabs;
	EditText grow_edit1;
	EditText grow_edit2;
	EditText rain_edit1;
	EditText rain_edit2;
	EditText rain_edit3;
	EditText race1_edit1;
	EditText race1_edit2;
	EditText race1_edit3;
	EditText race1_edit4;
	EditText race1_edit5;
	EditText race2_edit1;
	EditText race2_edit2;
	EditText race2_edit3;
	EditText race2_edit4;
	EditText race2_edit5;
	TextView show_info,num_race1,num_race2;
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pantalla = new Pantallaprincipal(this);
        setContentView(R.layout.main);
        Engine.PLAY =false;
        Engine.FIRST_LOOP = true;
        Display display = getWindowManager().getDefaultDisplay();
        Engine.SCREEN_W=display.getWidth();
        Resources res = getResources();
        
        tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();
         
        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Sim",res.getDrawable(android.R.drawable.ic_menu_gallery));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Plants",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Spice 1",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Spice 2",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Help",res.getDrawable(android.R.drawable.ic_menu_help));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);
        
        pantalla.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.pantallasimulacio);
        upper.addView(pantalla);
        Button rain_update = (Button) findViewById(R.id.plants_update);
        Button race1_update = (Button) findViewById(R.id.race1_update);
        Button race2_update = (Button) findViewById(R.id.race2_update);
        grow_edit1=(EditText)findViewById(R.id.grow_edit1);
        grow_edit2=(EditText)findViewById(R.id.grow_edit2);
        rain_edit1=(EditText)findViewById(R.id.rain_edit1);
        rain_edit2=(EditText)findViewById(R.id.rain_edit2);
        rain_edit3=(EditText)findViewById(R.id.rain_edit3);
        show_info=(TextView)findViewById(R.id.show_info);
        race1_edit1=(EditText)findViewById(R.id.race1_edit1);
        race1_edit2=(EditText)findViewById(R.id.race1_edit2);
        race1_edit3=(EditText)findViewById(R.id.race1_edit3);
        race1_edit4=(EditText)findViewById(R.id.race1_edit4);
        race1_edit5=(EditText)findViewById(R.id.race1_edit5);
        race2_edit1=(EditText)findViewById(R.id.race2_edit1);
        race2_edit2=(EditText)findViewById(R.id.race2_edit2);
        race2_edit3=(EditText)findViewById(R.id.race2_edit3);
        race2_edit4=(EditText)findViewById(R.id.race2_edit4);
        race2_edit5=(EditText)findViewById(R.id.race2_edit5);
        rain_edit1.setText(Integer.toString(Engine.CPLUJA));
        rain_edit2.setText(Integer.toString(Engine.MAX_RAINING_TIME));
        rain_edit3.setText(Integer.toString(Engine.PROB_RAIN));
        grow_edit1.setText(Integer.toString(Engine.CNORMAL));
        grow_edit2.setText(Integer.toString(Engine.CPROXIM));
        race1_edit1.setText(Integer.toString(Engine.C_BORN_RACE1));
        race1_edit2.setText(Integer.toString(Engine.EFICIENCY_RACE1));
        race1_edit3.setText(Integer.toString(Engine.RACE1_MAX_AGE));
        race1_edit4.setText(Integer.toString(Engine.X_RACE1));
        race1_edit5.setText(Integer.toString(Engine.Y_RACE1));
        race2_edit1.setText(Integer.toString(Engine.C_BORN_RACE2));
        race2_edit2.setText(Integer.toString(Engine.EFICIENCY_RACE2));
        race2_edit3.setText(Integer.toString(Engine.RACE2_MAX_AGE));
        race2_edit4.setText(Integer.toString(Engine.X_RACE2));
        race2_edit5.setText(Integer.toString(Engine.Y_RACE2));
        gestor = new Handler() {
            public void handleMessage(Message msg) {
            	switch (msg.what) {
            	case LifeProjectActivity.UPDATEID:
            		pantalla.invalidate();
            		show_info.setText("(Green) Plants: "+(float)Engine.NUM_OF_GRASS/100+ "%\n(Red) Specie 1: "+Engine.NUM_OF_RACE1+" alive ("+(float)Engine.DEAD_RACE1*100/Engine.NUM_OF_RACE1+" % died)\n (Yellow) Specie 2: "+Engine.NUM_OF_RACE2+" alive ("+(float)Engine.DEAD_RACE2*100/Engine.NUM_OF_RACE2+" % died)");
            		break;
            	}
            	super.handleMessage(msg);
            }
        };
        bucleprincipal = new Thread() {
        	public void run() {
        		while (true) {
        				if (Engine.PLAY==true) {
        					Message msg = new Message();
        					msg.what= LifeProjectActivity.UPDATEID;
        					LifeProjectActivity.gestor.sendMessage(msg);
        					try {
        						Thread.sleep(300);
        					} catch (InterruptedException e) {
        						Engine.PLAY=false;
        					}
        				} else {
        					//nothing
        				}
        		}
        	}
        };
        bucleprincipal.start();
        

        rain_update.setOnClickListener(new Button.OnClickListener() {
       	   public void onClick(View v) {
       		  Engine.CPLUJA=Integer.parseInt(rain_edit1.getText().toString());
       		  Engine.MAX_RAINING_TIME=Integer.parseInt(rain_edit2.getText().toString());
       		  Engine.PROB_RAIN=Integer.parseInt(rain_edit3.getText().toString());
       		  Engine.CNORMAL=Integer.parseInt(grow_edit1.getText().toString());
     		  Engine.CPROXIM=Integer.parseInt(grow_edit2.getText().toString());
       		  tabs.setCurrentTab(0);
       	   }
          });
        race1_update.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Engine.C_BORN_RACE1=Integer.parseInt(race1_edit1.getText().toString());
        		Engine.EFICIENCY_RACE1=Integer.parseInt(race1_edit2.getText().toString());
        		Engine.RACE1_MAX_AGE=Integer.parseInt(race1_edit3.getText().toString());
        		Engine.X_RACE1=Integer.parseInt(race1_edit4.getText().toString());
        		Engine.Y_RACE1=Integer.parseInt(race1_edit5.getText().toString());
        		tabs.setCurrentTab(0);
        	   }
           });
        race2_update.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v) {
        		Engine.C_BORN_RACE2=Integer.parseInt(race2_edit1.getText().toString());
           		Engine.EFICIENCY_RACE2=Integer.parseInt(race2_edit2.getText().toString());
           		Engine.RACE2_MAX_AGE=Integer.parseInt(race2_edit3.getText().toString());
           		Engine.X_RACE2=Integer.parseInt(race2_edit4.getText().toString());
        		Engine.Y_RACE2=Integer.parseInt(race2_edit5.getText().toString());
           		tabs.setCurrentTab(0);
        	   }
           });
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_exit:
	        	finish();
	            break;
	        case R.id.menu_start:
	        	Engine.PLAY=true;                
	        	break;
	        case R.id.menu_stop: 
	        	Engine.PLAY=false; 
	        	break;
	    }
	    return true;
	}
}