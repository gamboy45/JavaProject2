import org.newdawn.slick.Input;

public class Turtle extends Sprite {
    
	private static final String ASSET_PATH = "assets/turtles.png";
	private static final float SPEED = 0.085f;

	private float myDelta = 0; // current counter
	private float deltaMax = 7000; // 7 seconds
	private float twoSec = 2000; // 2 seconds
	private float offscreen = 1000;
	private float inity;


	private boolean moveRight;
	
	private final float getInitialX() {
		return moveRight ? -World.TILE_SIZE / 2
						 : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
	}
	
	public Turtle(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y);
		
		this.moveRight = moveRight;
		this.setHit(false);
		inity = y;
	}
	
	public void movePlayer(Sprite other, int delta) {
		if (this.collides(other) && other.isPlayer()) {
			float dx = SPEED;
			if (other.getX() + dx - World.TILE_SIZE / 2 < 0 && !moveRight || 
					moveRight && other.getX() + dx + World.TILE_SIZE / 2 > App.SCREEN_WIDTH) {
				dx = 0;
			}
			other.move(dx*delta*(moveRight ? 1 : -1),0);
		}
	}
	
	@Override
	public void update(Input input, int delta) {
		
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
				
		// check if the vehicle has moved off the screen
		if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE || getX() < -World.TILE_SIZE) {
			setX(getInitialX());
		}
		
		
		
		
		
	}
	
	@Override
	public void rideCollision(Sprite other) { 
		other.setHit(false);
	}
	
	@Override
	//Turtle to disappear every 7 seconds and come back on screen after 2 seconds
	public void Turtlemove(float pDelta) {
		  myDelta += pDelta;
		  if(myDelta >= deltaMax) {
			this.setY(offscreen);
		  }
		  if (myDelta > deltaMax + twoSec) {
			  this.setY(inity);
			  myDelta = 0;
		  }
		}
	
}
