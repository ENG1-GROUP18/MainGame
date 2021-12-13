package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import york.eng1.team18.loader.BodyEditorLoader;

public class Map extends Actor {
    //TODO create map class

    private World world;
    private Body body;
    private float sizeX;
    private float sizeY;
    private float spawnPosX = 0.20f; // As percentage of x across map to scale
    private float spawnPosY = 0.06f; // As percentage of y across map to scale



    public Map(World world, float sizeX, float sizeY) {
        this.world = world;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("paths/UniLake.json"));

        BodyDef bd = new BodyDef();
        //bd.position.set(sizeX/2, sizeY/2);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.5f;

        body = world.createBody(bd);
        body.setUserData("Map");

        loader.attachFixture(body, "Lake", fd, sizeX);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }


    public float getSpawnX() {
        return (spawnPosX * sizeX);
    }

    public float getSpawnY() {
        return (spawnPosY * sizeY);
    }

}