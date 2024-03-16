package HHSMod.powers.blue;

import HHSMod.powers.BasePower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class ForeignOrbsPower extends BasePower
{
    public static final String POWER_ID = makeID(ForeignOrbsPower.class.getSimpleName());
    static public Map<String, AbstractOrb> orbs = new HashMap<>();

    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public ForeignOrbsPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (target instanceof AbstractPlayer)
        {
            AbstractPlayer player = (AbstractPlayer) target;
            if (player.masterMaxOrbs <= 0)
            {
                addToTop(new IncreaseMaxOrbAction(1));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
        for (AbstractOrb o: orbs.values())
        {
            description = description + o.name + DESCRIPTIONS[1];
        }
//        description += DESCRIPTIONS[2];
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
//        super.renderAmount(sb, x, y, c);
    }
}
