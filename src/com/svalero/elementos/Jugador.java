package com.svalero.elementos;

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

public class Jugador {
	private int x;
	private int y;
	
	private int posicion;
	
	private static int vidasRestantes=3;
	private static int puntuacion=0;
	
	private Bitmap bitmapJugador;
	public int tamaño;
	
	public Jugador(Resources res){
		bitmapJugador=BitmapFactory.decodeResource(res, R.drawable.pjprincipal);
		vidasRestantes=3;
		puntuacion=0;
		
		tamaño=Surface.ancho/6;
		posicion=2;
		x=posicion*(tamaño+(Surface.ancho-tamaño*5)/5);
		
		this.x=x;
		this.y=Surface.alto-tamaño;
	}
	
	public void doDraw(Canvas canvas) {
		Rect sourceRectJugador = new Rect(0, 0, bitmapJugador.getWidth(), bitmapJugador.getHeight());
		Rect destRectJugador = new Rect(x, y, x+tamaño, y+tamaño);
		
		canvas.drawBitmap(bitmapJugador, sourceRectJugador, destRectJugador,null);
	}
	public void moverDerecha(){
		if(posicion<4){
			posicion++;
			x=posicion*(tamaño+(Surface.ancho-tamaño*5)/5);
		}
	}
	public void moverIzquierda(){
		if(posicion>0){
			posicion--;
			x=posicion*(tamaño+(Surface.ancho-tamaño*5)/5);
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

	public static int getVidasRestantes() {
		return vidasRestantes;
	}

	public static void setVidasRestantes(int vidasRestantes) {
		Jugador.vidasRestantes = vidasRestantes;
	}

	public static int getPuntuacion() {
		return puntuacion;
	}

	public static void aumentarPuntuacion(int puntuacion) {
		Jugador.puntuacion+= puntuacion;
	}
}
