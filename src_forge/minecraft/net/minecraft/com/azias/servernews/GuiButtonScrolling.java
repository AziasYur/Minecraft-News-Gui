package com.azias.servernews;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Azias
 * @version 1.3 Indev
 * @ForgeVersion 1.8 - 11.14.3.1493
 */
@SideOnly(Side.CLIENT)
public class GuiButtonScrolling extends GuiButton {
    private final ResourceLocation serverNewsButtonsTextures = new ResourceLocation("azias", "textures/gui/server_news_buttons.png");
    private int texOff;
    private boolean isEnabled;

    public GuiButtonScrolling(int buttonID, int xPos, int yPos, int texOff) {
        super(buttonID, xPos, yPos, 10, 10, "");
    	this.texOff=texOff;
    	this.isEnabled=true;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(serverNewsButtonsTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = 0;

            if(this.isEnabled){
            	if (flag) {
            		k += this.height;
            	}
            } else {
            	k += this.height*2;
            }
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.texOff*10, k, this.width, this.height);
        }
    }
    
    public void enabling(boolean par1) {
    	this.isEnabled = par1;
    }
}