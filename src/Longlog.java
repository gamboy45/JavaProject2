import org.newdawn.slick.Input;

public class Longlog extends Sprite {
	private static final String ASSET_PATH = "assets/longlog.png";
	private static final float SPEED = 0.07f;

	private boolean moveRight;
	
	private final float getInitialX() {
		return moveRight ? -World.TILE_SIZE / 2
						 : App.SCREEN_WIDTH + World.TILE_SIZE / 2;
	}
	
	public Longlog(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y);
		
		this.moveRight = moveRight;
		this.setHit(false);
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
		if (getX() > App.SCREEN_WIDTH + World.TILE_SIZE * 2 || getX() < -World.TILE_SIZE * 2) {
			setX(getInitialX());
		}
		
	}
	
	@Override
	public void rideCollision(Sprite other) { 
		other.setHit(false);
	
	}
	
	
}
