package HHSMod.cards.blue.foreginorbs;

import HHSMod.cards.BaseCard;
import HHSMod.powers.blue.ForeignOrbsPower;
import HHSMod.util.CardStats;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

public abstract class OrbCard extends BaseCard {
    public AbstractOrb makeOrb(){
        return new Lightning();
    }
    public OrbCard(String ID, CardStats info) {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractOrb orb = makeOrb();
        AbstractPower pow = p.getPower(ForeignOrbsPower.POWER_ID);
        if (pow instanceof ForeignOrbsPower){
            ForeignOrbsPower fop = (ForeignOrbsPower) pow;
            if (!fop.orbs.containsKey(orb.ID)){
                fop.orbs.put(orb.ID, orb);
                fop.updateDescription();
            }
        }
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new ChannelAction(orb.makeCopy()));
        }
    }
}
