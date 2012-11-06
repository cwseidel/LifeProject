package com.sim.lifeproject;



import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class LifeProjectActivity extends Activity {
    /** Called when the activity is first created. */
	Thread bucleprincipal;
	View pantalla;
	LinearLayout upper;
	protected static final int UPDATEID=0x101;
	static Handler gestor;
	int en_moviment=1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pantalla = new Pantallaprincipal(this);
        setContentView(R.layout.main);
        Resources res = getResources();
        
        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();
         
        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Simulation",res.getDrawable(android.R.drawable.ic_dialog_info));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Setup1",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Setup2",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);
        
        pantalla.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.pantallasimulacio);
        upper.addView(pantalla);
        Button buttonstart = (Button) findViewById(R.id.start);
        Button buttonstop = (Button) findViewById(R.id.stop);
        
        gestor = new Handler() {
            public void handleMessage(Message msg) {
            	switch (msg.what) {
            	case LifeProjectActivity.UPDATEID:
            		pantalla.invalidate();
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
        						Thread.sleep(100);
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
        buttonstart.setOnClickListener(new Button.OnClickListener() {
     	   public void onClick(View v) {
     		   en_moviment=1;
     	   }
        });
        buttonstop.setOnClickListener(new Button.OnClickListener() {
      	   public void onClick(View v) {
      		   en_moviment=0;
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