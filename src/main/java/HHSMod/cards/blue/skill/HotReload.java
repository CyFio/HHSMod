package HHSMod.cards.blue.skill;

import HHSMod.cards.BaseCard;
import HHSMod.powers.blue.LoseFocusPower;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

public class HotReload extends BaseCard
{
    public static final String ID = makeID(HotReload.class.getSimpleName());

    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );
    public HotReload(){
        super(ID, info);
        setMagic(2, 2);
    }

    /**
     * @param p: player
     * @param m: moster
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(p, magicNumber), magicNumber));
    }
}
