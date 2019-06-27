package me.kix.uzi.management.plugin.internal.toggleable.combat;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.property.properties.NumberProperty;
import me.kix.uzi.api.util.math.angle.Angle;
import me.kix.uzi.api.util.math.angle.AngleUtil;
import me.kix.uzi.api.util.math.timing.Timer;
import me.kix.uzi.api.event.events.entity.EventUpdate;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * Hits nearby players.
 *
 * @author Kix
 * @since April 2018 (Revised June 22, 2019).
 */
public class KillAura extends ToggleablePlugin {

    /**
     * The frequency of hits.
     */
    private final NumberProperty<Integer> aps = new NumberProperty<>("APS", 15, 1, 20);

    /**
     * The highest possible distance the player can be from an entity.
     */
    private final NumberProperty<Float> range = new NumberProperty<>("Range", 4f, 3f, 6f);

    /**
     * Whether or not to apply cooldown to hit times.
     */
    private final Property<Boolean> cooldown = new Property<>("Cooldown", true);

    /**
     * Whether or not to attack players.
     */
    private final Property<Boolean> players = new Property<>("Players", true);

    /**
     * Whether or not to attack animals.
     */
    private final Property<Boolean> animals = new Property<>("Animals", false);

    /**
     * Whether or not to attack monsters.
     */
    private final Property<Boolean> monsters = new Property<>("Monsters", false);

    /**
     * The timing utility for the aura.
     */
    private final Timer timer = new Timer();

    /**
     * The current list of possible targets.
     */
    private final List<EntityLivingBase> entities = new ArrayList<>();

    /**
     * The current target.
     */
    private EntityLivingBase target;

    public KillAura() {
        super("KillAura", Category.COMBAT);
        setDisplay("Kill Aura");
        setColor(-55552);
        getProperties().add(aps);
        getProperties().add(range);
        getProperties().add(cooldown);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(monsters);
    }

    @Register
    public void onPreUpdate(EventUpdate.Pre event) {
        target = getBestTarget();
        if (target != null) {
            Angle angle = AngleUtil.getAngle(target);
            event.getViewAngles()
                    .setYaw(angle.getYaw())
                    .setPitch(angle.getPitch());
        }
    }

    @Register
    public void onPostUpdate(EventUpdate.Post event) {
        if (target != null) {
            if (cooldown.getValue()) {
                if (mc.player.getCooledAttackStrength(0) >= 1f && timer.completed(250)) {
                    attack(mc.player, target);
                    timer.reset();
                }
            } else {
                if (timer.completed(1000 / aps.getValue())) {
                    attack(mc.player, target);
                    timer.reset();
                }
            }
        }
    }

    /**
     * Attacks the given entity with the player.
     *
     * <p>
     * This attack function takes into account various abilities and their uses.
     * </p>
     *
     * @param player The attacker.
     * @param target The target being attacked.
     */
    private void attack(EntityPlayerSP player, EntityLivingBase target) {
        ItemStack item = player.inventory.getCurrentItem();
        boolean sprinting = player.isSprinting();
        boolean sneaking = player.isSneaking();
        boolean blocking = item.getItem() != Items.AIR && item.getItem().getItemUseAction(item) == EnumAction.BLOCK &&
                player.isActiveItemStackBlocking();
        if (sprinting) {
            player.connection.sendPacket(new CPacketEntityAction(player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        if (sneaking) {
            player.connection.sendPacket(new CPacketEntityAction(player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (blocking) {
            player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        mc.playerController.attackEntity(player, target);
        player.swingArm(EnumHand.MAIN_HAND);
        if (sprinting) {
            player.connection.sendPacket(new CPacketEntityAction(player, CPacketEntityAction.Action.START_SPRINTING));
        }
        if (sneaking) {
            player.connection.sendPacket(new CPacketEntityAction(player, CPacketEntityAction.Action.START_SNEAKING));
        }
        if (blocking) {
            player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        }
        player.setSprinting(sprinting);
        player.setSneaking(sneaking);
        if (blocking) {
            mc.playerController.processRightClick(player, mc.world, EnumHand.MAIN_HAND);
        }
    }

    /**
     * Provides the best possible nearby target.
     *
     * @return The best nearby target.
     */
    private EntityLivingBase getBestTarget() {
        entities.clear();
        for (Entity ent : mc.world.loadedEntityList) {
            if (ent instanceof EntityLivingBase) {
                if (isEntityApplicable(ent)) {
                    entities.add((EntityLivingBase) ent);
                }
            }
        }
        if (entities.isEmpty()) {
            return null;
        }
        entities.sort((player, t1) -> Float.compare(mc.player.getDistanceToEntity(player), mc.player.getDistanceToEntity(t1)));
        return entities.get(0);
    }

    /**
     * Tells if the given entity is viable for hitting.
     *
     * @param entity The entity being checked.
     * @return Whether or not the entity is able to be aura'd.
     */
    private boolean isEntityApplicable(Entity entity) {
        boolean notMe = entity != mc.player;
        boolean withinRange = mc.player.getDistanceToEntity(entity) <= range.getValue();
        boolean existedLongEnough = entity.ticksExisted >= 10;
        boolean alive = entity.isEntityAlive();
        boolean oneOfChosenEntities = entity instanceof EntityPlayer && players.getValue() || entity instanceof EntityAnimal && animals.getValue() || (entity instanceof EntityMob && monsters.getValue());
        return notMe && withinRange && existedLongEnough && alive && oneOfChosenEntities && !Uzi.INSTANCE.getFriendManager().isFriend(entity.getName());
    }
}