/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

public class BaseStrategy implements IStrategy {
	private NonPlayerCyborg npcCyborg;
	private GameWorld gw;

	// Constructor
	public BaseStrategy(NonPlayerCyborg npcCyborg, GameWorld gw) {
		this.npcCyborg = npcCyborg;
		this.gw = gw;
	}
	
	@Override
	public void apply() {
		gw.headToNextBase(this.npcCyborg);			// Invoke headToNextBase() method from GameWorld which contains the logic to implement the Base Strategy
	}
	
	public String toString() {
		return "Head to Next Base Strategy";
	}
	
}
