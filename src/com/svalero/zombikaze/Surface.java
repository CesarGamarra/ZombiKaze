package com.svalero.zombikaze;

import java.util.ArrayList;
import java.util.List;

import com.svalero.elementos.Bala;
import com.svalero.elementos.Jugador;
import com.svalero.elementos.Niebla;
import com.svalero.elementos.Zombie;
import com.svalero.zombikaze.R;

import android.R.bool;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Surface extends SurfaceView implements SurfaceHolder.Callback{
	
	private ControlPantalla hiloPantalla;
	public static int alto;
	public static int ancho;
	private boolean pantallaInicio=true;
	
	Niebla niebla=new Niebla(getResources());
	Jugador jugador=null;
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public static ArrayList<Bala> balas = new ArrayList<Bala>();
	
	GeneracionZombies generacion=new GeneracionZombies(getResources());
	private Bitmap bitmapFondo = BitmapFactory.decodeResource(getResources(), R.drawable.map);
	private Bitmap bitmapPrincipal = BitmapFactory.decodeResource(getResources(), R.drawable.inicial);
	
	Paint paint=new Paint();
	
	public static void añadirZombie(Zombie zombie){
		zombies.add(zombie);
	}
	
	public Surface(Context context) {
		super(context);
		
		getHolder().addCallback(this);
		hiloPantalla = new ControlPantalla(this);
	}
	
	public void surfaceChanged(SurfaceHolder holder, int formato, int ancho, int alto) {
		this.ancho=ancho;
		this.alto=alto;
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if(!hiloPantalla.isAlive()){
			hiloPantalla = new ControlPantalla(this);
			hiloPantalla.setRunning(true);
			hiloPantalla.start();
		}
		
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d("Finalizar", "Surface esta siendo destruido");
		
		generacion.setRunning(false);
		hiloPantalla.setRunning(false);
		MainActivity.getInstance().mediaPlayer.stop();
		Log.d("Finalizar.", "Thread cerrado correctamente");
	}
	
	private void terminarPartida(){
		boolean retry = true;
		generacion.setRunning(false);
		while (retry) {
			try {
				generacion=new GeneracionZombies(getResources());
				zombies = new ArrayList<Zombie>();
				balas = new ArrayList<Bala>();
				retry = false;
			} catch (Exception e) {
				Log.e("Error", "Error hilos");
			}
		}
		Log.d("Finalizar.", "Thread cerrado correctamente");
	}
	
	public void animar(long tiempoTranscurrido){
		niebla.animar(tiempoTranscurrido);
		for(int i=0;i<zombies.size();i++){
			zombies.get(i).animar(tiempoTranscurrido);
		}
		for(int i=0;i<balas.size();i++){
			balas.get(i).animar(tiempoTranscurrido);
		}
	}
	
	public void doDraw(long tiempoTranscurrido,Canvas canvas) {

		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		
		if(pantallaInicio){
			Rect sourceRectPrincipal = new Rect(0, 0, bitmapPrincipal.getWidth(), bitmapPrincipal.getHeight());
			Rect destRectPrincipal = new Rect(0, 0, ancho, alto);
			
			canvas.drawBitmap(bitmapPrincipal, sourceRectPrincipal, destRectPrincipal, null);
			
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(20);
			canvas.drawText("Toca la pantalla para empezar a jugar ", Surface.ancho/2, Surface.alto/2, paint);
		}else{
			if(Jugador.getVidasRestantes()>0){
				Rect sourceRectFondo = new Rect(0, 0, bitmapFondo.getWidth(), bitmapFondo.getHeight());
				Rect destRectFondo = new Rect(0, 0, ancho, alto);
				
				canvas.drawBitmap(bitmapFondo, sourceRectFondo, destRectFondo, null);
				
				for(int i=zombies.size()-1;i>=0;i--){
					zombies.get(i).doDraw(canvas);
				}
				for(int i=balas.size()-1;i>=0;i--){
					balas.get(i).doDraw(canvas);
				}
				jugador.doDraw(canvas);
				niebla.doDraw(canvas);
				
				//paint.setTextAlign(Align.LEFT);
				//canvas.drawText("FPS: " + Math.round(1000f / tiempoTranscurrido), 10, 50, paint);
				
				paint.setTextAlign(Align.LEFT);
				canvas.drawText("PUNTUACION: " + Jugador.getPuntuacion(), 0, 50, paint);
				paint.setTextAlign(Align.RIGHT);
				canvas.drawText("VIDAS: " + Jugador.getVidasRestantes(), getWidth(), 50, paint);
			
			}else{
				canvas.drawColor(Color.BLACK);
				paint.setTextAlign(Align.CENTER);
				paint.setTextSize(80);
				canvas.drawText("GAME OVER ", Surface.ancho/2, Surface.alto/2, paint);
				paint.setTextSize(40);
				canvas.drawText("TU PUNTUACION: " + Jugador.getPuntuacion(), Surface.ancho/2, Surface.alto/2+100, paint);
				terminarPartida();
				if(partidaPerdida==0)
					partidaPerdida=System.currentTimeMillis();
			}
		}
	}
	long partidaPerdida;
	final Handler handle=new Handler();
	
	final Runnable proceso=new Runnable(){
		public void run(){
			MainActivity.getInstance().temaInicio();
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(pantallaInicio){
			pantallaInicio=false;
			if(jugador==null)
				jugador=new Jugador(getResources());
			if(!generacion.isAlive()){
				generacion.setRunning(true);
				generacion.start();
			}
			MainActivity.getInstance().mediaPlayer.stop();
			MainActivity.getInstance().mediaPlayer.release();
		}else{
			if(Jugador.getVidasRestantes()<=0 && System.currentTimeMillis()-partidaPerdida>1500){
				pantallaInicio=true;
				jugador=new Jugador(getResources());
				handle.post(proceso);
				partidaPerdida=0;
			}else if(Jugador.getVidasRestantes()>0){
				if((int)event.getY()>Surface.alto-jugador.tamaño){
					if((int) event.getX()>jugador.getX()+jugador.tamaño){
						jugador.moverDerecha();
					}else if((int) event.getX()<jugador.getX()){
						jugador.moverIzquierda();
					}
				}else{
					Bala bala=new Bala(getResources(), jugador.getX()+jugador.tamaño/2);
					balas.add(bala);
					handle.post(disparo);
				}
			}
		}
		return super.onTouchEvent(event);
	}
	
	final Runnable disparo=new Runnable(){
		public void run(){
			MainActivity.getInstance().misil();
		}
	};
}
