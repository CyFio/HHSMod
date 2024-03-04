package HHSMod.powers.blue;

import HHSMod.powers.BasePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class LoseFocusPower extends BasePower {
    private static final boolean TURN_BASED = true;
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    public static final String POWER_ID = makeID(LoseFocusPower.class.getSimpleName());
    public LoseFocusPower(AbstractPlayer p, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, p, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, -this.amount), -this.amount));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    }
}
