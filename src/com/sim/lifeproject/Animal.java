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

public class Animal {
	private int x;
	private int y;
	private int age;
	private int energy;
	private int max_age;
	private boolean plaguecontrol=false;
	private boolean is_dead;
	private boolean is_inmortal;
	public Animal(int x, int y, int max_age,boolean inmortal) { // animal born
		this.x=x;
		this.y=y;
		this.age=0;
		this.energy=10;
		this.max_age=max_age;
		this.is_inmortal=inmortal;
		this.is_dead=false;
	}
	public void feed() {
		this.energy=this.energy+5;
	}
	public void grow() {
		int limit=this.max_age/3;
		int plaguemodifier;
		Random randomGenerator = new Random();
		if (this.is_inmortal==false) {
			if (this.plaguecontrol==true) { plaguemodifier=30; } else { plaguemodifier=0; }
			int death_chance = randomGenerator.nextInt(100); //  range 0..99
			if (this.age<limit) {
				if (death_chance>15+plaguemodifier) {
					this.age++;
					this.energy--;
				} else {
					this.is_dead=true;
					this.energy=0;
				}
			}
			if (this.age>=limit && this.age<limit*2) {
				if (death_chance>5+plaguemodifier) {
					this.age++;
					this.energy--;
				} else {
					this.is_dead=true;
					this.energy=0;
				}
			}
			if (this.age>=limit*2) {
				if (death_chance>40+plaguemodifier) {
					this.age++;
					this.energy--;
				} else {
					this.is_dead=true;
					this.energy=0;
				}
			}
			if (this.energy<=0) { this.is_dead=true; }
		} else {
			this.age++;
		}
	}
	public void move() {
		Random randomGenerator = new Random();
		int nextplace = randomGenerator.nextInt(4); //  range 0..3
		if (nextplace==0 && this.x>0) { this.x--; }
		if (nextplace==1 && this.y>0) { this.y--; }
		if (nextplace==2 && this.x<99) { this.x++; }
		if (nextplace==3 && this.y<99) { this.y++; }
	}
	public boolean ready_to_reproduce(int agelimit, int energylimit) {
		if (this.age>agelimit && this.energy>energylimit) return true; else return false;
	}
	public void reproduce() {
		this.energy=this.energy-5;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getAge() {
		return this.age;
	}
	public int getEnergy() {
		return this.energy;
	}
	public void setX(int value) {
		this.x=value;
	}
	public void setY(int value) {
		this.y=value;
	}
	public void setAge(int value) {
		this.age=value;
	}
	public void setEnergy(int value) {
		this.energy=value;
	}
	public boolean is_dead() {
		return this.is_dead;
	}
	public void setplaguecontrol(boolean value) {
		this.plaguecontrol=value;
	}
	public boolean getplaguecontrol() {
		return this.plaguecontrol;
	}
	
}
