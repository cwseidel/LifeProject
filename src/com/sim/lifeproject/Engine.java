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

public class Engine {
// plants variables
public static int PLANTS_NORMAL_GROWTH_RATIO=40;
public static int PLANTS_TOTAL_UNITS=0;
public static int CHANCES_OF_RAIN=70;
public static int PLANTS_ENHANCED_GROWTH_RATIO=60;
public static int PLANTS_ONRAIN_GROWTH_RATIO=80;
public static int MAX_RAINING_TIME=5;
// species variables
public static int SPECIE1_MAX_AGE=20;
public static int SPECIE2_MAX_AGE=20;
public static int SPECIE1_TOTAL_UNITS=0;
public static int SPECIE2_TOTAL_UNITS=0;
public static int SPECIE1_LAST_DEADS=0;
public static int SPECIE2_LAST_DEADS=0;
public static int SPECIE1_CHANCES_TO_BORN=99;
public static int SPECIE2_CHANCES_TO_BORN=99;
public static int SPECIE1_ENERGY_NEEDED=10;
public static int SPECIE2_ENERGY_NEEDED=15;
public static int SPECIE1_X_START=10;
public static int SPECIE1_Y_START=10;
public static int SPECIE2_X_START=90;
public static int SPECIE2_Y_START=90;

// global variables
public static int SCREEN_W; // screen width
public static int SCREEN_H; // screen height -not used-
public static boolean PLAY=false; // app begins stopped
public static boolean FIRST_LOOP=true; // is the first loop of the main while?
public static float X_SCALE=1.f;
public static float Y_SCALE=1.f;
public static float X_SCALE_CENTER=0;
public static float Y_SCALE_CENTER=0;

}
