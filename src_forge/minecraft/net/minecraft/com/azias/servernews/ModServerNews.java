package com.azias.servernews;

import java.util.Arrays;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Azias
 * @version 1.3 Indev
 * @ForgeVersion 1.8 - 11.14.3.1493
 */
@Mod(modid = "ServerNewsGUI", name= "Server News Gui", version = "1.3", clientSideOnly=true)
public class ModServerNews {
	public final String modVersion = "1.3";

    @EventHandler
	public void preload(FMLPreInitializationEvent event) {
		if(event.getSide().isClient()){}
	}

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		ModMetadata modmeta = event.getModMetadata();
		modmeta.version = modVersion;
		modmeta.url = "https://github.com/AziasYur";
		modmeta.name = "Server News Gui";
		modmeta.authorList = Arrays.asList(new String[] { "Azias" });
		modmeta.description = "This mod allow you to show a gui with the news from your server.";
		modmeta.credits = "Nope";
		//modmeta.logoFile = "";
		modmeta.autogenerated = false;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
}