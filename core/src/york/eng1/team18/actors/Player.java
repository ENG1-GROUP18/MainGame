package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;

import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.actors.HUD.HUD;

import com.badlogic.gdx.scenes.scene2d.Stage;

import york.eng1.team18.controller.InputController;
import york.eng1.team18.views.MenuScreen;

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
    public float currentSpeed = 0f;
    private float rateOfAcceleration = 0.05f;
    private float rateOfDeceleration = 0.03f;
    private float maxRateOfTurn = 1.8f;
    public int health;
    public int points;
    public int numCollageDestroyed;

    private Boolean inIntro = true; //TODO Change back to true
    private long creationTime;
    private long fireLimitTimer;
    private float ammoReplenishTimer;
    private float ammoReplenishRate = 1f;
    private Orchestrator orchestrator;

    public boolean is_contact = false;
    public String contact_side = "";
    public boolean hit = false;
    public boolean hit_enemyBase = false;
    public boolean hit_collage = false;



    public Player(World world, InputController inpt, HUD hud , Stage stage, Camera camera, float pos_x, float pos_y,Orchestrator orchestrator){

       // Set image, position and world reference
        super();
        this.inpt = inpt;
        this.world = world;
        this.hud = hud;
        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);
        this.orchestrator = orchestrator;

        this.health = 100;
        this.points = 0;
        this.numCollageDestroyed = 0;

        // Create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);
        body.setTransform(pos_x, pos_y, (float)Math.PI/2);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size_x/2, size_y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;
        //Uses contact filtering bits to state what can and the body can and can't interact with
        //So maskBits = 8 means it can collide with other bodies of bit 8,  means it can collide with enemy cannonballs, map, ect
        //And categoryBits = 6 means it will accept other bodies of type 6 for collisions
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

        // Record start times
        creationTime = TimeUtils.millis();
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

        // For intro cutscene
        if (inIntro) {
            float time = TimeUtils.timeSinceMillis(creationTime);
            currentSpeed = 5;

            if (time < 19000) {
                 // Straight ahead

            } else if (time < 23000) {
                // Turn Right
                body.setAngularVelocity(-0.1f);

            } else if(time < 33000) {
                // Turn Left
                body.setAngularVelocity(0.1f);
            }else if(time < 34500){
                 //Straight Ahead
            } else {
                inIntro = false;
                Gdx.input.setInputProcessor(inpt);
            }
        }

        //Gdx.input.setInputProcessor(inpt); //TODO remove this line

        // Update position of box2d body based on updated movement properties.
        float velX = MathUtils.cos(angle) * currentSpeed;
        float velY = MathUtils.sin(angle) * currentSpeed;
        body.setLinearVelocity((velX + body.getLinearVelocity().x)/2f, (velY + body.getLinearVelocity().y)/2f);


        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x - this.getWidth()/2, body.getPosition().y - this.getHeight()/2);


        //Handle getting hit
        if (hit == true){
            health = hud.getHealth();
            hud.changeHealthTo(health - 10);
            hit = false;
            if (this.points >= 10) {
                this.points -= 10;
            }
            hud.setPoints(points);
        }

        //Handle death
        if (health <= 0){
            orchestrator.changeScreen(Orchestrator.GAMEOVER);
        }

        //Handle hitting Collage
        if (hit_collage){
            this.numCollageDestroyed+=1;
            this.points+=100;
            hud.setPoints(points);
            hit_collage = false;
        }

        //Handle hitting Enemy base
        if (hit_enemyBase){
            this.points+=10;
            hud.setPoints(points);
            hit_enemyBase = false;
        }


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

        }

        //pop up
        //at the start
        if (body.getPosition().x > 210f && body.getPosition().x < 212f && body.getPosition().y > 199f && body.getPosition().y < 202f){
            hud.instructionPopup("Use WASD to move, the longer you hold W the faster you accelerate!");
            //popUp_status = true;
        }

        //at first college
        if (body.getPosition().x > 175f && body.getPosition().x < 215f && body.getPosition().y > 220f && body.getPosition().y < 250f){
            hud.instructionPopup("Click the mouse in the direction you want to fire!");
        }

        //Escape key to go back to main menu
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            orchestrator.changeScreen(Orchestrator.MENU);
            orchestrator.dispose();
        }


        super.act(delta);
    }

//    public void setPositionSync(Boolean bool) {
//        // When positionSync is set true, the players position is synced with its body in box2d
//        // When toggled, the box2d body is updated to the actors position.
//        this.positionSynced = bool;
//        body.setTransform(this.getX(), this.getY(), this.getRotation() * MathUtils.degreesToRadians);
//    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}


