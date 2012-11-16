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


	public Pantallaprincipal(Context context) {
		super(context);
		randomGenerator = new Random();
			
	}
	@Override
    public void onDraw(Canvas canvas) {
		if (Engine.PLAY==true) {
		super.onDraw(canvas);
        canvas.save();
        if (Engine.FIRST_LOOP==true) {
        	// create first animals of each race
    		specie1List.add(new Animal(Engine.SPECIE1_X_START,Engine.SPECIE1_Y_START,Engine.SPECIE1_MAX_AGE));
    		specie2List.add(new Animal(Engine.SPECIE2_X_START,Engine.SPECIE2_Y_START,Engine.SPECIE2_MAX_AGE));
    		Engine.FIRST_LOOP=false;
        }
        int position_offset;
        if (Engine.SCREEN_W>=500) {
        	position_offset=(Engine.SCREEN_W-500)/2;
        } else {
        	position_offset=(Engine.SCREEN_W-300)/2;
        }
        if (position_offset<0) { position_offset=0; }
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

		
		if (Engine.SCREEN_W>500) { tile_size=5; } else { tile_size=3; }
		// update an draw the matrix
		for (int x=0;x<100;x++) {
			for (int y=0;y<100;y++) {
				matriu_herba.grow(x, y);
				int chance = randomGenerator.nextInt(100); //  range 0..99
				if (chance>100-Engine.PLANTS_NORMAL_GROWTH) {
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-Engine.CPROXIM && matriu_herba.getNear(x,y)==1) { // en cas que estigui proxima a una altre herba
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-Engine.CPLUJA && matriu_herba.getRain(x,y)==1) {
					matriu_herba.born(x,y);
				}
				// dibuixem el fons negre
				canvas.drawRect(position_offset+(x*tile_size), (y*tile_size), position_offset+(x*tile_size)+tile_size, (y*tile_size)+tile_size, fons_matriu);
				// dibuixem el fons terra
				canvas.drawRect(position_offset+(x*tile_size)+1, (y*tile_size)+1, position_offset+(x*tile_size)+tile_size-1, (y*tile_size)+tile_size-1, color_terra);
				// dibuixem les herbes vives segons els diferents nivells d'energia
				if (matriu_herba.getAge(x,y)==1) {
					canvas.drawRect(position_offset+(x*tile_size)+1, (y*tile_size)+1, position_offset+(x*tile_size)+tile_size-1, (y*tile_size)+tile_size-1, color_planta1);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==2) {
					canvas.drawRect(position_offset+(x*tile_size)+1, (y*tile_size)+1, position_offset+(x*tile_size)+tile_size-1, (y*tile_size)+tile_size-1, color_planta2);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==3) {
					canvas.drawRect(position_offset+(x*tile_size)+1, (y*tile_size)+1, position_offset+(x*tile_size)+tile_size-1, (y*tile_size)+tile_size-1, color_planta3);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==4) {
					canvas.drawRect(position_offset+(x*tile_size)+1, (y*tile_size)+1, position_offset+(x*tile_size)+tile_size-1, (y*tile_size)+tile_size-1, color_planta4);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getAge(x,y)==5) {
					canvas.drawRect(position_offset+(x*tile_size)+1, (y*tile_size)+1, position_offset+(x*tile_size)+tile_size-1, (y*tile_size)+tile_size-1, color_planta5);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}
				if (matriu_herba.getRain(x,y)==1) {
					canvas.drawRect(position_offset+(x*tile_size)+1, (y*tile_size)+1, position_offset+(x*tile_size)+tile_size-1, (y*tile_size)+tile_size-1, color_rain);
					Engine.PLANTS_TOTAL_UNITS=Engine.PLANTS_TOTAL_UNITS+1;
				}

			}
		}
		int race1size= specie1List.size();
		int race2size= specie2List.size();
		Engine.SPECIE1_TOTAL_UNITS=race1size;
		Engine.SPECIE2_TOTAL_UNITS=race2size;
		deadAnimals.clear();
		// bucle per visualitzar i fer evolucionar els animals (RACE1)
		for (int i = 0; i < race1size; i++) {
			Animal item=specie1List.get(i);
			
			if (matriu_herba.getEnergy(item.getX(),item.getY())>0) { // si la planta te energia
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
			canvas.drawRect(position_offset+(item.getX()*tile_size)+1, (item.getY()*tile_size)+1, position_offset+(item.getX()*tile_size)+tile_size-1, (item.getY()*tile_size)+tile_size-1, color_race1);
		}
		// remove and clear dead animals (RACE1)
		Engine.SPECIE1_LAST_DEADS=deadAnimals.size();
		for (int i=0;i<deadAnimals.size(); i++ ) {
			Animal item=deadAnimals.get(i);
			specie1List.remove(item);
		}
		deadAnimals.clear();
		// bucle per visualitzar i fer evolucionar els animals (RACE2)
		for (int i = 0; i < race2size; i++) {
			Animal item=specie2List.get(i);
			
			if (matriu_herba.getEnergy(item.getX(),item.getY())>0) { // si la planta te energia
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
			canvas.drawRect(position_offset+(item.getX()*tile_size)+1, (item.getY()*tile_size)+1, position_offset+(item.getX()*tile_size)+tile_size-1, (item.getY()*tile_size)+tile_size-1, color_race2);
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
