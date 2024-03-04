package HHSMod.cards.blue.skill;

import HHSMod.cards.BaseCard;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class LimiterRemoval extends BaseCard {
    public static final String ID = makeID(LimiterRemoval.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    public LimiterRemoval(){
        super(ID, info);
        this.exhaust = true;
        this.baseExhaust = true;
        this.upgExhaust = false;
        this.upgradesDescription = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = p.getPower(FocusPower.POWER_ID);
        if(power != null)
        {
            addToTop(new ApplyPowerAction(p, p, new FocusPower(p, power.amount)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LimiterRemoval();
    }
}
