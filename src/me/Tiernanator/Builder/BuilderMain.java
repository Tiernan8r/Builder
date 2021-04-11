package me.Tiernanator.Builder;

import me.Tiernanator.Builder.Commands.Butcher;
import me.Tiernanator.Builder.Commands.Edits.*;
import me.Tiernanator.Builder.Commands.Edits.Cuboidular.Base;
import me.Tiernanator.Builder.Commands.Edits.Cuboidular.Cap;
import me.Tiernanator.Builder.Commands.Edits.Cuboidular.Walls;
import me.Tiernanator.Builder.Commands.Edits.Liquids.Drain;
import me.Tiernanator.Builder.Commands.Edits.Liquids.Flood;
import me.Tiernanator.Builder.Commands.Platform;
import me.Tiernanator.Builder.Commands.RegionSelect.*;
import me.Tiernanator.Builder.Commands.Volume;
import me.Tiernanator.Builder.Commands.Wand;
import me.Tiernanator.Builder.Events.PlayerInteract;
import me.Tiernanator.Builder.Events.Set;
import me.Tiernanator.Builder.Events.WandBreakBlock;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Builder.Materials.ListMaterials;
import me.Tiernanator.Builder.Undo.Undo;
import me.Tiernanator.Builder.Undo.UndoConfig;
import me.Tiernanator.Builder.WorldTemplates.*;
import me.Tiernanator.Utilities.SQL.SQLServer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BuilderMain extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		setPlugin();
		initialiseSQL();
		registerCommands();
		registerEvents();
		
	}

	@Override
	public void onDisable() {
//		try {
////			getSQL().getConnection().close();
//			getSQL().closeConnection();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	private void registerCommands() {
		getCommand("wand").setExecutor(new Wand(this));
		getCommand("replace").setExecutor(new Replace(this));
		getCommand("fill").setExecutor(new Fill(this));
		getCommand("materials").setExecutor(new ListMaterials(this));
		getCommand("remove").setExecutor(new Remove(this));
		getCommand("changeTo").setExecutor(new ChangeTo(this));
		getCommand("clear").setExecutor(new Clear(this));
		getCommand("save").setExecutor(new Save(this));
		getCommand("move").setExecutor(new Move(this));
//		getCommand("protect").setExecutor(new Protect(this));
		getCommand("getTemplate").setExecutor(new GetTemplate(this));
		getCommand("removeTemplate").setExecutor(new RemoveTemplate(this));
		getCommand("listTemplates").setExecutor(new ListTemplates(this));
		getCommand("drain").setExecutor(new Drain(this));
		getCommand("flood").setExecutor(new Flood(this));
		getCommand("sphere").setExecutor(new Sphere(this));
		getCommand("dome").setExecutor(new Dome(this));
		getCommand("cuboid").setExecutor(new Cuboid(this));
		getCommand("cylinder").setExecutor(new Cylinder(this));
		getCommand("circle").setExecutor(new Circle(this));
		getCommand("set").setExecutor(new Set(this));
		getCommand("walls").setExecutor(new Walls(this));
		getCommand("cap").setExecutor(new Cap(this));
		getCommand("base").setExecutor(new Base(this));
		getCommand("platform").setExecutor(new Platform(this));
		getCommand("bowl").setExecutor(new Bowl(this));
		getCommand("flip").setExecutor(new Flip(this));
		getCommand("butcher").setExecutor(new Butcher(this));
		getCommand("undo").setExecutor(new Undo(this));
//		getCommand("setbiome").setExecutor(new SetBiome(this));
		getCommand("volume").setExecutor(new Volume(this));
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new WandSelect(this), this);
		pm.registerEvents(new WandBreakBlock(this), this);
		
	}
	
	private void setPlugin() {
		TemplateConfig.setPlugin(this);
		UndoConfig.setPlugin(this);
	}

	private void initialiseSQL() {
		
		String query = "CREATE TABLE IF NOT EXISTS Templates ( "
				+ "ID int AUTO_INCREMENT,"
				+ "Name varchar(255), "
				+ "PRIMARY KEY (ID)"
				+ ");";
		SQLServer.executeQuery(query);
		
	}

}
