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

import java.util.ArrayList;
import java.util.Random;



public class Engine {
// plants variables
public static int PLANTS_NORMAL_GROWTH_RATIO=15;
public static int PLANTS_TOTAL_UNITS=0;
public static int CHANCES_OF_RAIN=70;
public static int PLANTS_ENHANCED_GROWTH_RATIO=25;
public static int PLANTS_ONRAIN_GROWTH_RATIO=60;
public static int MAX_RAINING_TIME=10;
// species variables
public static int SPECIE1_MAX_AGE=15;
public static int SPECIE2_MAX_AGE=15;
public static int SPECIE1_TOTAL_UNITS=0;
public static int SPECIE2_TOTAL_UNITS=0;
public static int SPECIE1_LAST_DEADS=0;
public static int SPECIE2_LAST_DEADS=0;
public static int SPECIE1_CHANCES_TO_BORN=90;
public static int SPECIE2_CHANCES_TO_BORN=90;
public static int SPECIE1_ENERGY_NEEDED=7;
public static int SPECIE2_ENERGY_NEEDED=7;
public static int SPECIE1_X_START=10;
public static int SPECIE1_Y_START=10;
public static int SPECIE2_X_START=90;
public static int SPECIE2_Y_START=90;
public static int SPECIE1_MINIMUM_ENERGY_TO_REPRODUCE=7;
public static int SPECIE1_MINIMUM_AGE_TO_REPRODUCE=5;
public static int SPECIE2_MINIMUM_ENERGY_TO_REPRODUCE=7;
public static int SPECIE2_MINIMUM_AGE_TO_REPRODUCE=5;
//arraylist per les dos llistes d'animals
public static ArrayList<Animal> specie1List = new ArrayList<Animal>();
public static ArrayList<Animal> specie2List = new ArrayList<Animal>();
public static ArrayList<Animal> deadAnimals = new ArrayList<Animal>();

// global variables
public static int SCREEN_W; // screen width
public static int SCREEN_H=500; // screen height -not used-
public static boolean PLAY=false; // app begins stopped
public static boolean FIRST_LOOP=true; // is the first loop of the main while?
public static float X_SCALE=1.f;
public static float Y_SCALE=1.f;
public static float X_SCALE_CENTER=0;
public static float Y_SCALE_CENTER=0;
public static float MASTER_SCALE=1.f;
public static boolean ISTABLET=false;
public static int LOOPS=0;
public static boolean IS_MAGNIFIED=false;
public static int PROCESSINGLIMIT=5000;
public static GrassMatrix matriu_herba=new GrassMatrix();
private static int is_raining=0;
private static int raining_time=0;
private static int current_rain_x=0;
private static int current_rain_y=0;


// graph
public static int[] plantsgraphvalues = new int[100];
public static int[] specie1graphvalues = new int[100];
public static int[] specie2graphvalues = new int[100];


public static void evolve() {
	if (Engine.PLAY==true) { // if simulation is running
    Engine.LOOPS++;
    if (Engine.FIRST_LOOP==true) {
    	// create first animals of each race
		specie1List.add(new Animal(Engine.SPECIE1_X_START,Engine.SPECIE1_Y_START,Engine.SPECIE1_MAX_AGE, true));
		specie2List.add(new Animal(Engine.SPECIE2_X_START,Engine.SPECIE2_Y_START,Engine.SPECIE2_MAX_AGE,true));
		Engine.FIRST_LOOP=false;
    }
    Engine.PLANTS_TOTAL_UNITS=0;
    Random randomGenerator = new Random();
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
			// dibuixem les herbes vives segons els diferents nivells d'energia
			if (matriu_herba.getAge(x,y)!=0) {
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
		// activem el plaguecontrol (per evitar la superpoblacio i el bloqueig del telefon)
		if ((SPECIE1_TOTAL_UNITS+SPECIE2_TOTAL_UNITS)>PROCESSINGLIMIT) {
			item.setplaguecontrol(true);
		} else {
			item.setplaguecontrol(false);
		}
		if (matriu_herba.getEnergy(item.getX(),item.getY())>Engine.SPECIE1_ENERGY_NEEDED) { // si la planta te energia suficient
			if (item.getEnergy()<=10) { // si te gana
					item.feed(); // s'alimenta
					matriu_herba.setEnergy(item.getX(),item.getY(),Engine.SPECIE1_ENERGY_NEEDED); // restem energia a la planta
			}
		}
		if (item.ready_to_reproduce(Engine.SPECIE1_MINIMUM_AGE_TO_REPRODUCE,Engine.SPECIE1_MINIMUM_ENERGY_TO_REPRODUCE)) { // si esta preparat per reproduirse
			item.reproduce(); // es reprodueix
			int chances_to_born = randomGenerator.nextInt(100); //  range 0..99
			if (chances_to_born<Engine.SPECIE1_CHANCES_TO_BORN) {
				specie1List.add(new Animal(item.getX(),item.getY(),Engine.SPECIE1_MAX_AGE,false));
			}
			item.grow(); // creix
		} else {
			item.move(); // es mou
			item.grow(); // creix
		}
		if (item.is_dead()) {
			deadAnimals.add(item); // el posem a la llista d'animals morts
		}
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
		// activem el plaguecontrol (per evitar la superpoblacio i el bloqueig del telefon)
		if ((SPECIE1_TOTAL_UNITS+SPECIE2_TOTAL_UNITS)>PROCESSINGLIMIT) {
			item.setplaguecontrol(true);
		} else {
			item.setplaguecontrol(false);
		}
		if (matriu_herba.getEnergy(item.getX(),item.getY())>Engine.SPECIE2_ENERGY_NEEDED) { // si la planta te energia
			if (item.getEnergy()<=10) { // si te gana
					item.feed(); // s'alimenta
					matriu_herba.setEnergy(item.getX(),item.getY(),Engine.SPECIE2_ENERGY_NEEDED); // restem energia a la planta
			}
		}
		if (item.ready_to_reproduce(Engine.SPECIE2_MINIMUM_AGE_TO_REPRODUCE,Engine.SPECIE2_MINIMUM_ENERGY_TO_REPRODUCE)) { // si esta preparat per reproduirse
			item.reproduce(); // es reprodueix
			int chances_to_born = randomGenerator.nextInt(100); //  range 0..99
			if (chances_to_born<Engine.SPECIE2_CHANCES_TO_BORN) {
				specie2List.add(new Animal(item.getX(),item.getY(),Engine.SPECIE2_MAX_AGE,false));
			}
			item.grow(); // creix
		} else {
			item.move(); // es mou
			item.grow(); // creix
		}
		if (item.is_dead()) {
			deadAnimals.add(item); // el posem a la llista d'animals morts
		}
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

public static void buildgraph() {
	// move all values to the left
	for (int x=0;x<99;x++) {
		plantsgraphvalues[x]=plantsgraphvalues[x+1];
		specie1graphvalues[x]=specie1graphvalues[x+1];
		specie2graphvalues[x]=specie2graphvalues[x+1];
	}
	// put new values on the last position
	plantsgraphvalues[98]=(int)(Engine.PLANTS_TOTAL_UNITS/100);
	specie1graphvalues[98]=(int)((float)((float)Engine.SPECIE1_TOTAL_UNITS/(float)(Engine.SPECIE1_TOTAL_UNITS+Engine.SPECIE2_TOTAL_UNITS))*100);
	specie2graphvalues[98]=(int)((float)((float)Engine.SPECIE2_TOTAL_UNITS/(float)(Engine.SPECIE1_TOTAL_UNITS+Engine.SPECIE2_TOTAL_UNITS))*100);
}

}
