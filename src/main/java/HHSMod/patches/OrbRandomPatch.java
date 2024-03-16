package HHSMod.patches;

import HHSMod.orbs.BronzeOrb;
import HHSMod.powers.blue.ForeignOrbsPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(clz = AbstractOrb.class, method = "getRandomOrb")
public class OrbRandomPatch {
    @SpireInsertPatch(locator = Locator.class, localvars = {"orbs"})
    public static void Insert(boolean useCardRng, ArrayList<AbstractOrb> orbs) {
        for (AbstractOrb ob : ForeignOrbsPower.orbs.values())
        {
            orbs.add(ob.makeCopy());
        }
        orbs.add(new BronzeOrb());
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
            return LineFinder.findInOrder(ctBehavior, (Matcher) methodCallMatcher);
        }
    }
}
