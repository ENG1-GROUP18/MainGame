package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import york.eng1.team18.views.MainScreen;

public class HUD extends Group {

    Group playerStatsGroup;
    Group miniMapGroup;


    // Components of the HUD all extend Actor class in some way. Useful for animation through actions.
    BackPlate backPlate;
    HealthBar healthBar;
    CannonBar cannonBar;
    MiniMap map;

    public HUD(MainScreen parent) {
        super();

        // Create and add components to display players stats.
        playerStatsGroup = new Group();
        this.addActor(playerStatsGroup);
        playerStatsGroup.setSize(300, 96);

        backPlate = new BackPlate();
        playerStatsGroup.addActor(backPlate);

        healthBar = new HealthBar(22, 61, 100, 256);
        playerStatsGroup.addActor(healthBar);

        cannonBar = new CannonBar(34, 14, 6);
        playerStatsGroup.addActor(cannonBar);


        // Create and add minimap
        miniMapGroup = new Group();
        this.addActor(miniMapGroup);
        miniMapGroup.setPosition(20, 20);


        map = new MiniMap();
        miniMapGroup.addActor(map);
        map.setSize(300, 200);
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
        // Centers the HUD horizontally on screen.
        playerStatsGroup.setPosition((Gdx.graphics.getWidth() - playerStatsGroup.getWidth())/2, 20);
    }


    public void increaseCannonTicks() {
        cannonBar.increaseActiveTicks();
    }

    public void decreaseCannonTicks() {
        cannonBar.decreaseActiveTicks();
    }

    public void changeHealthBy(int value) {

        healthBar.setValue(healthBar.getValue() + value);
    }

    public void changeHealthTo(int value) {
        healthBar.setValue(value);

    }


}


