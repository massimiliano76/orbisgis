/**
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the 
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 * 
 * OrbisGIS is distributed under GPL 3 license.
 *
 * Copyright (C) 2007-2014 CNRS (IRSTV FR CNRS 2488)
 * Copyright (C) 2015-2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.coremap.layerModel.mapcatalog;

import org.orbisgis.corejdbc.DataManager;

import java.io.File;
import java.net.URL;

/**
 * Hold parameters that define a connexion to a remote catalog API.
 * @author Nicolas Fortin
 */
public class ConnectionProperties {
    /**
     * The connexion fails if it exceed this waiting time in milliseconds.
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    private int connectionTimeOut = DEFAULT_CONNECTION_TIMEOUT; // In milliseconds
    private File mapFolderCache;
    private URL apiUrl;
    private DataManager dataManager;

    /**
     * Constructor with mandatory parameters
     * @param catalogApiUrl Catalog API URL
     * @param dataManager Loaded map context will register data into this manager.
     * @param mapFolderCache Download map context are placed in this folder when saved.
     */
    public ConnectionProperties(URL catalogApiUrl, DataManager dataManager,File mapFolderCache) {
        this.apiUrl = catalogApiUrl;
        this.dataManager = dataManager;
        this.mapFolderCache = mapFolderCache;
    }

    /**
     * @return Download map context are placed in this folder when saved.
     */
    public File getMapFolderCache() {
        return mapFolderCache;
    }

    /**
     * @return Loaded map context will register data into this manager.
     */
    public DataManager getDataManager() {
        return dataManager;
    }

    /**
     * Get the value of catalogApiUrl
     *
     * @return the value of catalogApiUrl
     */
    public URL getApiUrl() {
        return apiUrl;
    }

    /**
     * Set the value of catalogApiUrl
     *
     * @param catalogApiUrl new value of catalogApiUrl
     */
    public void setApiUrl(URL catalogApiUrl) {
        this.apiUrl = catalogApiUrl;
    }

    /**
     * Get the value of connectionTimeOut
     *
     * @return the value of connectionTimeOut in milliseconds
     */
    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    /**
     * Set the value of connectionTimeOut
     *
     * @param connectionTimeOut new value of connectionTimeOut in milliseconds
     */
    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

}
