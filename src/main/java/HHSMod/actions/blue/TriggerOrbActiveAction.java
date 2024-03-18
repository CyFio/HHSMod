package HHSMod.actions.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class TriggerOrbActiveAction extends AbstractGameAction
{

    private final AbstractOrb orb;

    public TriggerOrbActiveAction(AbstractOrb orb)
    {
        this.orb = orb;
    }

    /**
     *
     */
    @Override
    public void update() {
        if(orb != null)
        {
            orb.triggerEvokeAnimation();
            orb.onEvoke();
        }
        this.isDone = true;
    }
}
