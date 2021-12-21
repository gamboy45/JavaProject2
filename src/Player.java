import org.newdawn.slick.Input;

public class Player extends Sprite {
	private static final String ASSET_PATH = "assets/frog.png";
	public static float initx;
	public static float inity;
	public static int life;

	private float prevx;
	private float prevy;

	
	
	public Player(float x, float y) {
		super(ASSET_PATH, x, y);
		initx = x;
		inity = y;
		life = 3;
		this.setPlayer(true);
	}

	@Override
	public void update(Input input, int delta) {
		int dx = 0,
			dy = 0;
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= World.TILE_SIZE;
		}
		
		// make sure the frog stays on screen
		if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 	> App.SCREEN_WIDTH) {
			dx = 0;
		}
		if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}
		
		//prevx, prevy stored so frog coordinate can be set when met an obstacle
		prevx = getX();
		prevy = getY();
		
		move(dx, dy);
		
		
	}
	
	@Override
	public void onCollision(Sprite other) {
		//When Player meets hazard and hazard is not colliding rideable object
		if ((other.hasTag(Sprite.HAZARD) || other.hasTag(WATER)) && other.isHit()) {
			//life to be decreased and puts Player to initial position
			if (life > 1) {
				life = life -1;
				this.setX(initx);
				this.setY(inity);
			}
			else {
				System.exit(0);
			}
		}
		
		//Hampers player from jumping into objects that is an obstacle (meaning not moveable)
		if (other.hasTag(Sprite.NOTMOVE)) {
			this.setX(prevx);
			this.setY(prevy);
			
		}
		
		if (other instanceof ExtraLife) {
			if (life == World.MAXLIFE) {
				
			}
			else {
				life = life + 1;
				//makes extralife invisible after collision
				other.visibility = false;
			}
		}
		
	}
	
	//detects goal and puts Player to initial position
	public void goal() {
		if (this.getY() == World.TILE_SIZE) {
			this.setX(initx);
			this.setY(inity);
		}
	
	}
}
