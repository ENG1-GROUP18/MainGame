package york.eng1.team18;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Map;

public class LeaderboardHandler {
    /**
     * Not implemented
     */

    private static final String PREFS_NAME = "leaderboardSave";
    private static final int max_items = 5;

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public void fillWithScores() {
        getPrefs().putInteger("1", 500);
        getPrefs().putInteger("2", 440);
        getPrefs().putInteger("3", 310);
        getPrefs().putInteger("4", 100);
        getPrefs().putInteger("5", 60);
    }

    // one based indexing, 1st place score is accessed with pos 1
    public int getScoreAtPos(int pos) {
        if (pos < max_items) {
            int score = getPrefs().getInteger(String.valueOf(pos + 1));
            return score;
        } else {return 0;}
    }

    public void addScoreToList(int score) {
        for (int i = 0; i < getPrefs().get().size(); i++) {
            if (getPrefs().getInteger(String.valueOf(i + 1)) < score){
                for (int j = i; j < getPrefs().get().size() - 1; j++) {
                    getPrefs().putInteger(String.valueOf(j + 2), getPrefs().getInteger(String.valueOf(j + 1)));
                }
                getPrefs().putInteger(String.valueOf(i + 1), score);
                break;
            }
        }
    }
}
