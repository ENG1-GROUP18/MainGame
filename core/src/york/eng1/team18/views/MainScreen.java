package york.eng1.team18.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.WorldContactListener;
import york.eng1.team18.actors.*;
import york.eng1.team18.actors.HUD.HUD;
import york.eng1.team18.controller.InputController;
import york.eng1.team18.actors.CannonBall;

import java.util.ArrayList;


public class MainScreen implements Screen {

    // When true, camera is still and zoomed out, used to debug.
    //----------------------------------
    private boolean BOX2D_WIREFRAME = false;
    //----------------------------------

    Color waterCol = new Color(111/255f, 164/255f, 189/255f, 0);

    private static final int mapImageX = 1155;  // height of map image
    private static final int mapImageY = 776;   // width of map image
    private float mapScale = 500f;               // map width in world units
    private float mapAspectRatio = 1.49f;        // Aspect ratio of image used for map
    private float cameraZoom = 60;               // ExtendViewport minimum size in world units
    private Vector2 gameCameraOffset;

    private Orchestrator parent;
    private Stage gameStage;
    private Stage hudStage;
    private HUD hud;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private WaterTrail waterTrail;

    public Player player; // made player a public variable

    InputController inpt;
    OrthographicCamera gameCamera;
    OrthographicCamera hudCamera;
    ExtendViewport gameViewport;


//    ArrayList<CannonBall> cannonBalls = new ArrayList<CannonBall>();
//    float timeSinceLastShot = 0;
//    public CannonBall cann;

    public MainScreen(Orchestrator orchestrator) {
        parent = orchestrator;

        // Set up game camera, viewport, stage, world
        inpt = new InputController();
        Gdx.input.setInputProcessor(inpt);


        gameCameraOffset = new Vector2(0, 15);
        gameCamera = new OrthographicCamera(1, 1);
        hudCamera = new OrthographicCamera();
        gameViewport = new ExtendViewport(cameraZoom, cameraZoom, gameCamera);
        gameStage = new Stage(gameViewport);
        hudStage = new Stage(new ScreenViewport());

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new WorldContactListener(this));



        // Add hud to hud stage
        hud = new HUD(this);
        hudStage.addActor(hud);


        // Add objects to world
        Map map = new Map(world, 800, 800);
        player = new Player(world,inpt, hud, gameStage, gameCamera, map.getSpawnX(), map.getSpawnY());
        College Halifax = new College(world, gameStage, gameCamera, map.getCollegeX("Halifax"), map.getCollegeY("Halifax"), "images/building1.png");
        College Wentworth = new College(world, gameStage, gameCamera, map.getCollegeX("Wentworth"), map.getCollegeY("Wentworth"), "images/building2.png");
        College James = new College(world, gameStage, gameCamera, map.getCollegeX("James"), map.getCollegeY("James"), "images/building4.png");
        College Vanbrugh = new College(world, gameStage, gameCamera, map.getCollegeX("Vanbrugh"), map.getCollegeY("Vanbrugh"), "images/building3.png");
        College Alcuin = new College(world, gameStage, gameCamera, map.getCollegeX("Alcuin"), map.getCollegeY("Alcuin"), "images/building5.png");
        College Derwent = new College(world, gameStage, gameCamera, map.getCollegeX("Derwent"), map.getCollegeY("Derwent"), "images/building6.png");

        map.setName("map");
        player.setName("player");

        waterTrail = new WaterTrail(gameCamera, player);
        gameStage.addActor(map);
        gameStage.addActor(player);
        gameStage.addActor(Halifax);
        gameStage.addActor(Wentworth);
        gameStage.addActor(James);
        gameStage.addActor(Vanbrugh);
        gameStage.addActor(Alcuin);
        gameStage.addActor(Derwent);

        debugRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear buffers
        ScreenUtils.clear(111/255f, 164/255f, 189/255f, 0);

        // Run game logic for each component
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        gameStage.act();
        waterTrail.act();
        hudStage.act();

        // Update camera position
        float myX = player.getX();
        float myY = player.getY();
        if (inpt.space) {
            // Allow player to look towards mouse pos
            Vector2 mousePos = gameStage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            gameCameraOffset = new Vector2(((mousePos.x - myX) / 2), ((mousePos.y - myY) / 2));
        }
        gameCamera.position.x = myX + gameCameraOffset.x;
        gameCamera.position.y = myY + gameCameraOffset.y;


        // Draw game
        gameStage.getViewport().apply();
        waterTrail.draw(); // Uses shape renderer so needs to be drawn before the stage batch begins.
        gameStage.draw();
        debugRenderer.render(world, gameStage.getCamera().combined);

        // Draws box2d hitboxes for debug only
        debugRenderer.render(world, gameStage.getCamera().combined);


        // Draw ui
        hudStage.getViewport().apply();
        hudStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        hudStage.getViewport().update(width, height, true);
        hud.recalculatePos();
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
