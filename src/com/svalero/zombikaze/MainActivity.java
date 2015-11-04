package com.svalero.zombikaze;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {
	
	public MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		principal=this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        //		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mediaPlayer = MediaPlayer.create(this, R.raw.temaintro);
        mediaPlayer.start();
        setContentView(new Surface(this));
	}
	
	public void temaInicio(){
		mediaPlayer = MediaPlayer.create(this, R.raw.temaintro);
		mediaPlayer.start();
	}
	public void explosion(){
		mediaPlayer = MediaPlayer.create(this, R.raw.explosion1);
		mediaPlayer.start();
	}
	public void misil(){
		mediaPlayer = MediaPlayer.create(this, R.raw.misil);
		mediaPlayer.start();
	}
	
	private static MainActivity principal;
	public static MainActivity getInstance(){
		return principal;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
