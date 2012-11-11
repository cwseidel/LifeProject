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

import com.sim.lifeproject.R;

import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;



public class Pantallaprincipal extends View {
	private Bitmap terra; 
	private Bitmap planta1;
	private Bitmap planta2;
	private Bitmap planta3;
	private Bitmap planta4;
	private Bitmap planta5;
	private Bitmap pluja;
	private int is_raining=0;
	private int raining_time=0;
	private int current_rain_x=0;
	private int current_rain_y=0;
	private GrassMatrix matriu_herba=new GrassMatrix();
	private Random randomGenerator;


	public Pantallaprincipal(Context context) {
		super(context);
		terra = BitmapFactory.decodeResource(getResources(), R.drawable.terra);
		planta1 = BitmapFactory.decodeResource(getResources(), R.drawable.planta1);
		planta2 = BitmapFactory.decodeResource(getResources(), R.drawable.planta2);
		planta3 = BitmapFactory.decodeResource(getResources(), R.drawable.planta3);
		planta4 = BitmapFactory.decodeResource(getResources(), R.drawable.planta4);
		planta5 = BitmapFactory.decodeResource(getResources(), R.drawable.planta5);
		pluja = BitmapFactory.decodeResource(getResources(), R.drawable.pluja);
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
				canvas.drawBitmap(terra, position_offset+(x*5), y*5, null);
				// dibuixem les herbes vives segons els diferents nivells d'energia
				if (matriu_herba.getAge(x,y)==1) {
					canvas.drawBitmap(planta1, position_offset+(x*5), y*5, null);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==2) {
					canvas.drawBitmap(planta2, position_offset+(x*5), y*5, null);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==3) {
					canvas.drawBitmap(planta3, position_offset+(x*5), y*5, null);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==4) {
					canvas.drawBitmap(planta4, position_offset+(x*5), y*5, null);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getAge(x,y)==5) {
					canvas.drawBitmap(planta5, position_offset+(x*5), y*5, null);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}
				if (matriu_herba.getRain(x,y)==1) {
					canvas.drawBitmap(pluja, position_offset+(x*5), y*5, null);
					Engine.NUM_OF_GRASS=Engine.NUM_OF_GRASS+1;
				}

			}
		}
		//invalidate();
	}

}
