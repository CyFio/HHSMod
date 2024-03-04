package HHSMod.cards.blue.skill;

import HHSMod.cards.BaseCard;
import HHSMod.powers.blue.ChargedBarrierPower;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChargedBarrier extends BaseCard
{
    public static final String ID = makeID(ChargedBarrier.class.getSimpleName());

    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );
    public ChargedBarrier(){
        super(ID, info);
        setBlock(12, 4);
        setMagic(1, 0);
    }

    /**
     * @param p: player
     * @param m: moster
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new ChargedBarrierPower(p, magicNumber), magicNumber));
    }
}
