package HHSMod.patches;

import HHSMod.HHSMod;
import HHSMod.cardmodifiers.XifyModifer;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard", requiredModId = "HHSMod")
public class XifyUsePatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "<class>")
    public static class MethodInsert
    {
        private static void patchClass(CtClass clz) throws CannotCompileException {
            HHSMod.logger.info("添加Xify方法");
            try {
                CtMethod newMethod = CtMethod.make(
                        "private void testXifyUse(AbstractCard card, AbstractPlayer p, AbstractMonster m) { " +
                                "   ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(card, XifyModifer.ID); " +
                                "   if(modifiers.size() > 0) { " +
                                "       XifyModifer modifier = (XifyModifer) modifiers.get(0); " +
                                "       int xifyTimes = modifier.getTimes(); " +
                                "       for (int i = 0; i < xifyTimes; i++) { " +
                                "           if(p != null && m != null) { " +
                                "               card.use(p, m); " +
                                "           } " +
                                "       } " +
                                "   } else { " +
                                "       HHSMod.logger.info('寄了')"+
                                "       card.use(p, m); " +
                                "   } " +
                                "}",
                        clz
                );
                clz.addMethod(newMethod);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
            // 创建一个新的函数体

        }
    }

    private void testXifyUse(AbstractCard card, AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCardModifier> modifiers = CardModifierManager.getModifiers(card, XifyModifer.ID);
        if (!modifiers.isEmpty()) {
            XifyModifer modifier = (XifyModifer) modifiers.get(0);
            int xifyTimes = modifier.getTimes();
            for (int i = 0; i < xifyTimes; i++) {
                if (p != null && m != null) {
                    card.use(p, m);
                }
            }
        } else {
            card.use(p, m);
        }
    }
    // 目前有问题，插入成功但不生效
    @SpireInstrumentPatch
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
//                HHSMod.logger.info(m.getClassName() + ":" + m.getMethodName());
//                if (m.getClassName().equals("com.megacrit.cardcrawl.cards.AbstractCard") && m.getMethodName().equals("use")) {
//                    HHSMod.logger.info(m.getClassName() + ":" + m.getMethodName());
//                    HHSMod.logger.info("插入！");
////                    m.replace("{ testXifyUse($0, $1, $2); }");
//                }
            }
        };
    }
}
