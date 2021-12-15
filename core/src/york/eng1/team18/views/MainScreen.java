package york.eng1.team18.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
    private boolean BOX2D_WIREFRAME = false;
    //----------------------------------

    private static final int mapImageX = 1155;  // height of map image
    private static final int mapImageY = 776;   // width of map image
    private float mapScale = 500f;               // map width in world units
    private float mapAspectRatio = 1.49f;        // Aspect ratio of image used for map
    private float cameraZoom = 60;               // ExtendViewport minimum size in world units
    private Vector2 cameraOffset;

    private Orchestrator parent;
    private Stage stage;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private WaterTrail waterTrail;

    public Player player; // made player a public variable

    InputController inpt;

    OrthographicCamera camera;
    ExtendViewport viewport;

//    ArrayList<CannonBall> cannonBalls = new ArrayList<CannonBall>();
//    float timeSinceLastShot = 0;
//    public CannonBall cann;

    public MainScreen(Orchestrator orchestrator) {
        parent = orchestrator;

        inpt = new InputController();
        Gdx.input.setInputProcessor(inpt);

        cameraOffset = new Vector2(0, 15);
        camera = new OrthographicCamera(1, 1);
        viewport = new ExtendViewport(cameraZoom, cameraZoom, camera);
        stage = new Stage(viewport);
        world = new World(new Vector2(0,0), true);
        world.setContactListener(new WorldContactListener(this));

        // Add objects to world
        Map map = new Map(world, 1000, 1000);
        player = new Player(world, orchestrator, camera, inpt, map.getSpawnX(), map.getSpawnY(), 6, 3);


        map.setName("map");
        player.setName("player");

        waterTrail = new WaterTrail(camera, player);
        stage.addActor(map);
        stage.addActor(player);

        debugRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);
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


        float myX = player.getX();
        float myY = player.getY();

        // Update camera position
        if (CAMERA_FOLLOWS) {
            if (inpt.space) {
                // Allow player to look towards mouse pos
                Vector2 mousePos = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

                cameraOffset = new Vector2(((mousePos.x - myX) / 2), ((mousePos.y - myY) / 2));
            }

            camera.position.x = myX + cameraOffset.x;
            camera.position.y = myY + cameraOffset.y;
        }

        stage.draw();

        debugRenderer.render(world, stage.getCamera().combined);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
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
