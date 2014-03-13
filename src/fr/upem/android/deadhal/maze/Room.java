package fr.upem.android.deadhal.maze;

import java.util.UUID;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.WebView.FindListener;

import fr.upem.android.deadhal.R;
import fr.upem.android.deadhal.utils.MazeBuilder;

public class Room {

	/**
     * 
     */
	private final String id;

	/**
     * 
     */
	private String name;

	/**
     * 
     */
	private float nameFontSize;

	/**
     * 
     */
	private int x;

	/**
     * 
     */
	private int y;

	/**
     * 
     */
	private int width;

	/**
     * 
     */
	private int height;

	/**
     * 
     */
	private float rotation;

	/**
     * 
     */
	private final Input inputs;

	/**
     * 
     */
	private final Output outputs;

	/**
     * 
     */
	private Interest interest;

	private boolean occuped;

	public Room(String id, String name) throws RuntimeException {
		MazeBuilder.getInstance().referenceRoomName(name);

		this.inputs = new Input();
		this.outputs = new Output();
		this.id = (id == null) ? UUID.randomUUID().toString() : id;
		this.name = name;
		this.nameFontSize = 25;
		this.occuped = false;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public float getRotation() {
		return this.rotation;
	}

	public Interest getInterest() {
		return this.interest;

	}

	public float getNameFontSize() {
		return this.nameFontSize;
	}

	public Room setName(String name) {
		this.name = name;

		return this;
	}

	public Room setX(int x) {
		this.x = x;

		return this;
	}

	public Room setY(int y) {
		this.y = y;

		return this;
	}

	public Room setWidth(int width) {
		this.width = width;

		return this;
	}

	public Room setHeight(int height) {
		this.height = height;

		return this;
	}

	public Room setRotation(float rotation) {
		this.rotation = rotation;

		return this;
	}

	public Room setInterest(Interest interest) {
		this.interest = interest;

		return this;
	}

	public Room setNameFontSize(float nameFontSize) {
		this.nameFontSize = nameFontSize;

		return this;
	}

	public Input getInputs() {
		return this.inputs;
	}

	public Output getOutputs() {
		return this.outputs;
	}

	public int getXLeft() {
		return getX() - (getHeight() / 2);
	}

	public int getXRight() {
		return getX() + (getHeight() / 2);
	}

	public int getYTop() {
		return getY() - (getWidth() / 2);
	}

	public int getYBottom() {
		return getY() + (getWidth() / 2);
	}

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Room) {
			if (this.getId() == ((Room) o).getId()) {
				return true;
			}
		}
		return false;
	}

	public boolean isOccuped() {
		return occuped;
	}

	public void setOccuped(boolean occuped) {
		this.occuped = occuped;
	}
}
