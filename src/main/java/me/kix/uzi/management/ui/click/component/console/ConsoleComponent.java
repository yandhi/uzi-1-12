package me.kix.uzi.management.ui.click.component.console;

import me.kix.sodapop.AbstractComponent;
import me.kix.sodapop.manage.GuiManager;
import me.kix.sodapop.util.MouseButton;
import me.kix.sodapop.util.MouseUtil;
import me.kix.sodapop.util.Rectangle;
import me.kix.uzi.Uzi;
import me.kix.uzi.api.command.Command;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows the user to input commands.
 *
 * @author jackson
 * @since 1/2/2022
 */
public class ConsoleComponent extends AbstractComponent {

    /**
     * The old input.
     */
    private final List<String> previousInput = new ArrayList<>();

    /**
     * The current text.
     */
    private String text;

    /**
     * Whether the input box is focused.
     */
    private boolean focused;

    /**
     * The max number of lines before it clears itself.
     */
    private final int maxLines = 35;

    /**
     * The height of the console.
     */
    private int height;

    public ConsoleComponent(GuiManager guiManager, Rectangle renderPosition, int height) {
        super("input box", guiManager, renderPosition);
        this.height = height;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        super.updateComponent(mouseX, mouseY);
        updateDimensions(getRenderPosition().getWidth(), height);

        if (previousInput.size() > maxLines) {
            previousInput.remove(0);
        }
    }

    @Override
    public void mousePressed(int mouseX, int mouseY, MouseButton mouseButton) {
        super.mousePressed(mouseX, mouseY, mouseButton);
        if (MouseUtil.mouseWithinRectangle(mouseX, mouseY, this.getRenderPosition()) && mouseButton == MouseButton.LEFT) {
            focused = !focused;
        }
    }

    @Override
    public void keyTyped(char c, int keyIndex) {
        super.keyTyped(c, keyIndex);

        if (focused) {
            if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                this.text = this.text + c;
            } else if (keyIndex == Keyboard.KEY_RETURN) {
                executeInput(text);
                previousInput.add(text);
                this.text = "";
            }
        }
    }

    /**
     * Executes the input into the console.
     *
     * @param text The input provided.
     */
    public void executeInput(String text) {
        boolean executed = false;
        for (Command command : Uzi.INSTANCE.getCommandManager().getContents()) {
            if (text.split(" ")[0].equalsIgnoreCase(command.getLabel())) {
                try {
                    command.execute(text);
                } catch (Exception e) {
                    previousInput.add("Execution Failed! Possible arguments are: " + command.getHelp() + ".");
                }
                executed = true;
            } else {
                for (String alias : command.getAliases()) {
                    if (text.split(" ")[0].equalsIgnoreCase(alias)) {
                        try {
                            command.execute(text);
                        } catch (Exception e) {
                            previousInput.add("Execution Failed! Possible arguments are: " + command.getHelp() + ".");
                        }
                        executed = true;
                    }
                }
            }
        }

        if (!executed) {
            previousInput.add("Command \"" + text + "\" was not found!");
        }
    }

    public boolean isFocused() {
        return focused;
    }

    public String getText() {
        return text;
    }

    public List<String> getPreviousInput() {
        return previousInput;
    }
}
