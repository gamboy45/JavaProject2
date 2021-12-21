
import java.util.Random;

public class ExtraLife extends Sprite {
	private static final String ASSET_PATH = "assets/extralife.png";
	private float myDelta = 0; // current counter
	private float deltaMax = 0; // 7 seconds
	private float seconds = 14000;
	private float offscreen = 1000;
	public static int trigger = 0;

	
	public ExtraLife (float x, float y) {
		super(ASSET_PATH, x, y);
		this.setHit(false);
	}
	
	private static int getRand(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	
	@Override
	public void spawnlife(float pDelta, Sprite other) {
		myDelta += pDelta;
		
		//Get random seconds between 25 and 35 and set it as deltaMax
		if (deltaMax == 0) {
			deltaMax = getRand(25000, 35000);
		}
	
		//after deltaMax seconds, put extralife figure on.
		if(myDelta >= deltaMax) {
			this.setX(other.getX());
			this.setY(other.getY());
			trigger = 1;
			if (!visibility) {
				myDelta = 50000;
			}
		}
		//after 14 seconds of existing on screen, delete extralife
		if (myDelta > deltaMax + seconds) {
			this.setY(offscreen);
			this.setX(App.SCREEN_WIDTH + World.TILE_SIZE);
			myDelta = 0;
			trigger = 0;
			deltaMax = 0;
			visibility = true;
		}
	}
	
}
