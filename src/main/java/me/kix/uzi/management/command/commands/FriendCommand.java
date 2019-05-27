package me.kix.uzi.management.command.commands;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.argument.factory.registration.RegisterArgument;
import me.kix.uzi.api.command.commands.ArgumentativeCommand;
import me.kix.uzi.api.friend.Friend;

public class FriendCommand extends ArgumentativeCommand {

    public FriendCommand() {
        super("Friend", new String[]{"fr", "friends", "addFriend", "f", "ally"}, "Allows the user to setup an alliance with an entity.");
    }

    @RegisterArgument({"add", "inclue", "a"})
    public void add(String ign, String alias) {
        Uzi.INSTANCE.getFriendManager().getContents().add(new Friend(ign, alias));
    }

    @RegisterArgument({"remove", "delete", "rem", "rm", "del", "r", "d"})
    public void remove(String ign) {
        Uzi.INSTANCE.getFriendManager().removeFriend(ign);
    }
}
