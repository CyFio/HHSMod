package HHSMod.cards.blue.foreginorbs.purple;

import HHSMod.actions.blue.TriggerOrbActiveAction;
import HHSMod.actions.blue.TriggerOrbPassiveAction;
import HHSMod.cards.blue.foreginorbs.OrbCard;
import HHSMod.orbs.DivinityOrb;
import HHSMod.orbs.PoisonOrb;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.sun.org.apache.xpath.internal.operations.Div;

public class DivinityOrbCard extends OrbCard {
    public static final String ID = makeID(DivinityOrbCard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    //    "red/attack/iron_wave"
    public DivinityOrbCard() {
        super(ID, info);
        setMagic(1, 0);
        this.cardsToPreview = new Insight();
    }

    @Override
    public AbstractOrb makeOrb() {
        return new DivinityOrb();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        if (upgraded) {
            for (AbstractOrb orb : p.orbs) {
                if (orb instanceof PoisonOrb) {
                    addToBot(new TriggerOrbPassiveAction(orb));
                }
            }
        }
        addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
    }
}
