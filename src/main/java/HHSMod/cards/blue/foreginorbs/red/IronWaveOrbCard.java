package HHSMod.cards.blue.foreginorbs.red;

import HHSMod.cards.blue.foreginorbs.OrbCard;
import HHSMod.orbs.IronWaveOrb;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IronWaveOrbCard extends OrbCard {
    public static final String ID = makeID(IronWaveOrbCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );
//    "red/attack/iron_wave"
    public IronWaveOrbCard() {
        super(ID, info);

        setCostUpgrade(1);
        setMagic(1, 1);
    }

    @Override
    public AbstractOrb makeOrb() {
        return new IronWaveOrb();
    }
}
