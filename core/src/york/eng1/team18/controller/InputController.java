package york.eng1.team18.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {

    public boolean forward;
    public boolean backward;
    public boolean left;
    public boolean right;
    public boolean space;

    public boolean leftClick;
    public boolean rightClick;

    public boolean escape;

    /**
     * Handles the logic when a key is pressed down
     * @param keycode an int representing the input key
     * @return returns true if valid keycode entered
     */
    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.W:
                forward = true;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                backward = true;
                keyProcessed = true;
                break;
            case Input.Keys.A:
                left = true;
                keyProcessed = true;
                break;
            case Input.Keys.D:
                right = true;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                space = true;
                keyProcessed = true;
                break;
            case Input.Keys.ESCAPE:
                escape = true;
                keyProcessed = true;
                break;


        }
        return keyProcessed;
    }

    /**
     * Handles the logic when a key is let go
     * @param keycode an int representing the input key
     * @return returns true if valid keycode entered
     */
    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.W:
                forward = false;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                backward = false;
                keyProcessed = true;
                break;
            case Input.Keys.A:
                left = false;
                keyProcessed = true;
                break;
            case Input.Keys.D:
                right = false;
                keyProcessed = true;
                break;
            case Input.Keys.SPACE:
                space = false;
                keyProcessed = true;
                break;
            case Input.Keys.ESCAPE:
                escape = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }



    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Handles mouse input
     * @param screenX an int representing the mouse x coords
     * @param screenY an int representing the mouse y coords
     * @param pointer an int representing the pointer for the event
     * @param button an int representing which button was pressed down
     * @return returns true if valid button entered
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean buttonProcessed = false;
        switch (button) {
            case Input.Buttons.LEFT:
                leftClick = true;
                buttonProcessed = true;
                break;
            case Input.Buttons.RIGHT:
                rightClick = true;
                buttonProcessed = true;
                break;
        }

        return buttonProcessed;
    }

    /**
     * Handles mouse input
     * @param screenX an int representing the mouse x coords
     * @param screenY an int representing the mouse y coords
     * @param pointer an int representing the pointer for the event
     * @param button an int representing which button was let go
     * @return returns true if valid button entered
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean buttonProcessed = false;
        switch (button) {
            case Input.Buttons.LEFT:
                leftClick = false;
                buttonProcessed = true;
                break;
            case Input.Buttons.RIGHT:
                rightClick = false;
                buttonProcessed = true;
                break;
        }

        return buttonProcessed;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
