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
import android.content.Context;
import android.content.res.Configuration;
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
	protected static final int UPDATEID=0x101;
	static Handler gestor;
	View pantalla, grafic;
	LinearLayout upper;
	TabHost tabs;
	EditText grow_edit1,grow_edit2;
	EditText rain_edit1,rain_edit2,rain_edit3;
	EditText specie1_edit1,specie1_edit2,specie1_edit3,specie1_edit4,specie1_edit5,specie1_edit6,specie1_edit7,specie1_edit8;
	EditText specie2_edit1,specie2_edit2,specie2_edit3,specie2_edit4,specie2_edit5,specie2_edit6,specie2_edit7,specie2_edit8;
	TextView info_sim,info_specie1,info_specie2,info_graph;
	Button plants_update,specie1_update,specie2_update;
	
	//public static boolean isHoneycomb() {
	    // Can use static final constants like HONEYCOMB, declared in later versions
	    // of the OS since they are inlined at compile time. This is guaranteed behavior.
	   // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	//}

	public static boolean isTablet(Context context) {
	    return (context.getResources().getConfiguration().screenLayout
	            & Configuration.SCREENLAYOUT_SIZE_MASK)
	            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	//public static boolean isHoneycombTablet(Context context) {
	//    return isHoneycomb() && isTablet(context);
	//}
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup variables
        Engine.PLAY=false;
        Engine.FIRST_LOOP=true;
        if (isTablet(getApplicationContext())) {
        	setContentView(R.layout.maintablet);
        	Engine.ISTABLET=true;
        } else {
        	setContentView(R.layout.main);
        	Engine.ISTABLET=false;
        }
        pantalla = new Simulator(this);
        grafic = new Graph(this); 
        Display display = getWindowManager().getDefaultDisplay();
        Engine.SCREEN_W=display.getWidth();
        // ara es fa amb getSize a partir de api 13
        Resources res = getResources();
        
        // tab setup
        tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup(); 
        TabHost.TabSpec spec=tabs.newTabSpec("mytab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Sim",res.getDrawable(android.R.drawable.ic_menu_gallery));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mytab5");
        spec.setContent(R.id.tab6);
        spec.setIndicator("Graph",res.getDrawable(android.R.drawable.ic_menu_gallery));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mytab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Plants",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mytab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator(" Red specie ",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mytab4");
        spec.setContent(R.id.tab4);
        spec.setIndicator(" Blue specie ",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mytab5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Help",res.getDrawable(android.R.drawable.ic_menu_help));
        tabs.addTab(spec);
        // setup width and height of tabs
        //tabs.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        tabs.setCurrentTab(5); // begin with help tab
        // control of tab
        /*tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				pantalla.invalidate();
				grafic.invalidate();
			}
        }); */
        
        pantalla.requestFocus();
        // add canvas to views
        LinearLayout canvassim = (LinearLayout) findViewById(R.id.pantallasimulacio);
        canvassim.addView(pantalla);
        LinearLayout canvasgraph = (LinearLayout) findViewById(R.id.graficsimulacio);
        canvasgraph.addView(grafic);
        
        // begin setup UI layout
        plants_update = (Button) findViewById(R.id.plants_update);
        specie1_update = (Button) findViewById(R.id.specie1_update);
        specie2_update = (Button) findViewById(R.id.specie2_update);
        grow_edit1=(EditText)findViewById(R.id.grow_edit1);
        grow_edit2=(EditText)findViewById(R.id.grow_edit2);
        rain_edit1=(EditText)findViewById(R.id.rain_edit1);
        rain_edit2=(EditText)findViewById(R.id.rain_edit2);
        rain_edit3=(EditText)findViewById(R.id.rain_edit3);
        info_sim=(TextView)findViewById(R.id.show_info);
        info_graph=(TextView)findViewById(R.id.show_infograph);
        specie1_edit1=(EditText)findViewById(R.id.specie1_edit1);
        specie1_edit2=(EditText)findViewById(R.id.specie1_edit2);
        specie1_edit3=(EditText)findViewById(R.id.specie1_edit3);
        specie1_edit4=(EditText)findViewById(R.id.specie1_edit4);
        specie1_edit5=(EditText)findViewById(R.id.specie1_edit5);
        specie1_edit6=(EditText)findViewById(R.id.specie1_edit6);
        specie1_edit7=(EditText)findViewById(R.id.specie1_edit7);
        specie1_edit8=(EditText)findViewById(R.id.specie1_edit8);
        specie2_edit1=(EditText)findViewById(R.id.specie2_edit1);
        specie2_edit2=(EditText)findViewById(R.id.specie2_edit2);
        specie2_edit3=(EditText)findViewById(R.id.specie2_edit3);
        specie2_edit4=(EditText)findViewById(R.id.specie2_edit4);
        specie2_edit5=(EditText)findViewById(R.id.specie2_edit5);
        specie2_edit6=(EditText)findViewById(R.id.specie2_edit6);
        specie2_edit7=(EditText)findViewById(R.id.specie2_edit7);
        specie2_edit8=(EditText)findViewById(R.id.specie2_edit8);
        rain_edit1.setText(Integer.toString(Engine.PLANTS_ONRAIN_GROWTH_RATIO));
        rain_edit2.setText(Integer.toString(Engine.MAX_RAINING_TIME));
        rain_edit3.setText(Integer.toString(Engine.CHANCES_OF_RAIN));
        grow_edit1.setText(Integer.toString(Engine.PLANTS_NORMAL_GROWTH_RATIO));
        grow_edit2.setText(Integer.toString(Engine.PLANTS_ENHANCED_GROWTH_RATIO));
        specie1_edit1.setText(Integer.toString(Engine.SPECIE1_CHANCES_TO_BORN));
        specie1_edit2.setText(Integer.toString(Engine.SPECIE1_ENERGY_NEEDED));
        specie1_edit3.setText(Integer.toString(Engine.SPECIE1_MAX_AGE));
        specie1_edit4.setText(Integer.toString(Engine.SPECIE1_X_START));
        specie1_edit5.setText(Integer.toString(Engine.SPECIE1_Y_START));
        specie1_edit6.setText(Integer.toString(Engine.SPECIE1_MINIMUM_AGE_TO_REPRODUCE));
        specie1_edit7.setText(Integer.toString(Engine.SPECIE1_MINIMUM_ENERGY_TO_REPRODUCE));
        specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        specie2_edit1.setText(Integer.toString(Engine.SPECIE2_CHANCES_TO_BORN));
        specie2_edit2.setText(Integer.toString(Engine.SPECIE2_ENERGY_NEEDED));
        specie2_edit3.setText(Integer.toString(Engine.SPECIE2_MAX_AGE));
        specie2_edit4.setText(Integer.toString(Engine.SPECIE2_X_START));
        specie2_edit5.setText(Integer.toString(Engine.SPECIE2_Y_START));
        specie2_edit6.setText(Integer.toString(Engine.SPECIE2_MINIMUM_AGE_TO_REPRODUCE));
        specie2_edit7.setText(Integer.toString(Engine.SPECIE2_MINIMUM_ENERGY_TO_REPRODUCE));
        specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        // end setup UI layout
        gestor = new Handler() {
            public void handleMessage(Message msg) {
            	switch (msg.what) {
            	case LifeProjectActivity.UPDATEID:
            		if (Engine.PLAY==true) { Engine.evolve(); Engine.buildgraph(); pantalla.invalidate(); grafic.invalidate(); }
            		float specie1DeadValue;
            		float specie2DeadValue;
            		if (Engine.SPECIE1_TOTAL_UNITS!=0) {
            			specie1DeadValue=(float)Math.round(((float)Engine.SPECIE1_LAST_DEADS*100/(float)Engine.SPECIE1_TOTAL_UNITS)*100)/100;
            		} else {
            			specie1DeadValue=0;
            		}
            		if (Engine.SPECIE2_TOTAL_UNITS!=0) {
            			specie2DeadValue=(float)Math.round(((float)Engine.SPECIE2_LAST_DEADS*100/(float)Engine.SPECIE2_TOTAL_UNITS)*100)/100;
            		} else {
            			specie2DeadValue=0;
            		}
            		info_sim.setText("Plants: "+(float)Engine.PLANTS_TOTAL_UNITS/100+"% ("+Engine.LOOPS/365+" years and "+Engine.LOOPS%365+" days)\nRed specie: "+Engine.SPECIE1_TOTAL_UNITS+" alive (death ratio: "+(float)specie1DeadValue+"%)\nBlue specie: "+Engine.SPECIE2_TOTAL_UNITS+" alive (death ratio: "+(float)specie2DeadValue+"%)");
        			info_graph.setText("Plants: "+(float)Engine.PLANTS_TOTAL_UNITS/100+"% ("+Engine.LOOPS/365+" years and "+Engine.LOOPS%365+" days)\nRed specie: "+Engine.SPECIE1_TOTAL_UNITS+" alive (death ratio: "+(float)specie1DeadValue+"%)\nBlue specie: "+Engine.SPECIE2_TOTAL_UNITS+" alive (death ratio: "+(float)specie2DeadValue+"%)");

            		break;
            	}
            	super.handleMessage(msg);
            }
        };
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
       		  tabs.setCurrentTab(0);
       	   }
          });
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
        			specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		} else {
        			specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        			specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		}
        		
        		tabs.setCurrentTab(0);
        	   }
           });
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
        			specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		} else {
        			specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        			specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        		}
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
	        	Engine.reset();
	        	finish();
	            break;
	        case R.id.menu_start:
	        	Engine.PLAY=true;                
	        	break;
	        case R.id.menu_stop: 
	        	Engine.PLAY=false; 
	        	break;
	        case R.id.menu_reset: 
	        	reset(); 
	        	break;
	    }
	    return true;
	}
	public void reset() {
		Engine.reset();
		pantalla.invalidate();
		grafic.invalidate();
		rain_edit1.setText(Integer.toString(Engine.PLANTS_ONRAIN_GROWTH_RATIO));
        rain_edit2.setText(Integer.toString(Engine.MAX_RAINING_TIME));
        rain_edit3.setText(Integer.toString(Engine.CHANCES_OF_RAIN));
        grow_edit1.setText(Integer.toString(Engine.PLANTS_NORMAL_GROWTH_RATIO));
        grow_edit2.setText(Integer.toString(Engine.PLANTS_ENHANCED_GROWTH_RATIO));
        specie1_edit1.setText(Integer.toString(Engine.SPECIE1_CHANCES_TO_BORN));
        specie1_edit2.setText(Integer.toString(Engine.SPECIE1_ENERGY_NEEDED));
        specie1_edit3.setText(Integer.toString(Engine.SPECIE1_MAX_AGE));
        specie1_edit4.setText(Integer.toString(Engine.SPECIE1_X_START));
        specie1_edit5.setText(Integer.toString(Engine.SPECIE1_Y_START));
        specie1_edit6.setText(Integer.toString(Engine.SPECIE1_MINIMUM_AGE_TO_REPRODUCE));
        specie1_edit7.setText(Integer.toString(Engine.SPECIE1_MINIMUM_ENERGY_TO_REPRODUCE));
        specie1_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
        specie2_edit1.setText(Integer.toString(Engine.SPECIE2_CHANCES_TO_BORN));
        specie2_edit2.setText(Integer.toString(Engine.SPECIE2_ENERGY_NEEDED));
        specie2_edit3.setText(Integer.toString(Engine.SPECIE2_MAX_AGE));
        specie2_edit4.setText(Integer.toString(Engine.SPECIE2_X_START));
        specie2_edit5.setText(Integer.toString(Engine.SPECIE2_Y_START));
        specie2_edit6.setText(Integer.toString(Engine.SPECIE2_MINIMUM_AGE_TO_REPRODUCE));
        specie2_edit7.setText(Integer.toString(Engine.SPECIE2_MINIMUM_ENERGY_TO_REPRODUCE));
        specie2_edit8.setText(Integer.toString(Engine.PROCESSINGLIMIT));
	}
}