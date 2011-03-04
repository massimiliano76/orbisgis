package org.orbisgis.core.renderer;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import java.awt.Shape;

public class AllowAllRenderContext implements RenderContext {

	@Override
	public boolean canDraw(Envelope env) {
		return true;
	}

    @Override
    public void addUsedArea(Envelope area) {
    }

	@Override
	public Geometry getValidGeometry(Geometry geometry, double distance) {
		return geometry;
	}
}
