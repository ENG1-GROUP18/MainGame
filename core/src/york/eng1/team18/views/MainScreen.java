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
    private Orchestrator parent;
    ShapeRenderer shapeRenderer;
    InputController controller;
    GameModel model;
    OrthographicCamera camera;
    ExtendViewport viewport;
    Box2DDebugRenderer debugRenderer;
    Sprite playerSprite;
    Sprite islandSprite;
    SpriteBatch batch;


    public MainScreen(Orchestrator orchestrator) {
        parent = orchestrator;

        controller = new InputController();
        model = new GameModel(controller);
        Gdx.input.setInputProcessor(controller);

        camera = new OrthographicCamera(1, 1);
        viewport = new ExtendViewport(50, 50, camera);

        debugRenderer = new Box2DDebugRenderer(false,false,false,true,false,false);
        shapeRenderer = new ShapeRenderer();

        playerSprite = new Sprite(new Texture(Gdx.files.internal("images/rubber_duck.jpg")));
        islandSprite = new Sprite(new Texture(Gdx.files.internal("images/island.jpg")));
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        ScreenUtils.clear(parent.assMan.waterCol);
        debugRenderer.render(model.world, camera.combined);

//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(parent.assMan.landCol2);
//        shapeRenderer.rect(model.islandBox.getPosition().x - 10, model.islandBox.getPosition().y - 10, 20, 20);
//        shapeRenderer.end();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        islandSprite.setSize(20, 20);
        islandSprite.setPosition(model.islandBox.getPosition().x, model.islandBox.getPosition().y);
        islandSprite.translate(-10, -10);
        islandSprite.draw(batch);
        batch.end();




        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        playerSprite.setSize(2, 4);
        playerSprite.setPosition(model.playerShip.getPosition().x, model.playerShip.getPosition().y);
        playerSprite.setOriginCenter();
        playerSprite.setRotation(model.playerShip.getAngle()*57.3f);
        playerSprite.translate(-1, -2);
        playerSprite.draw(batch);
        batch.end();
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
