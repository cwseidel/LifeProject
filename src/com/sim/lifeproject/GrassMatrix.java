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

public class GrassMatrix {
	private Grass g_matrix[][]= new Grass[100][100];
	
	public GrassMatrix() {
		for (int ix=0;ix<100;ix++) {
			for (int iy=0;iy<100;iy++) {
					this.g_matrix[ix][iy] = new Grass(ix,iy);
			}
		}
	}
	public void grow(int x,int y) {
		g_matrix[x][y].grow();
		if (g_matrix[x][y].getAge()==0) {
			update_proximity(x,y);
		}
		
	}
	public void update_proximity(int x,int y) {
		int has_near=0;
		if (x>0 && y>0) { if (g_matrix[x-1][y-1].getNear()==1) { has_near=1; } }
		if (x>0) { if(g_matrix[x-1][y].getNear()==1) { has_near=1; } }
		if (x>0 && y<99) { if(g_matrix[x-1][y+1].getNear()==1) { has_near=1; } }
		if (x<99 && y>0) { if(g_matrix[x+1][y-1].getNear()==1) { has_near=1; } }
		if (x<99) { if(g_matrix[x+1][y].getNear()==1) { has_near=1; } }
		if (x<99 && y<99) { if(g_matrix[x+1][y+1].getNear()==1) { has_near=1; } }
		if (y>0) { if(g_matrix[x][y-1].getNear()==1) { has_near=1; } }
		if (y<99) { if(g_matrix[x][y+1].getNear()==1) { has_near=1; } }
		// after scanning around
		if (has_near==0) { g_matrix[x][y].setNear(0); } // set near to 0 if no near grass
	}
	public void born(int x,int y) {
		if (g_matrix[x][y].getAge()==0) { // if no grass here
			g_matrix[x][y].born();
			if (x>0 && y>0) { g_matrix[x-1][y-1].setNear(1); }
			if (x>0) { g_matrix[x-1][y].setNear(1); }
			if (x>0 && y<99) { g_matrix[x-1][y+1].setNear(1); }
			if (x<99 && y>0) { g_matrix[x+1][y-1].setNear(1); }
			if (x<99) { g_matrix[x+1][y].setNear(1); }
			if (x<99 && y<99) { g_matrix[x+1][y+1].setNear(1); }
			if (y>0) { g_matrix[x][y-1].setNear(1);}
			if (y<99) { g_matrix[x][y+1].setNear(1); }
		}
	}
	public void rain_on(int x,int y) {
		for (int ix=x-5;ix<x+6;ix++) {
			for (int iy=y-5;iy<y+6;iy++) {
				if (iy>=0 && ix>=0 && iy<100 && ix<100) { 
					g_matrix[ix][iy].setRain(1);
				}
			}
		}
	}
	public void rain_off(int x,int y) {
		for (int ix=x-5;ix<x+6;ix++) {
			for (int iy=y-5;iy<y+6;iy++) {
				if (iy>=0 && ix>=0 && iy<100 && ix<100) { 
					g_matrix[ix][iy].setRain(0);
				}
			}
		}
	}
	public int getAge(int x,int y) {
		return g_matrix[x][y].getAge();
	}
	public int getNear(int x,int y) {
		return g_matrix[x][y].getNear();
	}
	public int getRain(int x,int y) {
		return g_matrix[x][y].getRain();
	}
}
