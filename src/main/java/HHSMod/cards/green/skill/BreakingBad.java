package HHSMod.cards.green.skill;

import HHSMod.cards.BaseCard;
import HHSMod.powers.green.AtkDmgToPoisonPower;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BreakingBad extends BaseCard {
    public static final String ID = makeID(BreakingBad.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );
    public BreakingBad() {
        super(ID, info);
        this.setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AtkDmgToPoisonPower(p)));
    }
}
