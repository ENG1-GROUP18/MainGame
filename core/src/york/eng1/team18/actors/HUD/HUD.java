package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import york.eng1.team18.Orchestrator;
import york.eng1.team18.views.MainScreen;

public class HUD extends Group {
    HealthBar healthBar;
    CannonBar cannonBar;

    public HUD(MainScreen parent) {
        super();
        cannonBar = new CannonBar(5, 100, 100);
        this.addActor(cannonBar);

//        healthBar = new HealthBar(100, 50, 100, 200);
//        this.addActor(healthBar);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void increaseCannonTicks() {
        cannonBar.increaseActiveTicks();
    }

    public void decreaseCannonTicks() {
        cannonBar.decreaseActiveTicks();
    }


}


