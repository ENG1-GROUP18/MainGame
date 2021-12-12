package york.eng1.team18.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jetbrains.annotations.NotNull;

public class CannonBall {
    public static final int speed = 50;
    public static int speedy;
    public static int speedx;
    private static Texture texture;
    private static Pixmap original;
    private static Pixmap tenx9;


    float x,y,x_speed,y_speed,angle;

    public boolean remove = false;

    public CannonBall(float x, float y , float x_speed, float y_speed , float angle){
        this.x = x;
        this.y = y;
        this.x_speed = Math.abs(x_speed)  + 50;
        this.y_speed = Math.abs(y_speed) + 50;
        this.angle = angle;
        if (texture == null) {
            texture = new Texture("images/small_cannonball.png" );
            original = new Pixmap(Gdx.files.internal("images/small_cannonball.png"));
            tenx9 = new Pixmap(2,1,original.getFormat());
            tenx9.drawPixmap(original,
                    0, 0, original.getWidth(), original.getHeight(),
                    0, 0, tenx9.getWidth(), tenx9.getHeight()
            );
            texture = new Texture(tenx9);
            original.dispose();
            tenx9.dispose();
        }

    }

    public void update (float delta){
        float a_x = (float)Math.sin(angle - Math.PI);
        float a_y = (float)Math.cos(angle);
        y+= (x_speed * delta) * a_y;
        x+= (y_speed * delta) * a_x;
        if (y> Gdx.graphics.getHeight() || x > Gdx.graphics.getWidth()){
            remove = true;
        }
    }

    public void render(@NotNull SpriteBatch batch){
        batch.draw(texture,x,y);
    }


    public void remove(CannonBall cannonBall) {
    }
}
