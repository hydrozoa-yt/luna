package io.luna.game.model;

/**
 * Ported from Messages.kt
 *
 * @author hydrozoa
 */
public enum Messages {
    INVENTORY_FULL("You do not have enough space in your inventory."),
    ;

    private String text;

    private Messages(String message) {
        this.text = message;
    }

    public String getText() {
        return text;
    }
}
