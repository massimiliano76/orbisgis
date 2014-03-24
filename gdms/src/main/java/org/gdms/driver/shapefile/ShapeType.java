/**
 * The GDMS library (Generic Datasource Management System)
 * is a middleware dedicated to the management of various kinds of
 * data-sources such as spatial vectorial data or alphanumeric. Based
 * on the JTS library and conform to the OGC simple feature access
 * specifications, it provides a complete and robust API to manipulate
 * in a SQL way remote DBMS (PostgreSQL, H2...) or flat files (.shp,
 * .csv...).
 *
 * Gdms is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2014 IRSTV FR CNRS 2488
 *
 * This file is part of Gdms.
 *
 * Gdms is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Gdms is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Gdms. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 *
 * or contact directly:
 * info@orbisgis.org
 */
/*
 *    GeoTools - OpenSource mapping toolkit
 *    http://geotools.org
 *    (C) 2002-2006, Geotools Project Managment Committee (PMC)
 *    (C) 2002, Centre for Computational Geography
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.gdms.driver.shapefile;

/** Not much but a type safe enumeration of file types as ints and names. The
 * descriptions can easily be tied to a ResourceBundle if someone wants to do that.
 * @author Ian Schneider
 * @source $URL: http://svn.geotools.org/geotools/tags/2.3.1/plugin/shapefile/src/org/geotools/data/shapefile/shp/ShapeType.java $
 */
public final class ShapeType {
  
  /** Represents a Null shape (id = 0). */  
  public static final ShapeType NULL  = new ShapeType(0,"Null");
  /** Represents a Point shape (id = 1). */  
  public static final ShapeType POINT = new ShapeType(1,"Point");
  /** Represents a PointZ shape (id = 11). */  
  public static final ShapeType POINTZ = new ShapeType(11,"PointZ");
  /** Represents a PointM shape (id = 21). */  
  public static final ShapeType POINTM = new ShapeType(21,"PointM");
  /** Represents an Arc shape (id = 3). */  
  public static final ShapeType ARC   = new ShapeType(3,"Arc");
  /** Represents an ArcZ shape (id = 13). */  
  public static final ShapeType ARCZ   = new ShapeType(13,"ArcZ");
  /** Represents an ArcM shape (id = 23). */  
  public static final ShapeType ARCM   = new ShapeType(23,"ArcM");
  /** Represents a Polygon shape (id = 5). */  
  public static final ShapeType POLYGON = new ShapeType(5,"Polygon");
  /** Represents a PolygonZ shape (id = 15). */  
  public static final ShapeType POLYGONZ = new ShapeType(15,"PolygonZ");
  /** Represents a PolygonM shape (id = 25). */  
  public static final ShapeType POLYGONM = new ShapeType(25,"PolygonM");
  /** Represents a MultiPoint shape (id = 8). */  
  public static final ShapeType MULTIPOINT = new ShapeType(8,"MultiPoint");
  /** Represents a MultiPointZ shape (id = 18). */  
  public static final ShapeType MULTIPOINTZ = new ShapeType(18,"MultiPointZ");
  /** Represents a MultiPointZ shape (id = 28). */  
  public static final ShapeType MULTIPOINTM = new ShapeType(28,"MultiPointM");
  
  /** Represents an Undefined shape (id = -1). */  
  public static final ShapeType UNDEFINED = new ShapeType(-1,"Undefined");
  
  /** The integer id of this ShapeType. */  
  public final int id;
  /** The human-readable name for this ShapeType.<br>
   * Could easily use ResourceBundle for internationialization.
   */  
  public final String name;
  
  /** Creates a new instance of ShapeType. Hidden on purpose.
   * @param id The id.
   * @param name The name.
   */
  protected ShapeType(int id,String name) {
    this.id = id;
    this.name = name;
  }
  
  /** Get the name of this ShapeType.
   * @return The name.
   */  
        @Override
  public String toString() { return name; }
  
  /** Is this a multipoint shape? Hint- all shapes are multipoint except NULL,
   * UNDEFINED, and the POINTs.
   * @return true if multipoint, false otherwise.
   */  
  public boolean isMultiPoint() {
    boolean mp = true;
    if (this == UNDEFINED) { mp = false; }
    else if (this == NULL) { mp = false; }
    else if (this == POINT) { mp = false; }
    return mp;
  }
  
  public boolean isPointType() {
    return id % 10 == 1; 
  }
  
  public boolean isLineType() {
    return id % 10 == 3; 
  }
  
  public boolean isPolygonType() {
    return id % 10 == 5; 
  }
  
  public boolean isMultiPointType() {
    return id % 10 == 8; 
  }
  
  /** Determine the ShapeType for the id.
   * @param id The id to search for.
   * @return The ShapeType for the id.
   */  
  public static ShapeType forID(int id) {
    ShapeType t;
    switch (id) {
      case 0:
        t = NULL;
        break;
      case 1:
        t = POINT;
        break;
      case 11:
        t = POINTZ;
        break;
      case 21:
        t = POINTM;
        break;
      case 3:
        t = ARC;
        break;
      case 13:
        t = ARCZ;
        break;
      case 23:
        t = ARCM;
        break;
      case 5:
        t = POLYGON;
        break;
      case 15:
        t = POLYGONZ;
        break;
      case 25:
        t = POLYGONM;
        break;
      case 8:
        t = MULTIPOINT;
        break;
      case 18:
        t = MULTIPOINTZ;
        break;
      case 28:
        t = MULTIPOINTM;
        break;
      default:
        t = UNDEFINED;
        break;
    }
    return t;
  }
  
  /** Each ShapeType corresponds to a handler. In the future this should probably go
   * else where to allow different handlers, or something...
   * @throws ShapefileException If the ShapeType is bogus.
   * @return The correct handler for this ShapeType. Returns a new one.
   */  
  public ShapeHandler getShapeHandler() throws ShapefileException {
    ShapeHandler handler;
    switch (id) {
      case 1: case 11: case 21:
        handler = new PointHandler(this);
        break;
      case 3: case 13: case 23:
        handler = new MultiLineHandler(this);
        break;
      case 5: case 15: case 25:
        handler = new PolygonHandler(this);
        break;
      case 8: case 18: case 28:
        handler = new MultiPointHandler(this);
        break;
      default:
        handler = null;
    }
    return handler;
  }
  
}
