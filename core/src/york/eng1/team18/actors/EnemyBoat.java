package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;


public class EnemyBoat extends Group {
    private Body body;
    private long time;
    // Boat PROPERTIES:
    private float size_x = 6;
    private float size_y = 3;

    public EnemyBoat(World world, Stage stage , float pos_x, float pos_y){
        super();

        this.setPosition(pos_x, pos_y);
        this.setSize(size_x, size_y);


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos_x, pos_y);
        body = world.createBody(bodyDef);
        body.setTransform(pos_x, pos_y, 0);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size_x/2, size_y/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;

        fixtureDef.filter.categoryBits = 0x0008;
        body.createFixture(fixtureDef).setUserData("Enemy Boat");
        body.setUserData("Enemy Boat");

        body.setLinearDamping(1);
        body.setAngularDamping(1);


        shape.dispose();
        Image image = new Image(new Texture(Gdx.files.internal("images/enemyShip.png")));
        image.setSize(this.getWidth(),this.getHeight());
        this.addActor(image); //TODO replace with different image

        this.setOrigin(this.getWidth()/2, this.getHeight()/2);

        time = TimeUtils.millis();

        stage.addActor(this);
    }

    @Override
    public void act(float delta) {
        int speed = 10;

        body.setAngularVelocity(1);
        float velX = MathUtils.cos(body.getAngle()) * speed;
        float velY = MathUtils.sin(body.getAngle()) * speed;
        body.setLinearVelocity((velX + body.getLinearVelocity().x)/2f, (velY + body.getLinearVelocity().y)/2f);

        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        this.setPosition(body.getPosition().x - this.getWidth()/2, body.getPosition().y - this.getHeight()/2);


        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
