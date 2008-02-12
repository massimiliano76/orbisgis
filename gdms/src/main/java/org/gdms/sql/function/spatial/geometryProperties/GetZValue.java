/*
 * The GDMS library (Generic Datasources Management System)
 * is a middleware dedicated to the management of various kinds of
 * data-sources such as spatial vectorial data or alphanumeric. Based
 * on the JTS library and conform to the OGC simple feature access
 * specifications, it provides a complete and robust API to manipulate
 * in a SQL way remote DBMS (PostgreSQL, H2...) or flat files (.shp,
 * .csv...). GDMS is produced  by the geomatic team of the IRSTV
 * Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of GDMS.
 *
 * GDMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GDMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GDMS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *    <http://listes.cru.fr/sympa/info/orbisgis-developers/>
 *    <http://listes.cru.fr/sympa/info/orbisgis-users/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
package org.gdms.sql.function.spatial.geometryProperties;

import org.gdms.data.types.ConstraintNames;
import org.gdms.data.types.GeometryConstraint;
import org.gdms.data.types.Type;
import org.gdms.data.types.TypeFactory;
import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.gdms.sql.function.FunctionException;
import org.gdms.sql.strategies.IncompatibleTypesException;

import com.vividsolutions.jts.geom.Geometry;

public class GetZValue extends AbstractSpatialPropertyFunction {

	public Value evaluateResult(final Value[] args) throws FunctionException {
		if (args[0].isNull()) {
			return ValueFactory.createNullValue();
		} else {
			final Geometry geometry = args[0].getAsGeometry();
			final double z = geometry.getCoordinate().z;
			if (Double.isNaN(z)) {
				return ValueFactory.createNullValue();
			} else {
				return ValueFactory.createValue(z);
			}
		}
	}

	public String getName() {
		return "GetZ";
	}

	public Type getType(Type[] types) {
		return TypeFactory.createType(Type.DOUBLE);
	}

	@Override
	public void validateTypes(Type[] argumentsTypes)
			throws IncompatibleTypesException {
		super.validateTypes(argumentsTypes);
		GeometryConstraint constraint = (GeometryConstraint) argumentsTypes[0]
				.getConstraint(ConstraintNames.GEOMETRY);
		if ((constraint.getGeometryType() != GeometryConstraint.POINT_2D)
				|| (constraint.getGeometryType() != GeometryConstraint.POINT_3D)) {
			if (constraint.getGeometryType() != GeometryConstraint.MIXED) {
				throw new IncompatibleTypesException(getName()
						+ " only operates on points");
			}
		}
	}

	public boolean isAggregate() {
		return false;
	}

	public String getDescription() {
		return "Return the z value for a point geometry.";
	}

	public String getSqlOrder() {
		return "select GetZ(the_geom) from myTable;";
	}
}