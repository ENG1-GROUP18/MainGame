package york.eng1.team18.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import york.eng1.team18.LeaderboardHandler;
import york.eng1.team18.Orchestrator;

public class LeaderBoardScreen implements Screen {

    private Orchestrator parent;
    private Stage stage;
    private Skin skin;


    public LeaderBoardScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        stage.setDebugAll(parent.DEBUG_TABLES);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

        // LEADERBOARD HANDLER, STILL WORK IN PROGRESS
        LeaderboardHandler lbHandler = new LeaderboardHandler();
        lbHandler.fillWithScores();

        // Create items
        Label widthPaddingSetter = new Label("", skin);
        Label title = new Label("LEADERBOARD", skin);
        Label pos1text = new Label("1st", skin);
        Label pos2text = new Label("2nd", skin);
        Label pos3text = new Label("3rd", skin);
        Label pos4text = new Label("4th", skin);
        Label pos5text = new Label("5th", skin);
        Label pos1score = new Label(String.valueOf(lbHandler.getScoreAtPos(1)), skin);
        Label pos2score = new Label(String.valueOf(lbHandler.getScoreAtPos(2)), skin);
        Label pos3score = new Label(String.valueOf(lbHandler.getScoreAtPos(3)), skin);
        Label pos4score = new Label(String.valueOf(lbHandler.getScoreAtPos(4)), skin);
        Label pos5score = new Label(String.valueOf(lbHandler.getScoreAtPos(5)), skin);
        TextButton backToMenuBtn = new TextButton("BACK", skin);

        // Add items
        table.defaults().pad(6, 20, 6, 20);
        table.add(widthPaddingSetter).width(600).colspan(2).row();
        table.add(title).colspan(2).pad(20).row();
        table.add(pos1text).left();
        table.add(pos1score).right().space(5).row();
        table.add(pos2text).left();
        table.add(pos2score).right().row();
        table.add(pos3text).left();
        table.add(pos3score).right().row();
        table.add(pos4text).left();
        table.add(pos4score).right().row();
        table.add(pos5text).left();
        table.add(pos5score).right().row();
        table.add(backToMenuBtn).colspan(2).pad(20).height(backToMenuBtn.getHeight()-20).row();

        // BACK BUTTON
        backToMenuBtn.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("back button clicked");
                parent.changeScreen(Orchestrator.MENU);
            }
        });

        Gdx.input.setInputProcessor(stage);
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
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
