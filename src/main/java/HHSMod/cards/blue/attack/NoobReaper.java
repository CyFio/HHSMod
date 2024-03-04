package HHSMod.cards.blue.attack;

import HHSMod.cards.BaseCard;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.combat.ReaperEffect;


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
        this.isMultiDamage = true;
        this.setDamage(3, 1);
        this.setMagic(3,1);
        this.baseExhaust = true;
        this.exhaust = true;
        this.upgExhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = this.magicNumber;
        calculateCardDamage(m);
        addToBot(new VFXAction(new ReaperEffect()));
        addToBot(new VampireDamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        this.baseDamage = this.magicNumber;
        AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (focus != null)
        {
            this.baseDamage += focus.amount;
        }
        super.calculateCardDamage(m);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.magicNumber;
        AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if (focus != null)
        {
            this.baseDamage += focus.amount;
        }
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new NoobReaper();
    }
}
