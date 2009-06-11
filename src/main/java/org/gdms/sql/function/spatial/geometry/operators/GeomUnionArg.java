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
package org.gdms.sql.function.spatial.geometry.operators;

import java.util.ArrayList;

import org.gdms.data.values.Value;
import org.gdms.data.values.ValueFactory;
import org.gdms.sql.function.Argument;
import org.gdms.sql.function.Arguments;
import org.gdms.sql.function.FunctionException;
import org.gdms.sql.function.spatial.geometry.AbstractSpatialFunction;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.operation.union.UnaryUnionOp;

public class GeomUnionArg extends AbstractSpatialFunction {

	public Value evaluate(Value[] args) throws FunctionException {
		if (!args[0].isNull() && !args[1].isNull()) {
			ArrayList<Geometry> toUnite = new ArrayList<Geometry>();
			addGeometry(toUnite, args[0].getAsGeometry());
			addGeometry(toUnite, args[1].getAsGeometry());
			return ValueFactory.createValue(UnaryUnionOp.union(toUnite));
		}

		return ValueFactory.createNullValue();
	}

	private void addGeometry(ArrayList<Geometry> toUnite, Geometry geom) {
		if (geom.getGeometryType().equals("GeometryCollection")) {
			for (int i = 0; i < geom.getNumGeometries(); i++) {
				addGeometry(toUnite, geom.getGeometryN(i));
			}
		} else {
			toUnite.add(geom);
		}
	}

	public Value getAggregateResult() {
		return null;
	}

	public String getName() {
		return "GeomUnionArg";
	}

	public Arguments[] getFunctionArguments() {
		return new Arguments[] { new Arguments(Argument.GEOMETRY,
				Argument.GEOMETRY) };
	}

	public boolean isAggregate() {
		return false;
	}

	public String getDescription() {
		return "Compute the union of the function geometry parameters";
	}

	public String getSqlOrder() {
		return "select GeomUnion(the_geom1, the_geom2) from myTable;";
	}

	@Override
	public boolean isDesaggregate() {
		// TODO Auto-generated method stub
		return false;
	}
}