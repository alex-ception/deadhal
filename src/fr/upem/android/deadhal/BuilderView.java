package fr.upem.android.deadhal;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class BuilderView extends SurfaceView implements SurfaceHolder.Callback {

	SurfaceHolder mSurfaceHolder;
	DrawingThread mThread;

	public BuilderView(Context pContext) {
		super(pContext);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mThread = new DrawingThread();

		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Dessiner le fond de l'�cran en premier
		Paint paintRoom = new Paint();
		paintRoom.setStyle(Paint.Style.FILL); 
		paintRoom.setColor(Color.GREEN);
		
		Paint paintRoomName = new Paint();
		paintRoomName.setStyle(Paint.Style.FILL); 
		paintRoomName.setColor(Color.GRAY);
		
		Paint paintRoomInterest = new Paint();
		paintRoomInterest.setStyle(Paint.Style.FILL); 
		paintRoomInterest.setColor(Color.RED);
		
		Bitmap myicon=BitmapFactory.decodeResource(getResources(),R.drawable.pinguin);
		Bitmap bitmap = Bitmap.createScaledBitmap(myicon, 300, 300, false);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bitmap, 0,0, paintRoom);
        int xleft = 250;
        int ytop = 50;
        int xright = 500;
        int ybottom = 500;
        Rect r = new Rect(xleft, ytop, xright, ybottom);
        canvas.save();
        int centerx = (xleft+xright)/2;
		int centery = (ytop+ybottom)/2;
		canvas.rotate(45, centerx, centery);
        canvas.drawRect(r, paintRoom);
		paintRoomName.setTextSize(30);
		paintRoomName.setTextAlign(Align.CENTER);
		canvas.drawText("ROOOOOM !", centerx, centery, paintRoomName);
		paintRoomInterest.setTextSize(25);
		paintRoomName.setTextAlign(Align.LEFT);
		canvas.drawText("Interest MAN !", xleft, ytop+25, paintRoomInterest);
        
        canvas.restore();
	}

	@Override
	public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight) {
		//
	}

	@Override
	public void surfaceCreated(SurfaceHolder pHolder) {
		mThread.keepDrawing = true;
		mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder pHolder) {
		mThread.keepDrawing = false;
		boolean retry = true;
		while (retry) {
			try {
				mThread.join();
				retry = false;
			} catch (InterruptedException e) {}
		}
		
	}

	private class DrawingThread extends Thread {
		boolean keepDrawing = true;

		@Override
		public void run() {
			Canvas canvas;
			while (keepDrawing) {
				canvas = null;

				try {
					canvas = mSurfaceHolder.lockCanvas();
					synchronized (mSurfaceHolder) {
						onDraw(canvas);
					}
				} finally {
					if (canvas != null)
						mSurfaceHolder.unlockCanvasAndPost(canvas);
				}

				// Pour dessiner � 50 fps
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {}
			}
		}
	}

}
