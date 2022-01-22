package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class FadeImage extends Image {

    public FadeImage() {
        super(new Texture(Gdx.files.internal("images/almostBlackSquare.png")));
        this.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.setPosition(0,0);
        this.setTouchable(Touchable.disabled);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void fadeIn() {
        // Sets screen cover to fade out slowly
        AlphaAction aa = new AlphaAction();
        aa.setDuration(1);
        aa.setAlpha(0);
        this.addAction(aa);

    }

    public void setAlpha(float a) {
        this.setColor(1, 1, 1, a);
    }

}
