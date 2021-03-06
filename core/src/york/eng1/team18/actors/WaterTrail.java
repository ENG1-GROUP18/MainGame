package york.eng1.team18.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.Orchestrator;

import java.util.ArrayList;

public class WaterTrail extends Actor{

    private Actor source;
    private Camera camera;
    private Color colorSea;
    private Color colorTrail;
    ShapeRenderer shapeRenderer;

    long logTime;
    int trailSize = 60;
    Vector2 perp = new Vector2();
    private ArrayList<Vector2> trailPoints= new ArrayList<>();
    private ArrayList<Vector2> trailShapeRight = new ArrayList<Vector2>();
    private ArrayList<Vector2> trailShapeLeft = new ArrayList<Vector2>();


    /**
     * Creates an object which creates a trail/wave behind the player
     * @param camera a Camera object for use in the shape renderer
     * @param source an Actor object to hold info on where the player ship is
     */
    public WaterTrail(Camera camera, Actor source) {
        this.source = source;
        this.camera = camera;
        colorSea = new Color(255/255f, 255/255f, 255/255f, 0);
        colorTrail = new Color(255/255f, 255/255f, 255/255f, 1);
        shapeRenderer = new ShapeRenderer();

        // Record start time
        logTime = TimeUtils.nanoTime();

        // Fill trailPoints with blank points

        for (int i = 0; i < trailSize; i++){
            trailPoints.add(new Vector2(this.source.getX() + this.source.getWidth()/2, this.source.getY() + this.source.getHeight()/2));
        }

        // Fill trailShapePoints with empty vectors
        for (int i = 0; i < trailSize * 2; i++) {
            trailShapeRight.add(new Vector2(this.source.getX() + this.source.getWidth()/2, this.source.getY() + this.source.getHeight()/2));
            trailShapeLeft.add(new Vector2(this.source.getX() + this.source.getWidth()/2, this.source.getY() + this.source.getHeight()/2));
        }
    }

    /**
     * Updates the trail depending on the player position, and it's length
     */
    public void act() {

        // Update Trail every n nanoseconds
        if (TimeUtils.timeSinceNanos(logTime) > 25000000) {

            // Update trailPoints
            Vector2 tempPoint = new Vector2(source.getX() + source.getWidth()/2, source.getY() + source.getHeight()/2);
            trailPoints.add(0, tempPoint);
            trailPoints.remove(trailSize - 1);

            trailShapeLeft.clear();
            trailShapeRight.clear();
            // Update trailShapePoints
            for (int i = 0; i < trailSize - 1; i++) {
                Vector2 p1 = trailPoints.get(i);
                Vector2 p2 = trailPoints.get(i + 1);
                perp.set(p2).sub(p1).nor();


                // Wave offset
                perp.set(-perp.y * i/10, perp.x * i/10);

                // Create Left Points
                Vector2 innerPoint = new Vector2(p1.cpy().sub(new Vector2(perp.x, perp.y)));
                Vector2 outerPoint = new Vector2(p1.cpy().sub(new Vector2(perp.x * 1.2f, perp.y * 1.2f)));
                trailShapeLeft.add(innerPoint);
                trailShapeLeft.add(outerPoint);

                // Create Right Points
                Vector2 innerPoint2 = new Vector2(p1.cpy().add(new Vector2(perp.x, perp.y)));
                Vector2 outerPoint2 = new Vector2(p1.cpy().add(new Vector2(perp.x * 1.2f, perp.y * 1.2f)));
                trailShapeRight.add(innerPoint2);
                trailShapeRight.add(outerPoint2);

            }
            logTime = TimeUtils.nanoTime();
        }
    }

    /**
     * draws each of the shapes within the trail
     */
    public void draw() {
      Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND); //needed for transparency

        Color tCol;
        float rDiff = colorSea.r - colorTrail.r;
        float gDiff = colorSea.g - colorTrail.g;
        float bDiff = colorSea.b - colorTrail.b;
        float aDiff = colorSea.a - colorTrail.a;

        // Draw Trail
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < trailSize - 3; i++) {

            float newColR = colorTrail.r + (rDiff * i / trailSize);
            float newColG = colorTrail.g + (gDiff * i / trailSize);
            float newColB = colorTrail.b + (bDiff * i / trailSize);
            float newColA = (trailSize - i)/(float)trailSize;

            tCol = new Color(newColR, newColG, newColB, newColA);
            Vector2 l1 = trailShapeLeft.get(i);
            Vector2 l2 = trailShapeLeft.get(i + 1);
            Vector2 l3 = trailShapeLeft.get(i + 2);
            shapeRenderer.triangle(l1.x, l1.y, l2.x, l2.y, l3.x, l3.y, tCol, tCol, tCol);

            Vector2 r1 = trailShapeRight.get(i);
            Vector2 r2 = trailShapeRight.get(i + 1);
            Vector2 r3 = trailShapeRight.get(i + 2);
            shapeRenderer.triangle(r1.x, r1.y, r2.x, r2.y, r3.x, r3.y, tCol, tCol, tCol);
        }
        shapeRenderer.end();
    }
}
