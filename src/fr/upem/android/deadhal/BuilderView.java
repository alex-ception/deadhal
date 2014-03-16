package fr.upem.android.deadhal;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Switch;
import fr.upem.android.deadhal.maze.LinkedRoom;
import fr.upem.android.deadhal.maze.Maze;
import fr.upem.android.deadhal.maze.Room;
import fr.upem.android.deadhal.utils.MazeDrawer;
import fr.upem.android.deadhal.utils.Rooms;
import fr.upem.android.deadhal.utils.RotateGestureDetector;

/**
 * Controller handling logic for the level builder view
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
@SuppressLint({ "WrongCall", "ViewConstructor" })
public class BuilderView extends SurfaceView implements SurfaceHolder.Callback
{
	private static final int INVALID_POINTER_ID = -1;
	SurfaceHolder mSurfaceHolder;
	DrawingThread mThread;
	Maze maze;
	float beginSpan;
	float beginSpanX;
	float beginSpanY;
	float endSpan;
	float endSpanX;
	float endSpanY;

	Switch RotateSwitch;

	float beginRotate;
	float endRotate;

	Room selectedRoom;

	private float mLastTouchX;
	private float mLastTouchY;
	private int mActivePointerId = INVALID_POINTER_ID;

	private ScaleGestureDetector mScaleDetector;
	private RotateGestureDetector mRotateDetector;

	/**
	 * Class constructor
	 * @param context The BuilderActivity instance
	 */
	public BuilderView(BuilderActivity context)
	{
		super(context);

		mSurfaceHolder    = getHolder();
		mThread           = new DrawingThread();
		mScaleDetector    = new ScaleGestureDetector(context, new ScaleListener());
		mRotateDetector   = new RotateGestureDetector(context, new RotateListener());
		selectedRoom      = null;
		RotateSwitch      = (Switch) context.findViewById(R.id.RotateSwitch);
		this.maze         = context.getMaze();

		mSurfaceHolder.addCallback(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		mScaleDetector.onTouchEvent(event);
		mRotateDetector.onTouchEvent(event);
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
			selectedRoom = getSelectedRoom(mLastTouchX, mLastTouchY);
			// Only move if the ScaleGestureDetector isn't processing a gesture.
			if (!mScaleDetector.isInProgress()
					&& !mRotateDetector.isInProgress()) {

				final float dx = x - mLastTouchX;
				final float dy = y - mLastTouchY;
				if (selectedRoom != null) {
					selectedRoom.setX((int) (selectedRoom.getX() + dx));
					selectedRoom.setY((int) (selectedRoom.getY() + dy));
				} else {
					for (Room r : maze.getRooms().values()) {

						r.setX((int) (r.getX() + dx));
						r.setY((int) (r.getY() + dy));
					}
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
	 * Return the selected room based on coordinates that have been touched
	 * 
	 * @param mLastTouchXLoc the x position of the tap
	 * @param mLastTouchYLoc the y position of the tap
	 * 
	 * @return The corresponding Room
	 */
	private Room getSelectedRoom(float mLastTouchXLoc, float mLastTouchYLoc) {
		ArrayList<Room> temp = new ArrayList<Room>();
		for (Room r : maze.getRooms().values()) {
			if ((r.getXLeft() <= mLastTouchXLoc && mLastTouchXLoc <= r
					.getXRight())
					&& (r.getYTop() <= mLastTouchYLoc && mLastTouchYLoc <= r
							.getYBottom())) {
				temp.add(r);
			}
		}
		if (temp.size()>0){
			if(temp.size()>1){
				return selectedRoom;
			}
			return temp.get(0);
		}
		return null;
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
	 * Rotation listener
	 * 
	 * @author Alexandre ANDRE
	 * @author Dylan BANCE
	 * @author Remy BARBOSA
	 * @author Houmam WEHBEH
	 */
	private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener
	{
	    /**
	     * {@inheritDoc}
	     */
		@Override
		public boolean onRotate(RotateGestureDetector detector)
		{
			if (RotateSwitch.isChecked()) {
				endRotate = detector.getRotationDegreesDelta();
				if (selectedRoom != null) {
					selectedRoom.setRotation((float) (selectedRoom
							.getRotation() + (beginRotate - endRotate)));
				}
			}

			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean onRotateBegin(RotateGestureDetector detector)
		{
			beginRotate = detector.getRotationDegreesDelta();

			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onRotateEnd(RotateGestureDetector detector)
		{
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
		@SuppressLint("NewApi")
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector)
		{
			beginSpan    = (int) detector.getCurrentSpan();
			beginSpanX   = (int) detector.getCurrentSpanX();
			beginSpanY   = (int) detector.getCurrentSpanY();

			return true;
		}

        /**
         * {@inheritDoc}
         */
		@Override
		public boolean onScale(ScaleGestureDetector detector)
		{
			endSpan  = detector.getCurrentSpan();
			endSpanX = detector.getCurrentSpanX();
			endSpanY = detector.getCurrentSpanY();

			if (selectedRoom != null) {
				if (!RotateSwitch.isChecked()) {
					float XPercent = ((endSpanX * 100) / beginSpanX) / 100;
					float YPercent = ((endSpanY * 100) / beginSpanY) / 100;
					selectedRoom
							.setHeight((int) (selectedRoom.getHeight() * YPercent));
					selectedRoom
							.setWidth((int) (selectedRoom.getWidth() * XPercent));
				}
			} else {
				for (Room r : maze.getRooms().values()) {
					float percentScale = ((endSpan * 100) / beginSpan) / 100;
					if (!RotateSwitch.isChecked()) {

						r.setHeight((int) (r.getHeight() * percentScale));
						r.setWidth((int) (r.getWidth() * percentScale));
						r.setX((int) (r.getX() * percentScale));
						r.setY((int) (r.getY() * percentScale));
					}
				}
			}
			beginSpan = endSpan;
			beginSpanX = endSpanX ;
			beginSpanY = endSpanY ;
			return true;
		}

        /**
         * {@inheritDoc}
         */
		@SuppressLint("NewApi")
		@Override
		public void onScaleEnd(ScaleGestureDetector detector)
		{
			
		}
	}
}
