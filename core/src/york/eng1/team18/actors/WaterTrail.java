package york.eng1.team18.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import york.eng1.team18.Orchestrator;

import java.util.ArrayList;

public class WaterTrail {

    private Orchestrator orch;
    private Actor parent;
    private Camera camera;
    private Color colorSea;
    private Color colorTrail;
    private ImmediateModeRenderer20 imr;
    ShapeRenderer shapeRenderer;

    long logTime;
    int trailSize = 50;
    Vector2 perp = new Vector2();
    private ArrayList<Vector2> trailPoints= new ArrayList<>();
    private ArrayList<Vector2> trailShapeRight = new ArrayList<Vector2>();
    private ArrayList<Vector2> trailShapeLeft = new ArrayList<Vector2>();
    ArrayList<Color> pointColors = new ArrayList<Color>();


    public WaterTrail(Camera camera, Actor parent) {
        this.parent = parent;
        this.camera = camera;
        colorSea = new Color(122/255f, 180/255f, 196/255f, 1);
        colorTrail = new Color(153/255f, 193/255f, 201/255f, 1);
        shapeRenderer = new ShapeRenderer();

        // Record start time
        logTime = TimeUtils.nanoTime();

        // Fill trailPoints with blank points
//        float parentX = parent.getOriginX();
//        float parentY = parent.getOriginY();
        for (int i = 0; i < trailSize; i++){
            trailPoints.add(new Vector2(parent.getX() + parent.getWidth()/2, parent.getY() + parent.getHeight()/2));
        }

        // Fill trailShapePoints with empty vectors
        for (int i = 0; i < trailSize * 2; i++) {
            trailShapeRight.add(new Vector2(parent.getX() + parent.getWidth()/2, parent.getY() + parent.getHeight()/2));
            trailShapeLeft.add(new Vector2(parent.getX() + parent.getWidth()/2, parent.getY() + parent.getHeight()/2));
        }


    }

    public void act() {


        // Update Trail every n nanoseconds
        if (TimeUtils.timeSinceNanos(logTime) > 20000000) {

            // Update trailPoints
            Vector2 tempPoint = new Vector2(parent.getX() + parent.getWidth()/2, parent.getY() + parent.getHeight()/2);
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

    public void draw() {
        //super.draw(batch, parentAlpha);
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
            float newColA = colorTrail.a + (aDiff * i / trailSize);

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



        // FOR DEBUG, DRAWS trailpoints in red
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(trailPoints.get(1), trailPoints.get(1));
        for (int i = 0; i < trailPoints.size() - 2; i++) {
            shapeRenderer.line(trailPoints.get(i), trailPoints.get(i+1));
        }
        shapeRenderer.end();
    }
}
