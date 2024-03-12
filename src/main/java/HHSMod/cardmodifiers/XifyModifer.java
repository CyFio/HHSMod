package HHSMod.cardmodifiers;

import HHSMod.HHSMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * 将卡牌变成X牌
 */
public class XifyModifer extends AbstractCardModifier {
    public static final String ID = HHSMod.makeID(XifyModifer.class.getSimpleName());
    private int basic_cost = 0;
    private int basic_damage;
    private int basic_block;
    private int basic_magic;
    private AbstractCard card;

    public XifyModifer(){
        priority = 99;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    /**
     * @return
     */
    @Override
    public AbstractCardModifier makeCopy() {
        return new XifyModifer();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.card = card;
        RefreshXify();
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        card.dontTriggerOnUseCard = true;
        action.amount = card.energyOnUse;
        card.dontTriggerOnUseCard = false;
        HHSMod.logger.info("能量:" + card.costForTurn);
    }

    private void RefreshXify()
    {
        this.basic_cost = card.cost;
        this.basic_damage = card.damage;
        this.basic_block = card.block;
        this.basic_magic = card.magicNumber;
        card.cost = -1;
//        card.isCostModified = false;
    }

    public int getTimes() {
        HHSMod.logger.info("打:" + card.energyOnUse + "次");
        return card.energyOnUse;
    }
}
