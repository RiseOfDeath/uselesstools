package riseofdeath;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.sun.xml.internal.ws.util.StringUtils;


public class uselesstools extends JavaPlugin {

	public static Logger log;
    public static Server server;
    private PluginManager pm;
    private Configuration config;
    public int blocks[]=new int[256];
     
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		log.info( getDescription().getName() + " version " + getDescription().getVersion() + " is disabled" );
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		log = Logger.getLogger("Minecraft");
        pm = getServer().getPluginManager();
        config = getConfiguration();
        log.info( getDescription().getName() + " version " + getDescription().getVersion() + " is enabled" );
        
        for(int i=0;i<256;i++)
        {
        	blocks[i]=0;
        }
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		if(commandLabel.contains("generate")){
			if(sender.isOp()){
				sender.sendMessage("Ok, you can do this...");	
				if(args.length==5){
					double work;
					double count=0;
					double done = 0;
					String done_str;
					work = (Integer.parseInt(args[3]) - Integer.parseInt(args[1]))* (Integer.parseInt(args[4]) - Integer.parseInt(args[2]));
					try{
						sender.sendMessage("Starting generation");
						for(int i = Integer.parseInt(args[1]);i<=Integer.parseInt(args[3]);i++){
							for(int j =Integer.parseInt(args[2]);j<=Integer.parseInt(args[4]);j++){
								sender.getServer().getWorld(args[0]).getChunkAt(i, j);
								sender.getServer().getWorld(args[0]).unloadChunk(i, j);
								count++;
								done = count/work*100;
							}
							done_str = Double.toString(done);
							sender.sendMessage("Now done: " + done_str + "%");
						}
						sender.sendMessage("Job is done");
						
					}
					catch(CommandException e){
						sender.sendMessage(e.toString());						
					}
					
				}
				else{
					//no correct arguments
					sender.sendMessage("Wrong arguments. Type <command> <world> <x1> <y1> <x2> <y2>");	
				}
			}
			else{
				//player no OP ]:->
				sender.sendMessage("Oh... you don't OP, sorry...");
			}
		}
		if(commandLabel.contains("statistic"))
		{
			//подсчет кол-ва блоков
			if(sender.isOp()){
			sender.sendMessage("Ok, you can do this...");		
			double work;
			double count=0;
			double done = 0;
			String done_str;
			work = (Integer.parseInt(args[3]) - Integer.parseInt(args[1]))* (Integer.parseInt(args[4]) - Integer.parseInt(args[2]));
			if(args.length==5){
				try{
			
				sender.sendMessage("Try get statistic...");
				for(int i = Integer.parseInt(args[1]);i<=Integer.parseInt(args[3]);i++){
					for(int j =Integer.parseInt(args[2]);j<=Integer.parseInt(args[4]);j++){
						readblocks(sender.getServer().getWorld(args[0]).getChunkAt(i, j));
						sender.getServer().getWorld(args[0]).unloadChunk(i, j);
						count++;
						
					}
					done = count/work*100;
					done_str = Double.toString(done);
					sender.sendMessage("Now done: " + done_str + "%");	
				}
				for(int i=0;i<256;i++)
		        {
					sender.sendMessage("Block " + i + ": " +Integer.toString(blocks[i]));
		        }
				sender.sendMessage("Job is done");
				return true;
			}
			catch(CommandException e){
				sender.sendMessage(e.toString());						
			}
			}
			else
			{
				sender.sendMessage("Wrong arguments. Type <command> <world> <x1> <y1> <x2> <y2>");
				return false;
			}
			}
			else
			{
				sender.sendMessage("Oh... you don't OP, sorry...");
			}
		}

		return false;
	}
	boolean readblocks(Chunk chunk)
	{
		for(int i=0;i<16;i++)
		{
			for(int j=0;j<16;j++)
			{
				for(int k=0;k<128;k++)
				{
					blocks[chunk.getBlock(i, k, j).getTypeId()]++;
				}
			}
		}
		return false;
	}

}
