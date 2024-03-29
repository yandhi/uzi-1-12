package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.property.Property;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.event.events.render.EventRender;
import me.kix.uzi.api.event.events.render.EventRenderNameplate;
import me.kix.uzi.api.util.render.font.NahrFont;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yandhi
 * @since Revised 9/26/21
 */
public class Nametags extends ToggleablePlugin {

    /**
     * Allows the user to determine whether the nametags should be rendered through walls.
     */
    private final Property<Boolean> raytracing = new Property<>("Raytracing", false);

    private final Property<Boolean> armor = new Property<>("Armor", true);
    private final Property<Boolean> players = new Property<>("Players", true);
    private final Property<Boolean> animals = new Property<>("Animals", false);
    private final Property<Boolean> mobs = new Property<>("Monsters", false);
    private final Property<Boolean> items = new Property<>("Items", false);

    private final NahrFont font = new NahrFont("Consolas", 16);

    public Nametags() {
        super("Nametags", Category.RENDER);
        setHidden(true);
        getProperties().add(raytracing);
        getProperties().add(armor);
        getProperties().add(players);
        getProperties().add(animals);
        getProperties().add(mobs);
        getProperties().add(items);
    }

    @Register
    public void onRender2D(EventRender.TwoDimensional event) {
        if (RenderUtil.isInViewFrustrum(event.getEntity())) {
            /* raytracing check :) */
            if (raytracing.getValue() || mc.player.canEntityBeSeen(event.getEntity())) {
                if (event.getEntity() instanceof EntityPlayer && players.getValue() || event.getEntity() instanceof EntityAnimal && animals.getValue() || (event.getEntity() instanceof EntityMob && mobs.getValue()) || event.getEntity() instanceof EntityItem && items.getValue()) {
                    String name = (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.equalsIgnoreCase("hypixel.net")) ? event.getEntity().getName() : event.getEntity().getDisplayName().getFormattedText();

                    if (Uzi.INSTANCE.getFriendManager().isFriend(event.getEntity().getName())) {
                        name = Uzi.INSTANCE.getFriendManager().getReplacedText(name);
                    }

                    if (event.getEntity() instanceof EntityItem) {
                        name = ((EntityItem) event.getEntity()).getItem().getDisplayName();
                    }

                    if (event.getEntity() instanceof EntityPlayer && items.getValue()) {
                        GlStateManager.pushMatrix();
                        EntityPlayer player = (EntityPlayer) event.getEntity();
                        if (player.getGameProfile().getId() != player.getUniqueID()) {
                            return;
                        }
                        drawArmor(player, Math.round(event.getBox().x + ((event.getBox().w - event.getBox().x) / 2)), Math.round(event.getBox().y) - 30);
                        GlStateManager.enableDepth();
                        GlStateManager.depthMask(true);
                        GlStateManager.popMatrix();
                    }

                    if (event.getEntity() instanceof EntityLivingBase) {
                        if (name.isEmpty() || name.trim().isEmpty() || StringUtils.isNullOrEmpty(name)) return;
                        EntityLivingBase entityLivingBase = (EntityLivingBase) event.getEntity();
                        String tag = String.format("%s %s", name, getHealthColor(entityLivingBase) + Math.round(entityLivingBase.getHealth() / 2));
                        RenderUtil.drawRect(event.getBox().x + ((event.getBox().w - event.getBox().x) / 2) - (font.getStringWidth(tag) / 2f) - 1.5f, event.getBox().y - 11,
                                event.getBox().x + ((event.getBox().w - event.getBox().x) / 2) + (font.getStringWidth(tag) / 2f) + 0.5f, event.getBox().y - 2f, 0x60000000);
                        font.drawStringWithShadow(tag,
                                event.getBox().x + ((event.getBox().w - event.getBox().x) / 2) - (font.getStringWidth(tag) / 2f),
                                event.getBox().y - 8,
                                0xFFFFFFFF);
                    } else {
                        font.drawStringWithShadow(name,
                                event.getBox().x + ((event.getBox().w - event.getBox().x) / 2) - (font.getStringWidth(name) / 2f),
                                event.getBox().y - 8,
                                0xFFFFFFFF);
                    }
                }
            }
        }
    }

    @Register
    public void onRenderNameplate(EventRenderNameplate event) {
        if (event.getEntity() instanceof EntityPlayer && players.getValue() || event.getEntity() instanceof EntityAnimal && animals.getValue() || (event.getEntity() instanceof EntityMob && mobs.getValue()) || event.getEntity() instanceof EntityItem && items.getValue()) {
            event.setCancelled(true);
        }
    }

    /**
     * Draws armor onto the nametag.
     *
     * @param player The player being rendered.
     * @param x      The renderer x position of the armor.
     * @param y      The renderer y position of the armor.
     */
    private void drawArmor(EntityPlayer player, int x, int y) {
        if (!player.inventory.armorInventory.isEmpty()) {
            List<ItemStack> items = new ArrayList<>();

            if (player.getHeldItem(EnumHand.MAIN_HAND) != ItemStack.EMPTY) {
                items.add(player.getHeldItem(EnumHand.MAIN_HAND));
            }

            for (int index = 3; index >= 0; index--) {
                ItemStack stack = player.inventory.armorInventory.get(index);
                if (stack != ItemStack.EMPTY) {
                    items.add(stack);
                }
            }


            int armorX = x - ((items.size() * 16) / 2);
            for (ItemStack stack : items) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.enableLighting();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                RenderHelper.enableStandardItemLighting();
                mc.getRenderItem().renderItemIntoGUI(stack, armorX, y);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, stack, armorX, y, "");
                RenderHelper.disableStandardItemLighting();
                GlStateManager.enableAlpha();
                GlStateManager.disableBlend();
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.popMatrix();
                if (stack.isItemEnchanted()) {
                    NBTTagList enchants = stack.getEnchantmentTagList();
                    int enchantY = y;
                    if (enchants.tagCount() >= 4) {
                        RenderUtil.drawTinyString("\2476God", armorX + 3, enchantY, 0xFFFFFFFF);
                    } else {
                        for (int index = 0; index < enchants.tagCount(); ++index) {
                            short id = enchants.getCompoundTagAt(index).getShort("id");
                            short level = enchants.getCompoundTagAt(index).getShort("lvl");
                            Enchantment enchantment = Enchantment.getEnchantmentByID(id);
                            RenderUtil.drawTinyString("\247e" + I18n.format(enchantment.getName()).substring(0, 2) + ". " + level, armorX + 2.5f, enchantY, 0xFFD4D5F6);
                            enchantY += 5;
                        }
                    }
                }
                armorX += 16;
            }

        }
    }

    /**
     * Returns the minecraft color code that corresponds to the entity's health amount.
     *
     * @param entity The entity being checked for health amount.
     * @return The health color of the entity.
     */
    private String getHealthColor(EntityLivingBase entity) {
        double health = entity.getHealth() / 2;
        double maxHealth = entity.getMaxHealth() / 2;
        double percentage = 100 * (health / maxHealth);
        if (percentage > 75) {
            return "\247a";
        } else if (percentage > 50) {
            return "\247e";
        } else if (percentage > 25) {
            return "\2476";
        }
        return "\247c";
    }

}