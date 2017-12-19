/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.fejlxml.libri.parser;

import hu.unideb.fejlxml.libri.model.Discount;
import hu.unideb.fejlxml.libri.model.DiscountType;
import hu.unideb.fejlxml.libri.model.Price;
import hu.unideb.fejlxml.libri.model.SmallBook;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.jsoup.nodes.Element;

/**
 *
 * @author varadi
 */
public class SmallBookParser {
    public static SmallBook parser(Element e){
        SmallBook book = new SmallBook();
        try{
            book.setTitle(e.select("a.book-title").text().trim());
        } catch(Exception ex){
        }
        
        try{
            book.setProductCode(e.attr("data-id").trim());
        } catch(Exception ex){
        }
        try{
            book.setPublisher(e.attr("data-brand").trim());
        } catch(Exception ex){
        }
        try{
            book.setCategory(e.attr("data-category").trim());
        } catch(Exception ex){
        }
        try{
            book.setUri(e.select("a.book-title").attr("href").trim());
        } catch(Exception ex){
        }
        try{
            ArrayList<String> authors = new ArrayList<>();
            for(Element el : e.select(".base-info .author > .authors")){
                authors.add(el.text().trim());
            }
            book.setAuthors(authors);
        } catch(Exception ex){
        }
        Price price = new Price();
        try{
            price.setActualPrice(new BigDecimal(e.select("div.price > span.act-price").first().text().replaceAll("\\D+", "")));
        } catch(Exception ex){
        }
        try{
            price.setOriginalPrice(new BigDecimal(e.select("div.price > del").first().text().replaceAll("\\D+", "")));
        } catch(Exception ex){
        }
        Discount discount = new Discount();
        try{
            if(!e.select("span.badge > span.percent").isEmpty()){
                discount.setDiscountType(DiscountType.PERCENT);
                discount.setDiscountValue(e.select("span.badge").first().text().replaceAll("\\D+", ""));
            } else{
                discount.setDiscountValue(e.select("span.badge").first().text().trim());
                discount.setDiscountType(DiscountType.PLUSBOOK);
            }
        } catch(Exception ex){
        }
        price.setDiscount(discount);
        book.setPrice(price);
        return book;
    }
}
