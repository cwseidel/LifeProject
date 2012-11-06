package com.sim.lifeproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*display the splash screen image*/
		setContentView(R.layout.splash);
		/*start up the splash screen and main menu in a time delayed thread*/
		new Handler().postDelayed(new Thread() {
			@Override
			public void run() {
				Intent sim= new Intent(SplashScreen.this,LifeProjectActivity.class);
				SplashScreen.this.startActivity(sim);
				SplashScreen.this.finish();
			}
			}, 4000);
}
}
