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
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;



public class Pantallaprincipal extends View {
	private int is_raining=0;
	private int raining_time=0;
	private int current_rain_x=0;
	private int current_rain_y=0;
	private GrassMatrix matriu_herba=new GrassMatrix();
	private Random randomGenerator;
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
	Paint fons_matriu= new Paint();
	// arraylist per les dos llistes d'animals
	ArrayList<Animal> specie1List = new ArrayList<Animal>();
	ArrayList<Animal> specie2List = new ArrayList<Animal>();
	ArrayList<Animal> deadAnimals = new ArrayList<Animal>();
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;
	int h_position_offset;
	int v_position_offset=30;


	public Pantallaprincipal(Context context) {
		super(context);
		randomGenerator = new Random();
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
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
		
		if (Engine.PLAY==true) {
		super.onDraw(canvas);
        canvas.save();
       
        canvas.scale(Engine.X_SCALE,Engine.Y_SCALE,Engine.X_SCALE_CENTER,Engine.Y_SCALE_CENTER);
        //canvas.scale(mScaleFactor,mScaleFactor,Engine.X_SCALE_CENTER,Engine.Y_SCALE_CENTER);
        //canvas.scale(mScaleFactor,mScaleFactor);
       
        if (Engine.FIRST_LOOP==true) {
        	// create first animals of each race
    		specie1List.add(new Animal(Engine.SPECIE1_X_START,Engine.SPECIE1_Y_START,Engine.SPECIE1_MAX_AGE));
    		specie2List.add(new Animal(Engine.SPECIE2_X_START,Engine.SPECIE2_Y_START,Engine.SPECIE2_MAX_AGE));
    		Engine.FIRST_LOOP=false;
    		 if (Engine.SCREEN_W>540) {
    			 //Engine.X_SCALE_CENTER=h_position_offset+270;
    			 //Engine.Y_SCALE_CENTER=270;
    		 } else {
    			 //Engine.X_SCALE_CENTER=h_position_offset+170;
    			 //Engine.Y_SCALE_CENTER=170;
    		 }
        }
        // control position of the simulation matrix
        if (Engine.SCREEN_W>=540) {
        	h_position_offset=(Engine.SCREEN_W-540)/2;
        } else {
        	h_position_offset=(Engine.SCREEN_W-340)/2;
        }
        if (h_position_offset<0) { h_position_offset=0; }
        if (Engine.SCREEN_W>540) {
        	//canvas.translate((Engine.X_SCALE_CENTER-(Engine.SCREEN_W-540))/mScaleFactor,(Engine.Y_SCALE_CENTER)/mScaleFactor);
        	//canvas.translate(((Engine.X_SCALE_CENTER-(h_position_offset+250))/mScaleFactor),(Engine.Y_SCALE_CENTER-270)/mScaleFactor);
        } else {
        	//canvas.translate((Engine.X_SCALE_CENTER-(Engine.SCREEN_W-540))/mScaleFactor,(Engine.Y_SCALE_CENTER)/mScaleFactor);
        	//canvas.translate(((Engine.X_SCALE_CENTER-(h_position_offset+150))/mScaleFactor),(Engine.Y_SCALE_CENTER-170)/mScaleFactor);
        }
        Engine.PLANTS_TOTAL_UNITS=0;
		int random_x = randomGenerator.nextInt(100); //  range 0..99
		int random_y = randomGenerator.nextInt(100); //  range 0..99
		int rain_chance = randomGenerator.nextInt(100); //  range 0..99
		if (is_raining==1) { 
			raining_time=raining_time+1;
			if (raining_time>Engine.MAX_RAINING_TIME) { // si ha de parar de ploure
				matriu_herba.rain_off(current_rain_x,current_rain_y);
				is_raining=0;
				raining_time=0;
			}
		}
		if (rain_chance>100-Engine.CHANCES_OF_RAIN && is_raining==0) { // si no plou i es produeix la possiblitat
			matriu_herba.rain_on(random_x,random_y);
			current_rain_x=random_x;
			current_rain_y=random_y;
			is_raining=1; // plou
			raining_time=1;
		}
		
		if (Engine.SCREEN_W>500) { tile_size=5; } else { tile_size=3; } // set tile size
		// update an draw the matrix
		for (int x=0;x<100;x++) {
			for (int y=0;y<100;y++) {
				matriu_herba.grow(x, y);
				int chance = randomGenerator.nextInt(100); //  range 0..99
				if (chance>100-Engine.PLANTS_NORMAL_GROWTH_RATIO) {
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-Engine.PLANTS_ENHANCED_GROWTH_RATIO && matriu_herba.getNear(x,y)==1) { // en cas que estigui proxima a una altre herba
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-Engine.PLANTS_ONRAIN_GROWTH_RATIO && matriu_herba.getRain(x,y)==1) {
					matriu_herba.born(x,y);
				}
				// dibuixem el fons negre
				canvas.drawRect(h_position_offset+(x*tile_size), v_position_offset+(y*tile_size), h_position_offset+(x*tile_size)+tile_size, v_position_offset+(y*tile_size)+tile_size, fons_matriu);
				// dibuixem el fons terra
				canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_terra);
				// dibuixem les herbes vives segons els diferents nivells d'energia
				if (matriu_herba.getAge(x,y)==1) {
					canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_planta1);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==2) {
					canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_planta2);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==3) {
					canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_planta3);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==4) {
					canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_planta4);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==5) {
					canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_planta5);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getRain(x,y)==1) {
					canvas.drawRect(h_position_offset+(x*tile_size)+1, v_position_offset+(y*tile_size)+1, h_position_offset+(x*tile_size)+tile_size-1, v_position_offset+(y*tile_size)+tile_size-1, color_rain);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}

			}
		}
		int specie1size= specie1List.size();
		int specie2size= specie2List.size();
		Engine.SPECIE1_TOTAL_UNITS=specie1size;
		Engine.SPECIE2_TOTAL_UNITS=specie2size;
		deadAnimals.clear();
		// bucle per visualitzar i fer evolucionar els animals (SPECIE1)
		for (int i = 0; i < specie1size; i++) {
			Animal item=specie1List.get(i);
			
			if (matriu_herba.getEnergy(item.getX(),item.getY())>Engine.SPECIE1_ENERGY_NEEDED) { // si la planta te energia suficient
				if (item.getEnergy()<=10) { // si te gana
						item.feed(); // s'alimenta
						matriu_herba.setEnergy(item.getX(),item.getY(),Engine.SPECIE1_ENERGY_NEEDED); // restem energia a la planta
				}
			}
			if (item.ready_to_reproduce()) { // si esta preparat per reproduirse
				item.reproduce(); // es reprodueix
				Random randomGenerator = new Random();
				int chances_to_born = randomGenerator.nextInt(100); //  range 0..99
				if (chances_to_born<Engine.SPECIE1_CHANCES_TO_BORN) {
					specie1List.add(new Animal(item.getX(),item.getY(),Engine.SPECIE1_MAX_AGE));
				}
				item.grow(); // creix
			} else {
				item.move(); // es mou
				item.grow(); // creix
			}
			if (item.is_dead()) {
				deadAnimals.add(item); // el posem a la llista d'animals morts
			}
			canvas.drawRect(h_position_offset+(item.getX()*tile_size)+1, v_position_offset+(item.getY()*tile_size)+1, h_position_offset+(item.getX()*tile_size)+tile_size-1, v_position_offset+(item.getY()*tile_size)+tile_size-1, color_race1);
		}
		// remove and clear dead animals (RACE1)
		Engine.SPECIE1_LAST_DEADS=deadAnimals.size();
		for (int i=0;i<deadAnimals.size(); i++ ) {
			Animal item=deadAnimals.get(i);
			specie1List.remove(item);
		}
		deadAnimals.clear();
		// bucle per visualitzar i fer evolucionar els animals (SPECIE2)
		for (int i = 0; i < specie2size; i++) {
			Animal item=specie2List.get(i);
			
			if (matriu_herba.getEnergy(item.getX(),item.getY())>Engine.SPECIE2_ENERGY_NEEDED) { // si la planta te energia
				if (item.getEnergy()<=10) { // si te gana
						item.feed(); // s'alimenta
						matriu_herba.setEnergy(item.getX(),item.getY(),Engine.SPECIE2_ENERGY_NEEDED); // restem energia a la planta
				}
			}
			if (item.ready_to_reproduce()) { // si esta preparat per reproduirse
				item.reproduce(); // es reprodueix
				Random randomGenerator = new Random();
				int chances_to_born = randomGenerator.nextInt(100); //  range 0..99
				if (chances_to_born<Engine.SPECIE2_CHANCES_TO_BORN) {
					specie2List.add(new Animal(item.getX(),item.getY(),Engine.SPECIE2_MAX_AGE));
				}
				item.grow(); // creix
			} else {
				item.move(); // es mou
				item.grow(); // creix
			}
			if (item.is_dead()) {
				deadAnimals.add(item); // el posem a la llista d'animals morts
			}
			canvas.drawRect(h_position_offset+(item.getX()*tile_size)+1, v_position_offset+(item.getY()*tile_size)+1, h_position_offset+(item.getX()*tile_size)+tile_size-1, v_position_offset+(item.getY()*tile_size)+tile_size-1, color_race2);
		}
		// remove and clear dead animals (RACE2)
		Engine.SPECIE2_LAST_DEADS=deadAnimals.size();
		for (int i=0;i<deadAnimals.size(); i++ ) {
			Animal item=deadAnimals.get(i);
			specie2List.remove(item);
		}
		deadAnimals.clear();
		//invalidate();
	}
	}

}
