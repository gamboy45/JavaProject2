//The code was originally written by Eleanor and was modified by Dylan Lee to add new features
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileReader;
import java.io.IOException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;


public class World {
	public static final int TILE_SIZE = 48;
	public static final int MAXLIFE = 12;
	private ArrayList<Sprite> sprites = new ArrayList<>();
	private Sprite lives[] = new life [MAXLIFE];
	private Sprite goal[] = new Goal [5];
	private int goalhit = 0;
	private int lev = 1;
	private ArrayList<Sprite> logs = new ArrayList<>();
	private int index = 0;

	public World() {
		setUp(lev, sprites);
		logfill(logs);
	}
	
	public int rand(ArrayList<Sprite> sprites) {
		int rand = new Random().nextInt(sprites.size());
		return rand;
	}
	
	public void setUp(int lev, ArrayList<Sprite> sprites) {
		String levelFile1 = "assets/levels/" + lev +".lvl";
		String line = "";
		try (BufferedReader br = new BufferedReader(new FileReader(levelFile1))) {

            while ((line = br.readLine()) != null) {
            	float x, y;
                // use comma as separator
                String[] file1 = line.split(",");
                x = Float.parseFloat(file1[1]);
            	y = Float.parseFloat(file1[2]);
                if (file1[0].equals("water")) {
                	sprites.add(Tile.createWaterTile(x,y));
                }
                else if (file1[0].equals("grass")) {
                	sprites.add(Tile.createGrassTile(x,y));
                }
                else if (file1[0].equals("tree")) {
                	sprites.add(Tile.createTreeTile(x,y));
                }
                else if (file1[0].equals("bus")) {
                	sprites.add(new Bus(x, y, Boolean.parseBoolean(file1[3])));
                }
                else if (file1[0].equals("racecar")) {
                	sprites.add(new Racecar(x, y, Boolean.parseBoolean(file1[3])));
                }
                else if (file1[0].equals("log")) {
                	sprites.add(new Log(x, y, Boolean.parseBoolean(file1[3])));
                }
                else if (file1[0].equals("longLog")) {
                	sprites.add(new Longlog(x, y, Boolean.parseBoolean(file1[3])));
                }
                else if (file1[0].equals("bike")) {
                	sprites.add(new Bike(x, y, Boolean.parseBoolean(file1[3])));
                }
                else if (file1[0].equals("bulldozer")) {
                	sprites.add(new Bulldozer(x, y, Boolean.parseBoolean(file1[3])));
                }
                else if (file1[0].equals("turtle")) {
                	sprites.add(new Turtle(x, y, Boolean.parseBoolean(file1[3])));
                }
            }
            
		} catch (IOException e) {
            e.printStackTrace();
        }
		// create player
		sprites.add(new Player(App.SCREEN_WIDTH / 2, App.SCREEN_HEIGHT - TILE_SIZE));
		// create ExtraLife
		sprites.add(new ExtraLife(2000,1000));
	}
	
	//identifies logs
	public void logfill(ArrayList<Sprite> logs) {
		for (Sprite sprite: sprites) {
			if (sprite instanceof Log || sprite instanceof Longlog) {
				logs.add(sprite);
			}
		}
	}
	
	public void update(Input input, int delta) {
		if (ExtraLife.trigger == 0) {
			index = rand(logs);
		}
		Sprite random = logs.get(index);
		
		
		for (Sprite sprite : sprites) {
			sprite.update(input, delta);
			sprite.Turtlemove(delta);
			sprite.spawnlife(delta, random);
		}
		
		// loop over all pairs of sprites and test for intersection
		for (Sprite sprite1: sprites) {
			for (Sprite sprite2: sprites) {
				if (sprite1 != sprite2 && sprite1.collides(sprite2)) {
				
					sprite1.bulldozerCollision(sprite2, delta);
					sprite1.rideCollision(sprite2);
					sprite1.onCollision(sprite2);
					sprite1.movePlayer(sprite2, delta);
				}
			}
			
			//prevent Player from intruding into Goal spot when its filled.
			for (int i = 0; i < goalhit; i++) {
				if (sprite1.collides(goal[i])) {
					sprite1.onCollision(goal[i]);
				}
			}
			
			//set Water as Hazard again after Rideable collision.
			if (sprite1.hasTag(Sprite.WATER)) {
				sprite1.setHit(true);
			}
			
			//Turtle time disappearance
	
		}
		
		//Update game Setup when Player fills 5 goals.
		if (lev == 1 && goalhit == 5) {
			System.exit(0);
		}
		else {
			//Level to be increased and stage to be Setup according to 1.lvl
			if (goalhit == 5) {
				goalhit = 0;
				lev = 1;
				sprites.clear();
				logs.clear();
				setUp(lev, sprites);
				logfill(logs);
			}
		}
		
	}
	
	public void render(Graphics g) {
		for (Sprite sprite : sprites) {
			sprite.render();
			
			//Goal Picture to be stored on Goal array when player hits the goal coordinate
			if (sprite.getY() == World.TILE_SIZE && sprite.isPlayer()) {
				goal[goalhit] = new Goal (sprite.getX(), World.TILE_SIZE);
				goalhit += 1;
			}
			sprite.goal();
		}
		
		int i;
		float lifex = 24;
		float lifey = 744;
		
		//Goal Object to be rendered using goalhit as limit
		for (i=0; i < goalhit; i++) {
			goal[i].render();
		}
		
		//Life object to be created
		for (i = 0; i < MAXLIFE; i++ ) {
			lives[i] = new life (lifex, lifey);
			lifex += 32;
		}
		//Life object to be rendered according to player life
		for (i = 0; i < Player.life; i++) {
			lives[i].render();
		
		}
	}
}
