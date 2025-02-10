package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.ButtonClickEvent;
import io.luna.game.event.impl.ObjectClickEvent;
import io.luna.game.event.impl.WidgetItemClickEvent;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.inter.AmountInputInterface;
import io.luna.game.model.mob.varp.PersistentVarp;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;

import java.util.Set;

/**
 * Ported from openBank.kts and openDepositBox.kts
 *
 * @author hydrozoa
 */
public class BankPlugin extends Plugin {

    private Set<Integer> depositBoxes = Set.of(9398);

    private Set<Integer> bankObjects = Set.of(3193, 2213, 3095);

    public BankPlugin(LunaContext ctx) {
        super(ctx);
    }

    private void setWithdrawalMode(Player p, boolean value) {
        if (p.getBank().isOpen()) {
            p.getVarpManager().setAndSendValue(PersistentVarp.WITHDRAW_AS_NOTE, value ? 1 : 0);
        }
    }

    /**
     * Handler for opening the bank when clicked.
     */
    @EventListener(type = ObjectClickEvent.ObjectSecondClickEvent.class)
    public void handleObjectFirstClick(ObjectClickEvent.ObjectSecondClickEvent e) {
        if (!bankObjects.contains(e.getId())) {
            return;
        }

        e.getPlr().getBank().open();
    }

    /**
     * Handler for changing withdraw mode to unnoted.
     */
    @EventListener(type = ButtonClickEvent.class, filter="5387")
    public void turnOffNoted(ButtonClickEvent e) {
        setWithdrawalMode(e.getPlr(), false);
    }

    /**
     * Handler for changing withdraw mode to unnoted.
     */
    @EventListener(type = ButtonClickEvent.class, filter="5386")
    public void turnOnNoted(ButtonClickEvent e) {
        setWithdrawalMode(e.getPlr(), true);
    }

    private void deposit(WidgetItemClickEvent event, Integer amount) {
        Player p = event.getPlr();

        if (amount == null) {
            p.getBank().deposit(event.getIndex(), p.getInventory().computeAmountForId(event.getItemId()));
        } else {
            p.getBank().deposit(event.getIndex(), amount);
        }
    }

    private void withdraw(WidgetItemClickEvent event, Integer amount) {
        Player p = event.getPlr();

        if (amount == null) {
            p.getBank().withdraw(event.getIndex(), p.getInventory().computeAmountForId(event.getItemId()));
        } else {
            p.getBank().withdraw(event.getIndex(), amount);
        }
    }

    /**
     * Withdraw/deposit 1.
     */
    @EventListener(type = WidgetItemClickEvent.WidgetItemFirstClickEvent.class, filter="5064")
    public void deposit1(WidgetItemClickEvent.WidgetItemFirstClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        deposit(e, 1);
    }

    @EventListener(type = WidgetItemClickEvent.WidgetItemFirstClickEvent.class, filter="5382")
    public void withdraw1(WidgetItemClickEvent.WidgetItemFirstClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        withdraw(e, 1);
    }

    /**
     * Withdraw/deposit 5.
     */
    @EventListener(type = WidgetItemClickEvent.WidgetItemSecondClickEvent.class, filter="5064")
    public void deposit5(WidgetItemClickEvent.WidgetItemSecondClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        deposit(e, 5);
    }

    @EventListener(type = WidgetItemClickEvent.WidgetItemSecondClickEvent.class, filter="5382")
    public void withdraw5(WidgetItemClickEvent.WidgetItemSecondClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        withdraw(e, 5);
    }

    /**
     * Withdraw/deposit 10.
     */
    @EventListener(type = WidgetItemClickEvent.WidgetItemThirdClickEvent.class, filter="5064")
    public void deposit10(WidgetItemClickEvent.WidgetItemThirdClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        deposit(e, 10);
    }

    @EventListener(type = WidgetItemClickEvent.WidgetItemThirdClickEvent.class, filter="5382")
    public void withdraw10(WidgetItemClickEvent.WidgetItemThirdClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        withdraw(e, 10);
    }

    /**
     * Withdraw/deposit all.
     */
    @EventListener(type = WidgetItemClickEvent.WidgetItemFourthClickEvent.class, filter="5064")
    public void depositAll(WidgetItemClickEvent.WidgetItemFourthClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        deposit(e, null);
    }

    @EventListener(type = WidgetItemClickEvent.WidgetItemFourthClickEvent.class, filter="5382")
    public void withdrawAll(WidgetItemClickEvent.WidgetItemFourthClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        withdraw(e,null);
    }

    /**
     * Withdraw/deposit (x).
     */
    @EventListener(type = WidgetItemClickEvent.WidgetItemFifthClickEvent.class, filter="5064")
    public void depositX(WidgetItemClickEvent.WidgetItemFifthClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        e.getPlr().getInterfaces().open(new AmountInputInterface() {
            @Override
            public void onAmountInput(Player player, int value) {
                deposit(e, value);
            }
        });
    }

    @EventListener(type = WidgetItemClickEvent.WidgetItemFifthClickEvent.class, filter="5382")
    public void withdrawX(WidgetItemClickEvent.WidgetItemFifthClickEvent e) {
        if (!e.getPlr().getBank().isOpen()) {
            return;
        }

        e.getPlr().getInterfaces().open(new AmountInputInterface() {
            @Override
            public void onAmountInput(Player player, int value) {
                withdraw(e, value);
            }
        });
    }
}
