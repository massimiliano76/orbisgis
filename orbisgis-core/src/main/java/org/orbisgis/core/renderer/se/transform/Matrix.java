/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.orbisgis.core.renderer.se.transform;

import java.awt.geom.AffineTransform;
import java.util.HashSet;
import javax.xml.bind.JAXBElement;
import net.opengis.se._2_0.core.MatrixType;
import net.opengis.se._2_0.core.ObjectFactory;
import org.gdms.data.DataSource;
import org.orbisgis.core.map.MapTransform;
import org.orbisgis.core.renderer.se.SeExceptions.InvalidStyle;
import org.orbisgis.core.renderer.se.common.Uom;
import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.parameter.SeParameterFactory;
import org.orbisgis.core.renderer.se.parameter.UsedAnalysis;
import org.orbisgis.core.renderer.se.parameter.real.RealLiteral;
import org.orbisgis.core.renderer.se.parameter.real.RealParameter;
import org.orbisgis.core.renderer.se.parameter.real.RealParameterContext;

/**
 * Affine Transformation based on RealParameters
 * Warning: conversion to pixel unit will give strange behavior !
 * <p> The matrix as the following form :</p>
 * <p>{@code [A C E]}<br/>
 * {@code|B D F|}<br/>
 * {@code[0 0 1]}<br/></p>
 * <p>Note that the matrix is filled with {@code RealParameter} instances, not 
 * with {@code double} values.
 * 
 * @author maxence, alexis
 */
public final class Matrix implements Transformation {

        private static final double DEF_A = 1.0;
        private static final double DEF_B = 0.0;
        private static final double DEF_C = 0.0;
        private static final double DEF_D = 1.0;
        private static final double DEF_E = 0.0;
        private static final double DEF_F = 0.0;
        private RealParameter a;
        private RealParameter b;
        private RealParameter c;
        private RealParameter d;
        private RealParameter e;
        private RealParameter f;

        /**
         * Create an identity matrix
         *
         */
        public Matrix() {
                setA(new RealLiteral(DEF_A));
                setB(new RealLiteral(DEF_B));
                setC(new RealLiteral(DEF_C));
                setD(new RealLiteral(DEF_D));
                setE(new RealLiteral(DEF_E));
                setF(new RealLiteral(DEF_F));
        }

        /**
         * Create a new <code>Matrix</code> from <code>double</code> values.
         * @param a
         * @param b
         * @param c
         * @param d
         * @param e
         * @param f 
         */
        public Matrix(double a, double b, double c, double d, double e, double f) {
                setA(new RealLiteral(a));
                setB(new RealLiteral(b));
                setC(new RealLiteral(c));
                setD(new RealLiteral(d));
                setE(new RealLiteral(e));
                setF(new RealLiteral(f));
        }

        /**
         * Create a new <code>Matrix</code> from <code>RealParameter</code> instances. 
         * <code>null</code> values will be transformed to <code>new RealLiteral(0.0)</code>
         * @param a
         * @param b
         * @param c
         * @param d
         * @param e
         * @param f
         */
        public Matrix(RealParameter a, RealParameter b, RealParameter c,
                RealParameter d, RealParameter e, RealParameter f) {
                this();
                if (a != null) {
                        setA(a);
                }
                if (b != null) {
                        setB(b);
                }
                if (c != null) {
                        setC(c);
                }
                if (d != null) {
                        setD(d);
                }
                if (e != null) {
                        setE(e);
                }
                if (f != null) {
                        setF(f);
                }
        }

        /**
         * Creates a hard copy of <code>m</code>
         * @param m
         * @throws org.orbisgis.core.renderer.se.SeExceptions.InvalidStyle 
         */
        Matrix(MatrixType m) throws InvalidStyle {
                this();
                if (m.getA() != null) {
                        this.setA(SeParameterFactory.createRealParameter(m.getA()));
                }
                if (m.getB() != null) {
                        this.setB(SeParameterFactory.createRealParameter(m.getB()));
                }
                if (m.getC() != null) {
                        this.setC(SeParameterFactory.createRealParameter(m.getC()));
                }
                if (m.getD() != null) {
                        this.setD(SeParameterFactory.createRealParameter(m.getD()));
                }
                if (m.getE() != null) {
                        this.setE(SeParameterFactory.createRealParameter(m.getE()));
                }
                if (m.getF() != null) {
                        this.setF(SeParameterFactory.createRealParameter(m.getF()));
                }
        }

        /**
         * Get the A parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @return 
         * A {@code RealParameter} that is placed in a 
         * {@link RealParameterContext#REAL_CONTEXT}
         */
        public RealParameter getA() {
                return a;
        }

        /**
         * Set the A parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @param a 
         * A {@code RealParameter} that is placed (by this method) in a 
         * {@link RealParameterContext#REAL_CONTEXT}. If null, the value of A is
         * set to 0.
         */
        public void setA(RealParameter a) {
                if (a == null) {
                        this.a = new RealLiteral(0.0);
                } else {
                        this.a = a;
                }
                this.a.setContext(RealParameterContext.REAL_CONTEXT);
        }

        /**
         * Get the B parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @return 
         * A {@code RealParameter} that is placed in a 
         * {@link RealParameterContext#REAL_CONTEXT}
         */
        public RealParameter getB() {
                return b;
        }

        /**
         * Set the B parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @param b 
         * A {@code RealParameter} that is placed (by this method) in a 
         * {@link RealParameterContext#REAL_CONTEXT}. If null, the value of B is
         * set to 0.
         */
        public void setB(RealParameter b) {
                if (b == null) {
                        this.b = new RealLiteral(0.0);
                } else {
                        this.b = b;
                }
                this.b.setContext(RealParameterContext.REAL_CONTEXT);
        }

        /**
         * Get the C parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @return 
         * A {@code RealParameter} that is placed in a 
         * {@link RealParameterContext#REAL_CONTEXT}
         */
        public RealParameter getC() {
                return c;
        }

        /**
         * Set the C parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @param c
         * A {@code RealParameter} that is placed (by this method) in a 
         * {@link RealParameterContext#REAL_CONTEXT}. If null, the value of C is
         * set to 0.
         */
        public void setC(RealParameter c) {
                if (c == null) {
                        this.c = new RealLiteral(0.0);
                } else {
                        this.c = c;
                }
                this.c.setContext(RealParameterContext.REAL_CONTEXT);
        }

        /**
         * Get the D parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @return 
         * A {@code RealParameter} that is placed in a 
         * {@link RealParameterContext#REAL_CONTEXT}
         */
        public RealParameter getD() {
                return d;
        }

        /**
         * Set the D parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @param d
         * A {@code RealParameter} that is placed (by this method) in a 
         * {@link RealParameterContext#REAL_CONTEXT}. If null, the value of D is
         * set to 0.
         */
        public void setD(RealParameter d) {
                if (d == null) {
                        this.d = new RealLiteral(0.0);
                } else {
                        this.d = d;
                }
                this.d.setContext(RealParameterContext.REAL_CONTEXT);
        }

        /**
         * Get the E parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @return 
         * A {@code RealParameter} that is placed in a 
         * {@link RealParameterContext#REAL_CONTEXT}
         */
        public RealParameter getE() {
                return e;
        }

        /**
         * Set the E parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @param e 
         * A {@code RealParameter} that is placed (by this method) in a 
         * {@link RealParameterContext#REAL_CONTEXT}. If null, the value of E is
         * set to 0.
         */
        public void setE(RealParameter e) {
                if (e == null) {
                        this.e = new RealLiteral(0.0);
                } else {
                        this.e = e;
                }
                this.e.setContext(RealParameterContext.REAL_CONTEXT);
        }

        /**
         * Get the F parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @return 
         * A {@code RealParameter} that is placed in a 
         * {@link RealParameterContext#REAL_CONTEXT}
         */
        public RealParameter getF() {
                return f;
        }

        /**
         * Set the F parameter of this {@code Matrix}, as defined in the 
         * description of the class.
         * @param f 
         * A {@code RealParameter} that is placed (by this method) in a 
         * {@link RealParameterContext#REAL_CONTEXT}. If null, the value of F is
         * set to 0.
         */
        public void setF(RealParameter f) {
                if (f == null) {
                        this.f = new RealLiteral(0.0);
                } else {
                        this.f = f;
                }
                this.f.setContext(RealParameterContext.REAL_CONTEXT);
        }

        @Override
        public HashSet<String> dependsOnFeature() {
            HashSet<String> hs = new HashSet<String>();
            hs.addAll(a.dependsOnFeature());
            hs.addAll(b.dependsOnFeature());
            hs.addAll(c.dependsOnFeature());
            hs.addAll(d.dependsOnFeature());
            hs.addAll(e.dependsOnFeature());
            hs.addAll(f.dependsOnFeature());
            return hs;
        }

        @Override
        public UsedAnalysis getUsedAnalysis() {
            UsedAnalysis result = new UsedAnalysis();
            result.include(a);
            result.include(b);
            result.include(c);
            result.include(d);
            result.include(e);
            result.include(f);
            return result;
        }

        @Override
        public AffineTransform getAffineTransform(DataSource sds, long fid, Uom uom,
            MapTransform mt, Double width, Double height) throws ParameterException {
                return new AffineTransform(
                        //Uom.toPixel(a.getValue(feat), uom, mt.getDpi(), mt.getScaleDenominator(), null),
                        a.getValue(sds, fid),
                        b.getValue(sds, fid),
                        c.getValue(sds, fid),
                        //Uom.toPixel(b.getValue(feat), uom, mt.getDpi(), mt.getScaleDenominator(), null),
                        //Uom.toPixel(c.getValue(feat), uom, mt.getDpi(), mt.getScaleDenominator(), null),
                        //Uom.toPixel(d.getValue(feat), uom, mt.getDpi(), mt.getScaleDenominator(), null),
                        d.getValue(sds, fid),
                        Uom.toPixel(e.getValue(sds, fid), uom, mt.getDpi(), mt.getScaleDenominator(), width),
                        Uom.toPixel(f.getValue(sds, fid), uom, mt.getDpi(), mt.getScaleDenominator(), height));
        }

        @Override
        public boolean allowedForGeometries() {
                return false;
        }

        /**
         * This method simplifiy the matrix.
         * Every matrix element which doesn't depends on a feature is converted to a single RealLiteral
         *
         * @throws ParameterException when something went wrong...
         */
        public void simplify() throws ParameterException {
                HashSet<String> sa = a.dependsOnFeature();
                HashSet<String> sb = b.dependsOnFeature();
                HashSet<String>sc = c.dependsOnFeature();
                HashSet<String> sd = d.dependsOnFeature();
                HashSet<String> se = e.dependsOnFeature();
                HashSet<String> sf = f.dependsOnFeature();

                if (sa != null && !sa.isEmpty()) {
                        setA(new RealLiteral(a.getValue(null, -1)));
                }
                if (sb != null && !sb.isEmpty()) {
                        setB(new RealLiteral(b.getValue(null, -1)));
                }
                if (sc != null && !sc.isEmpty()) {
                        setC(new RealLiteral(c.getValue(null, -1)));
                }
                if (sd != null && !sd.isEmpty()) {
                        setD(new RealLiteral(d.getValue(null, -1)));
                }
                if (se != null && !se.isEmpty()) {
                        setE(new RealLiteral(e.getValue(null, -1)));
                }
                if (sf != null && !sf.isEmpty()) {
                        setF(new RealLiteral(f.getValue(null, -1)));
                }
        }

        @Override
        public JAXBElement<?> getJAXBElement() {
                MatrixType m = this.getJAXBType();

                ObjectFactory of = new ObjectFactory();
                return of.createMatrix(m);
        }

        @Override
        public MatrixType getJAXBType() {
                MatrixType m = new MatrixType();
                m.setA(a.getJAXBParameterValueType());
                m.setB(b.getJAXBParameterValueType());
                m.setC(c.getJAXBParameterValueType());
                m.setD(d.getJAXBParameterValueType());
                m.setE(e.getJAXBParameterValueType());
                m.setF(f.getJAXBParameterValueType());

                return m;
        }

        @Override
        public String toString() {
                return "Matrix";
        }
}
