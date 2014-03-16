package fr.upem.android.deadhal.utils;

import android.graphics.Point;

/**
 * A helper for rendering rooms
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class Rooms
{
    /**
     * Returns the computed point after rotation
     * 
     * @param point The point to compute
     * @param center The center of the rotation
     * @param rotation The angle of the rotation
     * 
     * @return The point computed after rotation
     */
	public static Point getnewRotatedPoint(Point point, Point center, float rotation)
	{
        double rot = Math.toRadians(rotation);
        
        double newX = center.x + (point.x-center.x)*Math.cos(rot) - (point.y-center.y)*Math.sin(rot);

        double newY = center.y + (point.x-center.x)*Math.sin(rot) + (point.y-center.y)*Math.cos(rot);

        return new Point((int)newX,(int) newY);
	}
}
