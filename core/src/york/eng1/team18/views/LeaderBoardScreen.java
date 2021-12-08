package york.eng1.team18.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import york.eng1.team18.Orchestrator;

public class LeaderBoardScreen implements Screen {

    private Orchestrator parent;
    private Stage stage;

    TextButton backToMenuBtn;


    public LeaderBoardScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        stage = new Stage(new ScreenViewport());

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

        backToMenuBtn = new TextButton("BACK", skin);
        table.add(backToMenuBtn).row();

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        // LEADERBOARD BUTTON
//        backToMenuBtn.addListener(new ClickListener() {
//            @Override
//            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
//                super.enter(event, x, y, pointer, fromActor);
//            }
//        });
//
//        backToMenuBtn.addListener(new ClickListener() {
//            @Override
//            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
//                super.exit(event, x, y, pointer, toActor);
//            }
//        });

        backToMenuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Back button clicked");
                parent.changeScreen(Orchestrator.MENU);
                dispose();
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
