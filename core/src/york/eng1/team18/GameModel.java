package york.eng1.team18;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import york.eng1.team18.controller.InputController;

public class GameModel {
    public World world;
    private OrthographicCamera camera;
    private InputController controller;
    private Box2DDebugRenderer debugRenderer;


    private Body playerShip;
    private Body bodys;
    private Body bodyk;


    public GameModel(InputController cont){
        controller = cont;
        world = new World(new Vector2(0, 0), true);
        createFloor();
        createBoat();
        createMovingObject();
    }

    public void logicStep(float delta){
        // Game logic goes here
        if (controller.forward) {
            playerShip.applyForceToCenter(0, 100f, true);
            System.out.println("up held");
        }
        world.step(delta, 3, 3);
    }

    private void createBoat() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 5);
        playerShip = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 2f);
        playerShip.createFixture(shape, 0.0f);
        shape.dispose();
    }



    private void createFloor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -5);
        playerShip = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10f, 1);
        playerShip.createFixture(shape, 0.0f);
        shape.dispose();
    }


    private void createObject(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
        playerShip = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        playerShip.createFixture(shape, 0.0f);
        shape.dispose();
    }

    private void createMovingObject(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0,-7);
        bodyk = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        bodyk.createFixture(shape, 0.0f);
        shape.dispose();
        bodyk.setLinearVelocity(0, 0.75f);
    }
}
