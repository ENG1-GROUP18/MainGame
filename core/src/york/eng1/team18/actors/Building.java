package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class Building extends Image {

    Group parent;

    public Building(Group parent, String imagePath) {
        super(new Texture(Gdx.files.internal(imagePath)));
        this.parent = parent;
        this.setSize(parent.getWidth(), parent.getHeight());
    }

    public void delete(){
        this.clear();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
