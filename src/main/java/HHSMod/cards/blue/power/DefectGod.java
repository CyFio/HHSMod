package HHSMod.cards.blue.power;

import HHSMod.actions.DefectGodAction;
import HHSMod.cards.BaseCard;
import HHSMod.powers.blue.DefectGodPower;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DefectGod extends BaseCard {
    public static final String ID = makeID(DefectGod.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    public DefectGod(){
        super(ID, info);
        this.setMagic(1, 0);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, (AbstractPower)new DefectGodPower(p, this.magicNumber), this.magicNumber));
        if (this.upgraded)
        {
            addToBot(new DefectGodAction(p, p, magicNumber, true));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DefectGod();
    }
}
