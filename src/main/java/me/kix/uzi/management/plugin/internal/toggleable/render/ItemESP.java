package me.kix.uzi.management.plugin.internal.toggleable.render;

import me.kix.uzi.api.event.Register;
import me.kix.uzi.api.plugin.Category;
import me.kix.uzi.api.plugin.toggleable.ToggleablePlugin;
import me.kix.uzi.api.util.render.RenderUtil;
import me.kix.uzi.api.event.events.render.EventRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;

/**
 * Shows an ESP for items that are on the ground.
 *
 * @author Kix
 * Created in Apr 2019
 */
public class ItemESP extends ToggleablePlugin {

    public ItemESP() {
        super("ItemESP", Category.RENDER);
        setDisplay("Item ESP");
        setHidden(true);
    }

    @Register
    public void onRenderHand(EventRender.Hand hand) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                double x = (entity.posX - entity.prevPosX) * hand.getPartialTicks();
                double y = (entity.posY - entity.prevPosY) * hand.getPartialTicks();
                double z = (entity.posZ - entity.prevPosZ) * hand.getPartialTicks();

                AxisAlignedBB bb = entity.getEntityBoundingBox().offset(-mc.getRenderManager().viewerPosX, -mc.getRenderManager().viewerPosY, -mc.getRenderManager().viewerPosZ);
                RenderUtil.bb(bb.offset(x, y, z), 2f, Color.CYAN);
            }
        }
    }

}
