package york.eng1.team18.views;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import jdk.tools.jmod.Main;
import york.eng1.team18.GameModel;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.controller.InputController;

public class MainScreen implements Screen {

    // When true, camera is still and zoomed out, used to debug.
    //----------------------------------
    private boolean CAMERA_FOLLOWS = true;
    private boolean BOX2D_WIREFRAME = false;
    //----------------------------------

    private static final int mapImageX = 1155;  // height of map image
    private static final int mapImageY = 776;   // width of map image
    public float playerHeight = 4f;             // player height in world units
    public float playerWidth = 2f;              // player width in world units
    public float mapScale = 500f;               // map width in world units
    public float mapAspectRatio = 1.49f;        // Aspect ratio of image used for map
    public float cameraZoom = 30;               // ExtendViewport minimum size in world units


    private Orchestrator parent;

    InputController controller;
    GameModel model;

    OrthographicCamera camera;
    ExtendViewport viewport;

    Sprite playerSprite;
    Sprite testMapSprite;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Box2DDebugRenderer debugRenderer;

    public MainScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        controller = new InputController();
        Gdx.input.setInputProcessor(controller);
        model = new GameModel(this, controller);

        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer(BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME,BOX2D_WIREFRAME);

        camera = new OrthographicCamera(1, 1);
        if (!CAMERA_FOLLOWS) {
            cameraZoom = mapScale / 1.5f;
        }
        viewport = new ExtendViewport(cameraZoom, cameraZoom, camera);

        playerSprite = new Sprite(new Texture(Gdx.files.internal("images/boatSprite.png")));
        playerSprite.setSize(playerWidth, playerHeight);

        testMapSprite = new Sprite(new Texture(Gdx.files.internal("paths/UniLake.jpg")));
        testMapSprite.setSize(mapScale, mapScale * (mapImageY / (float)mapImageX));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        ScreenUtils.clear(parent.assMan.waterCol);

        // Update camera position
        if (CAMERA_FOLLOWS) {
            camera.position.x = model.playerShip.getPosition().x;
            camera.position.y = model.playerShip.getPosition().y;
        }
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        testMapSprite.setPosition(model.lakeBody.getPosition().x, model.lakeBody.getPosition().y);

        playerSprite.setPosition(model.playerShip.getPosition().x, model.playerShip.getPosition().y);
        playerSprite.setOriginCenter();
        playerSprite.setRotation(model.playerShip.getAngle()*57.3f);
        playerSprite.translate(-playerWidth/2, -playerHeight/2);

        testMapSprite.draw(batch);
        playerSprite.draw(batch);

        batch.end();

        debugRenderer.render(model.world, camera.combined);
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
        shapeRenderer.dispose();
    }
}
