package HHSMod.actions.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class TriggerOrbPassiveAction extends AbstractGameAction
{

    private final AbstractOrb orb;

    public TriggerOrbPassiveAction(AbstractOrb orb)
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
            orb.onStartOfTurn();
        }
        if(orb != null)
        {
            orb.onEndOfTurn();
        }
        this.isDone = true;
    }
}
