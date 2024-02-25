package HHSMod.cards.green.attack;

import HHSMod.cards.BaseCard;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class StrikeThePoisonous extends BaseCard
{
    public static final String ID = makeID(StrikeThePoisonous.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );
    public StrikeThePoisonous(){
        super(ID, info);
        this.setMagic(3, 1);
        this.setDamage(9, 3);
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower poisonPower = m.getPower(PoisonPower.POWER_ID);
        if (poisonPower != null)
        {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        }
        else
        {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new StrikeThePoisonous();
    }
}
