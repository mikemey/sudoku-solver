
package sudoku.core.internal.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import sudoku.core.internal.RandomArrayList;

public class RandomArrayListTest {

	@Test
	public void testRandomArrayList() {
		boolean passed = true;
		boolean ralContainsElement = true;
		boolean listContainsElement = true;
		for (int run = 0; run < 20 && passed && ralContainsElement && listContainsElement; run++) {
			passed = false;

			ArrayList<Entry> list = new ArrayList<Entry>();
			RandomArrayList<Entry> ral = new RandomArrayList<Entry>();

			for (int i = 0; i < 10; i++) {
				Entry e = new Entry(i, i + ".");
				list.add(e);
				ral.add(e);

			}
			int index = 0;
			for (Entry entry : ral) {
				if (!entry.equals(list.get(index))) {
					passed = true;
					break;
				}
				index++;
			}

			ArrayList<Entry> checklist = new ArrayList<Entry>();
			for (Entry entry : ral) {
				checklist.add(entry);
				if (!list.contains(entry)) {
					ralContainsElement = false;
				}
			}
			for (Entry entry : list) {
				if (!checklist.contains(entry)) {
					listContainsElement = false;
				}
			}

		}
		if (!passed) {
			fail("RandomArrayList doesn't return entries randomly!");
		}
		if (!ralContainsElement) {
			fail("Elements aren't found in the RandomArrayList!");
		}
		if (!listContainsElement) {
			fail("RandomArrayList doesn't contain all entries!");
		}
	}

	private class Entry {

		private String _name;
		private int _id;

		public Entry(int id, String name) {
			_name = name;
			_id = id;
		}

		public String getName() {
			return _name;
		}

		public boolean equals(Object other) {
			if (other instanceof Entry) {
				Entry o = (Entry) other;
				return o._id == this._id;
			}
			return false;
		}
	}

}
