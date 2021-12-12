package york.eng1.team18;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import york.eng1.team18.controller.InputController;
import york.eng1.team18.loader.BodyEditorLoader;
import york.eng1.team18.views.MainScreen;

public class GameModel {
    public MainScreen parent;
    public World world;
    private OrthographicCamera camera;
    private InputController controller;
    private Box2DDebugRenderer debugRenderer;

    public Body playerBody;
    public Body lakeBody;

    public GameModel(MainScreen screen, InputController cont){
        parent = screen;
        controller = cont;
        world = new World(new Vector2(0, 0), true);

        createBoat();
        createLake();
    }

    public void logicStep(float delta){
        // Game logic goes here
        float enginePower = 100;
        float x = (float)Math.sin(playerBody.getAngle() - Math.PI);
        float y = (float)Math.cos(playerBody.getAngle());

        float speed_dependant = 1 - Math.abs((30  - (Math.abs(playerBody.getLinearVelocity().x) + Math.abs(playerBody.getLinearVelocity().y)))/30);


        if (controller.forward) {
            // Increase linear damping, apply forward force
            playerBody.applyForceToCenter(x * enginePower, y * enginePower,true);
        }
        if (controller.backward) {
            playerBody.applyForceToCenter(-x * 0.2f * enginePower, -y * 0.2f * enginePower,true);
        }
        if (controller.left && !controller.right) {
            playerBody.setAngularVelocity(1.5f + (speed_dependant * 1.5f));
            playerBody.setAngularDamping(4f - speed_dependant);
        } else if (controller.right && !controller.left) {
            playerBody.setAngularVelocity(-1.5f - (speed_dependant * 1.5f));
            playerBody.setAngularDamping(4f - speed_dependant);
        }
        else {
            playerBody.setAngularVelocity(0);
        }
        world.step(delta, 3, 3);
    }


    private void createBoat() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //bodyDef.position.set(-10, -5);
        playerBody = world.createBody(bodyDef);
        playerBody.setLinearDamping(1);
        playerBody.setAngularDamping(1.4f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(parent.playerWidth/2, parent.playerHeight/2);

        playerBody.setTransform(-150, -140, 0);
        playerBody.createFixture(shape, 0);
        shape.dispose();
    }

    private void createLake() {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("paths/UniLake.json"));

        BodyDef bd = new BodyDef();
        bd.position.set(-parent.mapScale/2, (-parent.mapScale / (parent.mapAspectRatio))/2);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.5f;

        lakeBody = world.createBody(bd);

        loader.attachFixture(lakeBody, "Lake", fd, parent.mapScale);
    }
}
