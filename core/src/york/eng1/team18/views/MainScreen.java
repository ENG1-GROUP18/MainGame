package york.eng1.team18.views;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
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
import york.eng1.team18.entities.CannonBall;

import java.util.ArrayList;

public class MainScreen implements Screen {
    private Orchestrator parent;

    InputController controller;
    GameModel model;

    OrthographicCamera camera;
    Vector2 camPos;
    ExtendViewport viewport;


    Sprite playerSprite;
    Sprite islandSprite;
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
        model = new GameModel(controller);
        Gdx.input.setInputProcessor(controller);

        camera = new OrthographicCamera(1, 1);
        viewport = new ExtendViewport(100, 100, camera);

        debugRenderer = new Box2DDebugRenderer(true,true,false,true,true,true);

        playerSprite = new Sprite(new Texture(Gdx.files.internal("images/rubber_duck.jpg")));
        playerSprite.setSize(2, 4);
        testMapSprite = new Sprite(new Texture(Gdx.files.internal("paths/UniLake.png")));
        // 200 = image scale when created.
        // 132 = 200 * image height / image width.
        testMapSprite.setSize(200, 132);
        //islandSprite = new Sprite(new Texture(Gdx.files.internal("images/island.jpg")));

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (controller.space){
            if (timeSinceLastShot <= 0){
                cannonBalls.add(new CannonBall(model.playerShip.getPosition().x, model.playerShip.getPosition().y, Math.abs(model.playerShip.getLinearVelocity().x), Math.abs(model.playerShip.getLinearVelocity().y) , model.playerShip.getAngle()));
                //sets how fast they can shoot
                timeSinceLastShot = 50;
            } else{
                timeSinceLastShot-=1;
            }
        }


        model.logicStep(delta);
        ScreenUtils.clear(parent.assMan.waterCol);

        camera.position.x = model.playerShip.getPosition().x;
        camera.position.y = model.playerShip.getPosition().y;
        camera.update();


//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(parent.assMan.landCol2);
//        shapeRenderer.rect(model.islandBox.getPosition().x - 10, model.islandBox.getPosition().y - 10, 20, 20);
//        shapeRenderer.end();


//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        islandSprite.setSize(20, 20);
//        islandSprite.setPosition(model.islandBox.getPosition().x, model.islandBox.getPosition().y);
//        islandSprite.translate(-10, -10);
//        islandSprite.draw(batch);
//        batch.end();

        //update cannonballs
        ArrayList<CannonBall> cannonBallsToRemove = new ArrayList<CannonBall>();
        if (cannonBalls != null){

            for (CannonBall cannonBall : cannonBalls){

                cannonBall.update(delta);
                if (cannonBall.remove){
                    cannonBallsToRemove.add(cannonBall);
                }
            }
            cannonBalls.removeAll(cannonBallsToRemove);


        }




        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        testMapSprite.setPosition(model.lakeBody.getPosition().x, model.lakeBody.getPosition().y);

        playerSprite.setPosition(model.playerShip.getPosition().x, model.playerShip.getPosition().y);
        playerSprite.setOriginCenter();
        playerSprite.setRotation(model.playerShip.getAngle()*57.3f);
        playerSprite.translate(-1, -2);

        testMapSprite.draw(batch);
        playerSprite.draw(batch);


        batch.end();

        batch2 = new SpriteBatch();
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
