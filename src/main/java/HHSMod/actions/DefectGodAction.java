package HHSMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class DefectGodAction extends AbstractGameAction {
    private final boolean isRandom;
    private AbstractPlayer p;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public DefectGodAction(AbstractPlayer target, AbstractCreature source, int amount, boolean isRandom) {
        this.p = target;
        setValues(target, source, amount);
        this.isRandom = isRandom;
        this.duration = DURATION;
    }

    /**
     *
     */
    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                int tmp = this.p.hand.size();
                for (int i = 0; i < tmp; i++) {
                    AbstractCard c = this.p.hand.getTopCard();
                    UpgradeAndPowerize(c);
                }
                this.isDone = true;
                return;
            }
            List<AbstractCard> nonUpgradedCards = new ArrayList<>();
            List<AbstractCard> upgradableCards = new ArrayList<>();

            // 分离未升级的牌和可以升级的牌
            for (AbstractCard card : this.p.hand.group) {
                if (card.canUpgrade()) {
                    nonUpgradedCards.add(card);
                } else {
                    upgradableCards.add(card);
                }
            }

            List<AbstractCard> selectedCards = new ArrayList<>();
            // 优先选择未升级的牌
            while (selectedCards.size() < amount && !nonUpgradedCards.isEmpty()) {
                int index = 0;
                if(isRandom){
                    index = AbstractDungeon.cardRandomRng.random((nonUpgradedCards.size() - 1));
                }
                selectedCards.add(nonUpgradedCards.remove(index));

            }

            // 如果未升级的牌数量不足 x 张，则从可以升级的牌中选择
            while (selectedCards.size() < amount && !upgradableCards.isEmpty()) {
                int index = 0;
                if(isRandom) {
                    index = AbstractDungeon.cardRandomRng.random((upgradableCards.size() - 1));
                }
                selectedCards.add(upgradableCards.remove(index));
            }
            for (AbstractCard c : selectedCards)
            {
                UpgradeAndPowerize(c);
            }
            this.isDone = true;
            return;
        }
    }

    protected void UpgradeAndPowerize(AbstractCard c) {
        if (c.canUpgrade()) {
            c.upgrade();
        }
        c.type = AbstractCard.CardType.POWER;
        c.superFlash();
        c.applyPowers();
        c.update();
    }
}
