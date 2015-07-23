package com.azias.servernews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.EmptyStackException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Azias
 * @version 1.3 Indev
 * @ForgeVersion 1.8 - 11.14.3.1493
 */
@SideOnly(Side.CLIENT)
public class GuiServerNews extends GuiScreen {
	//Textures
	private final ResourceLocation serverNewsGUITextures = new ResourceLocation("azias", "textures/gui/server_news.png"); //Put your texture location here
	private final ResourceLocation serverNewsSidebarTextures = new ResourceLocation("azias", "textures/gui/server_news_sidebar.png"); //Don't mind this; it's the debug sidebar texture.
	protected int newsPaneWidth = 256;
	protected int newsPaneHeight = 232;
	
	//URLs and error messages
	private final String errorMessage = "Error !#Unknown#Error !#Unknown#1#1/&xbYou encountered a wild bug !##&xbCause: &rCan't get text from Internet##&xbPossible Reasons:#*Your Internet Connection Might be Down#*Your Website Might be Down#*The 'newsServerURL' is Invalid##&xbUser Notes:#*Check your Internet Connection#*Contact the dev if it Doens't work"; //Default error message
	private String newsServerURLDefault = "http://localhost/php/NewsGui/news.php"; //Put your page URL here
	private String newsServerURL = "http://localhost/php/NewsGui/news.php"; //Don't mind this - Used for errors testing (error 404)
	
	//Misc
	private final GuiScreen parentScreen;
	private String guiTitleTop = "Server News GUI - "; //Title "Prefix"
	private boolean keyNavigation = true; //default: true
	private boolean tabLocking = false; //default: false
	private boolean exitOnError = false; //default: true
	private boolean sidebarEnabled = false; //default: false - It is a debug feature, don't let it on "true" !
	private int[] linesPosition = new int[13];
	private String[] lines = new String[13];
	private String[] titles = {" "," "," "," "};
	private String[] spaces = {"0","1","1","1","1","1","1","1","1","1","1","1","1"};
	private int[] pageMaxNumbers = {1,1};
	private int newsPage = 1;
	private int newsType = 0;
	private int newsScrollOffset = 0;
	
	public GuiServerNews(GuiScreen par1GuiScreen) {
		this.parentScreen = par1GuiScreen;
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		if(this.sidebarEnabled) {
			this.drawNewsSideBar(par1, par2, par3);
		}
		this.drawNewsBackground(par1, par2, par3);
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		this.drawTitle();
		this.drawText();
		this.drawPageNumbre();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
	}
	
	private void getPageText() {
		boolean hasBugged = false;
		try {
			this.getUrlInformation();
		} catch (Exception e1) {
			e1.printStackTrace();
			hasBugged = true;
		}
		
		if(exitOnError && hasBugged) {
			this.mc.displayGuiScreen(parentScreen);
		} else if(exitOnError==false && hasBugged) {
			try {
				this.loadText(errorMessage);
			} catch (Exception e2) {
				e2.printStackTrace();
				this.mc.displayGuiScreen(parentScreen);
			}
		}
	}
	
	private void getUrlInformation() {
		try {
			URL getNews;
			getNews = new URL(newsServerURLDefault+"?page="+newsPage+"&type="+newsType);
			URLConnection yc = getNews.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				try {
					loadText(inputLine);
				} catch (Exception e) {
					e.printStackTrace();
					this.mc.displayGuiScreen(parentScreen);
				}
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new EmptyStackException();
		}
	}
	
	private void loadText(String par1) {
		String[] content = par1.split("/");
		
		//Loading Gui Titles & Parameters
		String[] parameters = content[0].split("#");
		for(int i=0; i<4; i++ ) {
			titles[i] = parameters[i];
		}
		pageMaxNumbers[0]=Integer.parseInt(parameters[4]);
		pageMaxNumbers[1]=Integer.parseInt(parameters[5]);
		
		//Loading Text
		String[] textParts = content[1].split("#");
		lines = new String[textParts.length];
		for(int j = 0; j < textParts.length; j++) {
			lines[j] = textParts[j].trim();
		}
		
		//Loading Spaces
		spaces = content[2].split("(?!^)");
	}
	
	public void initGui() {
		this.getPageText();
		String bold = EnumChatFormatting.BOLD.toString();
		GuiButton buttonLeft, buttonRight, buttonTab1, buttonTab2;
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 + 24, (this.height-30) / 2 + 74, 80, 20, "Done"));
		this.buttonList.add(buttonTab1 = new GuiButton(1, this.width / 2 - 117 , (this.height + 30) / 2 + 74, 80, 20, titles[1]));
		this.buttonList.add(buttonTab2 = new GuiButton(2, this.width / 2 + 36 , (this.height + 30) / 2 + 74, 80, 20, titles[3]));
		this.buttonList.add(buttonLeft = new GuiButton(3, (width - newsPaneWidth) / 2 + 18, (height-30) / 2 + 74, 25, 20, bold + "<"));
		this.buttonList.add(buttonRight = new GuiButton(4, (width - newsPaneWidth) / 2 + 74 + 32, (height-30) / 2 + 74, 25, 20, bold + ">"));
		if(tabLocking) {
			buttonTab1.enabled = this.isTabLocking(0);
			buttonTab2.enabled = this.isTabLocking(1);
		}
		buttonLeft.enabled = this.isPageLockingLeft();
		buttonRight.enabled = this.isPageLockingRight();
		if(this.sidebarEnabled) {
			this.buttonList.add(new GuiButton(5, this.width/2+128+6 , this.height/2-128-32 + 74, 80, 20, bold + "URL Error"));
			this.buttonList.add(new GuiButton(6, this.width/2+128+6 , this.height/2-128 + 74, 80, 20, bold + "Page Error"));
			this.buttonList.add(new GuiButton(7, this.width/2+128+6 , this.height/2-64-32 + 74, 80, 20, bold + "Type Error"));
			this.buttonList.add(new GuiButton(8, this.width/2+128+6 , this.height/2-64 + 74, 80, 20, bold + "???"));
			this.buttonList.add(new GuiButton(9, this.width/2+128+6 , this.height/2-32 + 74, 80, 20, "Close"));
		}
		GuiButtonScrolling buttonUp, buttonDown;
		this.buttonList.add(buttonUp = new GuiButtonScrolling(10, this.width/2+128-14, this.height/2 - 100, 0));
		this.buttonList.add(buttonDown = new GuiButtonScrolling(11, this.width/2+128-14, this.height/2 + 45, 1));
		buttonUp.enabling(newsScrollOffset-1>=0);
		buttonDown.enabling(newsScrollOffset+12<lines.length-1);
	}
	
	private boolean isTabLocking(int par1) {
		if(this.newsType==par1) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isPageLockingLeft() {
		if (newsPage <= 1) {
			return false;
		} else {
			return true;
		}
	}
	
	private boolean isPageLockingRight() {
		try {
			if(newsPage >= pageMaxNumbers[newsType]) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			if(exitOnError==true) {
				e.printStackTrace();
				this.mc.displayGuiScreen(parentScreen);
			}
			return false;
		}
	}
	
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) {
			this.mc.displayGuiScreen(parentScreen);
		}
		
		if (par1GuiButton.id == 1) {
			newsType=0;
			newsPage=1;
			newsScrollOffset=0;
			this.initGui();
		}

		if (par1GuiButton.id == 2) {
			newsType=1;
			newsPage=1;
			newsScrollOffset=0;
			this.initGui();
		}
		
		if (par1GuiButton.id == 3) {
			if(newsPage > 1) {
				newsPage--;
				newsScrollOffset=0;
			}
			this.initGui();
		}
		
		if (par1GuiButton.id == 4) {
			if(newsPage < pageMaxNumbers[newsType]) {
				newsPage++;
				newsScrollOffset=0;
			}
			this.initGui();
		}
		
		if (par1GuiButton.id == 5) {
			this.newsServerURLDefault="http://404InDaFace";
			newsScrollOffset=0;
			this.initGui();
		}
		if (par1GuiButton.id == 6) {
			newsPage=42;
			newsScrollOffset=0;
			this.initGui();
		}
		if (par1GuiButton.id == 7) {
			newsType=42;
			newsScrollOffset=0;
			this.initGui();
		}
		if (par1GuiButton.id == 8) {
			newsScrollOffset=0;
			this.initGui();
		}
		if (par1GuiButton.id == 9) {
			this.newsPage = 1;
			this.newsType = 0;
			this.newsServerURLDefault=this.newsServerURL;
			this.sidebarEnabled=false;
			newsScrollOffset=0;
			this.initGui();
		}
		if (par1GuiButton.id == 10) {
			if(newsScrollOffset-1>=0) {
				newsScrollOffset--;
			}
			this.initGui();
		}
		if (par1GuiButton.id == 11) {
			if(newsScrollOffset+12 < lines.length-1) {
				newsScrollOffset++;
			}
			this.initGui();
		}
		try {
			super.actionPerformed(par1GuiButton);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void keyTyped(char par1, int par2) {
		if(par2 == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		}
		
		if(keyNavigation) {
			if(par2==203||par2==30||par2==16) {
				if(newsPage > 1) {
					newsPage--;
					newsScrollOffset=0;
				}
				this.initGui();
			}
			
			if(par2==205||par2==32) {
				if(newsPage < pageMaxNumbers[newsType]) {
					newsPage++;
					newsScrollOffset=0;
				}
				this.initGui();
			}
			
			if(par2==200) {
				//Scroll Up
				System.out.println("Up");
			}
			
			if(par2==208) {
				//Scroll Down
				System.out.println("Down");
			}
			System.out.println(par2);
		}
	}
	
	private void drawText() {
		int x = (this.width - this.newsPaneWidth) / 2;
		int y = (this.height - this.newsPaneHeight) / 2;
		int[] horizontalSpacing = {x+20,x+25,x+30,x+35};
		int ytitle = y+22;
		int ytxt = ytitle+12;
		
		try {
			this.fontRendererObj.drawString(lines[0], horizontalSpacing[Integer.parseInt(spaces[0])], ytitle, 4210752);
		} catch(Exception e) {
			this.fontRendererObj.drawString(lines[0], horizontalSpacing[0], ytitle, 4210752);
		}
		if(lines.length<=13) {
			for(int k=1; k < lines.length; k++) {
				try {
					this.fontRendererObj.drawString(lines[k], horizontalSpacing[Integer.parseInt(spaces[k])], ytxt+11*(k-1), 4210752);
				} catch(Exception e) {
					this.fontRendererObj.drawString(lines[k], horizontalSpacing[1], ytxt+11*(k-1), 4210752);
				}
			}
		} else {
			for(int k=1+newsScrollOffset; k < 13+newsScrollOffset; k++) {
				try {
					this.fontRendererObj.drawString(lines[k], horizontalSpacing[Integer.parseInt(spaces[k])], ytxt+11*(k-1-newsScrollOffset), 4210752);
				} catch(Exception e) {
					this.fontRendererObj.drawString(lines[k], horizontalSpacing[1], ytxt+11*(k-1-newsScrollOffset), 4210752);
				}
			}
		}
		
	}
	
	protected void drawTitle() {
		int i = (this.width - this.newsPaneWidth) / 2;
		int j = (this.height - this.newsPaneHeight) / 2;
		String title = null;
		switch (newsType) {
			case 0:title = titles[0];break;
			case 1:title = titles[2];break;
			default:title = "Error !";
		}
		this.fontRendererObj.drawString(guiTitleTop + title, i + 10, j + 5, 4210752);
	}
	
	protected void drawPageNumbre() {
		int i = (this.width - this.newsPaneWidth) / 2 + 48 + 10;
		int j = (this.height - 30) / 2 + 80;
		this.fontRendererObj.drawString("Page " + newsPage, i, j, 4210752);
	}
	
	protected void drawNewsBackground(int par1, int par2, float par3) {
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(serverNewsGUITextures);
		this.drawTexturedModalRect((this.width-this.newsPaneWidth)/2, (this.height-this.newsPaneHeight)/2, 0, 0, this.newsPaneWidth, this.newsPaneHeight);
		GL11.glPopMatrix();
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		super.drawScreen(par1, par2, par3);
	}
	
	protected void drawNewsSideBar(int par1, int par2, float par3) {
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(129.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(serverNewsSidebarTextures);
		this.drawTexturedModalRect((this.width-this.newsPaneWidth)/2, (this.height-this.newsPaneHeight)/2, 0, 0, this.newsPaneWidth, this.newsPaneHeight);
		GL11.glPopMatrix();
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		super.drawScreen(par1, par2, par3);
	}
	
	public boolean doesGuiPauseGame() {
		return true;
	}
}