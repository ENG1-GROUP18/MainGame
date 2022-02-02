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
import com.badlogic.gdx.utils.Timer;

public class HUD extends Group {

    private Player player;
    private Map  map;
    private float mapSize;
    private MainScreen parent;

    private Group playerStatsGroup;
    private Group miniMapGroup;

    // Components of the HUD all extend Actor class in some way. Useful for animation through actions.
    private BackPlate backPlate;
    private HealthBar healthBar;
    private CannonBar cannonBar;
    private MiniMap miniMap;
    private Image playerPointer;
    //Label debugLabel1;
    //Label debugLabel2;
    private Label points;
    public Label popUp;
    public Label objectiveLabel;
    public Label objectiveLabel2;
    public boolean startstatus = false;
    public boolean shootstatus = false;


    public HUD(MainScreen parent, Player player, float mapSize) {
        super();

        this.player = player;
        this.mapSize = mapSize;
        this.parent = parent;


        // Create and add components to display players stats.
        playerStatsGroup = new Group();
        this.addActor(playerStatsGroup);
        playerStatsGroup.setSize(300, 96);

        backPlate = new BackPlate();
        playerStatsGroup.addActor(backPlate);

        healthBar = new HealthBar(22, 61, 200, 256);
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



        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));

        points = new Label("Points: 0", skin);

        points.setPosition((300f/1920f)*Gdx.graphics.getWidth(), Gdx.graphics.getHeight() -(50f/1080f)*Gdx.graphics.getHeight());
        this.addActor(points);

        objectiveLabel = new Label("", skin);
        objectiveLabel2 = new Label("", skin);

        // Reveals hud after delay on game start.
        DelayAction da = new DelayAction();
        da.setDuration(36); // TODO Change back to 36

        AlphaAction aa = new AlphaAction();
        aa.setAlpha(1);
        aa.setDuration(1);

        this.setColor(1, 1, 1, 0);
        SequenceAction sa = new SequenceAction(da, aa);
        this.addAction(sa);

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
        // Checks if the game is tabbed in if in fullscreen
        if (Gdx.graphics.getWidth() !=0 && Gdx.graphics.getHeight() != 0){
            // Centers the HUD horizontally on screen.
            playerStatsGroup.setPosition((Gdx.graphics.getWidth() - playerStatsGroup.getWidth())/2, 20);

            //Sets position relative to aspect ratio, values to normalise for 1080p, works for any 16:9 resolution
            /*Formula for calculating relative size of UI:
                (ImageSize/1920)* WindowSize
            */
            Vector2 relativeMapSizeToWindow = new Vector2((500f/1920f)*Gdx.graphics.getWidth(),(400f/1080f)*Gdx.graphics.getHeight());
            miniMapGroup.setPosition(Gdx.graphics.getWidth() - relativeMapSizeToWindow.x, Gdx.graphics.getHeight() - relativeMapSizeToWindow.y);
            miniMap.setSize(relativeMapSizeToWindow.x, relativeMapSizeToWindow.y);
            playerPointer.setSize((32f/1920f)*Gdx.graphics.getWidth(), (32f/1080f)*Gdx.graphics.getHeight());

            points.setPosition((50f/1920f)*Gdx.graphics.getWidth(), Gdx.graphics.getHeight() -(150f/1080f)*Gdx.graphics.getHeight());
            points.setFontScale(1f/(500f/Gdx.graphics.getWidth())*(25f/96f) , 1f/(400f/Gdx.graphics.getHeight())*(10f/27f));

            objectiveLabel.setPosition((300f/1920f)*Gdx.graphics.getWidth(), (600f/1080f)*Gdx.graphics.getHeight());
            objectiveLabel2.setPosition((300f/1920f)*Gdx.graphics.getWidth(), (400f/1080f)*Gdx.graphics.getHeight());

            objectiveLabel.setFontScale(1f/(500f/Gdx.graphics.getWidth())*(25f/96f) , 1f/(400f/Gdx.graphics.getHeight())*(10f/27f));
            objectiveLabel2.setFontScale(1f/(500f/Gdx.graphics.getWidth())*(25f/96f) , 1f/(400f/Gdx.graphics.getHeight())*(10f/27f));
        }


    }


    public void increaseCannonTicks() {
        cannonBar.increaseActiveTicks();
    }

    public void decreaseCannonTicks() {
        cannonBar.decreaseActiveTicks();
    }

    public int getHealth() {
        return healthBar.getValue();
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
    
    public void objectivePopup(boolean start_status){
        if(start_status == true){
            startstatus = true;
        }
        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));
        objectiveLabel.setText("New Objective");
        objectiveLabel2.setText("Conquer all opposing Colleges");
        objectiveLabel.setPosition((300f/1920f)*Gdx.graphics.getWidth(), (600f/1080f)*Gdx.graphics.getHeight());
        objectiveLabel2.setPosition((300f/1920f)*Gdx.graphics.getWidth(), (400f/1080f)*Gdx.graphics.getHeight());
        objectiveLabel.setColor(0,0,0,1);
        objectiveLabel2.setColor(0,0,0,1);
        this.getStage().addActor(objectiveLabel);
        this.getStage().addActor(objectiveLabel2);

        Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    objectiveLabel.remove();
                    objectiveLabel2.remove();
                    objectivePopup2();
                }
            },6);

    }

    public void objectivePopup2(){
        objectiveLabel.setText("To destroy cannons they need to be hit twice");
        objectiveLabel2.setText("To conquer colleges they need to be hit 5 times");
        this.getStage().addActor(objectiveLabel);
        this.getStage().addActor(objectiveLabel2);
        Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                objectiveLabel.remove();
                objectiveLabel2.remove();
                objectivePopup3();
            }
        },6);
    }
    public void objectivePopup3(){
        objectiveLabel.setText("Good luck!");
        this.getStage().addActor(objectiveLabel);
        Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                objectiveLabel.remove();
                objectiveLabel2.remove();
            }
        },6);
    }

    public void instructionPopup(String text1, boolean start_status, boolean shoot_status){
        if(start_status == true){
            startstatus = true;
        }
        if(shoot_status == true){
            popUp.remove();
            shootstatus = true;
        }
        Skin skin = new Skin(Gdx.files.internal("skin/customSkin.json"));
        popUp = new Label(text1, skin);
        //coordinates are 400 ,300
        popUp.setPosition((50f/1920f)*Gdx.graphics.getWidth(), (400f/1920f)*Gdx.graphics.getHeight());
        popUp.setFontScale(1f/(600f/Gdx.graphics.getWidth())*(25f/96f), 1f/(500f/Gdx.graphics.getHeight())*(10f/27f));
        popUp.setAlignment(Align.left);
        this.addActor(popUp);
        //timed remove after popup
        if(shoot_status == true){
            Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                @Override
                public void run() {
                    popUp.remove();
                }
            },4);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;

    }

    public void setMap(Map map) {
        this.map = map;

    }

    public void setPoints(int points){
        this.points.setText("Points: "+ Integer.toString(points));
    }

}
