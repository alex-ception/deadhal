package fr.upem.android.deadhal.utils;

import fr.upem.android.deadhal.maze.LinkedRoom;
import fr.upem.android.deadhal.maze.Room;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Align;

/**
 * A class helper to render a maze
 * 
 * @author Alexandre ANDRE
 * @author Dylan BANCE
 * @author Remy BARBOSA
 * @author Houmam WEHBEH
 */
public class MazeDrawer
{
    /**
     * Draw a room
     * 
     * @param r The room to draw
     * @param canvas The canvas to render on
     */
	public static void drawRoom(Room r, Canvas canvas)
	{
		Paint paintRoom = new Paint();
		paintRoom.setStyle(Paint.Style.FILL);
		paintRoom.setColor(Color.rgb(145, 190, 242));

		Paint paintRoomName = new Paint();
		paintRoomName.setStyle(Paint.Style.FILL);
		paintRoomName.setColor(Color.BLACK);

		Paint paintRoomInterest = new Paint();
		paintRoomInterest.setStyle(Paint.Style.FILL);
		paintRoomInterest.setColor(Color.YELLOW);

		int xleft = r.getXLeft();
		int ytop = r.getYTop();
		int xright = r.getXRight();
		int ybottom = r.getYBottom();
		Rect rect = new Rect(xleft, ytop, xright, ybottom);
		canvas.save();
		float centerx = r.getX();
		float centery = r.getY();
		canvas.rotate(r.getRotation(), centerx, centery);
		canvas.drawRect(rect, paintRoom);
		paintRoomName.setTextSize((int)(r.getWidth()*0.1));
		paintRoomName.setTextAlign(Align.CENTER);
		canvas.drawText(r.getName(), centerx, centery, paintRoomName);
		if (r.getInterest() != null) {
			int interestFontSize= (int)(r.getWidth()*0.08);
			paintRoomInterest.setTextSize(interestFontSize);
			paintRoomName.setTextAlign(Align.LEFT);
			canvas.drawText(r.getName(), xleft, ytop
					+ interestFontSize, paintRoomInterest);
		}

		canvas.restore();
	}

	/**
	 * Draw inputs
	 * 
	 * @param r The room to build inputs
	 * @param canvas The canvas to render on
	 * @param input The linkedRoom
	 * @param rotatedPointRoom The rotatedPointRoom
	 */
	public static void drawInputs(Room r, Canvas canvas, LinkedRoom input, Point rotatedPointRoom)
	{
		Paint paintInputs = new Paint();
		paintInputs.setStyle(Paint.Style.FILL);
		paintInputs.setColor(Color.DKGRAY);
		Point linkedPoint = input.getEndingPoint();
		Point RotatedlinkedPoint = Rooms
				.getnewRotatedPoint((new Point(linkedPoint.x, linkedPoint.y)),
						new Point(input.getRoom().getX(), input.getRoom()
								.getY()), input.getRoom().getRotation());
		canvas.drawLine(rotatedPointRoom.x, rotatedPointRoom.y,
				RotatedlinkedPoint.x, RotatedlinkedPoint.y, paintInputs);
		canvas.save();
		canvas.rotate(25, rotatedPointRoom.x, rotatedPointRoom.y);
		canvas.drawLine(
				rotatedPointRoom.x,
				rotatedPointRoom.y,
				(float) (rotatedPointRoom.x - ((rotatedPointRoom.x - RotatedlinkedPoint.x) * 0.1)),
				(float) (rotatedPointRoom.y - ((rotatedPointRoom.y - RotatedlinkedPoint.y) * 0.1)),
				paintInputs);
		canvas.restore();
		canvas.save();
		canvas.rotate(-25, rotatedPointRoom.x, rotatedPointRoom.y);
		canvas.drawLine(
				rotatedPointRoom.x,
				rotatedPointRoom.y,
				(float) (rotatedPointRoom.x - ((rotatedPointRoom.x - RotatedlinkedPoint.x) * 0.1)),
				(float) (rotatedPointRoom.y - ((rotatedPointRoom.y - RotatedlinkedPoint.y) * 0.1)),
				paintInputs);
		canvas.restore();
	}

	/**
	 * Draw a character
	 * 
	 * @param bm The bitmap to draw
	 * @param r The room to draw on
	 * @param canvas The canvas to render on
	 */
	public static void drawCharacter(Bitmap bm, Room r, Canvas canvas)
	{
		Paint paintRoom = new Paint();
		paintRoom.setStyle(Paint.Style.FILL);
		paintRoom.setColor(Color.rgb(145, 190, 242));

		canvas.save();
		float centerx = r.getX();
		float centery = r.getY();
		canvas.rotate(r.getRotation(), centerx, centery);
		canvas.drawBitmap(bm, r.getXRight()-bm.getWidth(), r.getYBottom()-bm.getHeight(), paintRoom);
		canvas.restore();
	}
}
