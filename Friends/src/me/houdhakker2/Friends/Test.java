package me.houdhakker2.Friends;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.houdhakker2.Friends.listeners.BootsListener;

// Friends
public class Test extends JavaPlugin {

	public Logger logger;
	public PluginDescriptionFile pdfFile;
	public FileConfiguration config;
	public YamlConfiguration friends; 

	public void onEnable() {
		pdfFile = getDescription();
		logger = getLogger();
		config = getConfig();
		
		friends = new YamlConfiguration();
		
		try {
			File file = new File(getDataFolder()+File.separator+"friends.yml");
			if(file.exists() == false){
				file.createNewFile();
				friends.load(IOUtils.toString(getResource("friends.yml")));
			}else {
				friends.load(file);
			}
		} catch (Exception e) {
		}
		config.options().copyDefaults(true);
		
		logger.info(pdfFile.getFullName() + " is enabled");

	}

	public void onDisable() {
		
		saveconfig();
		
		logger.info(pdfFile.getFullName() + " is disabled");

	}
	
	public void saveconfig(){
		saveConfig();
		
		try {
			friends.save(new File(this.getDataFolder() + File.separator + "friends.yml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("friends")) {
				logger.info(args[0]);
				if(args[0].equalsIgnoreCase("add")){
					List<Object> list = (List<Object>) this.friends.getList("friends."+player.getName()+".friends");
					
					list.add(args[1]);
					
					saveconfig();
				}else if(args[0].equalsIgnoreCase("del")){
					List<Object> list = (List<Object>) this.friends.getList("friends."+player.getName()+".friends");
					
					list.remove(args[1]);
					
					saveconfig();
				}
			}
		} else {
			sender.sendMessage("You need to be a player!");
			return false;
		}
		return false;
	}

}
