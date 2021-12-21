import org.newdawn.slick.Input;

public class Bike extends Sprite {
	private static final String ASSET_PATH = "assets/bike.png";
	private static final float SPEED = 0.2f;
	
	private boolean moveRight;
	
	public Bike(float x, float y, boolean moveRight) {
		super(ASSET_PATH, x, y, new String[] { Sprite.HAZARD });
		
		this.moveRight = moveRight;
	}
	
	@Override
	public void update(Input input, int delta) {
		move(SPEED * delta * (moveRight ? 1 : -1), 0);
		
		// check if the vehicle has moved off the screen
		if (getX() > 1000|| getX() < 20) {
			this.moveRight = !moveRight;
		}
	}
}
