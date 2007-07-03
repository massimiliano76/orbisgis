package org.gdms.geotoolsAdapter;

import java.util.Iterator;

import org.gdms.data.DataSource;
import org.gdms.driver.DriverException;

public class IteratorAdapter implements Iterator {

	private DataSource ds;

	private int index;

	public IteratorAdapter(DataSource ds) {
		index = 0;
		this.ds = ds;
	}

	public boolean hasNext() {
		try {
			return index < ds.getRowCount();
		} catch (DriverException e) {
			throw new RuntimeException(e);
		}
	}

	public Object next() {
		index++;
		return new FeatureAdapter(ds, index - 1);
	}

	public void remove() {
		throw new Error();
	}

}
