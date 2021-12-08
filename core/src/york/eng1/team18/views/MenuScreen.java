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

    Label title;
    TextButton playBtn;
    TextButton leaderboardBtn;
    TextButton quitBtn;
    Cursor cursorPointer;
    Cursor cursorHand;


    public MenuScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        cursorPointer = Gdx.graphics.newCursor(
                new Pixmap(Gdx.files.internal("cursorPointer.png")), 0, 0);
        cursorHand = Gdx.graphics.newCursor(
                new Pixmap(Gdx.files.internal("cursorHand.png")), 7, 0);


        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

//        title = new Label("TEAM-18", skin);
//        title.setAlignment(Align.center);

        playBtn = new TextButton("PLAY", skin);
        leaderboardBtn = new TextButton("LEADERBOARD", skin);
        quitBtn = new TextButton("QUIT", skin);

//        table.add(title).height(60).padBottom(50).row();
        table.add(playBtn).size(playBtn.getWidth() + 60, playBtn.getHeight() -20).row();
        table.add(leaderboardBtn).size(leaderboardBtn.getWidth() + 60, leaderboardBtn.getHeight()-20).row();
        table.add(quitBtn).size(quitBtn.getWidth() + 60, quitBtn.getHeight()-20).row();


//        table.add(newGame).width(newGame.getWidth() + 40).height(80).fillX().pad(10);
//        table.add(preferences).width(preferences.getWidth() + 40).height(80).fillX().pad(10);
//        table.add(exit).fillX().width(exit.getWidth() + 40).height(80).fillX().pad(10);

    }

    @Override
    public void show() {
        playBtn.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                super.enter(event, x, y, pointer, fromActor);
            }
        });

        playBtn.addListener(new ClickListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                super.exit(event, x, y, pointer, toActor);
            }
        });

        leaderboardBtn.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                super.enter(event, x, y, pointer, fromActor);
            }
        });

        leaderboardBtn.addListener(new ClickListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                super.exit(event, x, y, pointer, toActor);
            }
        });

        quitBtn.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                super.enter(event, x, y, pointer, fromActor);
            }
        });

        quitBtn.addListener(new ClickListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                super.exit(event, x, y, pointer, toActor);
            }
        });

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
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
