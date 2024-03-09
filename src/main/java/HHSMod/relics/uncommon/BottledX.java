package HHSMod.relics.uncommon;

import HHSMod.HHSMod;
import HHSMod.relics.BaseRelic;
import HHSMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public class BottledX extends BaseRelic {
    public final static String ID = HHSMod.makeID("BottledX");
    private static final Texture IMG = TextureLoader.getTexture(HHSMod.imagePath("relics/bottledX.png"));
    public BottledX(){
        super(ID, "bottledX", RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }
}
