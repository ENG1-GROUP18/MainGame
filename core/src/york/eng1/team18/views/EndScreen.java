package york.eng1.team18.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.actors.FadeImage;

public class EndScreen implements Screen {

    private Orchestrator parent;
    private Stage stage;
    private FadeImage fadeImage;

    public EndScreen(Orchestrator orchestrator){
        this.parent = orchestrator;
        this.stage = new Stage(new ScreenViewport());



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




        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

        // Create button objects
        Label title = new Label("CONGRATULATIONS! YOU WIN", skin);
        TextButton restart = new TextButton("RETURN TO MENU", skin);

        //Add items
        table.add(title).colspan(2).pad(20).row();
        table.add(restart).height(restart.getHeight() -20).row();

        restart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AlphaAction aa = new AlphaAction();
                aa.setDuration(1);
                aa.setAlpha(1);
                RunnableAction ra = new RunnableAction();
                ra.setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        // changes screen
                        parent.changeScreen(Orchestrator.MENU);
                    }
                });
                SequenceAction sa = new SequenceAction(aa, ra);
                fadeImage.addAction(sa);
            }
        });
    }

    @Override
    public void render(float delta) {
        fadeImage.fadeIn();
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
