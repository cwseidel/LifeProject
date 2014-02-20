/*
    Copyright (C) 2012  Ferran F�bregas

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


import android.content.Context;

import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;



public class Simulator extends View {

	private float tile_size;
	Paint color_terra = new Paint();
	Paint color_planta1 = new Paint();
	Paint color_planta2 = new Paint();
	Paint color_planta3 = new Paint();
	Paint color_planta4 = new Paint();
	Paint color_planta5 = new Paint();
	Paint color_rain = new Paint();
	Paint color_race1 = new Paint();
	Paint color_race2 = new Paint();
	Paint start_image = new Paint();
	Paint lupa_paint = new Paint();
	Paint fons_matriu= new Paint();
	float h_position_offset;
	int v_position_offset=30;
	Bitmap fons_start, lupa;
	int celldistance=1;
	int real_size_x;
	int real_size_y;
	int xtranslation=0;
	int ytranslation=0;
	float basedist;
	PointF midpoint;
	int mode; // 0=zoom / 1=move


	public Simulator(Context context) {
		super(context);
		// get size of the screen
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		real_size_x = size.x;
		real_size_y = size.y;
		// set tile colors
		color_terra.setColor(Color.rgb(136, 96, 17));
		color_planta1.setColor(Color.rgb(113, 255, 113));
		color_planta2.setColor(Color.rgb(4, 255, 4));
		color_planta3.setColor(Color.rgb(0, 232, 0));
		color_planta4.setColor(Color.rgb(0, 170, 0));
		color_planta5.setColor(Color.rgb(0, 115, 0));
		color_race1.setColor(Color.rgb(255, 0, 0));
		color_race2.setColor(Color.rgb(0, 0, 255));
		fons_matriu.setColor(Color.rgb(0, 0, 0));
		color_rain.setColor(Color.rgb(0, 128, 255));
		color_rain.setAlpha(100);
		color_race1.setAlpha(200);
		color_race2.setAlpha(200);
		
		// default scale
		Engine.X_SCALE=1.f;
	   	Engine.Y_SCALE=1.f;
	   	Engine.X_SCALE_CENTER = 0;
	   	Engine.Y_SCALE_CENTER = 0;
	   	
		// end of set tile colors
		lupa=BitmapFactory.decodeResource(getResources(),R.drawable.lupa);
		if (Engine.ISTABLET==true) {
        	fons_start=BitmapFactory.decodeResource(getResources(), R.drawable.starttablet);
        } else {
        	fons_start=BitmapFactory.decodeResource(getResources(), R.drawable.starthi);
        }
		
		
		
			
	}
	@Override

	public boolean onTouchEvent(MotionEvent event) {
	   switch(event.getAction()) {
	   case MotionEvent.ACTION_DOWN:
		  /*Engine.X_SCALE_CENTER = (int) event.getX();
		  Engine.Y_SCALE_CENTER = (int) event.getY();
		  Engine.X_SCALE=2.f;
		  Engine.Y_SCALE=2.f;
		  Engine.IS_MAGNIFIED=true;
		  if (Engine.PLAY==false && Engine.FIRST_LOOP==true) {
			  Engine.PLAY=true;
		  }*/
		  if (Engine.PLAY==false && Engine.FIRST_LOOP==true) {
				  Engine.PLAY=true;
		  }
		  Engine.X_SCALE=1.01f;
		  Engine.Y_SCALE=1.01f;
		  mode=1;
	      break;
	   case MotionEvent.ACTION_UP:
		   	/*Engine.X_SCALE=1.f;
		   	Engine.Y_SCALE=1.f;
		   	Engine.X_SCALE_CENTER = 0;
		   	Engine.Y_SCALE_CENTER = 0;
		   	Engine.IS_MAGNIFIED=false;*/
		    mode=1;
		   	break;
	   case MotionEvent.ACTION_MOVE:
		   	if (mode==0) { // zoom
		   		float newdist;
		   		Engine.X_SCALE_CENTER = (int) midPoint(event).x;
		   		Engine.Y_SCALE_CENTER = (int) midPoint(event).y;
		   		newdist=spacing(event);
		   		if (newdist>basedist) { // zoom in
		   			Engine.X_SCALE+=1.1f;
		   			Engine.Y_SCALE+=1.1f;
		   		}
		   		if (newdist>basedist) { // zoom out
		   			Engine.X_SCALE-=1.1f;
		   			Engine.Y_SCALE-=1.1f;
		   		}
		   		basedist=newdist;
		   	}
		   	if (mode==1) { // move
		   		//Engine.X_SCALE_CENTER = (int) event.getX();
		   		//Engine.Y_SCALE_CENTER = (int) event.getY();
		   		//Log.v("touchpad","x-center:"+(int) event.getX());
		   		//Log.v("touchpad","y-center:"+(int) event.getY());
		   		//Engine.IS_MAGNIFIED=true;
		   		
		   		if (event.getX()>real_size_x/2) {
		   			xtranslation+=40;
		   		}
		   		if (event.getX()<real_size_x/2) {
		   			xtranslation-=40;
		   		}
		   		if (event.getY()>real_size_y/2) {
		   			ytranslation+=40;
		   		}
		   		if (event.getY()<real_size_x/2) {
		   			ytranslation-=40;
		   		}
		   	}
	   		/*Engine.X_SCALE_CENTER = (int) event.getX();
	   		Engine.Y_SCALE_CENTER = (int) event.getY();
	   		Engine.IS_MAGNIFIED=true;*/
	   		break;
	   case MotionEvent.ACTION_POINTER_DOWN:
		   	// second finger
		   basedist=spacing(event);
		   midpoint=midPoint(event);
		   mode=0;
		   break;
	   case MotionEvent.ACTION_POINTER_UP:
		   	// second finger release
		   mode=1;
		   break;
	   }
	  
	   return true;
	}
	
	public float spacing(MotionEvent event) { // distance between two points
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return  (float) Math.sqrt(x * x + y * y);
	}
	
	private PointF midPoint(MotionEvent event) { // midPoint between two points
		PointF point = new PointF();
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
		return point;
	}
	
	@Override
    public void onDraw(Canvas canvas) {
		if (Engine.PLAY==false && Engine.FIRST_LOOP==false) {
			// This method shows a snapshot of the simulation matrix
			showSnapshot(canvas);
		}
		if (Engine.PLAY==false && Engine.FIRST_LOOP==true) { // si encara no s'ha apretat START per primer cop mostrem la pantalla inicial
			super.onDraw(canvas);
	        canvas.save();
	        canvas.drawBitmap(fons_start, 0, 0, start_image);    
		}
		if (Engine.PLAY==true) { // if simulation is running
			showSnapshot(canvas);
		}
	}
	private void showSnapshot(Canvas canvas) {
		// This method shows a snapshot of the simulation matrix  
        Engine.MASTER_SCALE=1.f;
        canvas.translate(xtranslation, ytranslation);
        canvas.scale(Engine.X_SCALE, Engine.Y_SCALE);
        //canvas.scale(Engine.X_SCALE*Engine.MASTER_SCALE,Engine.Y_SCALE*Engine.MASTER_SCALE,Engine.X_SCALE_CENTER,Engine.Y_SCALE_CENTER); 
		tile_size=Engine.rescaling_x(5,real_size_x);
		h_position_offset=(real_size_x-((tile_size*100)+(Engine.rescaling_x(celldistance,real_size_x)*100)))/2;
        // update an draw the matrix
		for (int x=0;x<100;x++) {
			for (int y=0;y<100;y++) {
				// dibuixem el fons negre
				canvas.drawRect(h_position_offset+(x*tile_size), v_position_offset+(y*tile_size), h_position_offset+(x*tile_size)+tile_size, v_position_offset+(y*tile_size)+tile_size, fons_matriu);
				// dibuixem el fons terra
				canvas.drawRect(h_position_offset+(x*tile_size)+celldistance, v_position_offset+(y*tile_size)+celldistance, h_position_offset+(x*tile_size)+tile_size-celldistance, v_position_offset+(y*tile_size)+tile_size-celldistance, color_terra);
				// dibuixem les herbes vives segons els diferents nivells d'energia
				if (Engine.matriu_herba.getAge(x,y)>0 && Engine.matriu_herba.getAge(x,y)<6) {
					canvas.drawRect(h_position_offset+(x*tile_size)+celldistance, v_position_offset+(y*tile_size)+celldistance, h_position_offset+(x*tile_size)+tile_size-celldistance, v_position_offset+(y*tile_size)+tile_size-celldistance, color_planta1);
				}
				/*if (Engine.matriu_herba.getAge(x,y)==2) {
					canvas.drawRect(h_position_offset+(x*tile_size)+celldistance, v_position_offset+(y*tile_size)+celldistance, h_position_offset+(x*tile_size)+tile_size-celldistance, v_position_offset+(y*tile_size)+tile_size-celldistance, color_planta2);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (Engine.matriu_herba.getAge(x,y)==3) {
					canvas.drawRect(h_position_offset+(x*tile_size)+celldistance, v_position_offset+(y*tile_size)+celldistance, h_position_offset+(x*tile_size)+tile_size-celldistance, v_position_offset+(y*tile_size)+tile_size-celldistance, color_planta3);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (Engine.matriu_herba.getAge(x,y)==4) {
					canvas.drawRect(h_position_offset+(x*tile_size)+celldistance, v_position_offset+(y*tile_size)+celldistance, h_position_offset+(x*tile_size)+tile_size-celldistance, v_position_offset+(y*tile_size)+tile_size-celldistance, color_planta4);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (Engine.matriu_herba.getAge(x,y)==5) {
					canvas.drawRect(h_position_offset+(x*tile_size)+celldistance, v_position_offset+(y*tile_size)+celldistance, h_position_offset+(x*tile_size)+tile_size-celldistance, v_position_offset+(y*tile_size)+tile_size-celldistance, color_planta5);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				} */
				if (Engine.matriu_herba.getRain(x,y)==1) {
					canvas.drawRect(h_position_offset+(x*tile_size)+celldistance, v_position_offset+(y*tile_size)+celldistance, h_position_offset+(x*tile_size)+tile_size-celldistance, v_position_offset+(y*tile_size)+tile_size-celldistance, color_rain);
				}
			}
		}
		int specie1size= Engine.specie1List.size();
		int specie2size= Engine.specie2List.size();
		// bucle per visualitzar i fer evolucionar els animals (SPECIE1)
		for (int i = 0; i < specie1size; i++) {
			Animal item=Engine.specie1List.get(i);
			canvas.drawRect(h_position_offset+(item.getX()*tile_size)+celldistance, v_position_offset+(item.getY()*tile_size)+celldistance, h_position_offset+(item.getX()*tile_size)+tile_size-celldistance, v_position_offset+(item.getY()*tile_size)+tile_size-celldistance, color_race1);
		}
		// bucle per visualitzar i fer evolucionar els animals (SPECIE2)
		for (int i = 0; i < specie2size; i++) {
			Animal item=Engine.specie2List.get(i);
			canvas.drawRect(h_position_offset+(item.getX()*tile_size)+celldistance, v_position_offset+(item.getY()*tile_size)+celldistance, h_position_offset+(item.getX()*tile_size)+tile_size-celldistance, v_position_offset+(item.getY()*tile_size)+tile_size-celldistance, color_race2);
		}
		// bucle per visualitzar la lupa si toca
		if (Engine.IS_MAGNIFIED==true) {
			canvas.drawBitmap(lupa,Engine.X_SCALE_CENTER+60, Engine.Y_SCALE_CENTER-20, lupa_paint);		
		}
	}

}
