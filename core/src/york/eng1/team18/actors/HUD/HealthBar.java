package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.w3c.dom.Text;


public class HealthBar extends Group {

    private int barCurrentValue;
    private int barMaxValue = 100;
    private int barWidth;
    private int barHeight = 32;

    public HealthBar(int posX, int posY, int maxHealth, int barLength) {
        super();
        this.barCurrentValue = maxHealth;
        this.barMaxValue = maxHealth;
        this.barWidth = barLength;


        // Create health bar
        Image bar = new Image(new Texture(Gdx.files.internal("images/WhiteSquare.png")));
        bar.setPosition(posX, posY);
        bar.setSize(barWidth, barHeight);
        this.addActor(bar);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void setValue() {


    }

}
