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

import java.util.Random;



public class Pantallaprincipal extends View {
	private int is_raining=0;
	private int raining_time=0;
	private int current_rain_x=0;
	private int current_rain_y=0;
	private GrassMatrix matriu_herba=new GrassMatrix();
	private Random randomGenerator;
	Paint color_terra = new Paint();
	Paint color_planta1 = new Paint();
	Paint color_planta2 = new Paint();
	Paint color_planta3 = new Paint();
	Paint color_planta4 = new Paint();
	Paint color_planta5 = new Paint();
	Paint color_rain = new Paint();


	public Pantallaprincipal(Context context) {
		super(context);
		randomGenerator = new Random();
		
	}
	@Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        int position_offset=(Engine.SCREEN_W-500)/2;
        if (position_offset<0) { position_offset=0; }
        Engine.NUM_OF_GRASS=0;
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
		if (rain_chance>100-Engine.PROB_RAIN && is_raining==0) { // si no plou i es produeix la possiblitat
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
		color_rain.setColor(Color.rgb(0, 128, 255));
		color_rain.setAlpha(100);
		// update an draw the matrix
		for (int x=0;x<100;x++) {
			for (int y=0;y<100;y++) {
				matriu_herba.grow(x, y);
				int chance = randomGenerator.nextInt(100); //  range 0..99
				if (chance>100-Engine.CNORMAL) {
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-Engine.CPROXIM && matriu_herba.getNear(x,y)==1) { // en cas que estigui proxima a una altre herba
					matriu_herba.born(x,y);	// mira si aquesta casella es pot convertir en una nova herba
				}
				if (chance>100-Engine.CPLUJA && matriu_herba.getRain(x,y)==1) {
					matriu_herba.born(x,y);
				}
				// dibuixem el fons
				canvas.drawRect(position_offset+(x*5), y*5, position_offset+(x*5)+5, (y*5)+5, color_terra);
				// dibuixem les herbes vives segons els diferents nivells d'energia
				if (matriu_herba.getAge(x,y)==1) {
					canvas.drawRect(position_offset+(x*5), y*5, position_offset+(x*5)+5, (y*5)+5, color_planta1);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==2) {
					canvas.drawRect(position_offset+(x*5), y*5, position_offset+(x*5)+5, (y*5)+5, color_planta2);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==3) {
					canvas.drawRect(position_offset+(x*5), y*5, position_offset+(x*5)+5, (y*5)+5, color_planta3);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==4) {
					canvas.drawRect(position_offset+(x*5), y*5, position_offset+(x*5)+5, (y*5)+5, color_planta4);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==5) {
					canvas.drawRect(position_offset+(x*5), y*5, position_offset+(x*5)+5, (y*5)+5, color_planta5);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getRain(x,y)==1) {
					canvas.drawRect(position_offset+(x*5), y*5, position_offset+(x*5)+5, (y*5)+5, color_rain);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}

			}
		}
		//invalidate();
	}

}
