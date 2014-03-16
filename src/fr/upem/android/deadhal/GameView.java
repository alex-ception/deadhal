package fr.upem.android.deadhal;

import java.util.ArrayList;

import fr.upem.android.deadhal.maze.LinkedRoom;
import fr.upem.android.deadhal.maze.Maze;
import fr.upem.android.deadhal.maze.Room;
import fr.upem.android.deadhal.utils.MazeDrawer;
import fr.upem.android.deadhal.utils.Rooms;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Controller handling logic for the game view
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
@SuppressLint({ "WrongCall", "ViewConstructor" })
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	private static final int INVALID_POINTER_ID = -1;
	private Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.yoshi);
	int bmWidth;
	int bmHeight;
	SurfaceHolder mSurfaceHolder;
	DrawingThread mThread;
	Maze maze;
	float beginSpan;
	float beginSpanX;
	float beginSpanY;
	float endSpan;
	float endSpanX;
	float endSpanY;
	Room selectedRoom;
	private float mLastTouchX;
	private float mLastTouchY;
	private int mActivePointerId = INVALID_POINTER_ID;
	private ScaleGestureDetector mScaleDetector;

	/**
	 * Class constructor 
	 * @param gameActivity The GameActivity instance
	 */
	@SuppressLint("NewApi")
	public GameView(GameActivity gameActivity)
	{
		super(gameActivity);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mThread = new DrawingThread();
		mScaleDetector = new ScaleGestureDetector(gameActivity,
				new ScaleListener());

		this.maze = gameActivity.getMaze();
		bmWidth=75;
		bmHeight=75;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		mScaleDetector.onTouchEvent(event);
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			final float x = event.getX();
			final float y = event.getY();
			mLastTouchX = x;
			mLastTouchY = y;
			mActivePointerId = event.getPointerId(0);
			selectedRoom = getSelectedRoom(mLastTouchX, mLastTouchY);
			moveTo(selectedRoom);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			final int pointerIndex = event.findPointerIndex(mActivePointerId);
			final float x = event.getX(pointerIndex);
			final float y = event.getY(pointerIndex);
			selectedRoom = getSelectedRoom(mLastTouchX, mLastTouchY);
			// Only move if the ScaleGestureDetector isn't processing a gesture.
			if (!mScaleDetector.isInProgress()) {

				final float dx = x - mLastTouchX;
				final float dy = y - mLastTouchY;

				for (Room r : maze.getRooms().values()) {

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
				mLastTouchX = event.getX(newPointerIndex);
				mLastTouchY = event.getY(newPointerIndex);
				mActivePointerId = event.getPointerId(newPointerIndex);
			}
			break;
		}

		}
		return true;
	}

	/**
	 * Move to a new room
	 * 
	 * @param selectedRoom2 The selected room
	 */
	private void moveTo(Room selectedRoom2)
	{
		for (Room r : maze.getRooms().values()) {
			if (r.isOccuped()) {
				if (wayIsGood(r, selectedRoom2)) {
					r.setOccuped(false);
					selectedRoom.setOccuped(true);
					return;
				}
				else{
					return;
				}
			}
		}
		selectedRoom.setOccuped(true);
		
	}

	/**
	 * Defines if you can go to new room
	 * 
	 * @param r The current room
	 * @param selectedRoom2 The room to go to
	 * @return A boolean representing if you can go or not in the selected room
	 */
	private boolean wayIsGood(Room r, Room selectedRoom2)
	{
		for (LinkedRoom lr : r.getOutputs().getEast()) {
			if (lr.getRoom().equals(selectedRoom2)) {
				return true;
			}
		}
		for (LinkedRoom lr :  r.getOutputs().getWest()) {
			if (lr.getRoom().equals(selectedRoom2)) {
				return true;
			}
		}
		for (LinkedRoom lr :  r.getOutputs().getNorth()) {
			if (lr.getRoom().equals(selectedRoom2)) {
				return true;
			}
		}
		for (LinkedRoom lr :  r.getOutputs().getSouth()) {
			if (lr.getRoom().equals(selectedRoom2)) {
				return true;
			}
		}
		return false;
	}

    /**
     * Return the selected room based on coordinates that have been touched
     * 
     * @param mLastTouchXLoc the x position of the tap
     * @param mLastTouchYLoc the y position of the tap
     * 
     * @return The corresponding Room
     */
	private Room getSelectedRoom(float mLastTouchXLoc, float mLastTouchYLoc)
	{
		ArrayList<Room> temp = new ArrayList<Room>();
		for (Room r : maze.getRooms().values()) {
			if ((r.getXLeft() <= mLastTouchXLoc && mLastTouchXLoc <= r
					.getXRight())
					&& (r.getYTop() <= mLastTouchYLoc && mLastTouchYLoc <= r
							.getYBottom())) {
				temp.add(r);
			}
		}
		if (temp.size() > 0)
			return temp.get(0);
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.WHITE);
		// Dessiner le fond de l'�cran en premier
		for (Room r : maze.getRooms().values()) {
			MazeDrawer.drawRoom(r, canvas);
			if(r.isOccuped()){
				Bitmap bitmap = Bitmap.createScaledBitmap(bm, bmWidth, bmHeight, false);
				MazeDrawer.drawCharacter(bitmap,r,canvas);
			}
		}

		for (Room r : maze.getRooms().values()) {
			for (LinkedRoom input : r.getInputs().getEast()) {
				Point RotatedPointRoom = Rooms.getnewRotatedPoint(
						(new Point(r.getXRight(), r.getY())),
						new Point(r.getX(), r.getY()), r.getRotation());
				MazeDrawer.drawInputs(r, canvas, input, RotatedPointRoom);

			}
			for (LinkedRoom input : r.getInputs().getNorth()) {
				Point RotatedPointRoom = Rooms.getnewRotatedPoint(
						(new Point(r.getX(), r.getYTop())), new Point(r.getX(),
								r.getY()), r.getRotation());
				MazeDrawer.drawInputs(r, canvas, input, RotatedPointRoom);
			}
			for (LinkedRoom input : r.getInputs().getSouth()) {
				Point RotatedPointRoom = Rooms.getnewRotatedPoint(
						(new Point(r.getX(), r.getYBottom())),
						new Point(r.getX(), r.getY()), r.getRotation());
				MazeDrawer.drawInputs(r, canvas, input, RotatedPointRoom);
			}
			for (LinkedRoom input : r.getInputs().getWest()) {
				Point RotatedPointRoom = Rooms.getnewRotatedPoint(
						(new Point(r.getXLeft(), r.getY())), new Point(
								r.getX(), r.getY()), r.getRotation());
				MazeDrawer.drawInputs(r, canvas, input, RotatedPointRoom);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight)
	{
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void surfaceCreated(SurfaceHolder pHolder)
	{
		mThread.keepDrawing = true;
		mThread.start();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void surfaceDestroyed(SurfaceHolder pHolder)
	{
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

    /**
     * Drawing thread
     * 
     * @author Alexandre ANDRE
     * @author Dylan BANCE
     * @author Remy BARBOSA
     * @author Houmam WEHBEH
     */
	private class DrawingThread extends Thread
	{
		boolean keepDrawing = true;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run()
		{
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

    /**
     * Scale listener
     * 
     * @author Alexandre ANDRE
     * @author Dylan BANCE
     * @author Remy BARBOSA
     * @author Houmam WEHBEH
     */
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
	{
	    /**
	     * {@inheritDoc}
	     */
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector)
		{
			beginSpan = (int) detector.getCurrentSpan();
			beginSpanX = (int) detector.getCurrentSpanX();
			beginSpanY = (int) detector.getCurrentSpanY();
			return true;
		}

        /**
         * {@inheritDoc}
         */
		@Override
		public boolean onScale(ScaleGestureDetector detector)
		{
			return true;
		}

        /**
         * {@inheritDoc}
         */
		@SuppressLint("NewApi")
		@Override
		public void onScaleEnd(ScaleGestureDetector detector)
		{
			endSpan = detector.getCurrentSpan();
			for (Room r : maze.getRooms().values()) {
				float percentScale = ((endSpan * 100) / beginSpan) / 100;

				r.setHeight((int) (r.getHeight() * percentScale));
				r.setWidth((int) (r.getWidth() * percentScale));
				r.setNameFontSize(r.getNameFontSize() * (percentScale));
				if (r.getInterest() != null) {
					r.getInterest().setFontSize(
							r.getInterest().getFontSize() * (percentScale));
				}
				r.setX((int) (r.getX() * percentScale));
				r.setY((int) (r.getY() * percentScale));
			}
		}
	}
}
