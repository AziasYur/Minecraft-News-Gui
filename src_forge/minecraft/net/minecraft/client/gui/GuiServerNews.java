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
	 * @author AziasCreations, Herwin0996
	 * @version 1.0
	 */

@SideOnly(Side.CLIENT)
public class GuiServerNews extends GuiScreen {
    private final GuiScreen parentScreen;

    private int newsPage = 1;
    private int newsType = 0;
    protected int achievementsPaneWidth = 256;
    protected int achievementsPaneHeight = 232;
    protected double guiMapX, guiMapY;
    protected double field_74124_q, field_74123_r, field_74117_m, field_74115_n;
	private String line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11, line12, line13;
    private static final int guiMapTop = AchievementList.minDisplayColumn * 24 - 112;
    private static final int guiMapLeft = AchievementList.minDisplayRow * 24 - 112;
    private static final int guiMapBottom = AchievementList.maxDisplayColumn * 24 - 77;
    private static final int guiMapRight = AchievementList.maxDisplayRow * 24 - 77;

    private static final ResourceLocation serverNewsTextures = new ResourceLocation("textures/gui/server_news.png");
    public String NewsServerURL = "http://localhost/php/NewsGui/news.php";
    public int maxPages = 3;
    public String Type0Name = "Updates";
    public String Button0Name = "Updates";
    public String Type1Name = "Events";
    public String Button1Name = "Events";
    
	public GuiServerNews(GuiScreen par1GuiScreen) {
        this.parentScreen = par1GuiScreen;
	}
	
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.genNewsBackground(par1, par2, par3);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        this.drawTitle(); this.drawText();
        this.drawPageNumbre();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
       
    public void getpagetext() {
        try {
			this.geturlinformation();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void geturlinformation() throws IOException {
    	URL getNews = new URL(NewsServerURL+"?page="+newsPage+"&type="+newsType);
        URLConnection yc = getNews.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        try{
        	doSplashes(inputLine);
        } catch (Exception e) {
    		e.printStackTrace();
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        in.close();
    }
    
    private void doSplashes(String str1) {
		str1 = str1.replaceAll("~~", "              ".toString());
		str1 = str1.replaceAll("&0", Colors.BLACK.toString());
		str1 = str1.replaceAll("&1", Colors.DARK_BLUE.toString());
		str1 = str1.replaceAll("&2", Colors.DARK_GREEN.toString());
		str1 = str1.replaceAll("&3", Colors.DARK_AQUA.toString());                                      
		str1 = str1.replaceAll("&4", Colors.DARK_RED.toString());
		str1 = str1.replaceAll("&5", Colors.DARK_PURPLE.toString());
		str1 = str1.replaceAll("&6", Colors.GOLD.toString());
		str1 = str1.replaceAll("&7", Colors.GRAY.toString());
		str1 = str1.replaceAll("&8", Colors.DARK_GRAY.toString());
		str1 = str1.replaceAll("&9", Colors.BLUE.toString());
		str1 = str1.replaceAll("&a", Colors.GREEN.toString());
		str1 = str1.replaceAll("&b", Colors.AQUA.toString());
		str1 = str1.replaceAll("&c", Colors.RED.toString());
		str1 = str1.replaceAll("&d", Colors.LIGHT_PURPLE.toString());
		str1 = str1.replaceAll("&e", Colors.YELLOW.toString());
		str1 = str1.replaceAll("&f", Colors.WHITE.toString());
		str1 = str1.replaceAll("&xb", Colors.BOLD.toString());
		str1 = str1.replaceAll("&xu", Colors.UNDERLINE.toString());
		str1 = str1.replaceAll("&xi", Colors.ITALIC.toString());
		str1 = str1.replaceAll("&xs", Colors.STRIKETHROUGH.toString());
		str1 = str1.replaceAll("&r", Colors.RESET.toString());
    	String[] parts = str1.split("#");
    	line1 = parts[0].trim();line2 = parts[1].trim();
    	line3 = parts[2].trim();line4 = parts[3].trim();
    	line5 = parts[4].trim();line6 = parts[5].trim();
    	line7 = parts[6].trim();line8 = parts[7].trim();
    	line9 = parts[8].trim();line10 = parts[9].trim();
    	line11 = parts[10].trim();line12 = parts[11].trim();
    	line13 = parts[12].trim();
	}

    public boolean isPageLockingLeft() {
    	if (newsPage == 1)
    	{return false;}
    	else
    	{return true;}
    }

    public boolean isPageLockingRight() {
    	if (newsPage >= maxPages)
    	{return false;}
    	else
    	{return true;}
    }
    
    public void initGui() {
    	String bold = Colors.BOLD.toString();
        this.buttonList.clear();
        this.buttonList.add(new GuiSmallButton(1, this.width / 2 + 24, (this.height-30) / 2 + 74, 80, 20, StatCollector.translateToLocal("Done")));
        this.buttonList.add(new GuiSmallButton(4, this.width / 2 + 36 , (this.height + 30) / 2 + 74, 80, 20, StatCollector.translateToLocal(Button1Name)));
        this.buttonList.add(new GuiSmallButton(5, this.width / 2 - 117 , (this.height + 30) / 2 + 74, 80, 20, StatCollector.translateToLocal(Button0Name)));
        
        GuiButton buttonLeft;
        this.buttonList.add(buttonLeft = new GuiSmallButton(2, (width - achievementsPaneWidth) / 2 + 18, (height-30) / 2 + 74, 25, 20, StatCollector.translateToLocal(bold + "<")));
        buttonLeft.enabled = this.isPageLockingLeft();
        GuiButton buttonRight;
        this.buttonList.add(buttonRight = new GuiSmallButton(3, (width - achievementsPaneWidth) / 2 + 74 + 32, (height-30) / 2 + 74, 25, 20, StatCollector.translateToLocal(bold + ">")));
        buttonRight.enabled = this.isPageLockingRight();
        
        this.getpagetext();
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        
        //Page -1
        if (par1GuiButton.id == 2) {
        	if(newsPage == 1)
        	{}
        	else
        	{newsPage--;}
            this.initGui();
        }
        
        //Page +1
        if (par1GuiButton.id == 3) {
        	if(newsPage >= maxPages)
        	{}
        	else
        	{newsPage++;}
            this.initGui();
        }

        //Page Events
        if (par1GuiButton.id == 4) {
        	if(newsType == 0) {
        		newsType++;
        		newsPage = 1;
        	} else {}
            this.initGui();
        }
        
        //Page Changelog
        if (par1GuiButton.id == 5) {
        	if(newsType == 1) {
        		newsType--;
        		newsPage = 1;
        	} else {}
            this.initGui();
        }
        super.actionPerformed(par1GuiButton);
    }
    
    private void drawText() {
    	int i = (this.width - this.achievementsPaneWidth) / 2;
        int j = (this.height - this.achievementsPaneHeight) / 2;
        String bold, undl, ital, rest;
        bold = Colors.BOLD.toString();
        undl = Colors.UNDERLINE.toString();
        rest = Colors.RESET.toString();
        int title = i+20;
        int mtext = i+25;
        int stext = i+30;
        int ytitle = j+22;
        int ytxt = ytitle+12;
        
        if(newsType==0) {
        	this.fontRenderer.drawString(line1, title, ytitle, 4210752);
        	this.fontRenderer.drawString(line2, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line3, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line4, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line5, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line6, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line7, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line8, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line9, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line10, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line11, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line12, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line13, mtext, ytxt, 4210752);ytxt = ytxt+11;
        } else if(newsType==1) {
        	this.fontRenderer.drawString(line1, title, ytitle, 4210752);
        	this.fontRenderer.drawString(line2, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line3, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line4, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line5, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line6, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line7, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line8, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line9, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line10, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line11, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line12, mtext, ytxt, 4210752);ytxt = ytxt+11;
        	this.fontRenderer.drawString(line13, mtext, ytxt, 4210752);ytxt = ytxt+11;
        }
	}
    
    public void updateScreen() {
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
    }

    protected void drawTitle() {
        int i = (this.width - this.achievementsPaneWidth) / 2;
        int j = (this.height - this.achievementsPaneHeight) / 2;
        String title = null;
        switch (newsType) {
            case 0:title = Type0Name;break;
            case 1:title = Type1Name;break;
            default:title = "ERROR !!!";
        }
        this.fontRenderer.drawString("Server News GUI - " + title, i + 10, j + 5, 4210752);
    }
    
    protected void drawPageNumbre() {
        int i = (this.width - this.achievementsPaneWidth) / 2 + 48 + 10;
        int j = (this.height - 30) / 2 + 80;
    	this.fontRenderer.drawString("Page " + newsPage, i, j, 4210752);
    }
    
    protected void genNewsBackground(int par1, int par2, float par3) {
        int i1 = (this.width - this.achievementsPaneWidth) / 2;
        int j1 = (this.height - this.achievementsPaneHeight) / 2;
        int k1 = i1 + 16; int l1 = j1 + 17;
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
        this.drawTexturedModalRect(i1, j1, 0, 0, this.achievementsPaneWidth, this.achievementsPaneHeight);
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