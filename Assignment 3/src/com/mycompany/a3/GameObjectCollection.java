/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

import java.util.Vector;

public class GameObjectCollection implements ICollection<GameObject> {
	
	private Vector<GameObject> theCollection;
	
	// Constructor
	public GameObjectCollection() {
		theCollection = new Vector<GameObject>();
	}

	@Override
	public void add(GameObject newObject) {
		theCollection.addElement(newObject);		// Add GameObject elements to the GameObjectCollection
		
	}

	@Override
	public IIterator<GameObject> getIterator() {
		return new GameObjectIterator();			// Create an iterator to iterate through the GameObjectCollection
	}
	
	private class GameObjectIterator implements IIterator<GameObject> {
		private int currElementIndex;
		
		// Constructor for GameObjectIterator
		public GameObjectIterator() {
			currElementIndex = -1;
		}
		
		@Override
		public boolean hasNext() {							// Check if there's another element in the GameObjectCollection
			if (theCollection.size() <= 0) return false;
			if (currElementIndex == theCollection.size() - 1)
				return false;
			return true;
		}

		@Override
		public GameObject getNext() {				// Get the next element in the GameObjectCollection
			currElementIndex++;
			return (theCollection.elementAt(currElementIndex));
		}
		
		@Override
		public void remove() {				// Remove an element from the GameObjectCollection
			theCollection.remove(currElementIndex--);
		}
		
		@Override
		public void removeAll() {
			theCollection.removeAllElements();		// Remove all elements from the GameObjectCollection
		}
		
	}  // end GameObjectIterator class
}  // end GameObjectCollection class
