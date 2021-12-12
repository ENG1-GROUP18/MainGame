package york.eng1.team18.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import york.eng1.team18.GameModel;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.controller.InputController;
import york.eng1.team18.entities.CannonBall;
import java.util.ArrayList;

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
    Sprite cannonBallSprite;

    SpriteBatch batch;
    SpriteBatch batch2;
    ShapeRenderer shapeRenderer;
    Box2DDebugRenderer debugRenderer;

    ArrayList<CannonBall> cannonBalls = new ArrayList<CannonBall>();
    float timeSinceLastShot = 0;
    public CannonBall cann;


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

        debugRenderer = new Box2DDebugRenderer(true,true,false,true,true,true);

        playerSprite = new Sprite(new Texture(Gdx.files.internal("images/rubber_duck.jpg")));
        playerSprite.setSize(playerWidth, playerHeight);
        testMapSprite = new Sprite(new Texture(Gdx.files.internal("paths/UniLake.jpg")));
        testMapSprite.setSize(mapScale, mapScale * (mapImageY / (float)mapImageX));

        batch = new SpriteBatch();
        batch2 = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timeSinceLastShot -=1;
        if (controller.space){

            if (timeSinceLastShot <= 0){
                cannonBalls.add(new CannonBall(model.playerBody.getPosition().x, model.playerBody.getPosition().y, Math.abs(model.playerBody.getLinearVelocity().x), Math.abs(model.playerBody.getLinearVelocity().y) , model.playerBody.getAngle()));
                //sets how fast they can shoot

                timeSinceLastShot = 50;
            }
        }


        model.logicStep(delta);
        ScreenUtils.clear(parent.assMan.waterCol);

        // Update camera position
        if (CAMERA_FOLLOWS) {
            camera.position.x = model.playerBody.getPosition().x;
            camera.position.y = model.playerBody.getPosition().y;
        }
        camera.update();

        //update cannonballs
        ArrayList<CannonBall> cannonBallsToRemove = new ArrayList<CannonBall>();
        if (cannonBalls != null){

            for (CannonBall cannonBall : cannonBalls){

                cannonBall.update(delta);
                if (cannonBall.remove){
                    System.out.println(cannonBall);
                    cannonBallsToRemove.add(cannonBall);
                }
            }
            cannonBalls.removeAll(cannonBallsToRemove);


        }




        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        testMapSprite.setPosition(model.lakeBody.getPosition().x, model.lakeBody.getPosition().y);

        playerSprite.setPosition(model.playerBody.getPosition().x, model.playerBody.getPosition().y);
        playerSprite.setOriginCenter();
        playerSprite.setRotation(model.playerBody.getAngle()*57.3f);
        playerSprite.translate(-1, -2);

        testMapSprite.draw(batch);
        playerSprite.draw(batch);


        batch.end();


        batch2.setProjectionMatrix(camera.combined);
        batch2.begin();
        if (cannonBalls != null) {
            for (CannonBall cannonBall : cannonBalls) {

                //System.out.println(model.playerShip.getPosition().x);
                cannonBall.render(batch2);
            }
        }
        batch2.end();


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
