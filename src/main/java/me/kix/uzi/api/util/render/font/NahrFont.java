package me.kix.uzi.api.util.render.font;

import me.kix.uzi.api.game.accessors.client.font.TextRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class NahrFont {

    private final Pattern patternControlCode = Pattern.compile("(?i)\247[0-9A-FK-OG]");
    private final Pattern patternUnsupported = Pattern.compile("(?i)\247[K-O]");
    private Font theFont;
    private boolean antiAlias = true;
    private Graphics2D theGraphics;
    private FontMetrics theMetrics;
    private float fontSize;
    private int startChar;
    private int endChar;
    private float[] xPos;
    private float[] yPos;
    private BufferedImage bufferedImage;
    private float extraSpacing = 0.0F;
    private DynamicTexture dynamicTexture;
    private ResourceLocation resourceLocation;

    public NahrFont(Object font, float size) {
        this(font, size, 0.0F);
    }

    public NahrFont(Object font, float size, float spacing) {
        this.fontSize = size;
        this.startChar = 32;
        this.endChar = 255;
        this.extraSpacing = spacing;
        this.xPos = new float[this.endChar - this.startChar];
        this.yPos = new float[this.endChar - this.startChar];
        setupGraphics2D();
        createFont(font, size);
    }

    private void setupGraphics2D() {
        GlStateManager.pushMatrix();
        this.bufferedImage = new BufferedImage(256, 256, 2);
        this.theGraphics = ((Graphics2D) this.bufferedImage.getGraphics());
        if (antiAlias) {
            this.theGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        } else {
            this.theGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        GlStateManager.popMatrix();
    }

    private void createFont(Object font, float size) {
        GlStateManager.pushMatrix();
        try {
            if ((font instanceof Font))
                this.theFont = ((Font) font);
            else if ((font instanceof File))
                this.theFont = Font.createFont(0, (File) font).deriveFont(size);
            else if ((font instanceof InputStream))
                this.theFont = Font.createFont(0, (InputStream) font).deriveFont(size);
            else if ((font instanceof String))
                this.theFont = new Font((String) font, 0, Math.round(size));
            else {
                this.theFont = new Font("Verdana", 0, Math.round(size));
            }

            this.theGraphics.setFont(this.theFont);
        } catch (Exception e) {
            e.printStackTrace();
            this.theFont = new Font("Verdana", 0, Math.round(size));

            this.theGraphics.setFont(this.theFont);
        }
        this.theGraphics.setColor(new Color(255, 255, 255, 0));
        this.theGraphics.fillRect(0, 0, 256, 256);
        this.theGraphics.setColor(Color.white);
        this.theMetrics = this.theGraphics.getFontMetrics();

        float x = 5.0F;
        float y = 5.0F;
        for (int i = this.startChar; i < this.endChar; i++) {
            this.theGraphics.drawString(Character.toString((char) i), x, y + this.theMetrics.getAscent());
            this.xPos[(i - this.startChar)] = x;
            this.yPos[(i - this.startChar)] = (y - this.theMetrics.getMaxDescent());
            x += this.theMetrics.stringWidth(Character.toString((char) i)) + 2.0F;
            if (x >= 250 - this.theMetrics.getMaxAdvance()) {
                x = 5.0F;
                y += this.theMetrics.getMaxAscent() + this.theMetrics.getMaxDescent() + this.fontSize / 2.0F;
            }
        }
        this.resourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("font" + font.toString() + size, this.dynamicTexture = new DynamicTexture(this.bufferedImage));
        GlStateManager.popMatrix();
    }

    public void drawCenteredString(String text, float x, float y, FontType fontType, int color, int color2) {
        drawString(text, x - getStringWidth(text) / 2, y, fontType, color, color2);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        drawString(text, x, y, FontType.SHADOW_THIN, color, 0xff000000);
    }

    public void drawString(String text, float x, float y, FontType fontType, int color, int color2) {
        GlStateManager.pushMatrix();
        text = stripUnsupported(text);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        String text2 = stripControlCodes(text);
        switch (fontType.ordinal()) {
            case 1:
                drawer(text2, x + 0.5F, y, color2);
                drawer(text2, x - 0.5F, y, color2);
                drawer(text2, x, y + 0.5F, color2);
                drawer(text2, x, y - 0.5F, color2);
                break;
            case 2:
                drawer(text2, x + 0.5F, y + 0.5F, color2);
                break;
            case 3:
                drawer(text2, x + 1.0F, y + 1.0F, color2);
                break;
            case 4:
                drawer(text2, x, y + 0.5F, color2);
                break;
            case 5:
                drawer(text2, x, y - 0.5F, color2);
                break;
            case 6:
                break;
        }

        drawer(text, x, y, color);
        GlStateManager.popMatrix();
    }

    private void drawer(String text, float x, float y, int color) {
        y -= 5;
        x *= 2.0F;
        y *= 2.0F;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, alpha);
        float startX = x;
        for (int i = 0; i < text.length(); i++)
            if ((text.charAt(i) == (char) 0x00A7) && (i + 1 < text.length())) {
                char oneMore = Character.toLowerCase(text.charAt(i + 1));
                if (oneMore == 'n') {
                    y += this.theMetrics.getAscent() + 2;
                    x = startX;
                }
                int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                if (colorCode < 16)
                    try {
                        int newColor = ((TextRenderer) Minecraft.getMinecraft().fontRenderer).getColorCode()[colorCode];
                        GlStateManager.color((newColor >> 16) / 255.0F,
                                (newColor >> 8 & 0xFF) / 255.0F,
                                (newColor & 0xFF) / 255.0F, alpha);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                else if (oneMore == 'f')
                    GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
                else if (oneMore == 'r')
                    GlStateManager.color(red, green, blue, alpha);
                else if (oneMore == 'g') {
                    GlStateManager.color(0.47F, 0.67F, 0.27F, alpha);
                }
                i++;
            } else {
                try {
                    char c = text.charAt(i);
                    drawChar(c, x, y);
                    x += getStringWidth(Character.toString(c)) * 2.0F;
                } catch (ArrayIndexOutOfBoundsException indexException) {
                    char c = text.charAt(i);
                }
            }
        GlStateManager.popMatrix();
    }

    public float getStringWidth(String text) {
        try {
            return (float) (getBounds(text).getWidth() + this.extraSpacing) / 2.0F;
        } catch (Exception e) {
            return 0F;
        }
    }

    public float getStringHeight(String text) {
        try {
            return (float) getBounds(text).getHeight() / 2.0F;
        } catch (Exception e) {
            return 0F;
        }
    }

    private Rectangle2D getBounds(String text) {
        return this.theMetrics.getStringBounds(StringUtils.stripControlCodes(text), this.theGraphics);
    }

    private void drawChar(char character, float x, float y) throws ArrayIndexOutOfBoundsException {
        GlStateManager.pushMatrix();
        Rectangle2D bounds = this.theMetrics.getStringBounds(Character.toString(character), this.theGraphics);
        drawTexturedModalRect(x, y, this.xPos[(character - this.startChar)], this.yPos[(character - this.startChar)], (float) bounds.getWidth(), (float) bounds.getHeight() + this.theMetrics.getMaxDescent() + 1.0F);
        GlStateManager.popMatrix();
    }

    public void drawTexturedModalRect(float x, float y, float textureX, float textureY, float width, float height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x + 0), (double) (y + height), 0d).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), 0d).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + 0), 0d).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double) (x + 0), (double) (y + 0), 0d).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    public List listFormattedStringToWidth(String s, int width) {
        return Arrays.asList(wrapFormattedStringToWidth(s, width).split("\n"));
    }

    String wrapFormattedStringToWidth(String s, float width) {
        int wrapWidth = sizeStringToWidth(s, width);

        if (s.length() <= wrapWidth) {
            return s;
        }
        String split = s.substring(0, wrapWidth);
        String split2 = getFormatFromString(split)
                + s.substring(wrapWidth
                + ((s.charAt(wrapWidth) == ' ')
                || (s.charAt(wrapWidth) == '\n') ? 1 : 0));
        try {
            return split + "\n" + wrapFormattedStringToWidth(split2, width);
        } catch (Exception e) {
            Logger.getGlobal().warning("Cannot wrap string to width.");
        }
        return "";
    }

    private int sizeStringToWidth(String par1Str, float par2) {
        int var3 = par1Str.length();
        float var4 = 0.0F;
        int var5 = 0;
        int var6 = -1;

        for (boolean var7 = false; var5 < var3; var5++) {
            char var8 = par1Str.charAt(var5);

            switch (var8) {
                case '\n':
                    var5--;
                    break;
                case (char) 0x00A7:
                    if (var5 < var3 - 1) {
                        var5++;
                        char var9 = par1Str.charAt(var5);

                        if ((var9 != 'l') && (var9 != 'L')) {
                            if ((var9 == 'r') || (var9 == 'R') || (isFormatColor(var9)))
                                var7 = false;
                        } else
                            var7 = true;
                    }
                    break;
                case ' ':
                    var6 = var5;
                case '-':
                    var6 = var5;
                case '_':
                    var6 = var5;
                case ':':
                    var6 = var5;
                default:
                    String text = String.valueOf(var8);
                    var4 += getStringWidth(text);

                    if (var7) {
                        var4 += 1.0F;
                    }
                    break;
            }
            if (var8 == '\n') {
                var5++;
                var6 = var5;
            } else {
                if (var4 > par2) {
                    break;
                }
            }
        }
        return (var5 != var3) && (var6 != -1) && (var6 < var5) ? var6 : var5;
    }

    private String getFormatFromString(String par0Str) {
        String var1 = "";
        int var2 = -1;
        int var3 = par0Str.length();

        while ((var2 = par0Str.indexOf((char) 0x00A7, var2 + 1)) != -1) {
            if (var2 < var3 - 1) {
                char var4 = par0Str.charAt(var2 + 1);

                if (isFormatColor(var4))
                    var1 = Character.toString((char) 0x00A7) + var4;
                else if (isFormatSpecial(var4)) {
                    var1 = var1 + Character.toString((char) 0x00A7) + var4;
                }
            }
        }

        return var1;
    }

    private boolean isFormatColor(char par0) {
        return ((par0 >= '0') && (par0 <= '9')) || ((par0 >= 'a') && (par0 <= 'f')) || ((par0 >= 'A') && (par0 <= 'F'));
    }

    private boolean isFormatSpecial(char par0) {
        return ((par0 >= 'k') && (par0 <= 'o')) || ((par0 >= 'K') && (par0 <= 'O')) || (par0 == 'r') || (par0 == 'R');
    }

    public String stripControlCodes(String s) {
        return this.patternControlCode.matcher(s).replaceAll("");
    }

    public String stripUnsupported(String s) {
        return this.patternUnsupported.matcher(s).replaceAll("");
    }

    public Graphics2D getGraphics() {
        return this.theGraphics;
    }

    public Font getFont() {
        return this.theFont;
    }

    public enum FontType {
        NORMAL, OUTLINE_THIN, SHADOW_THIN, SHADOW_THICK, EMBOSS_TOP, EMBOSS_BOTTOM
    }


}
