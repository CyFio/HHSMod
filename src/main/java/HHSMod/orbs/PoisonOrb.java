package HHSMod.orbs;

import HHSMod.HHSMod;
import HHSMod.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

public class PoisonOrb extends AbstractOrb {
    public final static String ORB_ID = HHSMod.makeID(PoisonOrb.class.getSimpleName());
    public final static OrbStrings ORB_STRINGS = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private final static Texture ORB_IMG = TextureLoader.getTexture(HHSMod.imagePath("orbs/poison.png"));
    private float vfxTimer = 0.0f;

    public PoisonOrb() {
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
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (randomMonster == null) {
            return;
        }
        if (AbstractDungeon.player.hasPower("Electro")) {
            AbstractDungeon.actionManager.addToTop(new BouncingFlaskAction(randomMonster, this.evokeAmount, 2));

        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(randomMonster, AbstractDungeon.player, new PoisonPower(randomMonster, AbstractDungeon.player, evokeAmount)));
        }
    }

    @Override
    public void onStartOfTurn() {
        float speedTime = 0.2F / AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE) {

            speedTime = 0.0F;

        }
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), speedTime));
        if (AbstractDungeon.player.hasPower("Electro")) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    if (!m.isDead && !m.isDying) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, this.passiveAmount), this.passiveAmount));
                    }
                }
            }
        } else {
            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new PoisonPower(m, AbstractDungeon.player, this.passiveAmount), this.passiveAmount));
            }
        }

    }

    @Override
    public void onEndOfTurn() {
    }

    /**
     * @return
     */
    @Override
    public AbstractOrb makeCopy() {
        return new PoisonOrb();
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