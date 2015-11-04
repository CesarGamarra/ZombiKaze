package com.svalero.zombikaze;

import java.util.Random;

import com.svalero.elementos.Zombie;

import android.R.bool;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

public class GeneracionZombies extends Thread{
	
	private Resources res;
	private boolean funcionando=false;

	public GeneracionZombies(Resources res){
		this.res=res;
	}
	
	public void setRunning(boolean run){
		funcionando=run;
	}
	
	@Override
	public void run() {
		long tiempoInicio=System.currentTimeMillis();
		int cant=1000;
		int cantRest=100;
		while(funcionando){
			Zombie zombie=new Zombie(res);
			Surface.añadirZombie(zombie);
			
			Random rnd=new Random();
			
			if(System.currentTimeMillis()-tiempoInicio>10000){
				tiempoInicio=System.currentTimeMillis();
				cant-=cantRest;
				if(cant==500){
					cantRest=50;
				}else if(cant==200){
					cantRest=25;
				}else if(cant==100){
					cantRest=0;
				}
			}
			
			int esperar=(rnd.nextInt(8)+1)*cant;
			
			try {
				Thread.sleep(esperar);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
