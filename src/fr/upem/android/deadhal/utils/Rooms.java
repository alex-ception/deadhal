package fr.upem.android.deadhal.utils;

import android.graphics.Point;

public class Rooms {
	public static Point getnewRotatedPoint(Point point,Point center,float rotation) {

        double rot = Math.toRadians(rotation);
        
        double newX = center.x + (point.x-center.x)*Math.cos(rot) - (point.y-center.y)*Math.sin(rot);

        double newY = center.y + (point.x-center.x)*Math.sin(rot) + (point.y-center.y)*Math.cos(rot);


        return new Point((int)newX,(int) newY);
	}
}
