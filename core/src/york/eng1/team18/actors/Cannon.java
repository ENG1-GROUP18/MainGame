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
    private boolean leftFacing;
    private boolean activated;
    private float angleLimitLeft;
    private float angleLimitRight;


    // CANNON PROPERTIES:
    private float rateOfTurn = 1f; // degrees per act
    private float currentBearing;
    private float targetBearing;
    private float posX;
    private float posY;


    public Cannon(Group parent, float posX, float posY, boolean leftFacing) {
        super(new Texture(Gdx.files.internal("images/cannonShape.png")));
        this.parent = parent;
        this.leftFacing = leftFacing;

        this.setSize(parent.getWidth()/8, parent.getHeight());
        this.setPosition(posX - this.getWidth()/2, posY - this.getHeight()/3);
        this.setOrigin(this.getWidth()/2, this.getHeight()/3);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        handleRotation();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    private void handleRotation() {
        // Get coords of cannon origin in screen coords.
        float myX = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).x;
        float myY = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).y;

        // Get coords of mouse in screen coords
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();

        // Creates a bearing in degrees
        targetBearing = MathUtils.radiansToDegrees * MathUtils.atan2(mouseX - myX, mouseY - myY) - parent.getRotation() + 180;

        // make sure that both parent and cannon have rotation within 0 to 360 degrees.
        if(targetBearing < 0) {
            targetBearing += 360;
            parent.rotateBy(360);
        } else if(targetBearing > 360) {
            targetBearing -= 360;
            parent.rotateBy(-360);
        };

        // Activate cannon on side of mouse pointer
        if (leftFacing) {
            if ((targetBearing > 270 || targetBearing < 90)) {
                this.activated = true;
            } else {
                this.activated = false;
            }
        } else {
            if(targetBearing > 90 && targetBearing < 270) {
                this.activated = true;
            } else {
                this.activated = false;
            }
        }

        System.out.println(targetBearing);

        if (activated) {
            this.rotateTowards(targetBearing);
        }
        else {
            if(leftFacing) {
                this.rotateTowards(0);
            } else {
                this.rotateTowards(180);
            }
        }
    }

    private void rotateTowards(float targetAngle) {

        if (((targetAngle - this.getRotation() + 540)%360 - 180) > 0) {
            // Clockwise rotation
            this.rotateBy(rateOfTurn);
        } else {
            this.rotateBy(-rateOfTurn);
        }
    }

}
