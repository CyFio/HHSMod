package HHSMod.orbs;

import HHSMod.HHSMod;
import HHSMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

public class IronWaveOrb extends AbstractOrb {
    public final static String ORB_ID = HHSMod.makeID(IronWaveOrb.class.getSimpleName());
    public final static OrbStrings ORB_STRINGS = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private final static Texture ORB_IMG = TextureLoader.getTexture(HHSMod.imagePath("orbs/ironwave.png"));

    public IronWaveOrb() {
        this.ID = ORB_ID;
        this.baseEvokeAmount = 5;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 3;
        this.passiveAmount = this.basePassiveAmount;
        this.img = ORB_IMG;
        this.name = ORB_STRINGS.NAME;
        updateDescription();
        this.channelAnimTimer = 0.5F;
    }

    /**
     *
     */
    @Override
    public void updateDescription() {
        applyFocus();
        this.description = ORB_STRINGS.DESCRIPTION[0] + this.passiveAmount + ORB_STRINGS.DESCRIPTION[1] +   this.passiveAmount + ORB_STRINGS.DESCRIPTION[2] + this.evokeAmount + ORB_STRINGS.DESCRIPTION[3] + this.evokeAmount + ORB_STRINGS.DESCRIPTION[4];
        HHSMod.logger.info(description);
    }

    /**
     *
     */
    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.evokeAmount));
        if (AbstractDungeon.player.hasPower("Electro")) {
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS), true));

        } else {
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS), false));
        }
    }

    @Override
    public void onEndOfTurn() {
        float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE) {

            speedTime = 0.0F;

        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), speedTime));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.passiveAmount, true));
        if (AbstractDungeon.player.hasPower("Electro")) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));

            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), true));

        } else {

            AbstractDungeon.actionManager.addToBottom(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), this, false));

        }
    }

    /**
     * @return
     */
    @Override
    public AbstractOrb makeCopy() {
        return new IronWaveOrb();
    }

    /**
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        this.c.a /= 2.0f;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
        renderText(sb);
        this.hb.render(sb);
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
        super.triggerEvokeAnimation();
        CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(this.cX, this.cY));
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }
}
