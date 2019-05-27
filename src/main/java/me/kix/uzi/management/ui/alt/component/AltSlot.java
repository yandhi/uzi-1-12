package me.kix.uzi.management.ui.alt.component;

import me.kix.uzi.management.ui.alt.Alt;
import me.kix.uzi.management.ui.alt.screen.GuiAltManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;

public class AltSlot extends GuiSlot {

    private GuiAltManager screen;
    private int selected;

    public AltSlot(GuiAltManager screen, Minecraft mcIn, int width, int height, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
        super(mcIn, width, height, p_i1052_4_, p_i1052_5_, p_i1052_6_);

        this.screen = screen;
    }

    @Override
    protected int getSize() {
        return screen.getAccounts().size();
    }

    @Override
    public int getSlotHeight() {
        return super.getSlotHeight();
    }

    @Override
    protected void elementClicked(int slot, boolean var2, int var3, int var4) {
        this.selected = slot;

        if (var2) {
            screen.login(getAlt());
        }
    }

    @Override
    protected boolean isSelected(int slot) {
        return selected == slot;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int slot) {
        this.selected = slot;
    }

    public Alt getAlt() {
        return getAlt(getSelected());
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
        Alt alt = getAlt(p_192637_1_);

        if (alt != null) {
            mc.fontRenderer.drawStringWithShadow(alt.getUsername(), p_192637_2_ + 1, p_192637_3_ + 2, 0xFFFFFFFF);
        }
    }

    public Alt getAlt(int slot) {
        int count = 0;

        for (Alt alt : screen.getAccounts()) {
            if (count == slot) {
                return alt;
            }
            count++;
        }

        return null;
    }

    public void setBounds(int width, int height, int top, int bottom, int right) {
        this.width = width;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.right = right;
    }

}
