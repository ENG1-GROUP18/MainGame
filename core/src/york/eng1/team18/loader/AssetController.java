package york.eng1.team18.loader;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

import java.io.File;
import java.util.logging.FileHandler;

public class AssetController {

    public final AssetManager manager = new AssetManager();


    // Images
    public final String logoImage = "images/logo330w.png";

    // Colours
    public final Color waterCol = new Color(122/255f, 180/255f, 196/255f, 1);
    public final Color trailCol = new Color(153/255f, 193/255f, 201/255f, 1);
    public final Color landCol = new Color(165/255f, 189/255f, 145/255f, 1);
    public final Color landCol2 = new Color(138/255f, 189/255f, 145/255f, 1);
}
