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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
public class LifeProjectActivity extends FragmentActivity {
    /** Called when the activity is first created. */
	
	protected static final int UPDATEID=0x101;
	
	
	LinearLayout upper;
	TabHost tabs;
	ViewPager pager;
	MyFragmentsAdapter adapter;
	Simfragment Sf;
	Graphfragment Gf;
	Plantsfragment Pf;
	Specie1fragment S1f;
	Specie2fragment S2f;
	Helpfragment Hf;



	
	
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
       
        
        Display display = getWindowManager().getDefaultDisplay();
        Engine.SCREEN_W=display.getWidth();
        // ara es fa amb getSize a partir de api 13
        Resources res = getResources();
        
        // tab setup
        /*
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
        spec.setIndicator("  Red specie  ",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mytab4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("  Blue specie  ",res.getDrawable(android.R.drawable.ic_menu_edit));
        tabs.addTab(spec);
        spec=tabs.newTabSpec("mytab5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Help",res.getDrawable(android.R.drawable.ic_menu_help));
        tabs.addTab(spec);
        */
        // setup width and height of tabs
        //tabs.getTabWidget().getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        //tabs.setCurrentTab(5); // begin with help tab
        // control of tab
        /*tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				pantalla.invalidate();
				grafic.invalidate();
			}
        }); */

        // end setup UI layout
     // pager setup
     pager = (ViewPager) findViewById(R.id.viewpager);
     adapter = new MyFragmentsAdapter(getSupportFragmentManager());
     Sf = new Simfragment();
     Gf = new Graphfragment();
     Pf =  new Plantsfragment();
     S1f =  new Specie1fragment();
     S2f = new Specie2fragment();
     Hf = new Helpfragment();
     adapter.addFragment(Sf);
     adapter.addFragment(Gf);
     adapter.addFragment(Pf);
     adapter.addFragment(S1f);
     adapter.addFragment(S2f);
     adapter.addFragment(Hf);
     pager.setAdapter(adapter);
     pager.setOnPageChangeListener(new OnPageChangeListener() {
     	public void onPageSelected(int position)
         {
     		//adapter.notifyDataSetChanged();
     		//Engine.calculo();
     		tabs.setCurrentTab(position);
         }

     	public void onPageScrolled(int arg0, float arg1, int arg2) {
     		//adapter.notifyDataSetChanged();
     		//Engine.calculo();
     	}
         public void onPageScrollStateChanged(int arg0) {
         	//adapter.notifyDataSetChanged();
         	//Engine.calculo();
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
		//pantalla.invalidate();
		//grafic.invalidate();
		/*
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
        */
	}
}