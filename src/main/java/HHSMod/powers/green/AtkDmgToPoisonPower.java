package HHSMod.powers.green;

import HHSMod.powers.BasePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class AtkDmgToPoisonPower extends BasePower {
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final String POWER_ID = makeID(AtkDmgToPoisonPower.class.getSimpleName());
    private static final boolean TURN_BASED = true;

    private int damage = 0;

    public AtkDmgToPoisonPower(AbstractCreature player) {
        super(POWER_ID, TYPE, TURN_BASED, player, -1);
        this.priority = 99;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner == owner && info.type == DamageInfo.DamageType.NORMAL) {
            damage = damageAmount;
            return 0;
        }
        return damageAmount;
    }

    // 这个方法在onAttackToChangeDamage之后调用
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damage > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToTop(new ApplyPowerAction(target, this.owner, new PoisonPower(target, this.owner, damage), damage, true));
        }
    }
}
