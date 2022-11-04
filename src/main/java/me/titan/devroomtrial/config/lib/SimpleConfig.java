package me.titan.devroomtrial.config.lib;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SimpleConfig {

	protected File file;
	protected YamlConfiguration config;
	public SimpleConfig(String name, JavaPlugin plugin){
		file = new File(plugin.getDataFolder(),name);
		if(!file.exists()){
			plugin.saveResource(name,false);
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	public void reload(){
		config = YamlConfiguration.loadConfiguration(file);

		init();
	}
	protected void init(){

	}

}
