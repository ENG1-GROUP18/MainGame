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
    private float spawnPosX = 0.34f; // As percentage of x across map to scale .18
    private float spawnPosY = 0.56f; // As percentage of y across map to scale .16

    private float[][] enemyBasesX = {{0.19f},{0.185f,0.157f},{0.33f,0.365f},{0.28f,0.31f},{0.61f,0.63f},{0.91f,0.93f}}; //order: halifax, wentworth, james, vanbrugh, alcuin, derwent
    private float[][] enemyBasesY = {{0.11f},{0.36f,0.357f},{0.55f,0.53f},{0.62f,0.63f},{0.485f,0.48f},{0.46f,0.44f}};
    private float[] collegesX = {0.17f, 0.17f, 0.34f, 0.29f, 0.62f, 0.92f};
    private float[] collegesY = {0.11f, 0.36f, 0.53f, 0.63f, 0.48f, 0.46f};

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

    public float getSizeX(){ return sizeX;}

    public float getSizeY(){return sizeY;}

    public float getSpawnX() {
        return (spawnPosX * sizeX);
    }

    public float getSpawnY() {
        return (spawnPosY * sizeY);
    }

    public float getCollegeX(int college){ 
        return collegesX[college]*sizeX;
    }
    public float getCollegeY(int college){ 
        return collegesY[college]*sizeY;
    }
    public float getBaseX(int college, int base){
        return enemyBasesX[college][base]*sizeX;
    }
    public float getBaseY(int college, int base){
        return enemyBasesY[college][base]*sizeY;
    }

}
