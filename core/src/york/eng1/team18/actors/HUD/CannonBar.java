package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CannonBar extends Group {

    private int blockWidth = 40;
    private int blockHeight = 38;
    private int blockGap = 2;
    private Color barColor = new Color(0/255f, 255/255f, 255/255f, 1);


    public CannonBar(int numberOfBars, int posX, int posY) {
        super();

        Image[] bars = new Image[numberOfBars];


        for (int i = 0; i < numberOfBars; i++) {
            bars[i] = new Image(new Texture(Gdx.files.internal("images/WhiteSquare.png")));
            bars[i].setPosition(posX + i*blockWidth, posY);
            bars[i].setSize(blockWidth - blockGap, blockHeight);
            this.addActor(bars[i]);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
