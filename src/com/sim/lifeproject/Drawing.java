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

import java.util.Random;

public class Drawing {
	int is_raining=0;
	int raining_time=0;
	int max_rainining_time=5;
	int current_rain_x=0;
	int current_rain_y=0;
	int prob_rain=70; // probability of rain
	int cnormal=2; // cnormal es l'index de creixement normal (sobre 100)
	int cproxim=8; // cproxim es l'index de creixement proxim (sobre 100)
	int cpluja=50; // cpluja es l'index de creixement en zones de pluja (sobre 100)
	int num_of_grass=0;
	GrassMatrix matriu_herba=new GrassMatrix();
	public void draw() {
		num_of_grass=0;
		Random randomGenerator = new Random();
		int random_x = randomGenerator.nextInt(100); //  range 0..99
		int random_y = randomGenerator.nextInt(100); //  range 0..99
		int rain_chance = randomGenerator.nextInt(100); //  range 0..99
		if (is_raining==1) { 
			raining_time=raining_time+1;
			if (raining_time>max_rainining_time) { // si ha de parar de ploure
				matriu_herba.rain_off(current_rain_x,current_rain_y);
				is_raining=0;
				raining_time=0;
			}
		}
		if (rain_chance>100-prob_rain && is_raining==0) { // si no plou i es produeix la possiblitat
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
				if (chance>100-cnormal) {
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-cproxim && matriu_herba.getNear(x,y)==1) { // en cas que estigui proxima a una altre herba
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-cpluja && matriu_herba.getRain(x,y)==1) {
					matriu_herba.born(x,y);
				}
				// dibuixem les herbes vives segons els diferents nivells d'energia
				if (matriu_herba.getAge(x,y)==1) {
					num_of_grass=num_of_grass+1;
				}
				if (matriu_herba.getAge(x,y)==2) {
					num_of_grass=num_of_grass+1;
				}
				if (matriu_herba.getAge(x,y)==3) {
					num_of_grass=num_of_grass+1;
				}
				if (matriu_herba.getAge(x,y)==4) {
					num_of_grass=num_of_grass+1;
				}
				if (matriu_herba.getAge(x,y)==5) {
					num_of_grass=num_of_grass+1;
				}
				if (matriu_herba.getRain(x,y)==1) {
					num_of_grass=num_of_grass+1;
				}

			}
		}
		System.out.println(num_of_grass/100);
	}

}
