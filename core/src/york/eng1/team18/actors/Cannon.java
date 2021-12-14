package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import javax.accessibility.AccessibleRelation;

public class Cannon extends Image {
//    Sprite sprite;
//
//    Pixmap pixmapBlackish = new Pixmap(1,1, Pixmap.Format.RGBA8888);;

    Group parent;

    // CANNON PROPERTIES:
    float rateOfTurn;
    float sizeX;
    float sizeY;

    public Cannon(Group parent) {
        super(new Texture(Gdx.files.internal("images/cannonShape.png")));
        this.parent = parent;

        this.setSize(parent.getWidth()/8, parent.getHeight());
        this.setPosition(parent.getWidth()/3, parent.getHeight()/2);
        this.setOrigin(this.getWidth()/2, this.getHeight()/3);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Get bearing from cannon origin to mouse position

        // Get coords of cannon in stage coords.
        float myX = this.localToScreenCoordinates(new Vector2(0,0)).x;
        float myY = this.localToScreenCoordinates(new Vector2(0,0)).y;

        // Get coords of cursor in stage coords
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        // Creates a bearing in degrees, addition at end accounts for the direction the element faces in image source
        float bearingToMouse = MathUtils.radiansToDegrees * MathUtils.atan2(mouseX - myX, mouseY - myY) - parent.getRotation() + 180;
        // Stops the cannon spinning when the mouse hovers over it
        if ((myX <= mouseX + 10 && myX > mouseX -10) && (myY <= mouseY + 10 && myY > mouseY -10)){}
        else{
            this.setRotation(bearingToMouse);
        }


        // TODO tidy this code ^^



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
