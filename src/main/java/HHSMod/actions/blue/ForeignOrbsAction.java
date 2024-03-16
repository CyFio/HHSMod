package HHSMod.actions.blue;

import HHSMod.HHSMod;
import HHSMod.cards.blue.foreginorbs.OrbCard;
import HHSMod.util.CardLibraryHelper;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;

public class ForeignOrbsAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private final boolean bFreeForTurn;
    private final boolean bFreeForBattle;
    private final boolean upgrade;

    public ForeignOrbsAction(boolean upgrade, boolean bFreeForTurn, boolean bFreeForBattle)
    {
        this.bFreeForTurn = bFreeForTurn;
        this.bFreeForBattle = bFreeForBattle;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgrade = upgrade;
    }

    /**
     * 
     */
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (this.bFreeForTurn) {
                        disCard.setCostForTurn(0);
                    }
                    if (this.bFreeForBattle)
                    {
                        disCard.updateCost(0);
                    }

                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard.CardColor> colors = CardLibraryHelper.colors;
        ArrayList<AbstractCard> derp = new ArrayList<>();

        for(AbstractCard.CardColor color : colors)
        {
            HHSMod.logger.info(color);
            if (color == AbstractDungeon.player.getCardColor())
            {
                continue;
            }
            if(color != AbstractCard.CardColor.COLORLESS && color != AbstractCard.CardColor.CURSE)
            {
                int roll = AbstractDungeon.cardRandomRng.random(99);
                AbstractCard.CardRarity cardRarity;
                if (roll < 55) {
                    cardRarity = AbstractCard.CardRarity.COMMON;
                } else if (roll < 85) {
                    cardRarity = AbstractCard.CardRarity.UNCOMMON;
                } else {
                    cardRarity = AbstractCard.CardRarity.RARE;
                }
                AbstractCard card = CardLibraryHelper.GetRandomCard(color, (AbstractCard c) -> c instanceof OrbCard, true);
                if(card == null)
                {
                    continue;
                }
                AbstractCard copy = card.makeCopy();
                if (upgrade)
                {
                    copy.upgrade();
                }
                derp.add(copy);
                HHSMod.logger.info(copy.name);
            }
        }
        HHSMod.logger.info(derp.size());

        return derp;
    }
}
