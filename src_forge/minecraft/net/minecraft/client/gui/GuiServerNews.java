package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Azias
 * @version 1.1
 * @ForgeVersion 1.7.10
 */
@SideOnly(Side.CLIENT)
public class GuiServerNews extends GuiScreen {
	private final GuiScreen parentScreen;
	
	protected int newsPaneWidth = 256;
	protected int newsPaneHeight = 232;
	protected double guiMapX, guiMapY;
	protected double field_74124_q, field_74123_r, field_74117_m, field_74115_n;

	private static final ResourceLocation serverNewsTextures = new ResourceLocation("textures/gui/server_news.png"); //Put your texture loaction here
	private String NewsServerURL = "http://localhost/php/NewsGui/news.php"; //Put your page URL here
	private String guiTitleTop = "Server News GUI - "; //Title "Prefix"
	private int[] linesPosition = new int[13];
	private String[] lines = new String[13];
	private String[] titles = {" "," "," "," "};
	private int[] pageMaxNumbers = {1,1};
	private int newsPage = 1;
	private int newsType = 0;
	
	public GuiServerNews(GuiScreen par1GuiScreen) {
		this.parentScreen = par1GuiScreen;
	}
	
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
		this.genNewsBackground(par1, par2, par3);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		this.drawTitle();
		this.drawText();
		this.drawPageNumbre();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	   
	private void getpagetext() {
		try {
			this.geturlinformation();
		} catch (IOException e) {
			this.mc.displayGuiScreen((GuiScreen)null);
			e.printStackTrace();
		}
	}
	
	private void geturlinformation() throws IOException {
		URL getNews = new URL(NewsServerURL+"?page="+newsPage+"&type="+newsType);
		URLConnection yc = getNews.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
		try {
			doSplashes(inputLine);
		} catch (Exception e) {
			e.printStackTrace();
			this.mc.displayGuiScreen((GuiScreen)null);
		}
		in.close();
	}
	
	private void doSplashes(String par1) {
		String[] content = par1.split("/");
		
		//Loading Gui Titles & Parameters
		String[] parameters = content[0].split("#");
		for(int i=0; i<4; i++ ) {
			titles[i] = parameters[i];
		}
		pageMaxNumbers[0]=Integer.parseInt(parameters[4]);
		pageMaxNumbers[1]=Integer.parseInt(parameters[5]);
		
		//Loading Gui Text
		content[1]=content[1].replaceAll("&0", Colors.BLACK.toString());
		content[1]=content[1].replaceAll("&1", Colors.DARK_BLUE.toString());
		content[1]=content[1].replaceAll("&2", Colors.DARK_GREEN.toString());
		content[1]=content[1].replaceAll("&3", Colors.DARK_AQUA.toString());									  
		content[1]=content[1].replaceAll("&4", Colors.DARK_RED.toString());
		content[1]=content[1].replaceAll("&5", Colors.DARK_PURPLE.toString());
		content[1]=content[1].replaceAll("&6", Colors.GOLD.toString());
		content[1]=content[1].replaceAll("&7", Colors.GRAY.toString());
		content[1]=content[1].replaceAll("&8", Colors.DARK_GRAY.toString());
		content[1]=content[1].replaceAll("&9", Colors.BLUE.toString());
		content[1]=content[1].replaceAll("&a", Colors.GREEN.toString());
		content[1]=content[1].replaceAll("&b", Colors.AQUA.toString());
		content[1]=content[1].replaceAll("&c", Colors.RED.toString());
		content[1]=content[1].replaceAll("&d", Colors.LIGHT_PURPLE.toString());
		content[1]=content[1].replaceAll("&e", Colors.YELLOW.toString());
		content[1]=content[1].replaceAll("&f", Colors.WHITE.toString());
		content[1]=content[1].replaceAll("&xb", Colors.BOLD.toString());
		content[1]=content[1].replaceAll("&xu", Colors.UNDERLINE.toString());
		content[1]=content[1].replaceAll("&xi", Colors.ITALIC.toString());
		content[1]=content[1].replaceAll("&xs", Colors.STRIKETHROUGH.toString());
		content[1]=content[1].replaceAll("&r", Colors.RESET.toString());
		String[] textParts = content[1].split("#");
		for(int j = 0; j < 13; j++) {
			lines[j] = textParts[j].trim();
		}
		
		//Loading Lines Positions - Not working for the moment, for 1.2 or 2.0 update
		/*linesPosition = new int[13];
		int title = (this.width - this.newsPaneWidth) / 2 + 20;
		int text = title+5;
		int subtext = text+5;
		if(content.length > 2) {
			String[] textPos = content[2].split("(?!^)");
			for(int k=0;k > lines.length; k++){
				if(textPos[k].equals("a")) {
					linesPosition[k]=title;
				} else if(textPos[k].equals("b")) {
					linesPosition[k]=text;
				} else if(textPos[k].equals("c")) {
					linesPosition[k]=subtext;
				} else {
					linesPosition[k]=text;
				}
			}
		} else {
			linesPosition[0]=title;
			for(int l=1; l<linesPosition.length; l++) {
				linesPosition[l]=text;
			}
		}*/
	}

	public boolean isPageLockingLeft() {
		if (newsPage <= 1) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isPageLockingRight() {
		if(newsPage >= pageMaxNumbers[newsType]) {
			return false;
		} else {
			return true;
		}
	}
	
	public void initGui() {
		this.getpagetext();
		String bold = Colors.BOLD.toString();
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1, this.width / 2 + 24, (this.height-30) / 2 + 74, 80, 20, StatCollector.translateToLocal("Done")));
		this.buttonList.add(new GuiButton(4, this.width / 2 + 36 , (this.height + 30) / 2 + 74, 80, 20, StatCollector.translateToLocal(titles[3])));
		this.buttonList.add(new GuiButton(5, this.width / 2 - 117 , (this.height + 30) / 2 + 74, 80, 20, StatCollector.translateToLocal(titles[1])));
		GuiButton buttonLeft, buttonRight;
		this.buttonList.add(buttonLeft = new GuiButton(2, (width - newsPaneWidth) / 2 + 18, (height-30) / 2 + 74, 25, 20, StatCollector.translateToLocal(bold + "<")));
		this.buttonList.add(buttonRight = new GuiButton(3, (width - newsPaneWidth) / 2 + 74 + 32, (height-30) / 2 + 74, 25, 20, StatCollector.translateToLocal(bold + ">")));
		buttonLeft.enabled = this.isPageLockingLeft();
		buttonRight.enabled = this.isPageLockingRight();
	}

	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
		}
		
		//Page -1
		if (par1GuiButton.id == 2) {
			if(newsPage > 1) {
				newsPage--;
			}
			this.initGui();
		}
		
		//Page +1
		if (par1GuiButton.id == 3) {
			if(newsPage < pageMaxNumbers[newsType]) {
				newsPage++;
			}
			this.initGui();
		}

		//Page Events
		if (par1GuiButton.id == 4) {
			newsType=1;
			newsPage=1;
			this.initGui();
		}
		
		//Page Changelog
		if (par1GuiButton.id == 5) {
			newsType=0;
			newsPage=1;
			this.initGui();
		}
		super.actionPerformed(par1GuiButton);
	}
	
	private void drawText() {
		int x = (this.width - this.newsPaneWidth) / 2;
		int y = (this.height - this.newsPaneHeight) / 2;
		int title = x+20;
		int mtext = x+25;
		int stext = x+30;
		int ytitle = y+22;
		int ytxt = ytitle+12;
		
		for(int k=0; k < lines.length; k++) {
			if(k==0) {
				//this.fontRendererObj.drawString(lines[k], linesPosition[k], ytitle, 4210752);	
				this.fontRendererObj.drawString(lines[k], title, ytitle, 4210752);	
			} else {
				//this.fontRendererObj.drawString(lines[k], linesPosition[k], ytxt+11*(k-1), 4210752);	
				this.fontRendererObj.drawString(lines[k], mtext, ytxt+11*(k-1), 4210752);	
			}
		}
		/*this.fontRendererObj.drawString(lines[0], title, ytitle, 4210752);
		this.fontRendererObj.drawString(lines[1], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[2], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[3], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[4], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[5], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[6], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[7], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[8], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[9], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[10], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[11], mtext, ytxt, 4210752);ytxt = ytxt+11;
		this.fontRendererObj.drawString(lines[12], mtext, ytxt, 4210752);ytxt = ytxt+11;*/
	}
	
	/*public void updateScreen() {
		this.field_74117_m = this.guiMapX;
		this.field_74115_n = this.guiMapY;
		double d0 = this.field_74124_q - this.guiMapX;
		double d1 = this.field_74123_r - this.guiMapY;

		if (d0 * d0 + d1 * d1 < 4.0D) {
			this.guiMapX += d0;
			this.guiMapY += d1;
		}
		else {
			this.guiMapX += d0 * 0.85D;
			this.guiMapY += d1 * 0.85D;
		}
	}*/

	protected void drawTitle() {
		int i = (this.width - this.newsPaneWidth) / 2;
		int j = (this.height - this.newsPaneHeight) / 2;
		String title = null;
		switch (newsType) {
			case 0:title = titles[0];break;
			case 1:title = titles[2];break;
			default:title = "ERROR !!!";
		}
		this.fontRendererObj.drawString(guiTitleTop + title, i + 10, j + 5, 4210752);
	}
	
	protected void drawPageNumbre() {
		int i = (this.width - this.newsPaneWidth) / 2 + 48 + 10;
		int j = (this.height - 30) / 2 + 80;
		this.fontRendererObj.drawString("Page " + newsPage, i, j, 4210752);
	}
	
	protected void genNewsBackground(int par1, int par2, float par3) {
		int i1 = (this.width - this.newsPaneWidth) / 2;
		int j1 = (this.height - this.newsPaneHeight) / 2;
		//int k1 = i1 + 16; int l1 = j1 + 17;
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
		this.mc.getTextureManager().bindTexture(serverNewsTextures);
		this.drawTexturedModalRect(i1, j1, 0, 0, this.newsPaneWidth, this.newsPaneHeight);
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