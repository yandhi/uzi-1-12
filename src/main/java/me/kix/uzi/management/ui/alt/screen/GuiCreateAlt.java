package me.kix.uzi.management.ui.alt.screen;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.ui.alt.Alt;
import me.kix.uzi.management.ui.alt.component.GuiPasswordField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

/**
 * A UI screen for creating an alt.
 *
 * @author Jax
 * Created in Apr 2019
 */
public class GuiCreateAlt extends GuiScreen {

    /**
     * An instance of the parent alt manager.
     */
    private final GuiAltManager guiAltManager;

    /**
     * The username field.
     */
    private GuiTextField username;

    /**
     * The password field.
     */
    private GuiPasswordField password;

    public GuiCreateAlt(GuiAltManager guiAltManager) {
        this.guiAltManager = guiAltManager;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        /* Setup username component. */
        this.username = new GuiTextField(0, mc.fontRenderer, width / 2 - 100, height / 2 - 45, 200, 20);
        this.username.setVisible(true);

        /* Setup password component. */
        this.password = new GuiPasswordField(1, mc.fontRenderer, width / 2 - 100, height / 2 - 15, 200, 20);
        this.password.setVisible(true);

        /* Add a button to create the alt or cancel. */
        this.buttonList.add(new GuiButton(0, width / 2 - 25, height / 2 + 15, 50, 20, "Add"));
        this.buttonList.add(new GuiButton(1, width / 2 - 25, height / 2 + 45, 50, 20, "Cancel"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.enabled) {
            if (button.id == 0) {
                Uzi.INSTANCE.getAltManager().getContents().add(new Alt(username.getText(), password.getText()));
                username.setText("");
                password.setText("");
                mc.displayGuiScreen(guiAltManager);
            }

            if (button.id == 1) {
                mc.displayGuiScreen(guiAltManager);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        username.drawTextBox();
        password.drawTextBox();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        username.textboxKeyTyped(typedChar, keyCode);
        password.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        username.mouseClicked(mouseX, mouseY, mouseButton);
        password.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        username.updateCursorCounter();
        password.updateCursorCounter();
    }
}
