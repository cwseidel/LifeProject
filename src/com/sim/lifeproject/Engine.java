/*
    Copyright (C) 2012  Ferran F�bregas

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
/*Variables that will be used in the game*/
public static int CNORMAL=40; // cnormal es l'index de creixement normal (sobre 100) = 4000;
public static int NUM_OF_GRASS=0;
public static int PROB_RAIN=70; // probability of rain
public static int CPROXIM=8; // cproxim es l'index de creixement proxim (sobre 100)
public static int CPLUJA=50;
public static int MAX_RAINING_TIME=5;
// animal variables
public static int RACE1_MAX_AGE=20;
public static int RACE2_MAX_AGE=20;
public static int NUM_OF_RACE1=0;
public static int NUM_OF_RACE2=0;
public static int DEAD_RACE1=0;
public static int DEAD_RACE2=0;
public static int C_BORN_RACE1=99;
public static int C_BORN_RACE2=99;
public static int EFICIENCY_RACE1=10;
public static int EFICIENCY_RACE2=15;
public static int X_RACE1=10;
public static int Y_RACE1=10;
public static int X_RACE2=90;
public static int Y_RACE2=90;

// global variables
public static int SCREEN_W; // screen width
public static int SCREEN_H; // screen height -not used-
public static boolean PLAY=false; // app begins stopped
public static boolean FIRST_LOOP=true; // is the first loop of the main while?

}
