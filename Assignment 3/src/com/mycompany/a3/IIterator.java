/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

public interface IIterator<E> {
	public boolean hasNext();
	public E getNext();
	public void remove();
	public void removeAll();
}
