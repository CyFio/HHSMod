package HHSMod.actions.blue;

import HHSMod.HHSMod;
import HHSMod.orbs.BronzeOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BronzeStasisAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(HHSMod.makeID(BronzeStasisAction.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;
    private final BronzeOrb orb;
    private final AbstractPlayer player;

    public BronzeStasisAction(BronzeOrb orb, boolean optional) {
        this.orb = orb;
        this.player = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    /**
     *
     */
    @Override
    public void update() {
        if (orb == null || player == null || orb.StasisUsed() || player.drawPile.isEmpty()) {
            isDone = true;
            return;
        }
        if (this.duration == this.startDuration) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.player.drawPile.group) {
                tmp.addToRandomSpot(c);
            }

            if (tmp.size() == 1) {
                AbstractCard c = tmp.getTopCard();
                /*凝滞*/
                orb.Stasis(c);
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0], false);
            tickDuration();
            HHSMod.logger.info("选卡");
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            c.unhover();
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            /*凝滞*/
            HHSMod.logger.info("凝滞");
            orb.Stasis(c);
        }
//        tickDuration();
        isDone = true;
        return;
    }
}
