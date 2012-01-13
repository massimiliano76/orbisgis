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
package org.gdms.data.edition;

import org.junit.Test;
import java.io.File;
import org.gdms.TestBase;
import org.gdms.data.DataSource;
import org.gdms.data.DataSourceFactory;
import org.gdms.data.DigestUtilities;
import org.gdms.data.file.FileSourceCreation;
import org.gdms.data.values.Value;

import static org.junit.Assert.*;

/**
 * DOCUMENT ME!
 *
 * @author Fernando Gonzalez Cortes
 */
public class UndoRedoTests extends TestBase {

        @Test
        public void testUndoRedoMetadata() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "alltypes.shp"), null));
                DataSource d = dsf.getDataSource("big",
                        DataSourceFactory.EDITABLE);

                d.open();
                Value[][] content = super.getDataSourceContents(d);
                d.removeField(2);
                d.undo();
                assertTrue(equals(content, super.getDataSourceContents(d)));
                d.commit();
                d.close();
                d.open();
                assertTrue(equals(content, super.getDataSourceContents(d)));
                d.close();
        }

        @Test
        public void testAlphanumericModifyUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big",
                        DataSourceFactory.EDITABLE);

                d.open();
                Value v2 = d.getFieldValue(1, 0);
                Value v1 = d.getFieldValue(0, 0);
                d.setFieldValue(0, 0, v2);
                for (int i = 0; i < 100; i++) {
                        d.undo();
                        assertTrue(equals(d.getFieldValue(0, 0), v1));
                        d.redo();
                        assertTrue(equals(d.getFieldValue(0, 0), v2));
                }
                d.undo();
                d.commit();
                d.close();
        }

        @Test
        public void testAlphanumericDeleteUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big",
                        DataSourceFactory.EDITABLE);

                d.open();
                Value v1 = d.getFieldValue(1, 0);
                Value v2 = d.getFieldValue(2, 0);
                d.deleteRow(1);
                for (int i = 0; i < 100; i++) {
                        d.undo();
                        assertTrue(equals(d.getFieldValue(1, 0), v1));
                        d.redo();
                        assertTrue(equals(d.getFieldValue(1, 0), v2));
                }
                d.undo();
                d.commit();
                d.close();
        }

        @Test
        public void testAlphanumericInsertUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big",
                        DataSourceFactory.EDITABLE);

                d.open();
                Value v1 = d.getFieldValue(1, 0);
                d.insertEmptyRowAt(1);
                for (int i = 0; i < 100; i++) {
                        d.undo();
                        assertTrue(equals(d.getFieldValue(1, 0), v1));
                        d.redo();
                        assertTrue(d.getFieldValue(1, 0).isNull());
                }
                d.undo();
                d.commit();
                d.close();
        }

        private void testSpatialModifyUndoRedo(DataSource d)
                throws Exception {
                Value v2 = d.getFieldValue(1, 0);
                Value v1 = d.getFieldValue(0, 0);
                d.setFieldValue(0, 0, v2);
                for (int i = 0; i < 100; i++) {
                        d.undo();
                        assertTrue(equals(d.getFieldValue(0, 0), v1));
                        d.redo();
                        assertTrue(equals(d.getFieldValue(0, 0), v2));
                }
        }

        @Test
        public void testSpatialModifyUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big", DataSourceFactory.EDITABLE);

                d.open();
                testSpatialModifyUndoRedo(d);
                d.undo();
                d.commit();
                d.close();
        }

        private void testSpatialDeleteUndoRedo(DataSource d)
                throws Exception {
                long rc = d.getRowCount();
                Value v1 = d.getFieldValue(1, 0);
                Value v2 = d.getFieldValue(2, 0);
                d.deleteRow(1);
                for (int i = 0; i < 100; i++) {
                        d.undo();
                        assertTrue(equals(d.getFieldValue(1, 0), v1));
                        d.redo();
                        assertTrue(equals(d.getFieldValue(1, 0), v2));
                        assertEquals(rc - 1, d.getRowCount());
                }
        }

        @Test
        public void testSpatialDeleteUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big", DataSourceFactory.EDITABLE);

                d.open();
                testSpatialDeleteUndoRedo(d);
                d.undo();
                d.commit();
                d.close();
        }

        private void testSpatialInsertUndoRedo(DataSource d)
                throws Exception {
                long rc = d.getRowCount();
                d.insertEmptyRow();
                for (int i = 0; i < 100; i++) {
                        d.undo();
                        assertEquals(rc, d.getRowCount());
                        d.redo();
                        assertEquals(rc, d.getRowCount() - 1);
                }
        }

        @Test
        public void testSpatialInsertUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big", DataSourceFactory.EDITABLE);

                d.open();
                testSpatialInsertUndoRedo(d);
                d.undo();
                d.commit();
                d.close();
        }

        public void testAlphanumericEditionUndoRedo(DataSource d) throws Exception {
                byte[] snapshot1 = DigestUtilities.getDigest(d);
                d.setFieldValue(0, 0, d.getFieldValue(1, 0));
                byte[] snapshot2 = DigestUtilities.getDigest(d);
                d.setFieldValue(0, 0, d.getFieldValue(2, 0));
                byte[] snapshot3 = DigestUtilities.getDigest(d);
                d.deleteRow(0);
                byte[] snapshot4 = DigestUtilities.getDigest(d);
                d.setFieldValue(0, 1, d.getFieldValue(1, 1));
                byte[] snapshot5 = DigestUtilities.getDigest(d);
                d.insertEmptyRowAt(0);
                byte[] snapshot6 = DigestUtilities.getDigest(d);
                d.setFieldName(1, "newName");
                byte[] snapshot7 = DigestUtilities.getDigest(d);
                d.removeField(1);
                d.undo();
                assertTrue(DigestUtilities.equals(snapshot7, DigestUtilities.getDigest(d)));
                d.undo();
                assertTrue(DigestUtilities.equals(snapshot6, DigestUtilities.getDigest(d)));
                d.undo();
                assertTrue(DigestUtilities.equals(snapshot5, DigestUtilities.getDigest(d)));
                d.redo();
                assertTrue(DigestUtilities.equals(snapshot6, DigestUtilities.getDigest(d)));
                d.undo();
                d.undo();
                assertTrue(DigestUtilities.equals(snapshot4, DigestUtilities.getDigest(d)));
                d.undo();
                assertTrue(DigestUtilities.equals(snapshot3, DigestUtilities.getDigest(d)));
                d.undo();
                assertTrue(DigestUtilities.equals(snapshot2, DigestUtilities.getDigest(d)));
                d.redo();
                assertTrue(DigestUtilities.equals(snapshot3, DigestUtilities.getDigest(d)));
                d.undo();
                d.undo();
                assertTrue(DigestUtilities.equals(snapshot1, DigestUtilities.getDigest(d)));
        }

        @Test
        public void testAlphanumericEditionUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big",
                        DataSourceFactory.EDITABLE);

                d.open();
                testAlphanumericEditionUndoRedo(d);
                d.commit();
                d.close();
        }

        @Test
        public void testSpatialEditionUndoRedo() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big",
                        DataSourceFactory.EDITABLE);

                d.open();
                testAlphanumericEditionUndoRedo(d);
                d.commit();
                d.close();
        }

        @Test
        public void testAddTwoRowsAndUndoBoth() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big", DataSourceFactory.EDITABLE);

                d.open();
                Value[] row = d.getRow(0);
                long rc = d.getRowCount();
                d.insertFilledRow(row);
                d.insertFilledRow(row);
                d.undo();
                d.undo();
                assertEquals(d.getRowCount(), rc);
                d.close();
        }

        @Test
        public void testInsertModify() throws Exception {
                dsf.getSourceManager().remove("big");
                dsf.getSourceManager().register(
                        "big",
                        new FileSourceCreation(new File(TestBase.internalData
                        + "hedgerow.shp"), null));
                DataSource d = dsf.getDataSource("big", DataSourceFactory.EDITABLE);

                d.open();
                int ri = (int) d.getRowCount();
                d.insertEmptyRow();
                Value v1 = d.getFieldValue(0, 0);
                Value v2 = d.getFieldValue(0, 1);
                d.setFieldValue(ri, 0, v1);
                d.setFieldValue(ri, 1, v2);
                d.undo();
                d.undo();
                d.undo();
                d.redo();
                d.redo();
                d.redo();
                assertTrue(equals(d.getFieldValue(ri, 0), v1));
                assertTrue(equals(d.getFieldValue(ri, 1), v2));
        }
}