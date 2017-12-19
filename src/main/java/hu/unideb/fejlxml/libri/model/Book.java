package hu.unideb.fejlxml.libri.model;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlValue;

@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(
        namespace = "http://www.inf.unideb.hu/Libri",
        propOrder = {
            "title",
            "subTitle",
            "productCode",
            "isbn13",
            "authors",
            "price",
            "loyaltyPoints",
            "publisher",
            "date",
            "pages",
            "language",
            "cover",
            "transport",
            "exceptedDate",
            "rating",
            "description",
            "deliverypoints",
            "bestsellers",
            "publisher_news",
            "author_books"
        }
    )
public class Book {

    @XmlAttribute(required = true)
    private String uri;
    @XmlAttribute
    private Boolean antique_partner;
    @XmlAttribute
    private Boolean ebook;

    @XmlElementWrapper
    @XmlElement(name = "author", required = true)
    private ArrayList<String> authors;

    @XmlElement(required = true)
    private String title;
    
    @XmlElement
    private String subTitle;
    
    @XmlElement
    private String cover;
    
    @XmlElement
    private String productCode;
    
    @XmlElement
    private Transport transport;
    
    @XmlElement
    private Date exceptedDate;

    @XmlElement(required = true)
    private String publisher;

    @XmlElement(name = "published_date", required = false)
    private Integer date;

    @XmlElement(required = false)
    private String description;

    @XmlElement(required = false)
    @XmlSchemaType(name = "nonNegativeInteger")
    private Integer pages;

    @XmlElement(required = false)
    private String language;
    
    @XmlElement(required = true)
    private String isbn13;

    @XmlElement(required = false)
    private Price price;
    
    @XmlElement(required = true)
    private Rating rating;
    
    @XmlElement(required = false)
    private Integer loyaltyPoints;
    
    @XmlElementWrapper
    @XmlElement(name = "deliverypoint")
    private ArrayList<DeliveryPoint> deliverypoints = new ArrayList<DeliveryPoint>();
    
    @XmlElementWrapper
    @XmlElement(name = "bestseller")
    private ArrayList<SmallBook> bestsellers = new ArrayList<SmallBook>();
    
    @XmlElementWrapper
    @XmlElement(name = "publisher_new")
    private ArrayList<SmallBook> publisher_news = new ArrayList<SmallBook>();
    
    @XmlElementWrapper
    @XmlElement(name = "author_book")
    private ArrayList<SmallBook> author_books = new ArrayList<SmallBook>();

    public Book() {
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Date getExceptedDate() {
        return exceptedDate;
    }

    public void setExceptedDate(Date exceptedDate) {
        this.exceptedDate = exceptedDate;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
    
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public ArrayList<DeliveryPoint> getDeliverypoints() {
        return deliverypoints;
    }

    public void setDeliverypoints(ArrayList<DeliveryPoint> deliverypoints) {
        this.deliverypoints = deliverypoints;
    }

    public Boolean getAntiquePartner() {
        return antique_partner;
    }

    public void setAntiquePartner(Boolean antique_partner) {
        this.antique_partner = antique_partner;
    }

    public Boolean getAntique_partner() {
        return antique_partner;
    }

    public void setAntique_partner(Boolean antique_partner) {
        this.antique_partner = antique_partner;
    }

    public ArrayList<SmallBook> getBestsellers() {
        return bestsellers;
    }

    public void setBestsellers(ArrayList<SmallBook> bestsellers) {
        this.bestsellers = bestsellers;
    }

    public ArrayList<SmallBook> getPublisher_news() {
        return publisher_news;
    }

    public void setPublisher_news(ArrayList<SmallBook> publisher_news) {
        this.publisher_news = publisher_news;
    }

    public ArrayList<SmallBook> getAuthor_books() {
        return author_books;
    }

    public void setAuthor_books(ArrayList<SmallBook> author_books) {
        this.author_books = author_books;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Boolean getEbook() {
        return ebook;
    }

    public void setEbook(Boolean ebook) {
        this.ebook = ebook;
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
