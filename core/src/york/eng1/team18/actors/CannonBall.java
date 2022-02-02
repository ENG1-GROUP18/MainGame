package york.eng1.team18.actors;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;



public class CannonBall{

    public final static int speed = 50;
    private static Sprite sprite;

    World world;
    Camera camera;
    Cannon parent;
    float angle;
    private Body body;

    public boolean remove = false;

    float x,y;

    float vel_x =0;
    float vel_y =0;

    /**
     * Creates a new CannonBall object depending on the inputted variables
     * @param world a World object which holds the Box2D body's in the game
     * @param body_player the object Body of the player ship
     * @param angle the angle of the cannon at the time of the shot
     * @param parent the Cannon object
     * @param leftFacing states which side the cannon is on, or if it not on the ship
     */

    public CannonBall(World world, Body body_player, float angle, Cannon parent, float leftFacing){
        // Creates the sprite
        sprite = new Sprite(new Texture("images/8x8ball.png"));
        sprite.setScale(0.1f);
        sprite.setOrigin(sprite.getHeight()/2, sprite.getWidth()/2);

        //Gets the coords of the cannon
        this.x = parent.localToStageCoordinates(new Vector2(parent.getOriginX(), parent.getOriginY())).x;
        this.y = parent.localToStageCoordinates(new Vector2(parent.getOriginX(), parent.getOriginY())).y;

        this.world = world;

        //Creates the body for the cannonball
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/2, y/2);
        body = world.createBody(bodyDef);
        body.setTransform(x, y, angle);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        //Creates the fixture for the cannonball
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

        //Sets what the cannonball can collide withe depending on if it's attached to the player, or it's an enemy's
        if (leftFacing != 0){
            fixtureDef.filter.maskBits = 0x0002;
            fixtureDef.filter.categoryBits = 0x0004;
        }else{
            fixtureDef.filter.maskBits = 0x0006;
            fixtureDef.filter.categoryBits = 0x0008;
        }

        //Sets name to fixture and attaches the sprite to the body
        body.createFixture(fixtureDef).setUserData("CannonBall");
        body.setUserData(sprite);

        //Calculates the angle of the cannons by adding the angle of the
        float angle_x = (float)Math.cos( body_player.getAngle()+ Math.toRadians(angle));
        float angle_y = (float)Math.sin( body_player.getAngle()+ Math.toRadians(angle));


        if (leftFacing == 2){ // If cannon on the right side of the ship

            vel_x = (Math.abs (body_player.getLinearVelocity().x) + speed) * (angle_x);
            vel_y = (Math.abs (body_player.getLinearVelocity().y) + speed) * (angle_y); //Changed "Magic numbers" to speed"

        }else if (leftFacing == 1){ // If cannon on the left side of the ship

            vel_x = (Math.abs (body_player.getLinearVelocity().x) + speed) * -(angle_x);
            vel_y = (Math.abs (body_player.getLinearVelocity().y) + speed) * -(angle_y);

        } else{     //if the cannon is not on the player
            angle_x = (float)Math.cos(Math.toRadians(angle));
            angle_y = (float)Math.sin(Math.toRadians(angle));
            vel_x = (75) * (angle_x ); // adjust the value to reduce or increase speed of cannonballs
            vel_y = (75) * (angle_y);
        }


        //Sets velocity and rate of deceleration for cannonball
        body.setLinearVelocity(vel_x,vel_y);
        body.setLinearDamping(1);

    }


    // The function determines if the cannonball has stopped so can then destroy it
    public void act() {
        float x_vel = body.getLinearVelocity().x;
        float y_vel = body.getLinearVelocity().y;
        if (x_vel < 2 && x_vel > -2 && y_vel< 2 && y_vel > -2){
            remove = true;
            world.destroyBody(body);
        }

    }


}