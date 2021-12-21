//The code was originally written by Eleanor and was modified by Dylan Lee to add new features
import utilities.BoundingBox;



import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public abstract class Sprite {
	// this is a defined constant to avoid typos
	public final static String HAZARD = "hazard";
	public final static String NOTMOVE = "notmove";
	public final static String WATER = "water";


	public boolean visibility = true;
	private BoundingBox bounds;
	private Image image;
	private float x;
	private float y;
	
	private String[] tags;
	private boolean hit;
	private boolean player;
	
	public Sprite(String imageSrc, float x, float y) {
		setupSprite(imageSrc, x, y);
	}
	public Sprite(String imageSrc, float x, float y, String[] tags) {
		setupSprite(imageSrc, x, y);
		this.tags = tags;
		this.setHit(true);
		this.setPlayer(false);
	}
	
	private void setupSprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		//Different Boundingbox needed for Player to freely move on rideable when touched
		if (this instanceof Log || this instanceof Turtle || this instanceof Longlog) {
			//Boundingbox set a pixel larger for log
			//Player to not die at the end of rideable when it touches water on side
			bounds = new BoundingBox(x, y, image.getWidth() + World.TILE_SIZE, image.getHeight());
		}
		else {
			bounds = new BoundingBox(image, (int)x, (int)y);
		}
		
		
		tags = new String[0];		
	}

	/**
	 * Sets the x position of the sprite.
	 * @param x	 the target x position
	 */
	public final void setX(float x) { this.x = x; bounds.setX((int)x); }
	/**
	 * Sets the y position of the sprite.
	 * @param y	 the target y position
	 */
	public final void setY(float y) { this.y = y; bounds.setY((int)y); }
	/**
	 * Accesses the x position of the sprite.
	 * @return	the x position of the sprite
	 */
	public final float getX() { return x; }
	/**
	 * Accesses the y position of the sprite.
	 * @return	the y position of the sprite
	 */
	public final float getY() { return y; }
	
	public final void move(float dx, float dy) {
		setX(x + dx);
		setY(y + dy);
	}
	
	public final boolean onScreen(float x, float y) {
		return !(x + World.TILE_SIZE / 2 > App.SCREEN_WIDTH || x - World.TILE_SIZE / 2 < 0
			 || y + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT || y - World.TILE_SIZE / 2 < 0);
	}
	
	public final boolean onScreen() {
		return onScreen(getX(), getY());
	}
	
	public final boolean collides(Sprite other) {
		return bounds.intersects(other.bounds);
	}
	
	public void update(Input input, int delta) { }
	
	public void onCollision(Sprite other) { }
	
	public void rideCollision(Sprite other) { }
	
	public void movePlayer(Sprite other, int delta) { }
	
	public void render() {
		image.drawCentered(x, y);
	}
	
	public boolean hasTag(String tag) {
		for (String test : tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}
	public boolean isHit() {
		return hit;
	}
	public void setHit(boolean player) {
		this.hit = player;
	}
	public boolean isPlayer() {
		return player;
	}
	public void setPlayer(boolean player) {
		this.player = player;
	}
	
	public void bulldozerCollision(Sprite other, int delta) { }
	
	public void goal() { }
	
	public void Turtlemove(float pDelta) { }
	
	public void spawnlife(float delta, Sprite other) { }
}
