package cz.upol.fapapp.cfa.misc;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

public class TwoDimArrayTest {

	public TwoDimArrayTest() {
	}

	/**************************************************************************/

	@Test
	public void testTwoDimArrayCreatedBySetters() {
		TwoDimArray<String> arr = createBySetters();
		System.out.println(arr);

		checkOutOfBounds(arr);
		checkValues(arr);
		checkIterator(arr);
	}


	@Test
	public void testTwoDimArrayCreatedByMap() {
		TwoDimArray<String> arr = createByMap();
		System.out.println(arr);

		checkOutOfBounds(arr);
		checkValues(arr);
		checkIterator(arr);
	}

	@Test
	public void testItsEquality() {
		TwoDimArray<String> arrs = createBySetters();
		TwoDimArray<String> arrm = createByMap();

		assertTrue(arrs.equals(arrm));
	}

	@Test
	public void testBiggerTwoDimArrayCreatedBySetters() {
		TwoDimArray<Integer> arr = createBiggerBySetters();
		
		System.out.println(arr);
		
		assertEquals(-1 + -1 * 10, arr.get(-1, -1).intValue());
	}

	/**************************************************************************/

	private void checkOutOfBounds(TwoDimArray<String> arr) {
		tryGetWithException(arr, -1, 0);
		tryGetWithException(arr, +2, 0);
		tryGetWithException(arr, 0, -1);
		tryGetWithException(arr, 0, +2);

		trySetWithException(arr, -1, 0, "Blah  #1");
		trySetWithException(arr, +2, 0, "Blah  #2");
		trySetWithException(arr, 0, -1, "Blah  #3");
		trySetWithException(arr, 0, +2, "Blah  #4");
	}

	private void checkValues(TwoDimArray<String> arr) {
		assertEquals("foo", arr.get(0, 0));
		assertEquals("bar", arr.get(0, 1));
		assertEquals("baz", arr.get(1, 0));
		assertEquals("aux", arr.get(1, 1));
	}

	private void checkIterator(TwoDimArray<String> arr) {
		Iterator<String> iter = arr.iterator();
		
		assertTrue(iter.hasNext());
		
		assertEquals("foo", iter.next());
		assertTrue(iter.hasNext());
		
		assertEquals("bar", iter.next());
		assertTrue(iter.hasNext());
		
		assertEquals("baz", iter.next());
		assertTrue(iter.hasNext());
		
		assertEquals("aux", iter.next());
		assertFalse(iter.hasNext());
		
		
	}
	
	/**************************************************************************/

	private TwoDimArray<String> createBySetters() {
		TwoDimArray<String> arr = new TwoDimArray<>(0, 2);

		arr.set(0, 0, "foo");
		arr.set(0, 1, "bar");
		arr.set(1, 0, "baz");
		arr.set(1, 1, "aux");

		return arr;
	}

	private TwoDimArray<String> createByMap() {
		Map<Integer, Map<Integer, String>> map = new HashMap<>();

		Map<Integer, String> row1 = new HashMap<>();
		row1.put(0, "foo");
		row1.put(1, "bar");

		Map<Integer, String> row2 = new HashMap<>();
		row2.put(0, "baz");
		row2.put(1, "aux");

		map.put(0, row1);
		map.put(1, row2);

		TwoDimArray<String> arr = new TwoDimArray<>(0, 2, map);
		return arr;
	}

	private TwoDimArray<Integer> createBiggerBySetters() {
		TwoDimArray<Integer> arr = new TwoDimArray<>(-1, 4);

		for (int i = -1; i < 4; i++) {
			for (int j = -1; j < 4; j++) {
				int value = i + (10 * j);
				arr.set(i, j, value);
			}
		}

		return arr;
	}

	/**************************************************************************/

	private void trySetWithException(TwoDimArray<String> arr, int i, int j, String value) {
		try {
			arr.set(i, j, value);
			fail("(" + i + "," + j + ")" + " is not valid adress, should not allow to set");
		} catch (IllegalArgumentException eIgnore) {
			// ok
		}
	}

	private void tryGetWithException(TwoDimArray<String> arr, int i, int j) {
		try {
			arr.get(i, j);
			fail("(" + i + "," + j + ")" + " is not valid adress, should not allow to get");
		} catch (IllegalArgumentException eIgnore) {
			// ok
		}
	}

}
