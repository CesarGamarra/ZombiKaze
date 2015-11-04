package com.svalero.elementos;

import java.util.Random;

import com.svalero.zombikaze.MainActivity;
import com.svalero.zombikaze.R;
import com.svalero.zombikaze.Surface;
import com.svalero.zombikaze.R.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;

public class Bala {
	private int x;
	private int y;
	
	private int velocidad=Surface.alto/10;
	
	private Bitmap bitmapBala;
	int tamaño;
	
	public Bala(Resources res,int x){
		bitmapBala=BitmapFactory.decodeResource(res, R.drawable.bala);
		
		tamaño=Surface.ancho/10;
		
		this.x=x;
		this.y=Surface.alto-tamaño;
	}
	
	public void doDraw(Canvas canvas) {
		Rect sourceRectBala = new Rect(0, 0, bitmapBala.getWidth(), bitmapBala.getHeight());
		Rect destRectBala = new Rect(x, y, x+tamaño, y+tamaño);
		
		canvas.drawBitmap(bitmapBala, sourceRectBala, destRectBala,null);
	}
	
	public void animar(long tiempoTranscurrido){
		int avance=(int)(velocidad*(tiempoTranscurrido/20f));
		if(avance==0){
			avance=velocidad;
		}
		y-=avance;
		comprobarBordes();
		
		
		comprobarColision();
		
	}
	private void comprobarBordes(){
		if(y+tamaño<=0){
			Surface.balas.remove(this);
		}
	}
	private void comprobarColision(){
		Zombie zombie;
		boolean zombieEliminado=false;
		for(int i=Surface.zombies.size()-1;i>=0 && zombieEliminado==false;i--){
			zombie=Surface.zombies.get(i);
			if(x>zombie.getX() && x<zombie.getX()+zombie.getTamaño() 
					&& y<zombie.getY()+zombie.getTamaño() && y>zombie.getY()){
				if(zombie.getVida()>0){
					Surface.balas.remove(this);
					zombie.quitarVida();
					Jugador.aumentarPuntuacion(zombie.getPuntosMuerte());
					zombieEliminado=true;
					handle.post(proceso);
				}
			}
		}
	}
	final Handler handle=new Handler();
	
	final Runnable proceso=new Runnable(){
		public void run(){
			MainActivity.getInstance().explosion();
		}
	};
}
