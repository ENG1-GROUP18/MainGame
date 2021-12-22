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
    private float spawnPosX = 0.18f; // As percentage of x across map to scale
    private float spawnPosY = 0.16f; // As percentage of y across map to scale
    private float halifaxCannon0PosX = 0.19f;
    private float wentworthCannon0PosX = 0.185f;
    private float wentworthCannon1PosX = 0.155f;
    private float halifaxPosX = 0.17f;
    private float wentworthPosX = 0.17f;
    private float jamesPosX = 0.34f;
    private float vanbrughPosX = 0.29f;
    private float alcuinPosX = 0.62f;
    private float derwentPosX = 0.92f;
    private float halifaxCannon0PosY = 0.11f;
    private float wentworthCannon0PosY = 0.36f;
    private float wentworthCannon1PosY = 0.36f;
    private float halifaxPosY = 0.11f;
    private float wentworthPosY = 0.36f;
    private float jamesPosY = 0.53f;
    private float vanbrughPosY = 0.63f;
    private float alcuinPosY = 0.48f;
    private float derwentPosY = 0.46f;

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

    public float getCollegeX(String collegeName) {
        if(collegeName == "HalifaxCannon0"){
            return halifaxCannon0PosX*sizeX;
        }
        if(collegeName == "WentworthCannon0"){
            return wentworthCannon0PosX*sizeX;
        }
        if(collegeName == "WentworthCannon1"){
            return wentworthCannon1PosX*sizeX;
        }

        if(collegeName == "Halifax"){
            return halifaxPosX*sizeX;
        }
        if(collegeName == "Wentworth"){
            return wentworthPosX*sizeX;
        }
        if(collegeName == "James"){
            return jamesPosX*sizeX;
        }
        if(collegeName == "Vanbrugh"){
            return vanbrughPosX*sizeX;
        }
        if(collegeName == "Alcuin"){
            return alcuinPosX*sizeX;
        }
        if(collegeName == "Derwent"){
            return derwentPosX*sizeX;
        }
        return derwentPosX;
    }

    public float getCollegeY(String collegeName) {
        if(collegeName == "HalifaxCannon0"){
            return halifaxCannon0PosY*sizeY;
        }
        if(collegeName == "WentworthCannon0"){
            return wentworthCannon0PosY*sizeY;
        }
        if(collegeName == "WentworthCannon1"){
            return wentworthCannon1PosY*sizeY;
        }

        if(collegeName == "Halifax"){
            return halifaxPosY*sizeY;
        }
        if(collegeName == "Wentworth"){
            return wentworthPosY*sizeY;
        }
        if(collegeName == "James"){
            return jamesPosY*sizeY;
        }
        if(collegeName == "Vanbrugh"){
            return vanbrughPosY*sizeY;
        }
        if(collegeName == "Alcuin"){
            return alcuinPosY*sizeY;
        }
        if(collegeName == "Derwent"){
            return derwentPosY*sizeY;
        }
        return derwentPosY;
    }

}
