
package sudoku.core.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class RandomArrayList<E>
		implements Iterable<E> {

	private static final long serialVersionUID = -270418730262367927L;

	ArrayList<E> _list;

	public RandomArrayList() {
		_list = new ArrayList<E>();
	}

	public RandomArrayList(int initialCapacity) {
		_list = new ArrayList<E>(initialCapacity);
	}

	public RandomArrayList(Collection<? extends E> c) {
		_list = new ArrayList<E>(c);
	}

	public E get(int index) {
		return _list.get(index);
	}

	public boolean add(E element) {
		return _list.add(element);
	}

	public boolean contains(Object element) {
		return _list.contains(element);
	}

	public void clear() {
		_list.clear();
	}

	public boolean isEmpty() {
		return _list.isEmpty();
	}

	public Iterator<E> iterator() {
		return new RandomIterator();
	}

	class RandomIterator
			implements Iterator<E> {

		int cursor = 0;
		private LinkedList<Integer> _usedIndices = new LinkedList<Integer>();
		private int _listLength = _list.size();

		public boolean hasNext() {
			return cursor != _listLength;
		}

		public E next() {
			int index = (int) Math.floor(Math.random() * _listLength);
			boolean indexValid = false;
			while (!indexValid) {
				indexValid = true;
				for (Integer used : _usedIndices) {
					if (used == index) {
						indexValid = false;
						if ((index + 1) == _listLength) {
							index = 0;
						}
						else {
							index++;
						}
					}
				}
			}
			_usedIndices.add(index);
			E element = get(index);
			cursor++;
			return element;
		}

		public void remove() {
			throw new UnsupportedOperationException("not implemented!");
		}
	}
}
