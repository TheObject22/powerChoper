package pestControl;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
 
import java.awt.*;

import javax.swing.text.html.parser.Entity;
 
@ScriptManifest(author = "Garrett", info = "Simple powerchoper script", name = "powerChop", version = 0, logo = "")
public class main extends Script {
	/*
	 * THIS COMMENT IS FOR A PEST CONTROL SCRIPT THAT I WILL IMPLEMENT EVENTUALLY =/
	 * Check coordinates and look for boat if at dock and board if that case
	 * else look for trees to cut if there are less than 10 logs in inventory
	 * If there are 10 logs in inventory look for barricades to repair
	 * if can't find barricades move across the backside of map looking
	 * for the interact "repair barricade"
	 * REPEAT
	 * 
	 */
    @Override
    public void onStart() {
        log("Let's get started!");
    }
    
    private enum State{
    	CHOP,WAIT,DROP //Chop trees, wait for new trees and clear my inventory of logs
    };
    
    private State getState(){
    	RS2Object mapleTree = objects.closest("Maple tree");
    	
    	if(!inventory.isEmpty()){
    		return State.DROP;
    		//If the inventory doesn't have 10 logs look for trees to chop
    	}
    	if(mapleTree != null){
    		
    		if(mapleTree.isVisible()){
    		return State.CHOP; //If there is a tree chop
    		}
    	}
    	return State.WAIT;
    	
    }
    
    @Override
    public int onLoop() throws InterruptedException {
    	switch(getState()){
    	case CHOP:
    		RS2Object mapleTree = objects.closest("Maple tree");
    		if(mapleTree != null){
    			if(mapleTree.isVisible()){
    				mapleTree.interact("Chop down");
    			}
    		}
    		break;
    	
    	case DROP:
    		inventory.dropAll(1517); //just using id for maple logs right now
    		break;
    	
    	case WAIT://wait for tree to appear
    		sleep(random(500, 700));
			break;
    	}
    	
        return random(200, 300);
    }
 
    @Override
    public void onExit() {
        log("Thanks for running my powerchoper script!");
    }
 
    @Override
    public void onPaint(Graphics2D g) {
 
    }
 
}