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
package org.orbisgis.legend.thematic;

import org.junit.Test;
import org.orbisgis.coremap.renderer.se.AreaSymbolizer;
import org.orbisgis.coremap.renderer.se.Style;
import org.orbisgis.coremap.renderer.se.stroke.PenStroke;
import org.orbisgis.legend.AnalyzerTest;
import org.orbisgis.legend.analyzer.symbolizers.AreaSymbolizerAnalyzer;
import org.orbisgis.legend.thematic.recode.RecodedArea;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author alexis
 */
public class RecodedAreaTest extends AnalyzerTest {

    @Test
    public void testDefaultConstructor() {
        RecodedArea ar = new RecodedArea();
        assertTrue(true);
    }

    @Test
    public void testInstanciation() throws Exception{
        AreaSymbolizer as = getAreaSymbolizer();
        AreaSymbolizerAnalyzer asa = new AreaSymbolizerAnalyzer(as);
        RecodedArea ra = (RecodedArea) asa.getLegend();
        assertTrue(true);
    }

    @Test
    public void testGetParams() throws Exception {
        RecodedArea ra = getRecodedArea();
        assertTrue(ra.size() == 5);
        assertNull(ra.get("potato"));
        AreaParameters ap = new AreaParameters(new Color(34,51,68), 1.0, 1.0, "", new Color(34,51,68), 0.9);
        AreaParameters ret = ra.get("1");
        assertTrue(ret.equals(ap));
    }

    @Test
    public void testGetParamFallbackVal() throws Exception {
        RecodedArea ra = getRecodedArea();
        AreaParameters ap = new AreaParameters(new Color(88,174, 35), 1.0, 0.4, "", new Color(88,172, 35), 0.9);
        AreaParameters ret = ra.get("9999");
        assertTrue(ret.equals(ap));
        ap = new AreaParameters(new Color(170,23,180), 1.0, 0.4, "", new Color(51,85,102), .5);
        ret = ra.get("25");
        assertTrue(ret.equals(ap));
    }

    @Test
    public void testGetFallback() throws Exception {
        RecodedArea ra = getRecodedArea();
        AreaParameters fb = ra.getFallbackParameters();
        AreaParameters t = new AreaParameters(new Color(51, 85, 103),1.0,0.4,"",new Color(51,85,102),.9);
        assertTrue(t.equals(fb));
    }

    @Test
    public void testPutNonExistingKey() throws Exception {
        RecodedArea ra = getRecodedArea();
        assertTrue(ra.size()==5);
        AreaParameters ap = new AreaParameters(new Color(88,74, 235), 1.0, 0.4, "2", new Color(0xEC,0x44, 5), 0.9);
        String newKey = "I am new !";
        AreaParameters ret = ra.put(newKey, ap);
        assertNull(ret);
        assertTrue(ra.get(newKey).equals(new AreaParameters(new Color(88,74, 235), 1.0, 0.4, "2", new Color(0xEC,0x44, 5), 0.9)));
        assertTrue(ra.size() == 6);
    }

    @Test
    public void testPutExistingKey() throws Exception {
        RecodedArea ra = getRecodedArea();
        AreaParameters expected = new AreaParameters(new Color(88,174, 35), 1.0, 0.4, "", new Color(88,172, 35), 0.9);
        AreaParameters ap = new AreaParameters(new Color(88,74, 235), 1.0, 0.4, "2", new Color(0xEC,0x44, 5), 0.9);
        AreaParameters ret = ra.put("9999",ap);
        assertTrue(ret.equals(expected));
        assertTrue(ra.get("9999").equals(new AreaParameters(new Color(88, 74, 235), 1.0, 0.4, "2", new Color(0xEC, 0x44, 5), 0.9)));
    }

    @Test
    public void testPutNull() throws Exception{
        RecodedArea ra = getRecodedArea();
        try{
            ra.put(null, new AreaParameters());
            fail();
        } catch (NullPointerException npe){
        }
        try{
            ra.put("yo",null);
            fail();
        } catch (NullPointerException npe){
        }
        assertTrue(true);
    }

    @Test
    public void testRemove() throws Exception{
        RecodedArea ra = getRecodedArea();
        assertTrue(ra.size() == 5);
        AreaParameters expected = new AreaParameters(new Color(34,51,68), 1.0, 1.0, "", new Color(34,51,68), 0.9);
        AreaParameters ap = ra.remove("1");
        assertTrue(ap.equals(expected));
        assertTrue(ra.size()==4);
    }

    @Test
    public void testRemoveNull() throws Exception {
        RecodedArea ra = getRecodedArea();
        try{
            ra.remove(null);
            fail();
        } catch (NullPointerException npe){
        }
        assertTrue(true);
    }

    @Test
    public void testSetFallback() throws Exception {
        RecodedArea ra = getRecodedArea();
        AreaParameters ap = new AreaParameters();
        ra.setFallbackParameters(ap);
        assertTrue(ra.getFallbackParameters().equals(new AreaParameters()));
    }

    @Test
    public void testRemoveNotExisting() throws Exception {
        RecodedArea ra = getRecodedArea();
        assertNull(ra.remove("I do not exist :-("));
    }

    @Test
    public void testNullStroke() throws Exception {
        RecodedArea ra = getNullStrokeRecodedArea();
        assertFalse(ra.isStrokeEnabled());
    }

    @Test
    public void testSetEnabled() throws Exception {
        AreaSymbolizer as = getAreaSymbolizer();
        as.setStroke(null);
        RecodedArea ra = new RecodedArea(as);
        ra.setStrokeEnabled(true);
        assertNotNull(as.getStroke());
        assertTrue(ra.isStrokeEnabled());
        assertTrue(ra.getFallbackParameters().getLineColor().equals(Color.BLACK));
        assertTrue(ra.getFallbackParameters().getLineWidth().equals(PenStroke.DEFAULT_WIDTH));
        assertTrue(ra.getFallbackParameters().getLineDash().isEmpty());
        assertTrue(ra.getFallbackParameters().getLineOpacity().equals(1.0));
        ra.setStrokeEnabled(false);
        assertFalse(ra.isStrokeEnabled());
        assertTrue(ra.getFallbackParameters().getLineColor().equals(Color.WHITE));
        assertTrue(ra.getFallbackParameters().getLineWidth().equals(0.0));
        assertTrue(ra.getFallbackParameters().getLineDash().isEmpty());
        assertTrue(ra.getFallbackParameters().getLineOpacity().equals(0.0));
    }

    @Test
    public void testGetPutRemoveWithNullStroke() throws Exception {
        RecodedArea ra = getNullStrokeRecodedArea();
        assertTrue(ra.size() == 5);
        assertNotNull(ra.get("1"));
        assertNotNull(ra.remove("1"));
        assertNull(ra.put("1", new AreaParameters(Color.BLUE, .75, 2.0, "2", Color.YELLOW, .25)));
        AreaParameters ap = ra.get("1");
        assertTrue(ap.getLineColor().equals(Color.WHITE));
        assertTrue(ap.getLineWidth().equals(0.0));
        assertTrue(ap.getLineOpacity().equals(0.0));
        assertTrue(ap.getLineDash().isEmpty());
        assertNull(ra.getLineColor());
        assertNull(ra.getLineOpacity());
        assertNull(ra.getLineWidth());
        assertNull(ra.getLineDash());
    }

    private AreaSymbolizer getAreaSymbolizer() throws Exception{
        Style s = getStyle(AREA_RECODE);
        return (AreaSymbolizer) s.getRules().get(0).getCompositeSymbolizer().getChildren().get(0);
    }

    private RecodedArea getRecodedArea() throws Exception {
        return new RecodedArea(getAreaSymbolizer());
    }

    private RecodedArea getNullStrokeRecodedArea() throws Exception {
        AreaSymbolizer as = getAreaSymbolizer();
        as.setStroke(null);
        return new RecodedArea(as);
    }
}
