package fr.upem.android.deadhal.utils;

import android.content.Context;
import android.view.MotionEvent;

/**
 * A class handling rotating gesture
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class RotateGestureDetector extends TwoFingerGestureDetector
{
    /**
     * An interface representing the contract to follow for the listener
     */
	public interface OnRotateGestureListener
	{
		public boolean onRotate(RotateGestureDetector detector);
		public boolean onRotateBegin(RotateGestureDetector detector);
		public void onRotateEnd(RotateGestureDetector detector);
	}

	/**
     * A class handling rotation
     * 
     * @author Alexandre ANDRE
     * @author Dylan BANCE
     * @author Remy BARBOSA
     * @author Houmam WEHBEH
	 */
	public static class SimpleOnRotateGestureListener implements OnRotateGestureListener
	{
	    @Override
	    public boolean onRotate(RotateGestureDetector detector)
	    {
	        return false;
	    }

        @Override
	    public boolean onRotateBegin(RotateGestureDetector detector)
        {
	        return true;
	    }

	    public void onRotateEnd(RotateGestureDetector detector)
	    {
	    }
	}

    private final OnRotateGestureListener mListener;
    private boolean mSloppyGesture;

    /**
     * Class constructor
     * 
     * @param context The application context
     * @param listener The listener
     */
    public RotateGestureDetector(Context context, OnRotateGestureListener listener)
    {
    	super(context);
        mListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleStartProgressEvent(int actionCode, MotionEvent event)
    {
        switch (actionCode) {
            case MotionEvent.ACTION_POINTER_DOWN:
                resetState();
                mPrevEvent = MotionEvent.obtain(event);
                mTimeDelta = 0;
                updateStateByEvent(event);
                mSloppyGesture = isSloppyGesture(event);
                if(!mSloppyGesture)
                    mGestureInProgress = mListener.onRotateBegin(this);
            	break;
            
            case MotionEvent.ACTION_MOVE:
                if (!mSloppyGesture)
                	break;
                mSloppyGesture = isSloppyGesture(event);
                if(!mSloppyGesture)
                    mGestureInProgress = mListener.onRotateBegin(this);
                break;
                
            case MotionEvent.ACTION_POINTER_UP:
                if (!mSloppyGesture)
                	break;
                break; 
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleInProgressEvent(int actionCode, MotionEvent event)
    {
        switch (actionCode) {
            case MotionEvent.ACTION_POINTER_UP:
                updateStateByEvent(event);
                if (!mSloppyGesture)
                    mListener.onRotateEnd(this);
                resetState();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mSloppyGesture)
                    mListener.onRotateEnd(this);
                resetState();
                break;
            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);
                if (mCurrPressure / mPrevPressure > PRESSURE_THRESHOLD) {
                    final boolean updatePrevious = mListener.onRotate(this);
                    if (updatePrevious) {
                        mPrevEvent.recycle();
                        mPrevEvent = MotionEvent.obtain(event);
                    }
                }
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void resetState()
    {
        super.resetState();
        mSloppyGesture = false;
    }

    /**
     * Return the rotation difference from the previous rotate event to the current event 
     * 
     * @return The current rotation difference in degrees.
     */
	public float getRotationDegreesDelta()
	{
		double diffRadians = Math.atan2(mPrevFingerDiffY, mPrevFingerDiffX) - Math.atan2(mCurrFingerDiffY, mCurrFingerDiffX);

		return (float) (diffRadians * 180 / Math.PI);
	}
}
