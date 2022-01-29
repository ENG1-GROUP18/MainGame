package york.eng1.team18.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.WorldContactListener;
import york.eng1.team18.actors.*;
import york.eng1.team18.actors.HUD.HUD;
import york.eng1.team18.controller.InputController;


public class MainScreen implements Screen {

    // When true, camera is still and zoomed out, used to debug.
    //----------------------------------
    private boolean BOX2D_WIREFRAME = false;
    //----------------------------------

    Color waterCol = new Color(111/255f, 164/255f, 189/255f, 0);

    private FadeImage fadeImage;
    private Map map;
    private Sprite mapFogImage;
    private static final int mapImageX = 1155;  // height of map image
    private static final int mapImageY = 776;   // width of map image
    private float mapSize = 1000f;               // map width in world units
    private float mapAspectRatio = 1.49f;       // Aspect ratio of image used for map
    private float cameraZoom = 80;             // ExtendViewport minimum size in world units
    private Vector2 gameCameraOffset;

    private Orchestrator parent;
    private Stage gameStage;
    private Stage hudStage;
    private HUD hud;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private WaterTrail waterTrail;

    public Player player; // made player a public variable
    private Array<Body> tempBodies = new Array<Body>();

    SpriteBatch batch;
    InputController inpt;
    OrthographicCamera gameCamera;
    OrthographicCamera hudCamera;
    ExtendViewport gameViewport;



    public MainScreen(Orchestrator orchestrator) {
        parent = orchestrator;

        // Set up game camera, viewport, stage, world
        inpt = new InputController();
        Gdx.input.setInputProcessor(null); // disables inputs for intro cutscene


        gameCameraOffset = new Vector2(0, 15);
        gameCamera = new OrthographicCamera(1, 1);
        hudCamera = new OrthographicCamera();
        gameViewport = new ExtendViewport(cameraZoom, cameraZoom, gameCamera);
        gameStage = new Stage(gameViewport);
        hudStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),hudCamera));
        batch = new SpriteBatch();

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new WorldContactListener(this));


        // Add hud to hud stage
        hud = new HUD(this, player, mapSize);

        hudStage.addActor(hud);


        // Add objects to world
        fadeImage = new FadeImage();

        hudStage.addActor(fadeImage);
        fadeImage.setName("fadeImage");

        map = new Map(world, mapSize, mapSize);
        mapFogImage = new Sprite(new Texture("images/Map/mapFog.png"));
        mapFogImage.setOrigin(0, 0);
        mapFogImage.setScale(0.2f);

        player = new Player(world,inpt, hud, gameStage, gameCamera, map.getSpawnX(), map.getSpawnY());
        hud.setPlayer(player);

        map.setName("map");
        player.setName("player");

        waterTrail = new WaterTrail(gameCamera, player);

        //gameStage.addActor(mapImage);
        //gameStage.addActor(map);
        gameStage.addActor(player);


        //TODO colleges are Group objects but seem to just be an image???? And the cannon is the enemy base? this is such a bad way if implementing, needs changing.
        // Entire point of a group is that you can add child actors. College could be a group that is parent to cannons and an image.
        College Halifax = new College(world, gameStage, gameCamera, map.getCollegeX(0), map.getCollegeY(0), "images/building1.png");
        College Wentworth = new College(world, gameStage, gameCamera, map.getCollegeX(1), map.getCollegeY(1), "images/building2.png");
        College James = new College(world, gameStage, gameCamera, map.getCollegeX(2), map.getCollegeY(2), "images/building4.png");
        College Vanbrugh = new College(world, gameStage, gameCamera, map.getCollegeX(3), map.getCollegeY(3), "images/building3.png");
        College Alcuin = new College(world, gameStage, gameCamera, map.getCollegeX(4), map.getCollegeY(4), "images/building5.png");
        College Derwent = new College(world, gameStage, gameCamera, map.getCollegeX(5), map.getCollegeY(5), "images/building6.png");
        EnemyBase HalifaxCannon0 = new EnemyBase(0.03f, map, inpt, Halifax, player, world, gameStage, gameCamera, map.getBaseX(0,0), map.getBaseY(0,0));
        EnemyBase WentworthCannon0 = new EnemyBase(0.04f, map, inpt, Wentworth, player, world, gameStage, gameCamera, map.getBaseX(1,0), map.getBaseY(1,0));
        EnemyBase WentworthCannon1 = new EnemyBase(0.04f, map, inpt, Wentworth, player, world, gameStage, gameCamera, map.getBaseX(1,1), map.getBaseY(1,1));
        EnemyBase JamesCannon0 = new EnemyBase(0.04f, map, inpt, James, player, world, gameStage, gameCamera, map.getBaseX(2,0), map.getBaseY(2,0));
        EnemyBase JamesCannon1 = new EnemyBase(0.04f, map, inpt, James, player, world, gameStage, gameCamera, map.getBaseX(2,1), map.getBaseY(2,1));
        EnemyBase VanbrughCannon0 = new EnemyBase(0.04f, map, inpt, Vanbrugh, player, world, gameStage, gameCamera, map.getBaseX(3,0), map.getBaseY(3,0));
        EnemyBase VanbrughCannon1 = new EnemyBase(0.04f, map, inpt, Vanbrugh, player, world, gameStage, gameCamera, map.getBaseX(3,1), map.getBaseY(3,1));
        EnemyBase AlcuinCannon0 = new EnemyBase(0.04f, map, inpt, Alcuin, player, world, gameStage, gameCamera, map.getBaseX(4,0), map.getBaseY(4,0));
        EnemyBase AlcuinCannon1 = new EnemyBase(0.04f, map, inpt, Alcuin, player, world, gameStage, gameCamera, map.getBaseX(4,1), map.getBaseY(4,1));
        EnemyBase DerwentCannon0 = new EnemyBase(0.04f, map, inpt, Derwent, player, world, gameStage, gameCamera, map.getBaseX(5,0), map.getBaseY(5,0));
        EnemyBase DerwentCannon1 = new EnemyBase(0.04f, map, inpt, Derwent, player, world, gameStage, gameCamera, map.getBaseX(5,1), map.getBaseY(5,1));


        debugRenderer = new Box2DDebugRenderer(BOX2D_WIREFRAME, false, false, false, BOX2D_WIREFRAME, BOX2D_WIREFRAME);

//        // Runnable used to run code after animation finished
//        RunnableAction ra = new RunnableAction();
//        ra.setRunnable(new Runnable() {
//            @Override
//            public void run() {
//                // changes screen
//                player.setPositionSync(true);
//            }
//        });
//
//        MoveByAction mba = new MoveByAction();
//        mba.setDuration(15);
//        mba.setAmount(0,80);
//
//        SequenceAction sa = new SequenceAction(mba, ra);
//        player.addAction(sa);





    }

    @Override
    public void show() {
        fadeImage.setAlpha(1);
        fadeImage.fadeIn();
    }


    @Override
    public void render(float delta) {
        // Clear buffers
        ScreenUtils.clear(247/255f, 248/255f, 247/255f, 0);

        // Run game logic for each component
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        gameStage.act();
        waterTrail.act();
        hudStage.act();

        // Update camera position
        float myX = player.getX();
        float myY = player.getY();
        if (inpt.space) {
            // Allow player to look towards mouse position
            Vector2 mousePos = gameStage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            gameCameraOffset = new Vector2(((mousePos.x - myX) / 2), ((mousePos.y - myY) / 2));
        }
        gameCamera.position.x = myX + gameCameraOffset.x;
        gameCamera.position.y = myY + gameCameraOffset.y;

        // Update HUD
        hud.updatePointer();

        //TODO Tidy this mess up

        //debugRenderer.render(world, gameStage.getCamera().combined); // USE FOR DEBUG

        batch.setProjectionMatrix(gameStage.getCamera().combined);

        // Draw map base
        batch.begin();
        map.draw(batch, 1);
        batch.end();

        // Draw water trail
        gameStage.getViewport().apply(); // cant remember why this was here, doesnt seem to break anything when removed though
        waterTrail.draw();

        // Draw game objects
        gameStage.draw();

        //Draw cannonBalls
        batch.begin();
        world.getBodies(tempBodies);
        for (Body body : tempBodies) {
            if(body.getUserData() != null && body.getUserData() instanceof Sprite){
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x- (sprite.getHeight()/2),body.getPosition().y-(sprite.getWidth()/2));
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        batch.end();


        // Draw map fog
        batch.begin();
        mapFogImage.draw(batch);
        batch.end();

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
        hudStage.getViewport().apply();
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
