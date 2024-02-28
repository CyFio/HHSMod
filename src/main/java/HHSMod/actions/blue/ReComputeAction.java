package HHSMod.actions.blue;

import HHSMod.HHSMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class ReComputeAction extends AbstractGameAction
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(HHSMod.makeID(ReComputeAction.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;

    private int focusIncrease;

    public ReComputeAction(AbstractPlayer p, AbstractCreature c, int focusIncrease)
    {
        this.source = p;
        this.target = c;
        this.focusIncrease = focusIncrease;
    }

    /**
     *
     */
    @Override
    public void update() {
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.intent != AbstractMonster.Intent.ATTACK) {
                addToBot(new ApplyPowerAction(target, source,new FocusPower(target, focusIncrease)));
                this.isDone = true;
                return;
            }
        }
        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        this.isDone = true;
    }
}
