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
	private boolean is_dead;
	public Animal(int x, int y, int max_age) { // animal born
		this.x=x;
		this.y=y;
		this.age=0;
		this.energy=10;
		this.max_age=max_age;
		this.is_dead=false;
	}
	public void feed() {
		this.energy=this.energy+5;
	}
	public void grow() {
		this.age++;
		this.energy--;
		if (this.energy<=0 || this.age>this.max_age) { this.is_dead=true; }
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
	
}
