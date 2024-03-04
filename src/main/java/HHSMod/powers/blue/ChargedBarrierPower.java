package HHSMod.powers.blue;

import HHSMod.actions.blue.TriggerOrbPassiveAction;
import HHSMod.powers.BasePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class ChargedBarrierPower extends BasePower {
    private static final boolean TURN_BASED = true;
    private static final PowerType TYPE = PowerType.BUFF;
    public static final String POWER_ID = makeID(ChargedBarrierPower.class.getSimpleName());

    public ChargedBarrierPower(AbstractPlayer p, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, p, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    /**
     *
     * @param info
     * @param damageAmount
     * @return
     */
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount <= 0 && !AbstractDungeon.player.orbs.isEmpty()) {
            flash();
            for (int i = 0; i < this.amount; i++) {
                for(AbstractOrb orb : AbstractDungeon.player.orbs)
                {
                    addToTop(new TriggerOrbPassiveAction(orb));
                }
            }
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, ID));
    }
}
