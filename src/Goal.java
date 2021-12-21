public class Goal extends Sprite {
	private static final String ASSET_PATH = "assets/frog.png";
	
	public Goal (float x, float y) {
		super(ASSET_PATH, x, y, new String[] {Sprite.NOTMOVE});
	}
	
}
