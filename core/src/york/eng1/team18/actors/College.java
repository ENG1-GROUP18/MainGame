package york.eng1.team18.actors;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;


public class College extends Group {

    private Body body;

    // COLLEGE PROPERTIES:
    private float size_x = 6;
    private float size_y = 6;
    private boolean isConquered;
    private int numCannons;
    private Building building;

    private float health;



    public College(World world, Stage stage, Camera camera, float pos_x, float pos_y, String imagePath){


        // Set image, position, image path, conquered state and world reference
        super();
        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);
        this.isConquered = false;
        this.numCannons = 0;

        //Colleges have to be hit 5 times to be destroyed
        this.health = 50;

        // Create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);
        body.setTransform(pos_x +size_x/2, pos_y +size_y/2, (float)-Math.PI);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size_x/2, size_y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;
        //Uses contact filtering bits to state what can and the body can and can't interact with
        //So maskBits = 4 means it can collide with other bodies of bit 4, in the game means it can collide with player cannonballs
        //And categoryBits = 2 means it will accept other bodies of type 2 for collisions
        fixtureDef.filter.maskBits = 0x0004;
        fixtureDef.filter.categoryBits = 0x0002;
        body.createFixture(fixtureDef).setUserData("Collage");
        body.setUserData("Collage");


        // Dispose shapes used to create fixtures
        shape.dispose();

        // Add components to college
        building = new Building(this, imagePath);
        this.addActor(building); //TODO remove building class. Move its code to here.


        // Add college to stage
        stage.addActor(this);

    }

    private void conquer() {
        isConquered = true;
    }

    public void incrementCannons(){
        numCannons += 1;
    }

    public void decrementCannons(){
        numCannons -= 1;
        if (numCannons == 0){this.conquer();}
    }

    @Override
    public void act(float delta) {
        if (body.getUserData() == "Hit"){
            this.health -=10;
            body.setUserData("Enemy Base");
        }
        if (this.health <= 0 && !isConquered){
            for (Fixture fixture : body.getFixtureList()){
                body.destroyFixture(fixture);
            }
            Image tick = new Image(new Texture( "images/conquered.png"));
            tick.setSize(this.size_x,this.size_y);
            this.addActor(tick);
            conquer();

        }


        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
