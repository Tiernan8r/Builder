package me.Tiernanator.Builder.Events;

import me.Tiernanator.Utilities.Players.SelectAction;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//This is the custom wand select block event that is called by PlayerInteract, it just contains functions that
//return all the values needed

public final class WandSelectEvent extends Event {
	
	//handlers is a variable "handled"(...) by the server
    private static final HandlerList handlers = new HandlerList();
    //the clicked location
    private Location location;
    //the player who used the wand
    private Player player;
    //the type of action, left click or right click
    private SelectAction action;

    //constructor for the event that sets the variables
    public WandSelectEvent(Location block, Player player, SelectAction selectAction) {
        this.location = block;
        this.player = player;
        this.action = selectAction;
        
    }

    //return the location clicked
    public Location getSelectedLocation() {
        return this.location;
    }
    //return the type of click
    public SelectAction getAction() {
        return this.action;
    }
    //get the block selected
    public Block getSelectedBlock() {
        return this.location.getBlock();
    }
    //get the player who done it
    public Player getPlayer() {
        return this.player;
    }
    //the next two are necessary for the server to use the event
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}