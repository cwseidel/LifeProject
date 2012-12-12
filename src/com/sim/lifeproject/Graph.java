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


import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;



public class Graph extends View {
	

	private int tile_size;
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
	Paint fons_matriu= new Paint();
	// arraylist per les dos llistes d'animals
	ArrayList<Animal> specie1List = new ArrayList<Animal>();
	ArrayList<Animal> specie2List = new ArrayList<Animal>();
	ArrayList<Animal> deadAnimals = new ArrayList<Animal>();
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;
	int h_position_offset;
	int v_position_offset=30;
	Bitmap fons_start;


	public Graph(Context context) {
		super(context);
		// set tile colors
		color_terra.setColor(Color.rgb(136, 96, 17));
		color_planta1.setColor(Color.rgb(113, 255, 113));
		color_planta2.setColor(Color.rgb(4, 255, 4));
		color_planta3.setColor(Color.rgb(0, 232, 0));
		color_planta4.setColor(Color.rgb(0, 170, 0));
		color_planta5.setColor(Color.rgb(0, 115, 0));
		color_race1.setColor(Color.rgb(255, 0, 0));
		color_race2.setColor(Color.rgb(255, 255, 0));
		fons_matriu.setColor(Color.rgb(0, 0, 0));
		color_rain.setColor(Color.rgb(0, 128, 255));
		color_rain.setAlpha(100);
		color_race1.setAlpha(100);
		color_race2.setAlpha(100);
		// end of set tile colors
		
		
		
			
	}
	@Override

	public boolean onTouchEvent(MotionEvent event) {
	   switch(event.getAction()) {
	   case MotionEvent.ACTION_DOWN:
		  Engine.X_SCALE_CENTER = (int) event.getX();
		  Engine.Y_SCALE_CENTER = (int) event.getY();
		  Engine.X_SCALE=2.f;
		  Engine.Y_SCALE=2.f;
	      break;
	   case MotionEvent.ACTION_UP:
		   	Engine.X_SCALE=1.f;
		   	Engine.Y_SCALE=1.f;
		   	Engine.X_SCALE_CENTER = 0;
		   	Engine.Y_SCALE_CENTER = 0;
		   	break;
	   case MotionEvent.ACTION_MOVE:
	   		Engine.X_SCALE_CENTER = (int) event.getX();
	   		Engine.Y_SCALE_CENTER = (int) event.getY();
	   		break;
	   }
	  
	   //mScaleDetector.onTouchEvent(event);
	   return true;
	}
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();
	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
	        return true;
	    }
	}

	@Override
    public void onDraw(Canvas canvas) {
		// This method shows a snapshot of the simulation matrix
		showSnapshot(canvas);
		
	}
	private void showSnapshot(Canvas canvas) {
		// This method shows a snapshot of the simulation matrix
        //define master scale 
        if (Engine.SCREEN_W>300) {
        	Engine.MASTER_SCALE=.8f;
        }
        if (Engine.SCREEN_W>540) {
        	Engine.MASTER_SCALE=1.f;
        }
        canvas.scale(Engine.X_SCALE*Engine.MASTER_SCALE,Engine.Y_SCALE*Engine.MASTER_SCALE,Engine.X_SCALE_CENTER,Engine.Y_SCALE_CENTER);
        // control position of the simulation matrix
        if (Engine.SCREEN_W>=540) {
        	h_position_offset=(Engine.SCREEN_W-540)/2;
        } else {
        	h_position_offset=(Engine.SCREEN_W-272)/2;
        }
        if (Engine.SCREEN_W>500) { tile_size=5; } else { tile_size=3; } // set tile size
		// update an draw the matrix
		for (int x=0;x<100;x++) {
			for (int y=0;y<100;y++) {
				// dibuixem el fons negre
				canvas.drawRect(h_position_offset+(x*tile_size), v_position_offset+(y*tile_size), h_position_offset+(x*tile_size)+tile_size, v_position_offset+(y*tile_size)+tile_size, fons_matriu);
				// dibuixem el fons terra
				canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_terra);
			}
		}
	}

}
