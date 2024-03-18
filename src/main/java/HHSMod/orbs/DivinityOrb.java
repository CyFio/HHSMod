package HHSMod.orbs;

import HHSMod.HHSMod;
import HHSMod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

public class DivinityOrb extends AbstractOrb {
    public final static String ORB_ID = HHSMod.makeID(DivinityOrb.class.getSimpleName());
    public final static OrbStrings ORB_STRINGS = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private final static Texture ORB_IMG = TextureLoader.getTexture(HHSMod.imagePath("orbs/divinity.png"));
    private float vfxTimer = 0.0f;

    public DivinityOrb() {
        this.ID = ORB_ID;
        this.baseEvokeAmount = 4;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
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
        this.description = ORB_STRINGS.DESCRIPTION[0] + this.passiveAmount + ORB_STRINGS.DESCRIPTION[1] + this.evokeAmount + ORB_STRINGS.DESCRIPTION[2];
        HHSMod.logger.info(description);
    }

    /**
     *
     */
    @Override
    public void onEvoke() {
        float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE) {

            speedTime = 0.0F;

        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), speedTime));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, this.evokeAmount)));
    }

    @Override
    public void onStartOfTurn() {
        float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE) {

            speedTime = 0.0F;

        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), speedTime));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, this.passiveAmount)));
    }

    @Override
    public void onEndOfTurn() {
    }

    /**
     * @return
     */
    @Override
    public AbstractOrb makeCopy() {
        return new DivinityOrb();
    }

    /**
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        TextureAtlas.AtlasRegion glow = ImageMaster.EXHAUST_S;
        sb.setBlendFunction(770, 1);
        sb.setColor(0.36F, 0.07F, 0.34F, 0.5F);
        sb.draw(glow, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 0.66F + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 0.8F, this.angle);
        sb.draw(glow, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 0.8F, this.scale * 0.66F + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        renderText(sb);
        this.hb.render(sb);
    }

    /**
     *
     */
    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1F);
    }

    @Override
    public void triggerEvokeAnimation() {
        super.triggerEvokeAnimation();
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25F;
        }
    }
}