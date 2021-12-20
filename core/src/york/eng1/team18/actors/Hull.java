package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class Hull extends Image {

    Group parent;

    public Hull(Group parent) {
        super(new Texture(Gdx.files.internal("images/debugBoat.png")));
        this.parent = parent;
        this.setSize(parent.getWidth(), parent.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        batch.begin();

        super.draw(batch, parentAlpha);
    }
}
