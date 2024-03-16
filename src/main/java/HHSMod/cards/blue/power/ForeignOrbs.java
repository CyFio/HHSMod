package HHSMod.cards.blue.power;

import HHSMod.actions.blue.ForeignOrbsAction;
import HHSMod.cards.BaseCard;
import HHSMod.powers.blue.ForeignOrbsPower;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForeignOrbs extends BaseCard {
    public static final String ID = makeID(ForeignOrbs.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    public ForeignOrbs(){
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ForeignOrbsPower(p)));
        addToBot(new ForeignOrbsAction(upgraded, false, false));
    }
}
