package fr.upem.android.deadhal.utils;

import android.content.Context;
import android.view.MotionEvent;

/**
 * A class handling generic gesture
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public abstract class BaseGestureDetector
{
    protected static final float PRESSURE_THRESHOLD = 0.67f;

    protected final Context mContext;
    protected boolean mGestureInProgress;

    protected MotionEvent mPrevEvent;
    protected MotionEvent mCurrEvent;
    
    protected float mCurrPressure;
    protected float mPrevPressure;
    protected long mTimeDelta;

    /**
     * Class constructor
     * 
     * @param context The context of the application
     */
    public BaseGestureDetector(Context context)
    {
    	mContext = context;
    }

	/**
	 * On touch listener
	 * Starts or update the event
	 * 
	 * @param event The motion event
	 * @return true
	 */
    public boolean onTouchEvent(MotionEvent event)
    {
    	final int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
    	if (!mGestureInProgress)
    		handleStartProgressEvent(actionCode, event);
    	else
    		handleInProgressEvent(actionCode, event);

    	return true;
    }
    
    /**
     * Method called when starting a gesture
	 * 
	 * @param actionCode The action code
	 * @param event The event
	 */
    protected abstract void handleStartProgressEvent(int actionCode, MotionEvent event);
    
	/**
	 * Method called when updating a gesture
	 * 
	 * @param actionCode the action code
	 * @param event The event
	 */
    protected abstract void handleInProgressEvent(int actionCode, MotionEvent event);

    /**
     * Update the event
     * 
     * @param curr The event
     */
    protected void updateStateByEvent(MotionEvent curr)
    {
    	final MotionEvent prev = mPrevEvent;
    	
    	// Reset mCurrEvent
        if (mCurrEvent != null) {
            mCurrEvent.recycle();
            mCurrEvent = null;
        }
        mCurrEvent = MotionEvent.obtain(curr);

        // Delta time
        mTimeDelta = curr.getEventTime() - prev.getEventTime();

        // Pressure
        mCurrPressure = curr.getPressure(curr.getActionIndex());
        mPrevPressure = prev.getPressure(prev.getActionIndex());
    }

    /**
     * Reset events
     */
    protected void resetState()
    {
        if (mPrevEvent != null) {
            mPrevEvent.recycle();
            mPrevEvent = null;
        }

        if (mCurrEvent != null) {
            mCurrEvent.recycle();
            mCurrEvent = null;
        }

        mGestureInProgress = false;
    }


    /**
     * @return true if a gesture is currently in progress, false otherwise
     */
    public boolean isInProgress()
    {
        return mGestureInProgress;
    }

	/**
	 * Return the time difference in milliseconds between the previous accepted GestureDetector event and the current GestureDetector event
	 * 
	 * @return Time difference
	 */
	public long getTimeDelta() {
		return mTimeDelta;
	}

	/**
	 * Return the event time of the current GestureDetector event being processed
	 * 
	 * @return Current GestureDetector event time
	 */
	public long getEventTime() {
		return mCurrEvent.getEventTime();
	}
   
}
