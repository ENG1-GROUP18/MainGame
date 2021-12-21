package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import york.eng1.team18.actors.Map;
import york.eng1.team18.actors.Player;
import york.eng1.team18.views.MainScreen;

public class HUD extends Group {

    Player player;
    Map map;
    float mapSize;

    Group playerStatsGroup;
    Group miniMapGroup;

    // Components of the HUD all extend Actor class in some way. Useful for animation through actions.
    BackPlate backPlate;
    HealthBar healthBar;
    CannonBar cannonBar;
    MiniMap miniMap;
    Image playerPointer;

    public HUD(MainScreen parent, Player player, float mapSize) {
        super();

        this. player = player;
        this.mapSize = mapSize;

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

        miniMap = new MiniMap();
        miniMapGroup.addActor(miniMap);
        miniMap.setSize(300, 200);

        playerPointer = new Image(new Texture(Gdx.files.internal("images/hud/pointer.png")));
        playerPointer.setPosition(40, 10);
        playerPointer.setScale(0.5f, 0.5f);
        playerPointer.setOrigin(Align.center);
        miniMapGroup.addActor(playerPointer);
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

    public void updatePointer() {
        float x = miniMap.getWidth() * player.getX() / mapSize;
        float y = miniMap.getHeight() * player.getY() / mapSize;
        float angle = player.getRotation() - 90;

        playerPointer.setPosition(x - 20, y*1.49f - 20);
        playerPointer.setRotation(angle);

    }

    public void setPlayer(Player player) {
        this.player = player;

    }

    public void setMap(Map map) {
        this.map = map;

    }

}
