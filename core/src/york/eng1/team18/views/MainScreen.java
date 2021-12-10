package york.eng1.team18.views;

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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import york.eng1.team18.GameModel;
import york.eng1.team18.Orchestrator;

public class MainScreen implements Screen {
    private Orchestrator parent;

    Sprite sprite;
    Batch batch;
    GameModel model;
    OrthographicCamera cam;
    Box2DDebugRenderer debugRenderer;

    public MainScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        sprite = new Sprite(new Texture(Gdx.files.internal("logo330w.png")));
        batch = new SpriteBatch();
        sprite.setPosition(Gdx.graphics.getWidth()/ 2f, Gdx.graphics.getHeight()/ 2f);

        Gdx.input.setInputProcessor(null);
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);


        model = new GameModel();
        cam = new OrthographicCamera(32,24);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        // 122, 180, 196
        Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(model.world, cam.combined);
    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
    }
}
