package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.controller.InputController;

public class Player extends Image {

    private InputController inpt;
    private World world;
    private Body body;
    private WaterTrail waterTrail;

    // PLAYER PROPERTIES:
    private float maxSpeed = 16;
    private float maxReverseSpeed = -2f;
    private float currentSpeed = 0f;
    private float rateOfAcceleration = 0.05f;
    private float rateOfDeceleration = 0.02f;
    private float maxRateOfTurn = 1.8f;
    private boolean crash = false;
    private float count_update = 0f;

    public Player(World world, Orchestrator orch, Camera camera, InputController inpt, float pos_x, float pos_y , float size_x, float size_y){
       // Set image, position and world reference
        super(new Texture(Gdx.files.internal("images/rubber_duck.jpg")));
        this.inpt = inpt;
        this.world = world;
        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);

        // Create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);
        body.setTransform(pos_x, pos_y, MathUtils.degreesToRadians * 90f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size_x/2, size_y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);
        body.setUserData("Player");
        shape.dispose();

        // For rotation around center
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);
    }

    @Override
    public void act(float delta) {
        float angle = body.getAngle();
        float rateOfTurn = maxRateOfTurn * (currentSpeed / maxSpeed);

        // Handle player input, update movement properties
        if (inpt.forward && !inpt.backward) {
            // Accelerate forward
            currentSpeed = Math.min(currentSpeed + rateOfAcceleration, maxSpeed);

        }else if (inpt.backward && !inpt.forward) {
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
        body.setLinearVelocity(velX/2 + body.getLinearVelocity().x/2, velY/2 + body.getLinearVelocity().y/2);


        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x - this.getWidth()/2,
                body.getPosition().y - this.getHeight()/2);


        // Stops boat if collision
        for (Contact contact : world.getContactList()){
            Object nameA = contact.getFixtureA().getBody().getUserData();
            Object nameB = contact.getFixtureB().getBody().getUserData();
            //System.out.println(nameB);
            if (contact.isTouching()  && nameA == "Map" && nameB == "Player" && crash == false && count_update <= 0){

                currentSpeed = 0;
                //body.setLinearVelocity(0,0);

                crash = true;
                count_update = 100;
            } else if (contact.isTouching()  && nameA == "Map" && nameB == "Player" && crash == true && count_update > 0){
                //System.out.println("Hit");
                crash = false;

            }
        }
        //System.out.println(crash);
        if (count_update > 0){
            count_update -=1;
        }

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
