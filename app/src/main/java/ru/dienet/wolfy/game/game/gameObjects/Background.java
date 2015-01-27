package ru.dienet.wolfy.game.game.gameObjects;

public class Background {

	private int bgX, bgY, speedX;

	public Background(int x, int y){
		bgX = x;
		bgY = y;
		speedX = 0;
	}

	public void update() {
		bgX += speedX;
		//ToDo:what the magic constants?
		if (bgX <= -2160){
			bgX += 4320;
		}
	}

	public int getBgX() {
		return bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
}
