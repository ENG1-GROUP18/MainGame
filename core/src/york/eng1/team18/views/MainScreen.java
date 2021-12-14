package york.eng1.team18.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.WorldContactListener;
import york.eng1.team18.actors.*;
import york.eng1.team18.controller.InputController;

public class MainScreen implements Screen {

    // When true, camera is still and zoomed out, used to debug.
    //----------------------------------
    private boolean CAMERA_FOLLOWS = true;
    private boolean BOX2D_WIREFRAME = true;
    //----------------------------------

    private static final int mapImageX = 1155;  // height of map image
    private static final int mapImageY = 776;   // width of map image
    private float mapScale = 500f;               // map width in world units
    private float mapAspectRatio = 1.49f;        // Aspect ratio of image used for map
    private float cameraZoom = 30;               // ExtendViewport minimum size in world units

    private Orchestrator parent;
    private Stage stage;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private WaterTrail waterTrail;

    public Player player; // made player a public variable

    InputController controller;

    OrthographicCamera camera;
    ExtendViewport viewport;

//    ArrayList<CannonBall> cannonBalls = new ArrayList<CannonBall>();
//    float timeSinceLastShot = 0;
//    public CannonBall cann;

    public MainScreen(Orchestrator orchestrator) {
        parent = orchestrator;

        controller = new InputController();
        Gdx.input.setInputProcessor(controller);

        camera = new OrthographicCamera(1, 1);
        viewport = new ExtendViewport(50, 50, camera);
        stage = new Stage(viewport);
        world = new World(new Vector2(0,0), true);
        world.setContactListener(new WorldContactListener(this));
        // Add objects to world
        Map map = new Map(world, 1000, 1000);
        player = new Player(world, orchestrator, camera,controller, map.getSpawnX(), map.getSpawnY(), 4, 2);



        map.setName("map");
        player.setName("player");

        waterTrail = new WaterTrail(camera, player);
        stage.addActor(map);
        stage.addActor(player);

        // debugRenderer = new Box2DDebugRenderer(BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME);
        debugRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);

//        playerSprite = new Sprite(new Texture(Gdx.files.internal("images/rubber_duck.jpg")));
//        playerSprite.setSize(playerWidth, playerHeight);
//        testMapSprite = new Sprite(new Texture(Gdx.files.internal("paths/UniLake.jpg")));
//        testMapSprite.setSize(mapScale, mapScale * (mapImageY / (float)mapImageX));
//
//        batch = new SpriteBatch();
//        batch2 = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(parent.assMan.waterCol);

        waterTrail.act();
        waterTrail.draw();

        stage.act();

        if (CAMERA_FOLLOWS) {
            camera.position.x = stage.getRoot().findActor("player").getX();
            camera.position.y = stage.getRoot().findActor("player").getY();
        }

        stage.draw();

        debugRenderer.render(world, stage.getCamera().combined);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);


//        timeSinceLastShot -=1;
//        if (controller.space){
//
//            if (timeSinceLastShot <= 0){
//                cannonBalls.add(new CannonBall(model.playerBody.getPosition().x, model.playerBody.getPosition().y, Math.abs(model.playerBody.getLinearVelocity().x), Math.abs(model.playerBody.getLinearVelocity().y) , model.playerBody.getAngle()));
//                //sets how fast they can shoot
//
//                timeSinceLastShot = 50;
//            }
//        }
//
//        //update cannonballs
//        ArrayList<CannonBall> cannonBallsToRemove = new ArrayList<CannonBall>();
//        if (cannonBalls != null){
//
//            for (CannonBall cannonBall : cannonBalls){
//
//                cannonBall.update(delta);
//                if (cannonBall.remove){
//                    System.out.println(cannonBall);
//                    cannonBallsToRemove.add(cannonBall);
//                }
//            }
//            cannonBalls.removeAll(cannonBallsToRemove);
//        }
//
//
//
//
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//
//
//        testMapSprite.setPosition(model.lakeBody.getPosition().x, model.lakeBody.getPosition().y);
//
//        playerSprite.setPosition(model.playerBody.getPosition().x, model.playerBody.getPosition().y);
//        playerSprite.setOriginCenter();
//        playerSprite.setRotation(model.playerBody.getAngle()*57.3f);
//        playerSprite.translate(-1, -2);
//
//        testMapSprite.draw(batch);
//        playerSprite.draw(batch);
//
//
//        batch.end();
//
//
//        batch2.setProjectionMatrix(camera.combined);
//        batch2.begin();
//        if (cannonBalls != null) {
//            for (CannonBall cannonBall : cannonBalls) {
//
//                //System.out.println(model.playerShip.getPosition().x);
//                cannonBall.render(batch2);
//            }
//        }
//        batch2.end();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        debugRenderer.dispose();
    }
}
