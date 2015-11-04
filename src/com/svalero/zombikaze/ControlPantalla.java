package com.svalero.zombikaze;

import android.R.bool;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class ControlPantalla extends Thread{
	
	private Surface surface;
	private SurfaceHolder sHolder;
	private boolean funcionando=false;

	public ControlPantalla(Surface surface) {
		this.surface = surface;
		sHolder=surface.getHolder();
	}
	
	public void setRunning(boolean run){
		funcionando=run;
	}

	@Override
	public void run() {
		Canvas canvas=null;
		long tiempoInicio=System.currentTimeMillis();
		long tiempoTranscurrido=tiempoInicio;
		
		while(funcionando){
			canvas=sHolder.lockCanvas();
			
			if(canvas!=null){
				tiempoTranscurrido=System.currentTimeMillis()-tiempoInicio;
				surface.animar(tiempoTranscurrido);
				surface.doDraw(tiempoTranscurrido,canvas);
				tiempoInicio=System.currentTimeMillis();
			}
			sHolder.unlockCanvasAndPost(canvas);
		}
	}
}
