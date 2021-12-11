package york.eng1.team18.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import york.eng1.team18.Orchestrator;


public class MenuScreen implements Screen {

    private Orchestrator parent;
    private Stage stage;

    public MenuScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(parent.DEBUG_TABLES);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


        // temp
        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

        // Create button objects
        TextButton playBtn = new TextButton("PLAY", skin);
        TextButton leaderboardBtn = new TextButton("LEADERBOARD", skin);
        TextButton quitBtn = new TextButton("QUIT", skin);

        // Add button objects to table
        table.add(playBtn).height(playBtn.getHeight() -20).row();
        table.add(leaderboardBtn).height(leaderboardBtn.getHeight()-20).row();
        table.add(quitBtn).height(quitBtn.getHeight()-20).row();


        // PLAY BUTTON
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Orchestrator.APPLICATION);
            }
        });

        // LEADERBOARD BUTTON
        leaderboardBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Orchestrator.LEADERBOARD);
            }
        });

        // QUIT BUTTON
        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(30/255f, 30/255f, 30/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
