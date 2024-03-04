package HHSMod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PlayCardsAction
        extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(PlayCardsAction.class);
    private final ArrayList<AbstractCard> cards;

    private final boolean useCurrentEnergy;

    public PlayCardsAction(ArrayList<AbstractCard> cards, boolean useCurrentEnergy) {
        this.cards = cards;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.useCurrentEnergy = useCurrentEnergy;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractCard c : this.cards) {
                c.freeToPlayOnce = true;
                logger.info("打出了" + c.name);
                int energy = useCurrentEnergy ? EnergyPanel.getCurrentEnergy() : c.energyOnUse;
                switch (c.target) {
                    case ENEMY:
                    case SELF_AND_ENEMY:
                        AbstractDungeon.actionManager.addCardQueueItem(
                                new CardQueueItem(c, AbstractDungeon.getRandomMonster(), energy, true, true),
                                true);
                        continue;
                }
                AbstractDungeon.actionManager
                        .addCardQueueItem(new CardQueueItem(c, null, energy, true, true), true);
            }
        }
        tickDuration();
    }
}
