package york.eng1.team18.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.actors.FadeImage;


public class MenuScreen implements Screen {

    private Orchestrator parent;
    private Stage stage;
    private FadeImage fadeImage;

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

        fadeImage = new FadeImage();
        stage.addActor(fadeImage);
        fadeImage.setAlpha(1);

        // temp
        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

        // Create button objects
        TextButton playBtn = new TextButton("PLAY", skin);
        TextButton leaderboardBtn = new TextButton("LEADERBOARD", skin);
        final TextButton fullScrn = new TextButton("SET FULLSCREEN",skin);
        if (Gdx.graphics.isFullscreen()){
            fullScrn.setText("SET WINDOWED");
        }

        TextButton quitBtn = new TextButton("QUIT", skin);

        // Add button objects to table
        table.add(playBtn).height(playBtn.getHeight() -20).row();
        table.add(leaderboardBtn).height(leaderboardBtn.getHeight()-20).row();
        table.add(fullScrn).height(leaderboardBtn.getHeight()-20).row();
        table.add(quitBtn).height(fullScrn.getHeight()-20).row();


        // PLAY BUTTON
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                // fades screen out
                AlphaAction aa = new AlphaAction();
                aa.setDuration(1);
                aa.setAlpha(1);

                // Runnable used to run code after animation finished
                RunnableAction ra = new RunnableAction();
                ra.setRunnable(new Runnable() {
                   @Override
                   public void run() {
                      // changes screen
                      parent.changeScreen(Orchestrator.APPLICATION);
                   }
                });

                SequenceAction sa = new SequenceAction(aa, ra);
                fadeImage.addAction(sa);
            }
        });

        // LEADERBOARD BUTTON
        leaderboardBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Orchestrator.LEADERBOARD);
            }
        });

        //TOGGLE FULLSCREEN
        fullScrn.addListener((new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Boolean fullScreen = Gdx.graphics.isFullscreen();
                Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
                if (fullScreen == true) {
                    fullScrn.setText("SET FULLSCREEN");
                    Gdx.graphics.setWindowedMode(1280, 720);

                }else{
                    fullScrn.setText("SET WINDOWED");
                    Gdx.graphics.setFullscreenMode(currentMode);}

            }
        }));

        // QUIT BUTTON
        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        fadeImage.fadeIn();
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
        fadeImage.resize();
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
