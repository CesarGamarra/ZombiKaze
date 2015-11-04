package com.svalero.elementos;

import com.svalero.zombikaze.R;
import com.svalero.zombikaze.Surface;
import com.svalero.zombikaze.R.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Niebla {
	private int x;
	private int y;
	private int velocidad=-1;
	
	private Bitmap bitmapNiebla;
	
	public Niebla(Resources res){
		bitmapNiebla=BitmapFactory.decodeResource(res, R.drawable.niebla);
		
		x=0;
		y=0;
	}
	
	public void doDraw(Canvas canvas) {
		Rect sourceRectNiebla = new Rect(0, 0, bitmapNiebla.getWidth(), bitmapNiebla.getHeight());
		Rect destRectNiebla = new Rect(x, y, x+canvas.getWidth()*4, y+canvas.getHeight()*2);
		
		canvas.drawBitmap(bitmapNiebla, sourceRectNiebla, destRectNiebla,null);
	}
	
	public void animar(long tiempoTranscurrido){
		//x-=velocidad*(tiempoTranscurrido/20f);
		x+=velocidad;
		//Log.e("tiempo", tiempoTranscurrido+"");
		comprobarBordes();
	}
	
	private void comprobarBordes(){
		if(x<=-Surface.ancho){
			velocidad=-velocidad;
		}else if(x>=0){
			velocidad=-velocidad;
		}
	}
}
