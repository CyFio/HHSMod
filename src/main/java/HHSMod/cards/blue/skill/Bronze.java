package HHSMod.cards.blue.skill;

import HHSMod.cards.BaseCard;
import HHSMod.orbs.BronzeOrb;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bronze extends BaseCard
{
    public static final String ID = makeID(Bronze.class.getSimpleName());

    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );
    public Bronze(){
        super(ID, info);
        setMagic(1, 0);
        setCostUpgrade(0);
    }

    /**
     * @param p: player
     * @param m: moster
     */
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        BronzeOrb orb = new BronzeOrb();
        addToBot(new ChannelAction(orb));
    }
}
