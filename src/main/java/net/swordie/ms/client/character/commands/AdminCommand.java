package net.swordie.ms.client.character.commands;

/**
 * Created on 12/22/2017.
 */
public abstract class AdminCommand implements ICommand {

    public AdminCommand() {
    }

    public static char getPrefix() {
        return '!';
    }
}
