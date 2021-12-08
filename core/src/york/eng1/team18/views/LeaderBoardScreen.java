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
    private Skin skin;

    TextButton backToMenuBtn;


    public LeaderBoardScreen(Orchestrator orchestrator) {
        parent = orchestrator;
        stage = new Stage(new ScreenViewport());

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

        backToMenuBtn = new TextButton("BACK", skin);
        table.add(backToMenuBtn).row();

        addListeners();

    }

    @Override
    public void show() {
        System.out.println("SHOW RUN");
        Gdx.input.setInputProcessor(stage);


        backToMenuBtn.setStyle(backToMenuBtn.getStyle());
//        if(backToMenuBtn.isOver()) {
//            System.out.println("IS OVER");
//            InputEvent event = new InputEvent();
//            event.setType(InputEvent.Type.enter);
//            event.setPointer(-1);
//            backToMenuBtn.fire(event);
//            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
//
//        }


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

    private void addListeners(){
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
                hide();
            }
        });
    }
}
