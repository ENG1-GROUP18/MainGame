package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import york.eng1.team18.loader.BodyEditorLoader;

public class Map extends Image {

    private World world;
    private Body body;
    private Sprite mapImage;

    private float sizeX;
    private float sizeY;
    private float spawnPosX = 0.222f; // As percentage of x across map to scale
    private float spawnPosY = 0.12f; // As percentage of y across map to scale

    private float[][] enemyBasesX = {{0.16f},{0.200f,0.220f},{0.33f,0.365f},{0.28f,0.31f},{0.61f,0.63f},{0.82f,0.83f}}; //order: halifax, wentworth, james, vanbrugh, alcuin, derwent
    private float[][] enemyBasesY = {{0.24f},{0.36f,0.370f},{0.58f,0.56f},{0.64f,0.66f},{0.48f,0.48f},{0.495f,0.48f}};
    private float[] collegesX = {0.15f, 0.210f, 0.34f, 0.29f, 0.62f, 0.82f};
    private float[] collegesY = {0.24f, 0.36f, 0.56f, 0.66f, 0.48f, 0.48f};

    public Map(World world, float sizeX, float sizeY) {
        this.world = world;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        mapImage = new Sprite(new Texture("images/Map/mapBase.jpg"));
        mapImage.setOrigin(0,  0);
        mapImage.setScale(0.2f);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("paths/CurrentMap.json"));

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.5f;

        fd.filter.categoryBits = 0x0008;

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
        mapImage.draw(batch);
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
