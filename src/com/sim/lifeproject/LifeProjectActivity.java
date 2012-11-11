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
	int en_moviment=1;
	TabHost tabs;
	EditText grow_edit1;
	EditText grow_edit2;
	EditText rain_edit1;
	EditText rain_edit2;
	EditText rain_edit3;
	TextView num_grass;
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pantalla = new Pantallaprincipal(this);
        setContentView(R.layout.main);
        Display display = getWindowManager().getDefaultDisplay();
        Engine.SCREEN_W=display.getWidth();
        Resources res = getResources();
        
        tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();
         
        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Simulation",res.getDrawable(android.R.drawable.ic_dialog_info));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Rain Setup",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Grow Setup",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);
        
        pantalla.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.pantallasimulacio);
        upper.addView(pantalla);
        Button rain_update = (Button) findViewById(R.id.rain_update);
        Button grow_update = (Button) findViewById(R.id.grow_update);
        grow_edit1=(EditText)findViewById(R.id.grow_edit1);
        grow_edit2=(EditText)findViewById(R.id.grow_edit2);
        rain_edit1=(EditText)findViewById(R.id.rain_edit1);
        rain_edit2=(EditText)findViewById(R.id.rain_edit2);
        rain_edit3=(EditText)findViewById(R.id.rain_edit3);
        num_grass=(TextView)findViewById(R.id.show);
        rain_edit1.setText(Integer.toString(Engine.CPLUJA));
        rain_edit2.setText(Integer.toString(Engine.MAX_RAINING_TIME));
        rain_edit3.setText(Integer.toString(Engine.PROB_RAIN));
        grow_edit1.setText(Integer.toString(Engine.CNORMAL));
        grow_edit2.setText(Integer.toString(Engine.CPROXIM));
        
        gestor = new Handler() {
            public void handleMessage(Message msg) {
            	switch (msg.what) {
            	case LifeProjectActivity.UPDATEID:
            		pantalla.invalidate();
            		num_grass.setText("Plants: "+(float)Engine.NUM_OF_GRASS/100+ "%");
            		break;
            	}
            	super.handleMessage(msg);
            }
        };
        bucleprincipal = new Thread() {
        	public void run() {
        		while (true) {
        				if (en_moviment==1) {
        					Message msg = new Message();
        					msg.what= LifeProjectActivity.UPDATEID;
        					LifeProjectActivity.gestor.sendMessage(msg);
        					try {
        						Thread.sleep(300);
        					} catch (InterruptedException e) {
        						en_moviment=0;
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
     		   en_moviment=1;
     	   }
        });
        grow_update.setOnClickListener(new Button.OnClickListener() {
      	   public void onClick(View v) {
      		  Engine.CNORMAL=Integer.parseInt(grow_edit1.getText().toString());
      		  Engine.CPROXIM=Integer.parseInt(grow_edit2.getText().toString());
      		  tabs.setCurrentTab(0);
      	   }
         });
        rain_update.setOnClickListener(new Button.OnClickListener() {
       	   public void onClick(View v) {
       		  Engine.CPLUJA=Integer.parseInt(rain_edit1.getText().toString());
       		  Engine.MAX_RAINING_TIME=Integer.parseInt(rain_edit2.getText().toString());
       		  Engine.PROB_RAIN=Integer.parseInt(rain_edit3.getText().toString());
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
	        	en_moviment=1;                
	        	break;
	        case R.id.menu_stop: 
	        	en_moviment=0;
	        	break;
	    }
	    return true;
	}
}