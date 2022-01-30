package york.eng1.team18;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import york.eng1.team18.views.MainScreen;

public class WorldContactListener implements ContactListener {

    private MainScreen parent;

    public WorldContactListener(MainScreen parent){
        this.parent = parent;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fb.getType() != Shape.Type.Circle) {
            if (fb.getUserData() == "top" || fb.getUserData() == "bottom") {
                parent.player.currentSpeed = 0;
                fb.getBody().applyForceToCenter(new Vector2(-1000, -1000), true);
                parent.player.contact_side = fb.getUserData().toString();

            } else if (fb.getUserData() == "left" || fb.getUserData() == "right") {
                parent.player.currentSpeed = parent.player.currentSpeed / 2;
                fb.getBody().applyForceToCenter(new Vector2(-100, -100), true);
                parent.player.contact_side = fb.getUserData().toString();

            } else {
                parent.player.currentSpeed = parent.player.currentSpeed / 2;
                fb.getBody().applyForceToCenter(new Vector2(-1000, -1000), true);
                parent.player.is_contact = true;
                parent.player.contact_side = "";

            }
        } else{
            if (fa.getUserData() == "Player" ){
                parent.player.hit = true;
            }
            if (fa.getUserData() == "Collage"){
                parent.player.hit_collage = true;
                fa.getBody().setUserData("Hit");
            }
            if (fa.getUserData() == "Enemy Base"){
                parent.player.hit_enemyBase = true;
                fa.getBody().setUserData("Hit");
            }
        }
//        System.out.println(fa.getUserData());
//        System.out.println(fb.getUserData());
    }

    @Override
    public void endContact(Contact contact) {
        parent.player.is_contact = false;
        parent.player.contact_side = "";
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
