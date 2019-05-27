package me.kix.uzi.management.ui.alt.screen;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.ui.alt.Alt;
import me.kix.uzi.management.ui.alt.AuthenticationThread;
import me.kix.uzi.management.ui.alt.component.AltSlot;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.List;

/**
 * The UI for management of Alts.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class GuiAltManager extends GuiScreen {

    /**
     * The parent screen.
     */
    private final GuiScreen parentScreen;

    /**
     * The GUI screen for creating alts.
     */
    private GuiCreateAlt guiCreateAlt;

    /**
     * The slots for the alts.
     */
    private AltSlot alts;

    /**
     * The thread for authentication.
     */
    private AuthenticationThread authThread;

    public GuiAltManager(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.guiCreateAlt = new GuiCreateAlt(this);

        this.alts = new AltSlot(this, mc, width, height, 25, height - 98, 36);

        this.buttonList.add(new GuiButton(0, width / 2 - 25, height - 90, 50, 20, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 25, height - 60, 50, 20, "Add"));
        this.buttonList.add(new GuiButton(2, width / 2 - 25, height - 30, 50, 20, "Remove"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.enabled) {
            if (button.id == 0) {
                login(alts.getAlt());
            }
            if (button.id == 1) {
                mc.displayGuiScreen(guiCreateAlt);
            }
            if (button.id == 2) {
                Uzi.INSTANCE.getAltManager().remove(alts.getAlt().getUsername());
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        alts.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);

        drawCenteredString(mc.fontRenderer, "\247a" + mc.getSession().getUsername(), width / 2, 5, 0xFFFFFFFF);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        alts.handleMouseInput();
    }

    /**
     * Logs into the selected alt.
     *
     * @param alt The alt to be logged into.
     */
    public void login(Alt alt) {
        authThread = new AuthenticationThread(alt);
        new Thread(authThread, "Account Authentication Thread").start();
    }

    /**
     * @return A pipeline for components to access alts.
     */
    public List<Alt> getAccounts() {
        return Uzi.INSTANCE.getAltManager().getContents();
    }

}
