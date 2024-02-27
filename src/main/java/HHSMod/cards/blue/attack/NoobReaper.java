package HHSMod.cards.blue.attack;

import HHSMod.cards.BaseCard;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class NoobReaper extends BaseCard {
    public static final String ID = makeID(NoobReaper.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );
    public NoobReaper(){
        super(ID, info);
        this.setDamage(3,1);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new NoobReaper();
    }
}
