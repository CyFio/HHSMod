package HHSMod.relics.uncommon;

import HHSMod.HHSMod;
import HHSMod.cardmodifiers.XifyModifer;
import HHSMod.relics.BaseRelic;
import HHSMod.util.TextureLoader;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BottledX extends BaseRelic {
    public final static String ID = HHSMod.makeID("BottledX");
    private static final Texture IMG = TextureLoader.getTexture(HHSMod.imagePath("relics/bottledX.png"));
    public BottledX(){
        super(ID, "bottledX", RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            CardModifierManager.addModifier(card, new XifyModifer());
        }
    }
}
