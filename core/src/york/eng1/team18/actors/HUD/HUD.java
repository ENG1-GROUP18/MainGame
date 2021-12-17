package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class HUD extends Group {
    HealthBar healthBar;
    CannonBar cannonBar;

    public HUD() {
        super();

        cannonBar = new CannonBar(5, 120, 110);
        this.addActor(cannonBar);

        healthBar = new HealthBar(120, 60, 100, 200);
        this.addActor(healthBar);

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
