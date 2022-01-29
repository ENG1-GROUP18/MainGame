package york.eng1.team18.actors.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import sun.jvm.hotspot.utilities.BitMap;
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
    Label debugLabel1;
    Label debugLabel2;


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
        miniMapGroup.setPosition(0, 0);

        miniMap = new MiniMap();
        miniMapGroup.addActor(miniMap);
        miniMap.setSize((500f/1920f)*Gdx.graphics.getWidth(), (400f/1080f)*Gdx.graphics.getHeight());

        playerPointer = new Image(new Texture(Gdx.files.internal("images/hud/pointer.png")));
        playerPointer.setScale(0.5f, 0.5f);
        playerPointer.setOrigin(Align.center);
        miniMapGroup.addActor(playerPointer);



        //-----------------DEBUG LABELS------------------
        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));
        // skin.get("default-font", BitmapFont.class).getData().markupEnabled = true;


        debugLabel1 = new Label("debug label 1", skin);
        debugLabel1.setPosition(300, 150);
        this.addActor(debugLabel1);

        debugLabel2 = new Label("debug label 2", skin);
        debugLabel2.setPosition(300, 50);
        this.addActor(debugLabel2);

        // Reveals hud after delay on game start. //TODO uncomment this
//        DelayAction da = new DelayAction();
//        da.setDuration(36);
//
//        AlphaAction aa = new AlphaAction();
//        aa.setAlpha(1);
//        aa.setDuration(1);
//
//        this.setColor(1, 1, 1, 0);
//        SequenceAction sa = new SequenceAction(da, aa);
//        this.addAction(sa);
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

        //Sets position relative to aspect ratio, values to normalise for 1080p, works for any 16:9 resolution
        /*Formula for calculating relative size of UI:
            (ImageSize/1920)* WindowSize
        */
        Vector2 relativeMapSizeToWindow = new Vector2((500f/1920f)*Gdx.graphics.getWidth(),(400f/1080f)*Gdx.graphics.getHeight());
        miniMapGroup.setPosition(Gdx.graphics.getWidth() - relativeMapSizeToWindow.x, Gdx.graphics.getHeight() - relativeMapSizeToWindow.y);
        playerPointer.setSize((32f/1920f)*Gdx.graphics.getWidth(), (32f/1080f)*Gdx.graphics.getHeight());
        miniMap.setSize(relativeMapSizeToWindow.x, relativeMapSizeToWindow.y);

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
        float x = player.getX()/2 - 15 + miniMap.getX();
        float y = player.getY()/2 - 15 + miniMap.getY();
        float angle = player.getRotation() - 90;

        //debugLabel1.setText(String.valueOf(player.getX()));
        //debugLabel2.setText(String.valueOf(player.getY()));

        //sets position relative to aspect ratio
        playerPointer.setPosition(x * 1f/(500f/Gdx.graphics.getWidth())*(25f/96f) , y * 1f/(400f/Gdx.graphics.getHeight())*(10f/27f));
        playerPointer.setRotation(angle);

    }

    public void setPlayer(Player player) {
        this.player = player;

    }

    public void setMap(Map map) {
        this.map = map;

    }

}
