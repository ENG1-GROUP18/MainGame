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
    private int barMaxValue;
    private int barWidth;
    private int barHeight = 16;
    private Image bar;

  /**
   * HealthBar() method sets the values needed for the healthbar and the creates it
   *
   * @param posX Integer value used to set the position of the health bar, x coord
   * @param posY Integer value used to set the position of the health bar, y coord
   * @param maxHealth Integer value which sets the maximum value of player health displayed on the health bar
   * @param barLength Integer value which is set to the barWidth to determine the length of the health bar.
   */
    public HealthBar(int posX, int posY, int maxHealth, int barLength) {
        super();
        this.barCurrentValue = maxHealth;
        this.barMaxValue = maxHealth;
        this.barWidth = barLength;


        // Create health bar
        this.bar = new Image(new Texture(Gdx.files.internal("images/WhiteSquare.png")));
        bar.setPosition(posX, posY);
        bar.setSize(barWidth, barHeight);
        this.addActor(bar);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.bar.setSize(barWidth*( (float) barCurrentValue/(float) barMaxValue),barHeight);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }

    public void setValue(int value) {
        this.barCurrentValue = value;

    }

    public int getValue() {
        return this.barCurrentValue;

    }

}
