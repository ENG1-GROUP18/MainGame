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

public class EnemyBase extends Group {

    private InputController inpt;
    private World world;
    private Body body;
    private Player player;
    private Map map;
    private College college;

    // ENEMYBASE PROPERTIES:
    private float size_x = 6;
    private float size_y = 3;
    private float range;

    private long fireLimitTimer;
    private float ammoReplenishTimer;
    private float ammoReplenishRate = 1f;

    private float health;

    public boolean is_contact = false;
    public String contact_side = "";

    public boolean remove = false;

    private Cannon cannon;

    public EnemyBase(Map map, InputController inpt, College college, Player player, World world, Stage stage, Camera camera, float pos_x, float pos_y) {
        this(0.03f, map, inpt, college, player, world, stage, camera,pos_x, pos_y); 
    }
    public EnemyBase(float range, Map map, InputController inpt, College college, Player player, World world, Stage stage, Camera camera, float pos_x, float pos_y){

        // Set image, position and world reference
        super();
        this.inpt = inpt;
        this.world = world;
        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);
        this.player = player;
        this.map = map;
        this.range = range;
        this.college = college;
        college.incrementCannons();

        //Enemy bases have to be hit twice to be destroyed
        this.health = 20;

        // Create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);
        body.setTransform(pos_x, pos_y, (float)-Math.PI);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;
        //Uses contact filtering bits to state what can and the body can and can't interact with
        //So maskBits = 4 means it can collide with other bodies of bit 4, in the game means it can collide with cannonballs
        //And categoryBits = 2 means it will accept other bodies of type 2 for collisions
        fixtureDef.filter.maskBits = 0x0004;
        fixtureDef.filter.categoryBits = 0x0002;
        body.createFixture(fixtureDef).setUserData("Enemy Base");
        body.setUserData("Enemy Base");



        // Dispose shapes used to create fixtures
        shape.dispose();

        // Add components to base
        cannon = new Cannon(false, player, this, this.getWidth()*0.5f, this.getHeight()*0.5f, 0, world, camera, stage, body,inpt);
        this.addActor(cannon);

        // For rotation around center
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);

        // Record start time
        fireLimitTimer = TimeUtils.nanoTime();
        
        // Add base to stage
        stage.addActor(this);
    }

    @Override
    public void act(float delta) {

        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x - this.getWidth()/2,
                body.getPosition().y - this.getHeight()/2);

        if (body.getUserData() == "Hit"){
            this.health -=10;
            body.setUserData("Enemy Base");
        }
        if (this.health <= 0){
            for (Fixture fixture : body.getFixtureList()){
                body.destroyFixture(fixture);
            }
            cannon.delete();
            if (cannon.CannonBalls.isEmpty()){
                body.setUserData("Dead");
                this.remove();
            }

        }

        super.act(delta);
    }

    public boolean isInRange(){
        float p1 = body.getPosition().x/map.getSizeX() - player.getX()/map.getSizeX();
        float p2 = body.getPosition().y/map.getSizeY() - player.getY()/map.getSizeY();

        if (Math.abs(Math.hypot(p1,p2)) < range){return true;}
        else{return false;}
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
