package hu.unideb.fejlxml.libri.model;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        namespace = "http://www.inf.unideb.hu/Libri",
        propOrder = {
            "title",
            "uri",
            "authors",
            "productCode",
            "price",
            "loyaltyPoints",
            "transport",
            "rating"
        }
)
public class SearchResultItem {

    @XmlAttribute(required = true)
    private String uri;
    
    @XmlAttribute
    private Boolean ebook;

    @XmlElementWrapper
    @XmlElement(name = "author", required = false)
    private ArrayList<String> authors;

    @XmlElement(required = true)
    private String title;

    @XmlElement(required = true)
    private String productCode;

    @XmlElement
    private Price price;

    @XmlElement
    private Integer loyaltyPoints;
    
    @XmlElement
    private Transport transport;
    
    @XmlElement(required = true)
    private Rating rating;

    public SearchResultItem() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Boolean getEbook() {
        return ebook;
    }

    public void setEbook(Boolean ebook) {
        this.ebook = ebook;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
