package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import javax.accessibility.AccessibleRelation;

public class Cannon extends Image {
//    Sprite sprite;
//
//    Pixmap pixmapBlackish = new Pixmap(1,1, Pixmap.Format.RGBA8888);;

    // CANNON PROPERTIES:
    float rateOfTurn;
    float sizeX;
    float sizeY;

    public Cannon(Actor parent) {
        super(new Texture(Gdx.files.internal("images/cannonShape.png")));

        this.setSize(parent.getWidth()/8, parent.getHeight());
        this.setPosition(parent.getWidth()/3, parent.getHeight()/2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Get player pos
        // Get mouse pos

        // Maths

        // Point at mouse



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
