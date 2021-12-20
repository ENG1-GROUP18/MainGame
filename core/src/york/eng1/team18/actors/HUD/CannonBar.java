package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class CannonBar extends Group {

    private int maxTicks;
    private int currentTicks;

    private int tickWidth = 32;
    private int tickHeight = 32;
    private int tickGap = 8;

    private Image[] ticks;

    public CannonBar(int posX, int posY, int maxTicks) {
        super();
        this.maxTicks = maxTicks;
        this.currentTicks = maxTicks;


        // Create blocks
        ticks = new Image[maxTicks];
        for (int i = 0; i < maxTicks; i++) {
            ticks[i] = new Image(new Texture(Gdx.files.internal("images/hud/cannonball32.png")));
            ticks[i].setPosition(posX + i*(tickGap + tickWidth), posY);
            ticks[i].setSize(tickWidth, tickHeight);
            ticks[i].setOrigin(Align.center);
            this.addActor(ticks[i]);
        }


        increaseActiveTicks();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }




    public void increaseActiveTicks() {
        if (currentTicks < maxTicks) {
            AlphaAction aa = new AlphaAction();
            aa.setAlpha(1f);
            aa.setDuration(0.1f);

            ScaleToAction sta = new ScaleToAction();
            sta.setScale(1f);
            sta.setDuration(0.1f);

            ParallelAction pa = new ParallelAction(aa, sta);

            ticks[currentTicks].addAction(pa);

            currentTicks += 1;
        }
    }

    public void decreaseActiveTicks() {
        if (currentTicks > 0) {
            AlphaAction aa = new AlphaAction();
            aa.setAlpha(0.25f);
            aa.setDuration(0.1f);

            ScaleToAction sta = new ScaleToAction();
            sta.setScale(0.5f);
            sta.setDuration(0.1f);

            ParallelAction pa = new ParallelAction(aa, sta);

            ticks[currentTicks - 1].addAction(pa);
            currentTicks -= 1;
        }
    }


}
