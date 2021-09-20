/* Eric Wong
 * CSC 133
 * Assignment 3
 */

package com.mycompany.a3;

public interface ICollection<E> {
	public void add(E newObject);
	public IIterator<E> getIterator();
}
