package york.eng1.team18.actors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.Orchestrator;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;


public class CannonBall  extends Group {
    private SpriteBatch batch;
    public final static int SPEED = 500;
    private static Texture sprite;

    World world;
    Camera camera;
    Stage stage;
    Group player;
    Cannon parent;
    float angle;
    Boolean leftFacing;
    private Body body;
    private Body body_player;



    public boolean remove = false;

    float x,y;

    public CannonBall(Group player, World world, Camera camera, Body body_player, float angle, Cannon parent, boolean leftFacing){
        this.x = parent.localToStageCoordinates(new Vector2(parent.getOriginX(), parent.getOriginY())).x;
        this.y = parent.localToStageCoordinates(new Vector2(parent.getOriginX(), parent.getOriginY())).y;
//        this.x = body_player.getPosition().x;
//        this.y = body_player.getPosition().y;
        this.stage = stage;
        this.camera = camera;
        this.world = world;
        this.body_player = body_player;
        this.parent = parent;
        this.angle = angle;



        //batch = new SpriteBatch();
        //

        if (sprite == null){
            sprite = new Texture("images/temp_cannonball.png");
        }
        //this.addActor(new CannonBallImage(this));
        this.setPosition(this.x,this.y);
        this.setOrigin(x/2,y/2);
//        sprite.setPosition(this.x,this.y);
//        sprite.setOrigin(this.x,this.y);
//        sprite.setScale(0.10f);
//        System.out.println(x);
//        System.out.println(y);
//        System.out.println(body_player.getAngle());
//        System.out.println(Math.abs(player.getRotation()%360));
//        System.out.println(this.getX());



        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/2, y/2);
        body = world.createBody(bodyDef);
        body.setTransform(x, y, angle);
        CircleShape shape = new CircleShape(); //TODO change change shape to circle
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

//        char[] temp_char = {'a'};
//        Entity entity_temp = new Entity("",1, temp_char);
        fixtureDef.filter.maskBits = (short) 2;
        body.createFixture(fixtureDef);
        body.setUserData("Cannon ball");

        this.body = body;
//        System.out.println(body_player.getPosition());
        //need
//        System.out.println((float)Math.sin(angle));
//        System.out.println((float)Math.cos(angle - Math.PI));
//        System.out.println((float)Math.cos((angle - Math.abs(player.getRotation()%360)) - Math.PI) );
//        System.out.println(a_x);
//
        //TODO clean up code, make angles and velocities match
        float angle_x = (float)Math.cos(body_player.getAngle()  + (float)Math.cos(Math.toRadians(angle)- Math.PI));
        float angle_y = (float)Math.sin(body_player.getAngle() + (float)Math.sin(Math.toRadians(angle)- Math.PI/2));

        float vel_x = 0;
        float vel_y = 0;
        if (leftFacing){

            vel_x = (Math.abs (body_player.getLinearVelocity().x) + 50) * -(angle_y );
            vel_y = (Math.abs (body_player.getLinearVelocity().y) + 50) * (angle_x);
            System.out.println(vel_x);
            System.out.println(vel_y);
        }else{
            vel_x = (Math.abs (body_player.getLinearVelocity().x) + 50) * (angle_y );
            vel_y = (Math.abs (body_player.getLinearVelocity().y) + 50) * -(angle_x);
            System.out.println(vel_x);
            System.out.println(vel_y);
        }

//        System.out.println((float)Math.cos(Math.toRadians(angle)- Math.PI));
//        System.out.println(angle_y);

        System.out.println("end");

        body.setLinearVelocity(vel_x,vel_y);
        body.setLinearDamping(1);
    }

    public void update(float deltaTime){
        //y+=SPEED * deltaTime;
        //
        float x_vel = body.getLinearVelocity().x;
        float y_val = body.getLinearVelocity().y;
        if (x_vel < 1 && x_vel > -1 && y_val< 1 && y_val > -1){
            remove = true;
            world.destroyBody(body);
        }
//        float myX = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).x;
//        float myY = this.localToScreenCoordinates(new Vector2(this.getOriginX(),this.getOriginY())).y;
    }


//    public void create () {
//
//    }



    public void render (Batch batch) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        //batch.setProjectionMatrix(camera.combined);
        //sprite.setPosition(body.getPosition().x,body.getPosition().y);
        batch.draw(sprite, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f);
        //super.draw(batch,1);

    }
}