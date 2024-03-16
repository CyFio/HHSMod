package HHSMod.cards.red.skill;

import HHSMod.actions.common.ForeignCardAction;
import HHSMod.cards.BaseCard;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForeignPower extends BaseCard {
    public static final String ID = makeID(ForeignPower.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );
    public ForeignPower() {
        super(ID, info);
        setExhaust(true, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ForeignCardAction(upgraded, CardType.POWER, true, false));
    }
}
