package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.controller.InputController;

import javax.accessibility.AccessibleRelation;
import java.util.ArrayList;

public class Cannon extends Image {

    private Group parent;
    private Player player;
    private float leftFacing;
    private boolean activated;
    private float angleLimitLeft;
    private float angleLimitRight;
    private boolean isInRange;
    private EnemyBase enemyBase;

    private CannonBall ball;

    World world;
    Camera camera;
    Stage stage;


    // CANNON PROPERTIES:
    private float rateOfTurn = 2f; // degrees per act
    private float currentBearing;
    private float targetBearing;
    private float posX;
    private float posY;

    public ArrayList<CannonBall> CannonBalls;
    private Body body;

    private long fireLimitTimer;
    private int balls;
    private float ammoReplenishTimer;
    private float ammoReplenishRate = 2f;
    private InputController inpt;
    private boolean belongsToPlayer;

    public Cannon(Player parent, float posX, float posY, float leftFacing, World world, Camera camera, Stage stage, Body body, InputController inpt){
        this(true, parent, parent, posX, posY, leftFacing, world, camera, stage, body, inpt);
    }

    public Cannon(Boolean belongsToPlayer, Player player, Group parent, float posX, float posY, float leftFacing, World world, Camera camera, Stage stage, Body body, InputController inpt) {
        super(new Texture(Gdx.files.internal("images/cannonShape.png")));
        this.parent = parent;
        this.leftFacing = leftFacing;
        this.belongsToPlayer = belongsToPlayer;
        this.player = player;
        if (!belongsToPlayer){this.enemyBase = (EnemyBase) parent;}

        this.world = world;
        this.camera = camera;
        this.stage = stage;
        this.body = body;
        this.inpt = inpt;

        this.balls = 5;


        this.setSize(parent.getWidth()/10, parent.getHeight()*2/3);
        this.setPosition(posX - this.getWidth()/2, posY - this.getHeight()/3);
        this.setOrigin(this.getWidth()/2, this.getHeight()/3);

        if (leftFacing == 1) {
            this.setRotation(180);

        }
        CannonBalls = new ArrayList<CannonBall>();



    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (belongsToPlayer){playerHandleRotation();}
        else {collegeHandleRotation();
            isInRange = enemyBase.isInRange();
        }
        float angle;


        ammoReplenishTimer += delta;
        if (ammoReplenishTimer > ammoReplenishRate) {
            // Replenish ammo
            ammoReplenishTimer = 0;
            balls+=1;
        }

        /**if(!belongsToPlayer && TimeUtils.timeSinceNanos(fireLimitTimer) > 500000000) {
            angle = this.getRotation()+90;
            CannonBalls.add(new CannonBall(parent, world, camera, body, angle, this, leftFacing));
            fireLimitTimer = TimeUtils.nanoTime();
        }**/

        if (((!belongsToPlayer && isInRange)||(belongsToPlayer && inpt.leftClick)) && activated && TimeUtils.timeSinceNanos(fireLimitTimer) > 500000000 && balls> 0){

            if (leftFacing == 2) {
                angle = this.getRotation() +90;
            } else if (leftFacing == 1){
                angle = this.getRotation() - 90;
            } else{
                angle = this.getRotation() - 90;
            }
            CannonBalls.add(new CannonBall(world, body, angle, this, leftFacing));
            fireLimitTimer = TimeUtils.nanoTime();
            balls-=1; // change variable name
            //ball = new CannonBall(this, this.getOriginX(),this.getOriginY(), world, camera, stage);
            //stage.addActor(ball);

        }

        ArrayList<CannonBall> toRemove = new ArrayList<CannonBall>();
        for (CannonBall cannonBall : CannonBalls){
            cannonBall.act();
            if (cannonBall.remove){
                toRemove.add(cannonBall);
            }
        }
        CannonBalls.removeAll(toRemove);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


    }


    //TODO Repeated Code. just make target argument of function
    private void collegeHandleRotation(){
        float myX = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).x;
        float myY = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).y;

        //gets player coords
        float playerX = player.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).x;
        float playerY = player.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).y;

        // Creates a bearing in degrees
        targetBearing = MathUtils.radiansToDegrees * MathUtils.atan2(playerX - myX, playerY - myY) - (parent.getRotation()%360)+ 180;

        this.activated = true;
        this.rotateTowards(targetBearing, rateOfTurn);
    }

    private void playerHandleRotation() {
        // Get coords of cannon origin in screen coords.
        float myX = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).x;
        float myY = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).y;

        // Get coords of mouse in screen coords
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.input.getY();


        // Creates a bearing in degrees
        targetBearing = MathUtils.radiansToDegrees * MathUtils.atan2(mouseX - myX, mouseY - myY) - (parent.getRotation()%360) + 180;

        // make sure that both parent and cannon have rotation within 0 to 360 degrees.
        if(targetBearing < 0) {
            targetBearing += 360;
            parent.rotateBy(360);
        } else if(targetBearing > 360) {
            targetBearing -= 360;
            parent.rotateBy(-360);
        }

        // Activate cannon on side of mouse pointer
        if (leftFacing == 2) {
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

        if (activated) {
            this.rotateTowards(targetBearing, rateOfTurn);
        }
        else {
            if(leftFacing == 2) {
                this.rotateTowards(0, rateOfTurn/4);
            } else {
                this.rotateTowards(180, rateOfTurn/4);
            }
        }
    }

    private void rotateTowards(float targetAngle, float turnRate) {

        if(Math.abs(targetAngle - this.getRotation()) > 1) {
            if (((targetAngle - this.getRotation() + 540)%360 - 180) > 0) {
                // Clockwise rotation
                this.rotateBy(turnRate);

            } else {
                this.rotateBy(-turnRate);
            }

            // Prevents cannon rotation jumping over target angle, then back again each frame
            if(this.getRotation() >= (targetAngle - turnRate) && this.getRotation() <= (targetAngle + turnRate)){
                this.setRotation(targetAngle);
            }
        }

    }
}
