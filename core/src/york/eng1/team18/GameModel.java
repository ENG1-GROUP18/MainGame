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


    public Body playerShip;
    public Body islandBox;


    public GameModel(InputController cont){
        controller = cont;
        world = new World(new Vector2(0, 0), true);
        createIsland();
        createBoat();
    }

    public void logicStep(float delta){
        // Game logic goes here
        // For forward/backward force
        float enginePower = 100;
        float x = (float)Math.sin(playerShip.getAngle() - Math.PI);
        float y = (float)Math.cos(playerShip.getAngle());

        if (controller.forward) {
            // Increase linear damping, apply forward force
            playerShip.applyForceToCenter(x * enginePower, y * enginePower,true);
            playerShip.setLinearDamping(4f);
        } else {
            // Reduce Linear Damping
            playerShip.setLinearDamping(2f);
        }
        if (controller.backward) {
            playerShip.applyForceToCenter(-x * 0.2f * enginePower, -y * 0.2f * enginePower,true);
        }
        if (controller.left && !controller.right) {
            playerShip.setAngularVelocity(2f);
        } else if (controller.right && !controller.left) {
            playerShip.setAngularVelocity(-2f);
        }
//        else {
//            playerShip.setAngularVelocity(0);
//        }
        world.step(delta, 3, 3);
    }

    private void createBoat() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.position.set(-10, -5);
        playerShip = world.createBody(bodyDef);
        playerShip.setLinearDamping(2);
        playerShip.setAngularDamping(1.4f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 2f);
        playerShip.setTransform(-40, -10, (float)(Math.PI * 1.5));
        playerShip.createFixture(shape, 0.0f);
        shape.dispose();
    }



    private void createIsland() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(20, -5);
        islandBox = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10f, 10f);
        islandBox.createFixture(shape, 0.0f);
        shape.dispose();
    }
}
