package fr.upem.android.deadhal;

import java.util.ArrayList;
import java.util.List;

import fr.upem.android.deadhal.maze.Room;

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
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.widget.TextView;
import android.widget.Toast;

public class BuilderView extends SurfaceView implements SurfaceHolder.Callback {
	private static final int INVALID_POINTER_ID = -1;
	SurfaceHolder mSurfaceHolder;
	DrawingThread mThread;
	ArrayList<Room> rooms;
	int beginSpan;
	int endSpan;

	private float mLastTouchX;
	private float mLastTouchY;
	private int mActivePointerId = INVALID_POINTER_ID;

	private ScaleGestureDetector mScaleDetector;

	public BuilderView(Context context) {
		super(context);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mThread = new DrawingThread();

		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		rooms = new ArrayList<Room>();
		Room r1 = new Room("room 1 ", "room 1 ");
		Room r2 = new Room("room 2 ", "room 2 ");
		r1.setX(50).setY(50).setWidth(200).setHeight(500).setRotation(10);
		r2.setX(200).setY(200).setWidth(500).setHeight(200);
		rooms.add(r1);
		rooms.add(r2);

	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mScaleDetector.onTouchEvent(event);
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			final float x = event.getX();
			final float y = event.getY();
			mLastTouchX = x;
			mLastTouchY = y;
			mActivePointerId = event.getPointerId(0);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			final int pointerIndex = event.findPointerIndex(mActivePointerId);
			final float x = event.getX(pointerIndex);
			final float y = event.getY(pointerIndex);

			// Only move if the ScaleGestureDetector isn't processing a gesture.
			if (!mScaleDetector.isInProgress()) {

				final float dx = x - mLastTouchX;
				final float dy = y - mLastTouchY;
				for (Room r : rooms) {
					r.setX((int) (r.getX() + dx));
					r.setY((int) (r.getY() + dy));
				}

				invalidate();
			}

			mLastTouchX = x;
			mLastTouchY = y;

			break;
		}
		case MotionEvent.ACTION_UP: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		case MotionEvent.ACTION_CANCEL: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		case MotionEvent.ACTION_POINTER_UP: {

			final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			final int pointerId = event.getPointerId(pointerIndex);
			if (pointerId == mActivePointerId) {
				// This was our active pointer going up. Choose a new
				// active pointer and adjust accordingly.
				final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
				mLastTouchX = event.getX(newPointerIndex) ;
				mLastTouchY = event.getY(newPointerIndex) ;
				mActivePointerId = event.getPointerId(newPointerIndex);
			}
			break;
		}

		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
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

		for (Room r : rooms) {
			int xleft = r.getXLeft();
			int ytop = r.getYTop();
			int xright = r.getXRight();
			int ybottom = r.getYBottom();
			Rect rect = new Rect(xleft, ytop, xright, ybottom);
			canvas.save();
			int centerx = r.getX();
			int centery = r.getY();
			canvas.rotate(r.getRotation(), centerx, centery);
			canvas.drawRect(rect, paintRoom);
			paintRoomName.setTextSize(30);
			paintRoomName.setTextAlign(Align.CENTER);
			canvas.drawText(r.getName(), centerx, centery, paintRoomName);
			paintRoomInterest.setTextSize(25);
			paintRoomName.setTextAlign(Align.LEFT);
			canvas.drawText(r.getName(), xleft, ytop + 25, paintRoomInterest);

			canvas.restore();
		}
		/*
		 * Bitmap myicon = BitmapFactory.decodeResource(getResources(),
		 * R.drawable.pinguin); Bitmap bitmap =
		 * Bitmap.createScaledBitmap(myicon, 300, 300, false);
		 * canvas.drawColor(Color.BLACK); canvas.drawBitmap(bitmap, 0, 0,
		 * paintRoom);
		 * 
		 * Rect r = new Rect(xleft, ytop, xright, ybottom); canvas.save(); int
		 * centerx = (xleft + xright) / 2; int centery = (ytop + ybottom) / 2;
		 * canvas.rotate(45, centerx, centery); canvas.drawRect(r, paintRoom);
		 * paintRoomName.setTextSize(30);
		 * paintRoomName.setTextAlign(Align.CENTER);
		 * canvas.drawText("ROOOOOM !", centerx, centery, paintRoomName);
		 * paintRoomInterest.setTextSize(25);
		 * paintRoomName.setTextAlign(Align.LEFT);
		 * canvas.drawText("Interest MAN !", xleft, ytop + 25,
		 * paintRoomInterest);
		 * 
		 * canvas.restore();
		 */
	}

	@Override
	public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth,
			int pHeight) {
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
			} catch (InterruptedException e) {
			}
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
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			beginSpan = (int) detector.getCurrentSpan();
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {

			return true;
		}

		@SuppressLint("NewApi")
		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			endSpan = (int) detector.getCurrentSpan();
			for (Room r : rooms) {
				r.setHeight((int) (r.getHeight()
						* ((endSpan * 100) / beginSpan) / 100));
				r.setWidth((int) (r.getWidth() * ((endSpan * 100) / beginSpan) / 100));
			}

		}

	}

}
