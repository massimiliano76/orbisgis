/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able
 * to manipulate and create vector and raster spatial information. OrbisGIS
 * is distributed under GPL 3 license. It is produced  by the geo-informatic team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OrbisGIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
package org.orbisgis.errorManager;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class DefaultErrorManager implements ErrorManager {

	private static Logger logger = Logger.getLogger(DefaultErrorManager.class);

	private ArrayList<ErrorListener> listeners = new ArrayList<ErrorListener>();

	public void error(String userMsg, Throwable exception) {
		try {
			logger.error(userMsg, exception);
			String userMessage = getUserMessage(userMsg, exception);
			for (ErrorListener listener : listeners) {
				listener.error(userMessage, exception);
			}
		} catch (Throwable t) {
			logger.error("Error while managing exception", t);
		}
	}

	private static String getUserMessage(String userMsg, Throwable exception) {
		String ret = userMsg;
		if (exception != null) {
			ret = ret + ": " + exception.getMessage();
			while (exception.getCause() != null) {
				exception = exception.getCause();
				ret = ret + ":\n" + exception.getMessage();
			}
		}

		return ret;
	}

	public void warning(String userMsg, Throwable exception) {
		try {
			logger.warn("warning: " + userMsg, exception);
			String userMessage = getUserMessage(userMsg, exception);
			for (ErrorListener listener : listeners) {
				listener.warning(userMessage, exception);
			}
		} catch (Throwable t) {
			logger.error("Error while managing exception", t);
		}
	}

	public void error(String userMsg) {
		error(userMsg, null);
	}

	public void addErrorListener(ErrorListener listener) {
		listeners.add(listener);
	}

	public void removeErrorListener(ErrorListener listener) {
		listeners.add(listener);
	}

	public void warning(String userMsg) {
		warning(userMsg, null);
	}

}
