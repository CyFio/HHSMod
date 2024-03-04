package HHSMod.orbs;

import HHSMod.HHSMod;
import HHSMod.actions.blue.BronzeStasisAction;
import HHSMod.actions.common.PlayCardsAction;
import HHSMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;

import java.util.ArrayList;

/**
 * 生成时，如果抽牌堆有牌，选择抽牌堆里一张牌凝滞
 * 回合开始时，如果未凝滞牌，则尝试选择抽牌堆一张牌凝滞
 * 激发时，自动打出这张牌
 */
public class BronzeOrb extends AbstractOrb {
    public final static String ORB_ID = HHSMod.makeID(BronzeOrb.class.getSimpleName());
    public final static OrbStrings ORB_STRINGS = CardCrawlGame.languagePack.getOrbString(ORB_ID);

    private final static Texture ORB_IMG = TextureLoader.getTexture(HHSMod.imagePath("orbs/bronze.png"));
    private AbstractCard card;

    public BronzeOrb() {
        this.baseEvokeAmount = 1;
        this.basePassiveAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.passiveAmount = this.basePassiveAmount;
        this.img = ORB_IMG;
        this.name = ORB_STRINGS.NAME;
//        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        updateDescription();
    }
    public BronzeOrb(boolean triggerEffect) {
        this.baseEvokeAmount = 1;
        this.basePassiveAmount = 1;
        this.evokeAmount = this.baseEvokeAmount;
        this.passiveAmount = this.basePassiveAmount;
        this.img = ORB_IMG;
        this.name = ORB_STRINGS.NAME;
        updateDescription();
        if (triggerEffect) {
            onStartOfTurn();
        }
    }
    /**
     *
     */
    @Override
    public void updateDescription() {
        if (card != null) {
            this.description = ORB_STRINGS.DESCRIPTION[0] + card.name + ORB_STRINGS.DESCRIPTION[1];
        } else {
            this.description = ORB_STRINGS.DESCRIPTION[0] + ORB_STRINGS.DESCRIPTION[1];
        }
//        this.update();
        HHSMod.logger.info(this.name + ":" + this.description);
    }

    /**
     *
     */
    @Override
    public void onEvoke() {
        if (this.card != null) {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            for (int i = 0; i < this.evokeAmount; i++) {
                AbstractCard tmp = card;
                if (i != 0) {
                    tmp = card.makeSameInstanceOf();
                    tmp.purgeOnUse = true;
                }
                tmp.current_y = -200.0F * Settings.scale;
                tmp.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                tmp.targetAngle = 0.0F;
                tmp.lighten(false);
                tmp.drawScale = 0.12F;
                tmp.targetDrawScale = 0.75F;
                tmp.applyPowers();
                cards.add(tmp);
            }
            AbstractDungeon.actionManager.addToTop(new PlayCardsAction(cards, true));
            this.card = card.makeSameInstanceOf();
            this.card.purgeOnUse = true;
        }
    }

    @Override
    public void onStartOfTurn() {
        if (!StasisUsed()) {
            AbstractDungeon.actionManager.addToTop(new BronzeStasisAction(this, true));
        }
    }

//    @Override
//    public void onEndOfTurn() {
//        if (!StasisUsed()) {
//            AbstractDungeon.actionManager.addToBottom(new BronzeStasisAction(this));
//        }
//    }

    /**
     * @return
     */
    @Override
    public AbstractOrb makeCopy() {
        return new BronzeOrb();
    }

    /**
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        TextureAtlas.AtlasRegion glow = ImageMaster.EXHAUST_S;
        sb.setBlendFunction(770, 1);
        sb.setColor(0.6F, 0.6F, 0.21F, 1.0F);
        sb.draw(glow, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y + MathUtils.sin(this.angle / 12.566371F) * 8.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 0.66F + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 0.8F, this.angle);
        sb.draw(glow, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 0.8F, this.scale * 0.66F + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        this.hb.render(sb);
    }

    @Override
    public void applyFocus() {
        this.passiveAmount = this.basePassiveAmount;
        this.evokeAmount = this.baseEvokeAmount;
        onStartOfTurn();
    }

    /**
     *
     */
    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1F);
    }

    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
    }

    public boolean StasisUsed() {
        return this.card != null;
    }

    public void Stasis(AbstractCard c) {
        if (StasisUsed()) {
            return;
        }
        this.card = c;
        this.card.unhover();
        AbstractDungeon.player.drawPile.removeCard(c);
        AbstractDungeon.player.limbo.addToBottom(this.card);
        this.card.setAngle(0.0F);
        this.card.targetDrawScale = 0.75F;
        this.card.target_x = Settings.WIDTH / 2.0F;
        this.card.target_y = Settings.HEIGHT / 2.0F;
        this.card.lighten(false);
        this.card.unfadeOut();
        this.card.untip();
        this.card.stopGlowing();
        HHSMod.logger.info("展示凝滞卡");
        AbstractDungeon.actionManager.addToTop(new ShowCardAction(this.card));
        updateDescription();
    }
}
