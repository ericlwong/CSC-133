/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

public class AttackStrategy implements IStrategy {
	private GameWorld gw;
	private NonPlayerCyborg npcCyborg;
	
	// Constructor
	public AttackStrategy(NonPlayerCyborg npc, GameWorld gw) {
		this.npcCyborg = npc;
		this.gw = gw;
	}
	
	@Override
	public void apply() {
		gw.attackPlayer(this.npcCyborg);		// Invoke the attackPlayer method in GameWorld which holds the logic for the attack player strategy
	}
	
	public String toString() {
		return "Attack the Player Strategy";
	}

}
