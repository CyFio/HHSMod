package HHSMod.cards.blue.skill;

import HHSMod.cards.BaseCard;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class ReCompute extends BaseCard {
    public static final String ID = makeID(ReCompute.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );
    public ReCompute(){
        super(ID, info);
        this.setMagic(3, 1);
//        this.upgradesDescription = true;
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = p.getPower(FocusPower.POWER_ID);
        addToTop(new ApplyPowerAction(p, p, new FocusPower(p, power.amount)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ReCompute();
    }
}
