package HHSMod.util;

import HHSMod.HHSMod;
import basemod.interfaces.StartActSubscriber;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

// 这个initializer好像没效果
@SpireInitializer
public class CardLibraryHelper implements StartActSubscriber {
    public static boolean bDirty = true;
    public static ArrayList<AbstractCard.CardColor> colors = new ArrayList<>();
    public static HashMap<AbstractCard.CardColor, ArrayList<AbstractCard>> coloredCardMap = new HashMap<>();

    public static void refresh(){
        if (!bDirty)
        {
            return;
        }
        colors.clear();
        coloredCardMap.clear();
        // 获得卡牌颜色数量
        for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
            AbstractCard.CardColor color = c.getValue().color;
//            HHSMod.logger.info(c.getValue().description);
//            HHSMod.logger.info(c.getValue().color);
            if (!colors.contains(color)) {
                colors.add(color);
            }
            if(!coloredCardMap.containsKey(color))
            {
                coloredCardMap.put(color, new ArrayList<>());
            }
            coloredCardMap.get(color).add(c.getValue());
        }
        for(AbstractCard.CardColor col : colors)
        {
            HHSMod.logger.info(col);
        }
        HHSMod.logger.info("一共有" +colors.size()+"颜色");
        bDirty = false;
    }
    /**
     *
     */
    @Override
    public void receiveStartAct() {
    }

    public static AbstractCard GetRandomCard(AbstractCard.CardColor color, AbstractCard.CardType type, AbstractCard.CardRarity rarity, boolean useRng)
    {
        if (bDirty)
        {
            refresh();
        }
        ArrayList<AbstractCard> cards = new ArrayList<>();
        if(!coloredCardMap.containsKey(color))
        {
            return null;
        }
        ArrayList<AbstractCard> base = coloredCardMap.get(color);
        for (AbstractCard c: base)
        {
            if (c.type == type && c.rarity == rarity)
            {
                cards.add(c);
            }
        }
        if (cards.isEmpty())
        {
            return null;
        }
        if(useRng)
        {
            return cards.get(AbstractDungeon.cardRng.random(cards.size() - 1));
        }
        return cards.get(MathUtils.random((cards.size() - 1)));
    }
    public static AbstractCard GetRandomCard(AbstractCard.CardColor color, Predicate<AbstractCard> predicate, boolean useRng)
    {
        if (bDirty)
        {
            refresh();
        }
        ArrayList<AbstractCard> cards = new ArrayList<>();
        if(!coloredCardMap.containsKey(color))
        {
            return null;
        }
        ArrayList<AbstractCard> base = coloredCardMap.get(color);
        for (AbstractCard c: base)
        {
            if (predicate.test(c))
            {
                cards.add(c);
            }
        }
        if (cards.isEmpty())
        {
            return null;
        }
        if(useRng)
        {
            return cards.get(AbstractDungeon.cardRng.random(cards.size() - 1));
        }
        return cards.get(MathUtils.random((cards.size() - 1)));
    }
}
