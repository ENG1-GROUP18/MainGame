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
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;


public class CannonBall  extends Image {
    private SpriteBatch batch;
    public final static int SPEED = 500;
    private static Texture sprite;

    World world;
    Camera camera;
    Stage stage;
    Group player;
    Cannon parent;
    float angle;
    float leftFacing;
    private Body body;
    private Body body_player;



    public boolean remove = false;

    float x,y;

    public CannonBall(Group player, World world, Camera camera, Body body_player, float angle, Cannon parent, float leftFacing){
        this.x = parent.localToStageCoordinates(new Vector2(parent.getOriginX(), parent.getOriginY())).x;
        this.y = parent.localToStageCoordinates(new Vector2(parent.getOriginX(), parent.getOriginY())).y;
        this.stage = stage;
        this.camera = camera;
        this.world = world;
        this.body_player = body_player;
        this.parent = parent;
        this.angle = angle;


        if (sprite == null){
            sprite = new Texture("images/small_cannonball.png");
        }



        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/2, y/2);
        body = world.createBody(bodyDef);
        body.setTransform(x, y, angle);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;
        if (leftFacing != 0){
            fixtureDef.filter.maskBits = 0x0002;
            fixtureDef.filter.categoryBits = 0x0004;
        }else{
            fixtureDef.filter.maskBits = 0x0006;
            fixtureDef.filter.categoryBits = 0x0008;
        }

        body.createFixture(fixtureDef).setUserData("Cannon ball");



        this.body = body;


        float angle_x = (float)Math.cos( body_player.getAngle()+ Math.toRadians(angle));
        float angle_y = (float)Math.sin( body_player.getAngle()+ Math.toRadians(angle));
        float vel_x = 0;
        float vel_y = 0;
        if (leftFacing == 2){

            vel_x = (Math.abs (body_player.getLinearVelocity().x) + 50) * (angle_x); //TODO change added velocities so it works properly
            vel_y = (Math.abs (body_player.getLinearVelocity().y) + 50) * (angle_y);

        }else if (leftFacing == 1){
            vel_x = (Math.abs (body_player.getLinearVelocity().x) + 50) * -(angle_x);
            vel_y = (Math.abs (body_player.getLinearVelocity().y) + 50) * -(angle_y);

        } else{     //if the cannon is not on the player
            angle_x = (float)Math.cos(Math.toRadians(angle));
            angle_y = (float)Math.sin(Math.toRadians(angle));
            vel_x = (75) * (angle_x ); // adjust the value to reduce or increase speed of cannonballs
            vel_y = (75) * (angle_y);
        }



        body.setLinearVelocity(vel_x,vel_y);
        body.setLinearDamping(1);
    }

    public void update(float deltaTime){
        float x_vel = body.getLinearVelocity().x;
        float y_val = body.getLinearVelocity().y;
        if (x_vel < 2 && x_vel > -2 && y_val< 2 && y_val > -2){
            remove = true;
            world.destroyBody(body);
        }

    }




    public void render (Batch batch) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        //batch.setProjectionMatrix(camera.combined);
        //sprite.setPosition(body.getPosition().x,body.getPosition().y);
        batch.draw(sprite, body.getPosition().x - 0.5f, body.getPosition().y - 0.5f , 1 , 1);
        //super.draw(batch,1);

    }

    @Override
    public int getWidth(ImageObserver observer) {
        return 0;
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return 0;
    }

    @Override
    public ImageProducer getSource() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return null;
    }
}