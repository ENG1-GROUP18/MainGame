package york.eng1.team18.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.actors.HUD.HUD;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import york.eng1.team18.Orchestrator;

import york.eng1.team18.controller.InputController;

public class Player extends Group {

    private InputController inpt;
    private World world;
    private HUD hud;
    private Body body;
    private WaterTrail waterTrail;

    // PLAYER PROPERTIES:
    private float size_x = 6;
    private float size_y = 3;
    private float maxSpeed = 16;
    private float maxReverseSpeed = -8f;
    public float currentSpeed = 0f; // changed to public
    private float rateOfAcceleration = 0.05f;
    private float rateOfDeceleration = 0.03f;
    private float maxRateOfTurn = 1.8f;

    private long fireLimitTimer;
    private float ammoReplenishTimer;
    private float ammoReplenishRate = 1f;

    public boolean is_contact = false;
    public String contact_side = "";


    public Player(World world, InputController inpt, HUD hud , Stage stage, Camera camera, float pos_x, float pos_y){

       // Set image, position and world reference
        super();
        this.inpt = inpt;
        this.world = world;
        this.hud = hud;
        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);

        // Create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);
        body.setTransform(pos_x, pos_y, (float)-Math.PI);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size_x/2, size_y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.maskBits = 0x0008;
        fixtureDef.filter.categoryBits = 0x0006;
        body.createFixture(fixtureDef).setUserData("Player");


        //creating collision detectors around player
        //Top
        FixtureDef fdef = new FixtureDef();
        PolygonShape top = new PolygonShape();
        top.setAsBox(size_x/2 , size_y/2  -1, new Vector2(0.5f,0) , 0);
        fdef.shape = top;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("top");

        //bottom
        FixtureDef fdef3 = new FixtureDef();
        PolygonShape bottom = new PolygonShape();
        bottom.setAsBox(size_x/2 , size_y/2 -1, new Vector2(-0.5f,0) , 0);
        fdef3.shape = bottom;
        fdef3.isSensor = true;
        body.createFixture(fdef3).setUserData("bottom");

        // Dispose shapes used to create fixtures
        top.dispose();
        bottom.dispose();
        shape.dispose();

        // Add components to player
        this.addActor(new Hull(this));
        this.addActor(new Cannon(this, this.getWidth()*2/5, this.getHeight()/4, 1 , world, camera, stage, body,inpt));
        this.addActor(new Cannon(this, this.getWidth()*2/5, this.getHeight()*3/4, 2, world, camera, stage, body,inpt));

        // For rotation around center
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);

        // Record start time
        fireLimitTimer = TimeUtils.nanoTime();
    }

    @Override
    public void act(float delta) {
        float angle = body.getAngle();
        float rateOfTurn = maxRateOfTurn * (currentSpeed / maxSpeed);

        // Handle player input, update movement properties
        if (inpt.forward && !inpt.backward && contact_side != "top") {
            // Accelerate forward
            currentSpeed = Math.min(currentSpeed + rateOfAcceleration, maxSpeed);

        }else if (inpt.backward && !inpt.forward && contact_side != "bottom") {
            // Accelerate backward
            currentSpeed = Math.max(currentSpeed - rateOfAcceleration, maxReverseSpeed);
        } else {
            // If no player forward/backward input, decelerate against direction of movement
            if (currentSpeed < 0) {
                currentSpeed = Math.min(currentSpeed + rateOfDeceleration, 0);
            } else {
                currentSpeed = Math.max(currentSpeed - rateOfDeceleration, 0);
            }
        }
        if (inpt.left && !inpt.right) {
            // Turn right
            body.setAngularVelocity(rateOfTurn);
        } else if (inpt.right && !inpt.left) {
            // Turn left
            body.setAngularVelocity(-rateOfTurn);
        } else {
            // No turn
            body.setAngularVelocity(0f);
        }

        // Update position of box2d body based on updated movement properties.
        float velX = MathUtils.cos(angle) * currentSpeed;
        float velY = MathUtils.sin(angle) * currentSpeed;
        body.setLinearVelocity((velX + body.getLinearVelocity().x)/2f, (velY + body.getLinearVelocity().y)/2f);


        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x - this.getWidth()/2,
                body.getPosition().y - this.getHeight()/2);

        // Handle cannon firing

        ammoReplenishTimer += delta;
        if (ammoReplenishTimer > ammoReplenishRate) {
            // Replenish ammo
            hud.increaseCannonTicks();
            // Reset Replenish Timer
            ammoReplenishTimer = 0;
        }

        // Fire on mouse click
        if (TimeUtils.timeSinceNanos(fireLimitTimer) > 500000000) {
            if (inpt.leftClick) {
                hud.decreaseCannonTicks();
                fireLimitTimer = TimeUtils.nanoTime();
                ammoReplenishTimer = 0;
            }

            if (inpt.rightClick) {
                hud.increaseCannonTicks();
                fireLimitTimer = TimeUtils.nanoTime();
            }
        }




        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}


