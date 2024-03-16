package HHSMod.cards.green.skill;

import HHSMod.actions.common.ForeignCardAction;
import HHSMod.cards.BaseCard;
import HHSMod.powers.green.AtkDmgToPoisonPower;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ForeignInfluenceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javax.smartcardio.Card;

public class ForeignSkill extends BaseCard {
    public static final String ID = makeID(ForeignSkill.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );
    public ForeignSkill() {
        super(ID, info);
        setExhaust(true, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ForeignCardAction(upgraded, CardType.SKILL, true, false));
    }
}
