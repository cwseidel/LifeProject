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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;



public class Graph extends View {
	

	private int tile_size;
	Paint color_terra = new Paint();
	Paint color_planta = new Paint();
	Paint color_specie1 = new Paint();
	Paint color_specie2 = new Paint();
	Paint axis= new Paint();
	int h_position_offset;
	int v_position_offset=30;
	Bitmap fons_start;


	public Graph(Context context) {
		super(context);
		// set tile colors
		color_terra.setColor(Color.rgb(136, 96, 17));
		color_planta.setColor(Color.rgb(113, 255, 113));
		color_specie1.setColor(Color.rgb(255, 0, 0));
		color_specie2.setColor(Color.rgb(0, 0, 255));
		axis.setColor(Color.rgb(0, 0, 0));
		color_planta.setAlpha(200);
		color_specie1.setAlpha(200);
		color_specie2.setAlpha(200);
		color_planta.setStrokeWidth(5);
		color_specie1.setStrokeWidth(5);
		color_specie2.setStrokeWidth(5);
		// end of set tile colors
		
		
		
			
	}
	
	@Override
    public void onDraw(Canvas canvas) {
		// This method shows a snapshot of the simulation matrix
		showGraph(canvas);
		
	}
	private void showGraph(Canvas canvas) {
		// This method shows a snapshot of the simulation matrix
        //define master scale 
		int valorplanta,valorespecie1,valorespecie2,valorplantaanterior=100,valorespecie1anterior=100,valorespecie2anterior=100;
        if (Engine.SCREEN_W>300) {
        	Engine.MASTER_SCALE=.8f;
        }
        if (Engine.SCREEN_W>440) {
        	Engine.MASTER_SCALE=1.f;
        }
        canvas.scale(Engine.X_SCALE*Engine.MASTER_SCALE,Engine.Y_SCALE*Engine.MASTER_SCALE,Engine.X_SCALE_CENTER,Engine.Y_SCALE_CENTER);
        // control position of the simulation matrix
     // control position of the simulation matrix
        if (Engine.SCREEN_W>440) { tile_size=4; h_position_offset=(Engine.SCREEN_W-440)/2;} // set tile size
        if (Engine.SCREEN_W<=440) { tile_size=3; h_position_offset=(Engine.SCREEN_W-272)/2;}
        if (Engine.SCREEN_W>540) { tile_size=5; h_position_offset=(Engine.SCREEN_W-540)/2;} 
        // draw axis
		canvas.drawLine(h_position_offset+4, v_position_offset-4,h_position_offset+4, v_position_offset+(tile_size*99)-4,axis);
		canvas.drawLine(h_position_offset+4, v_position_offset+(tile_size*99)-4,h_position_offset+(tile_size*100)+4, v_position_offset+(tile_size*99)-4,axis);
		// draw graph
		valorplantaanterior=100-Engine.plantsgraphvalues[0];
		valorespecie1anterior=100-Engine.specie1graphvalues[0];
		valorespecie2anterior=100-Engine.specie2graphvalues[0];
		for (int x=0;x<99;x++) {
			valorplanta=100-Engine.plantsgraphvalues[x];
			valorespecie1=100-Engine.specie1graphvalues[x];
			valorespecie2=100-Engine.specie2graphvalues[x];
			canvas.drawLine(h_position_offset+((x+2)*tile_size), v_position_offset+(valorplanta*tile_size)-2*tile_size,h_position_offset+((x+1)*tile_size), v_position_offset+(valorplantaanterior*tile_size)-2*tile_size, color_planta);
			canvas.drawLine(h_position_offset+((x+2)*tile_size), v_position_offset+(valorespecie1*tile_size)-2*tile_size, h_position_offset+((x+1)*tile_size), v_position_offset+(valorespecie1anterior*tile_size)-2*tile_size, color_specie1);
			canvas.drawLine(h_position_offset+((x+2)*tile_size), v_position_offset+(valorespecie2*tile_size)-2*tile_size, h_position_offset+((x+1)*tile_size), v_position_offset+(valorespecie2anterior*tile_size)-2*tile_size, color_specie2);
			valorplantaanterior=valorplanta;
			valorespecie1anterior=valorespecie1;
			valorespecie2anterior=valorespecie2;
		}
		
	}

}
