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

public class Grass {
	private int x;
	private int y;
	private int age;
	private int energy;
	private int near;
	private int rain;
	public Grass(int x, int y) {
		this.x=x;
		this.y=y;
		this.age=0;
		this.near=0;
		this.rain=0;
		this.energy=0;
	}
	public void update_energy() {
		this.energy=this.energy+10;
	}
	public void grow() {
		Random randomGenerator = new Random();
		int death_chance = randomGenerator.nextInt(100); //  range 0..99
		if (this.age==5) {
			if (death_chance>59) {
				this.update_energy();
			} else {
				this.age=0;
				this.energy=0;
			}
		}
		if (this.age==4) {
			if (death_chance>29) {
				this.age=this.age+1;
				this.update_energy();
			} else {
				this.age=0;
				this.energy=0;
			}
		}
		if (this.age==3) {
			if (death_chance>14) {
				this.age=this.age+1;
				this.update_energy();
			} else {
				this.age=0;
				this.energy=0;
			}
		}
		if (this.age==2) {
			if (death_chance>9) {
				this.age=this.age+1;
				this.update_energy();
			} else {
				this.age=0;
				this.energy=0;
			}
		}
		if (this.age==1) {
			if (death_chance>24) {
				this.age=this.age+1;
				this.update_energy();
			} else {
				this.age=0;
				this.energy=0;
			}
		}
	}
	public void born() {
		if (this.age==0) { // if there is no grass
			this.age=1;
			update_energy();
			this.near=1;
			}
	}
	public int getAge() {
		return this.age;
	}
	public void setNear(int near) {
		this.near=near;
	}
	public void setRain(int rain) {
		this.rain=rain;
	}
	public void setEnergy(int energy) {
		this.energy=this.energy-energy;
		if (this.energy<=0) {
			this.age=0; // die
			this.energy=0;
		}
	}
	public int getNear() {
		return this.near;
	}
	public int getRain() {
		return this.rain;
	}
	public int getEnergy() {
		return this.energy;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}