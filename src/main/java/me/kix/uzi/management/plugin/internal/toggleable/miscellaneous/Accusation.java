package me.kix.uzi.management.plugin.internal.toggleable.miscellaneous;

import java.util.Optional;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.management.event.entity.EventUpdate;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Chooses a player at random throughout the entire server and starts accusing
 * them of cheating.
 * 
 * <p>
 * This plugin is a pretty harsh idea. Therefore, I suggest not utilizing this
 * if you don't wanna wreck havoc on a poor server. However, if you don't care,
 * feel free!
 * </p>
 * 
 * @author Kix
 * @since April 2019
 */
public class Accusation extends ToggleablePlugin {

    /**
     * Random accusations that an unsuspecting player can receive.
     */
    private final String[] accusations = new String[] { "is hacking!", "is hitting me through walls!", "is phasing!",
            "is using killaura!" };

    public Accusation() {
        super("Accusation", Category.MISCELLANEOUS);
    }

    @Register
    public void onLivingUpdate(EventUpdate.Living living) {
        int randomAccusationIndex = random.nextInt(accusations.length - 1);
        Optional<EntityPlayer> foundPlayer = mc.world.playerEntities.stream().filter(player -> player != mc.player)
                .findFirst();
        if (foundPlayer.isPresent()) {
            mc.player.sendChatMessage(foundPlayer.get().getName() + " " + accusations[randomAccusationIndex]);
            toggle();
        }
    }
}