package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import javax.accessibility.AccessibleRelation;

public class Cannon extends Image {
//    Sprite sprite;
//
//    Pixmap pixmapBlackish = new Pixmap(1,1, Pixmap.Format.RGBA8888);;

    private Group parent;

    // CANNON PROPERTIES:
    private float rateOfTurn = 3f; // in radians
    private float currentBearing;
    private float targetBearing;
    private float posX;
    private float posY;


    public Cannon(Group parent, float posX, float posY) {
        super(new Texture(Gdx.files.internal("images/cannonShape.png")));
        this.parent = parent;


        this.setSize(parent.getWidth()/8, parent.getHeight());
        this.setPosition(posX - this.getWidth()/2, posY - this.getHeight()/3);
        this.setOrigin(this.getWidth()/2, this.getHeight()/3);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Get coords of cannon origin in screen coords.
        float myX = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).x;
        float myY = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).y;

        // Get coords of mouse in screen coords
        float mouseX = Gdx.input.getX() ;
        float mouseY = Gdx.input.getY();

        // Creates a bearing in degrees, addition at end accounts for the direction the element faces in image source
        targetBearing = MathUtils.radiansToDegrees * MathUtils.atan2(mouseX - myX, mouseY - myY) - parent.getRotation() + 180;




        // TODO this is all a work in progress, broken af though so will comment out and fix soon
//        // Make cannon rotate the shortest distance to target direction
//        float tmp1 = targetBearing - currentBearing;
//        float tmp2 = targetBearing - currentBearing + 360;
//        float tmp3 = targetBearing - currentBearing - 360;
//
//        if (tmp1 < tmp2 && tmp2 < tmp3) {
//
//
//        }
//
//
//        // Take smallest of above values, if its positive rotate clockwise
//        if (Math.min(Math.min(Math.abs(tmp1), Math.abs(tmp2)), Math.abs(tmp3)) > 0){
//            // Rotate clockwise
//            currentBearing += rateOfTurn;
//            currentBearing = currentBearing % 360;
//            System.out.println(targetBearing);
//            System.out.println(currentBearing);
//            System.out.println("---");
//
//        } else {
//            //Rotate anti-clockwise
//            currentBearing -= rateOfTurn;
//            currentBearing = (currentBearing +360) % 360;
//        }



        // Rotate cannon
        this.setRotation(targetBearing);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
