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
package org.orbisgis.legend.analyzer.symbolizers;

import static org.junit.Assert.*;
import org.junit.Test;
import org.orbisgis.coremap.renderer.se.AreaSymbolizer;
import org.orbisgis.coremap.renderer.se.Style;
import org.orbisgis.coremap.renderer.se.fill.GraphicFill;
import org.orbisgis.coremap.renderer.se.fill.SolidFill;
import org.orbisgis.coremap.renderer.se.graphic.MarkGraphic;
import org.orbisgis.coremap.renderer.se.graphic.PieChart;
import org.orbisgis.coremap.renderer.se.stroke.GraphicStroke;
import org.orbisgis.coremap.renderer.se.stroke.PenStroke;
import org.orbisgis.legend.AnalyzerTest;

/**
 * Tests made on the class SymbolizerTypeAnalyzer.
 * @author Alexis Guéganno
 */
public class SymbolizerTypeAnalyzerTest extends AnalyzerTest {

        @Test
        public void testValidStroke() throws Exception {
                Style s = getStyle(COLOR_CATEGORIZE);
                AreaSymbolizer as = (AreaSymbolizer) s.getRules().get(0).getCompositeSymbolizer()
                        .getSymbolizerList().get(0);
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                assertTrue(sta.validateStroke(as.getStroke()));
        }

        @Test
        public void testValidFill() throws Exception {
                Style s = getStyle(COLOR_CATEGORIZE);
                AreaSymbolizer as = (AreaSymbolizer) s.getRules().get(0).getCompositeSymbolizer()
                        .getSymbolizerList().get(0);
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                assertTrue(sta.validateFill(as.getFill()));
        }

        @Test
        public void testInvalidFill() throws Exception {
                Style s = getStyle(DENSITY_FILL);
                AreaSymbolizer as = (AreaSymbolizer) s.getRules().get(0).getCompositeSymbolizer()
                        .getSymbolizerList().get(0);
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                assertFalse(sta.validateFill(as.getFill()));
        }

        @Test
        public void testInvalidStroke() throws Exception {
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                assertFalse(sta.validateStroke(new GraphicStroke()));
        }

        @Test
        public void testInvalidStrokeBis() throws Exception {
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                PenStroke s = new PenStroke();
                s.setFill(new GraphicFill());
                assertFalse(sta.validateStroke(s));
        }

        @Test
        public void testValidGraphic() throws Exception {
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                MarkGraphic mg = new MarkGraphic();
                assertTrue(sta.validateGraphic(mg));
        }

        @Test
        public void testInvalidGraphic() throws Exception {
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                MarkGraphic mg = new MarkGraphic();
                mg.setFill(new GraphicFill());
                assertFalse(sta.validateGraphic(mg));
        }

        @Test
        public void testInvalidGraphic2() throws Exception {
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                MarkGraphic mg = new MarkGraphic();
                mg.setStroke(new GraphicStroke());
                assertFalse(sta.validateGraphic(mg));
        }

        @Test
        public void testInvalidGraphic3() throws Exception {
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                MarkGraphic mg = new MarkGraphic();
                PenStroke ps = new PenStroke();
                ps.setFill(new GraphicFill());
                mg.setStroke(ps);
                assertFalse(sta.validateGraphic(mg));
        }

        @Test
        public void testInvalidGraphic4() throws Exception {
                SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
                assertFalse(sta.validateGraphic(new PieChart()));
        }

        @Test
        public void testNullStrokeAndFill() throws Exception {
            SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
            assertTrue(sta.validateStrokeAndFill(null, null));
        }

        @Test
        public void testNullStrokeNotFill() throws Exception {
            SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
            assertTrue(sta.validateStrokeAndFill(null,new SolidFill()));
        }

        @Test
        public void testNullFillNotStroke() throws Exception {
            SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
            assertTrue(sta.validateStrokeAndFill(new PenStroke(),null));
        }

        @Test
        public void testNotNullFillAndStroke() throws Exception {
            SymbolizerTypeAnalyzer sta = new SymbolizerTypeAnalyzer();
            assertTrue(sta.validateStrokeAndFill(new PenStroke(),new SolidFill()));
        }
}
