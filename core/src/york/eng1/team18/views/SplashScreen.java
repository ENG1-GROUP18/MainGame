package york.eng1.team18.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.Orchestrator;

public class SplashScreen implements Screen {

    private Orchestrator parent;

    private Sprite logoSprite;
    private SpriteBatch batch;

    private long startTime;
    private float logoAlpha = 0f;

    public SplashScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        batch = new SpriteBatch();
        logoSprite = new Sprite(new Texture(Gdx.files.internal("images/logo330w.png")));

        logoSprite.setPosition((Gdx.graphics.getWidth() - logoSprite.getWidth())/2f,
                (Gdx.graphics.getHeight() - logoSprite.getHeight())/2f);


    }

    @Override
    public void show() {
        startTime = TimeUtils.millis();
        logoAlpha = 0f;
    }

    @Override
    public void render(float delta) {

        if(TimeUtils.timeSinceMillis(startTime) < 1000) {
            // wait

        } else if(TimeUtils.timeSinceMillis(startTime) < 3000) {
            // fade in
            logoAlpha += 0.01;
            if(logoAlpha >= 1) { logoAlpha = 1;}

        } else if(TimeUtils.timeSinceMillis(startTime) < 5000) {
            // fade out
            logoAlpha -= 0.01;
            if(logoAlpha <= 0) { logoAlpha = 0;}

        } else {
            // dispose screen
            dispose();
            parent.changeScreen(Orchestrator.MENU);

            return;
        }

        Gdx.gl.glClearColor(30/255f, 30/255f, 30/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        logoSprite.draw(batch, logoAlpha);
        batch.end();
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
