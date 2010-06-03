//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.02 at 07:13:07 PM CEST 
//


package org.orbisgis.core.renderer.persistance.gml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * A container for an array of solids. The elements are always contained in the array property, referencing geometry elements or arrays of geometry elements is not supported.
 * 
 * <p>Java class for SolidArrayPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolidArrayPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.opengis.net/gml}gmlhack_Solid"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolidArrayPropertyType", propOrder = {
    "gmlhackSolid"
})
public class SolidArrayPropertyType {

    @XmlElementRef(name = "gmlhack_Solid", namespace = "http://www.opengis.net/gml", type = JAXBElement.class)
    protected List<JAXBElement<? extends AbstractSolidType>> gmlhackSolid;

    /**
     * Gets the value of the gmlhackSolid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gmlhackSolid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGmlhackSolid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link SolidType }{@code >}
     * {@link JAXBElement }{@code <}{@link AbstractSolidType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends AbstractSolidType>> getGmlhackSolid() {
        if (gmlhackSolid == null) {
            gmlhackSolid = new ArrayList<JAXBElement<? extends AbstractSolidType>>();
        }
        return this.gmlhackSolid;
    }

}