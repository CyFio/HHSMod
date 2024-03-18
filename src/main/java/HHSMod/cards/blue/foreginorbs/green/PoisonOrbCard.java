package HHSMod.cards.blue.foreginorbs.green;

import HHSMod.actions.blue.TriggerOrbActiveAction;
import HHSMod.actions.blue.TriggerOrbPassiveAction;
import HHSMod.cards.blue.foreginorbs.OrbCard;
import HHSMod.orbs.PoisonOrb;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class PoisonOrbCard extends OrbCard {
    public static final String ID = makeID(PoisonOrbCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    //    "red/attack/iron_wave"
    public PoisonOrbCard() {
        super(ID, info);

        setCostUpgrade(1);
        setMagic(1, 0);
    }

    @Override
    public AbstractOrb makeOrb() {
        return new PoisonOrb();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        if (upgraded) {
            for (AbstractOrb orb : p.orbs) {
                if (orb instanceof PoisonOrb) {
                    addToBot(new TriggerOrbActiveAction(orb));
                }
            }
        } else {
            for (AbstractOrb orb : p.orbs) {
                if (orb instanceof PoisonOrb) {
                    addToBot(new TriggerOrbPassiveAction(orb));
                }
            }
        }
    }
}
