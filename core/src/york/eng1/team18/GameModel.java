package york.eng1.team18;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import york.eng1.team18.controller.InputController;
import york.eng1.team18.loader.BodyEditorLoader;
import york.eng1.team18.views.MainScreen;

public class GameModel {
    public World world;
    private OrthographicCamera camera;
    private InputController controller;
    private Box2DDebugRenderer debugRenderer;

    public Body playerShip;
    public Body lakeBody;
    public Body islandBox;


    public GameModel(InputController cont){
        controller = cont;
        world = new World(new Vector2(0, 0), true);
        //createIsland();
        createBoat();
        createLake();
    }

    public void logicStep(float delta){
        // Game logic goes here
        // For forward/backward force
        float enginePower = 100;
        float x = (float)Math.sin(playerShip.getAngle() - Math.PI);
        float y = (float)Math.cos(playerShip.getAngle());

        float speed_dependant = 1- Math.abs((30  - (Math.abs(playerShip.getLinearVelocity().x) + Math.abs(playerShip.getLinearVelocity().y)))/30);


        if (controller.forward) {
            // Increase linear damping, apply forward force
            playerShip.applyForceToCenter(x * enginePower, y * enginePower,true);
            playerShip.setLinearDamping(5f);
        } else {
            // Reduce Linear Damping
            playerShip.setLinearDamping(2f);
        }
        if (controller.backward) {
            playerShip.applyForceToCenter(-x * 0.2f * enginePower, -y * 0.2f * enginePower,true);
        }
        if (controller.left && !controller.right) {
            System.out.println(speed_dependant);
            playerShip.setAngularVelocity(1.5f + (speed_dependant * 1.5f));
            playerShip.setAngularDamping(4f - speed_dependant);
        } else if (controller.right && !controller.left) {
            playerShip.setAngularVelocity(-1.5f - (speed_dependant * 1.5f));
            playerShip.setAngularDamping(4f - speed_dependant);
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

    private void createLake() {
        // Create loader
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("paths/UniLake.json"));
        // Create BodyDef
        BodyDef bd = new BodyDef();
        bd.position.set(-50 , -50);
        bd.type = BodyDef.BodyType.StaticBody;

        // Create FixtureDef
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        // Create Body
        lakeBody = world.createBody(bd);

        // Create body fixture with loader
        loader.attachFixture(lakeBody, "UniLake", fd, 200f);
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
