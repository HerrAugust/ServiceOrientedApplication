//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.29 at 11:32:12 AM CET 
//


package it.univaq.disim.sose.project.shop;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.univaq.disim.sose.project.shop package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.univaq.disim.sose.project.shop
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProductList }
     * 
     */
    public ProductList createProductList() {
        return new ProductList();
    }

    /**
     * Create an instance of {@link Product }
     * 
     */
    public Product createProduct() {
        return new Product();
    }

    /**
     * Create an instance of {@link PurchaseType }
     * 
     */
    public PurchaseType createPurchaseType() {
        return new PurchaseType();
    }

    /**
     * Create an instance of {@link PurchaseResponseType }
     * 
     */
    public PurchaseResponseType createPurchaseResponseType() {
        return new PurchaseResponseType();
    }

}
