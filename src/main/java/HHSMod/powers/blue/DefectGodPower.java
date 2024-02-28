package HHSMod.powers.blue;

import HHSMod.actions.blue.DefectGodAction;
import HHSMod.powers.BasePower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DefectGodPower extends BasePower {
    public static final String POWER_ID = makeID(DefectGodPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public DefectGodPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        AbstractPlayer p = (AbstractPlayer) this.owner;
        if (p != null) {
            if (card.type == AbstractCard.CardType.POWER && this.amount > 0) {
                flash();
                for (int i = 0; i < this.amount; i++) {
                    addToBot(new DefectGodAction(p, this.owner, this.amount, true));
                }
            }
        }
    }
}
