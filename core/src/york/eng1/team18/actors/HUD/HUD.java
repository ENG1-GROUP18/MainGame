package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.views.MainScreen;

public class HUD extends Group {

    BackPlate backPlate;
    HealthBar healthBar;
    CannonBar cannonBar;

    public HUD(MainScreen parent) {
        super();

        this.setSize(300, 96);

        backPlate = new BackPlate();
        this.addActor(backPlate);

        healthBar = new HealthBar(22, 61, 100, 256);


        cannonBar = new CannonBar(34, 14, 6);
        this.addActor(cannonBar);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void  recalculatePos() {
        this.setPosition((Gdx.graphics.getWidth() - this.getWidth())/2, 20);
    }


    public void increaseCannonTicks() {
        cannonBar.increaseActiveTicks();
    }

    public void decreaseCannonTicks() {
        cannonBar.decreaseActiveTicks();
    }


}


