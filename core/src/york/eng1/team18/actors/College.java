package york.eng1.team18.actors;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;


public class College extends Group {

    private World world;
    private Body body;

    // COLLEGE PROPERTIES:
    private float size_x = 6;
    private float size_y = 6;
    private float pos_x;
    private float pos_y;
    private boolean isConquered;
    private int numCannons;

    private long fireLimitTimer;
    private float ammoReplenishTimer;
    private float ammoReplenishRate = 2f;

    public boolean is_contact = false;
    public String contact_side = "";


    public College(World world, Stage stage, Camera camera, float pos_x, float pos_y, String imagePath){


        // Set image, position, image path, conquered state and world reference
        super();
        this.world = world;
        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);
        this.isConquered = false;
        this.numCannons = 0;

        // Create body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);
        body.setTransform(pos_x +size_x/2, pos_y +size_y/2, (float)-Math.PI);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size_x/2, size_y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.maskBits = 0x0004;
        fixtureDef.filter.categoryBits = 0x0002;
        body.createFixture(fixtureDef).setUserData("Collage");



        // Dispose shapes used to create fixtures
        shape.dispose();

        // Add components to college
        this.addActor(new Building(this, imagePath));
        //this.addActor(new Cannon(this, this.getWidth()*2/5, this.getHeight()/4, false , world, camera, stage, body));
        //this.addActor(new Cannon(this, this.getWidth()*2/5, this.getHeight()*3/4, true, world, camera, stage, body));

        // For rotation around center
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);

        // Record start time
        //fireLimitTimer = TimeUtils.nanoTime();
    }
    public void conquer() {
        isConquered = true;
    }

    public void incrementCannons(){
        numCannons += 1;
    }

    public void decrementCannons(){
        numCannons -= 1;
        if (numCannons == 0){this.conquer();}
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
