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

    private final InputController inpt;
    private World world;
    private HUD hud;
    private Body body;

    // PLAYER PROPERTIES:
    private float size_x = 6;
    private float size_y = 3;
    private float maxSpeed = 16;
    private float maxReverseSpeed = -8f;
    public float currentSpeed = 0f;
    private float rateOfAcceleration = 0.05f;
    private float rateOfDeceleration = 0.03f;
    private float maxRateOfTurn = 1.8f;
    public int points;
    private float pointsTimer;
    public int numCollageDestroyed;

    private Boolean inIntro = true;
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
    private boolean objPop = true;


    /**
     * An object which holds the data and controls the player ship and holds the actors hull and cannons which
     * are part of the ship.
     * Class creates and initialises player data, bodies, fixtures and movement
     *
     * @param world a World object to hold the box2D objects
     * @param inpt  an InputController object to get user inputs
     * @param hud a HUD object to get the hud stage
     * @param stage a Stage object to get the parent stage to add actors to
     * @param camera a Camera object passed from parent
     * @param pos_x the x coord for the start position of the ship
     * @param pos_y the y coord for the start position of the ship
     * @param orchestrator an Orchestrator object to allow the switching of scenes
     */
    public Player(World world, InputController inpt, HUD hud , Stage stage, Camera camera, float pos_x, float pos_y,Orchestrator orchestrator){

       // Set image, position and world reference
        super();
        this.inpt = inpt;
        this.world = world;
        this.hud = hud;
        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);
        this.orchestrator = orchestrator;

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

    /**
     * Handles the updating of the player speed and position, as well as shooting cannonballs, health and points.
     * @param delta time since function last called
     */
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

            if (time < 4000) {
                // Turn Right
                body.setAngularVelocity(-0.1f);
                if (objPop){
                    hud.objectivePopup(true);
                    objPop = false;
                }

            } else if (time < 14000) {
                // Turn Left
                body.setAngularVelocity(0.1f);

            } else if(time < 15000) {
                //Straight Ahead

            } else {
                inIntro = false;
                Gdx.input.setInputProcessor(inpt);
            }
        }



        // Update position of box2d body based on updated movement properties.
        float velX = MathUtils.cos(angle) * currentSpeed;
        float velY = MathUtils.sin(angle) * currentSpeed;
        body.setLinearVelocity((velX + body.getLinearVelocity().x)/2f, (velY + body.getLinearVelocity().y)/2f);


        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x - this.getWidth()/2, body.getPosition().y - this.getHeight()/2);


        //Handle getting hit
        int health = hud.getHealth();
        if (hit == true){
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
            orchestrator.dispose();
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

        //at the start
        if(hud.startstatus == false && ( body.getPosition().y > 190f && body.getPosition().y < 202f)) {
            hud.instructionPopup("Use WASD to move, the longer you hold W the faster you accelerate!", true, false);
        }


        //at first college
        if(hud.shootstatus == false && (body.getPosition().x > 175f && body.getPosition().x < 215f && body.getPosition().y > 220f && body.getPosition().y < 250f)  ) {
            hud.instructionPopup("Click the mouse in the direction you want to fire!", false, true);
        }

        //Escape key to go back to main menu
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            orchestrator.changeScreen(Orchestrator.MENU);
            orchestrator.dispose();
        }

        // If win condition met then go to win screen
        if (body.getPosition().x > 928 && numCollageDestroyed >= 5){
            orchestrator.changeScreen(Orchestrator.ENDGAME);
            orchestrator.dispose();
        }

        // Tick down players points over time
        pointsTimer += delta;
        if(pointsTimer > 5) {
            points = Math.max(points - 5, 0);
            hud.setPoints(points);
            pointsTimer = 0;
        }



        super.act(delta);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}


