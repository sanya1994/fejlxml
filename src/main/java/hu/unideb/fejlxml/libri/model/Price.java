package hu.unideb.fejlxml.libri.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(namespace = "http://www.inf.unideb.hu/Libri")
public class Price {

    @XmlElement
    private BigDecimal original_price;

    @XmlElement
    private BigDecimal actual_price;
    
    @XmlElement
    private Discount discount;

    public Price() {
    }

    public BigDecimal getOriginalPrice() {
        return original_price;
    }

    public void setOriginalPrice(BigDecimal original_price) {
        this.original_price = original_price;
        if(this.actual_price==null){
            this.actual_price = original_price;
        }
    }

    public BigDecimal getActualPrice() {
        return actual_price;
    }

    public void setActualPrice(BigDecimal actual_price) {
        this.actual_price = actual_price;
        if(this.original_price==null){
            this.original_price = actual_price;
        }
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }    

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    
}
