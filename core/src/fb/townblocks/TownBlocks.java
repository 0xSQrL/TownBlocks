package fb.townblocks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TownBlocks extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Pathfinder p, p2;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		WorldMap map = new WorldMap(100, 100, 20);
		ArrayList<Vector3I> motions = new ArrayList<>();
		motions.add(Vector3I.Forward);
		motions.add(Vector3I.Backward);
		motions.add(Vector3I.Left);
		motions.add(Vector3I.Right);
		p = new Pathfinder(map, motions, new Vector3I(10, 11, 10), new Vector3I(15, 11, 15));
		p.findPath();
		p2 = new Pathfinder(map, motions, new Vector3I(10, 11, 10), new Vector3I(20, 11, 15));
		p2.findPath();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		System.out.println(p);
		System.out.println(p2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
