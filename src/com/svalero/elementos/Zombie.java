package com.svalero.elementos;

import java.util.ArrayList;
import java.util.Random;

import com.svalero.zombikaze.R;
import com.svalero.zombikaze.Surface;
import com.svalero.zombikaze.R.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Zombie {
	private int x;
	private int y;
	
	private int velocidad=Surface.alto/250;
	private int vida=1;
	private int puntosMuerte=5;
	private long momentoMuerte;
	
	private ArrayList<Bitmap> bitmapZombie=new ArrayList<Bitmap>();
	private Bitmap imagenMostrar;
	
	int tamaño;
	
	public Zombie(Resources res){
		bitmapZombie.add(BitmapFactory.decodeResource(res, R.drawable.pierna_derecha));
		bitmapZombie.add(BitmapFactory.decodeResource(res, R.drawable.pierna_izquierda));
		bitmapZombie.add(BitmapFactory.decodeResource(res, R.drawable.explosion));
		imagenMostrar=bitmapZombie.get(0);
		tiempoImagen=System.currentTimeMillis();
		
		Random rnd=new Random();
		tamaño=Surface.ancho/6;
		int aleatorio=rnd.nextInt(5)+1;
		x=aleatorio*(tamaño+(Surface.ancho-tamaño*5)/5);
		
		this.x=x-tamaño;
		this.y=y-bitmapZombie.get(0).getHeight();
	}
	
	
	public void doDraw(Canvas canvas) {
		Rect sourceRectZombie = new Rect(0, 0, bitmapZombie.get(0).getWidth(), bitmapZombie.get(0).getHeight());
		Rect destRectZombie = new Rect(x, y, x+tamaño, y+tamaño);
		
		canvas.drawBitmap(imagenMostrar, sourceRectZombie, destRectZombie,null);
	}
	long tiempoImagen;
	public void animar(long tiempoTranscurrido){
		if(vida>0){
			int avance=(int)(velocidad*(tiempoTranscurrido/20f));
			if(avance==0){
				avance=Surface.alto/200;
			}
			if(imagenMostrar==bitmapZombie.get(0) && System.currentTimeMillis()-tiempoImagen>100){
				imagenMostrar=bitmapZombie.get(1);
				tiempoImagen=System.currentTimeMillis();
			}else if(imagenMostrar==bitmapZombie.get(1) && System.currentTimeMillis()-tiempoImagen>100){
				imagenMostrar=bitmapZombie.get(0);
				tiempoImagen=System.currentTimeMillis();
			}
			y+=avance;
			comprobarBordes();
		}else{			
			if(System.currentTimeMillis()-momentoMuerte>1500){
				Surface.zombies.remove(this);
			}
		}
		
	}
	
	private void comprobarBordes(){
		if(y+tamaño>=Surface.alto){
			Jugador.setVidasRestantes(Jugador.getVidasRestantes()-1);
			Surface.zombies.remove(this);
		}
	}
	
	public void quitarVida(){
		vida--;
		if(vida==0){
			imagenMostrar=bitmapZombie.get(2);
			momentoMuerte=System.currentTimeMillis();
		}
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getTamaño() {
		return tamaño;
	}

	public void setTamaño(int tamaño) {
		this.tamaño = tamaño;
	}

	public int getPuntosMuerte() {
		return puntosMuerte;
	}

	public void setPuntosMuerte(int puntosMuerte) {
		this.puntosMuerte = puntosMuerte;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	
}
