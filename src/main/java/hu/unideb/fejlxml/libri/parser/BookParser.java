package hu.unideb.fejlxml.libri.parser;

import hu.unideb.fejlxml.libri.model.Book;
import hu.unideb.fejlxml.libri.model.DeliveryPoint;
import hu.unideb.fejlxml.libri.model.Discount;
import hu.unideb.fejlxml.libri.model.DiscountType;
import hu.unideb.fejlxml.libri.model.Price;
import hu.unideb.fejlxml.libri.model.Rating;
import hu.unideb.fejlxml.libri.model.SmallBook;
import hu.unideb.fejlxml.libri.model.Transport;
import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BookParser {

    public BookParser() {
    }

    public Book parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla").validateTLSCertificates(false).get();
        Book book = parse(doc);
        book.setUri(url);
        return book;
    }

    public Book parse(File file) throws IOException {
        Document doc = Jsoup.parse(file, null);
        Book book = parse(doc);
        book.setUri(file.toURI().toString());
        return book;
    }

    public Book parse(Document doc) throws IOException {
        Book book = new Book();
        ArrayList<String> authors = new ArrayList<>();
        try {
            for (Element e : doc.select("#product-page-header > h2.authors > a.authors")) {
                authors.add(e.text().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        book.setAuthors(authors);

        String title = null;
        try {
            title = doc.select("#product-page-header > h1 > span.product-title").first().text().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        book.setTitle(title);

        String subTitle = null;
        try {
            subTitle = doc.select("#product-page-header > h1 > span.subtitle").first().text().trim();
        } catch (Exception e) {
        }
        book.setSubTitle(subTitle);

        String publisher = null;
        try {
            publisher = doc.select("div.sidebar-book-info > div.book-datas > div.book-datas-label:containsOwn(Kiadó:) + div.book-datas-value").first().text().trim();
        } catch (Exception e) {
        }
        book.setPublisher(publisher);

        Integer date = null;
        try {
            String s = doc.select("div.sidebar-book-info > div.book-datas > div.book-datas-label:containsOwn(Kiadás éve:) + div.book-datas-value").first().text().trim();
            date = Integer.parseInt(s);
        } catch (Exception e) {
        }
        book.setDate(date);

        String description = null;
        try {
            description = doc.select("header#product-page-header + div.tartalom > div.tartalomExtra").first().ownText().trim();
        } catch (Exception e) {
        }
        book.setDescription(description);

        Integer pages = null;
        try {
            pages = Integer.parseInt(doc.select("div.sidebar-book-info > div.book-datas > div.book-datas-label:containsOwn(Oldalak száma:) + div.book-datas-value").first().text().trim());
        } catch (Exception e) {
        }
        book.setPages(pages);

        String language = null;
        try {
            language = doc.select("div.sidebar-book-info > div.book-datas > div.book-datas-label:containsOwn(Nyelv:) + div.book-datas-value").first().text().trim();
        } catch (Exception e) {
        }
        book.setLanguage(language);

        String cover = null;
        try {
            cover = doc.select("div.sidebar-book-info > div.book-datas > div.book-datas-label:containsOwn(Borító:) + div.book-datas-value").first().text().trim();
        } catch (Exception e) {
        }
        book.setCover(cover);
        String productCode = null;
        try {
            productCode = doc.select("div.sidebar-book-info > div.book-datas > div.book-datas-label:containsOwn(Árukód:) + div.book-datas-value").first().text().split("/")[0].trim();
        } catch (Exception e) {
        }
        book.setProductCode(productCode);

        Transport transport = null;
        try{
            String s = doc.select("div.transport div.ordable-date").first().text().trim();
            transport = new Transport();
            if(s.contains("Akár ingyen szállítással")){
                transport.setFree(true);
                s = s.substring(0,s.lastIndexOf("Akár ingyen szállítással"));
            }
            transport.setTransport(s.substring("Szállítás:".length()).trim());
        } catch (Exception e){
        }
        book.setTransport(transport);
        
        Date expectedDate = null;
        try{
            DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            expectedDate = format.parse(doc.select("div.transport div.ordable-date + div").first().text().replace("Várható megjelenés:","").trim().replace(". ","-").replace(".",""));
        } catch(Exception ex){
        }
        book.setExceptedDate(expectedDate);
        
        String isbn13 = null;
        try {
            isbn13 = doc.select("div.sidebar-book-info > div.book-datas > div.book-datas-label:containsOwn(ISBN:) + div.book-datas-value").first().text().trim();
        } catch (Exception e) {
        }
        book.setIsbn13(isbn13);
        
        Price price = new Price();
        try{
            price.setActualPrice(new BigDecimal(doc.select("div.price_box > div.price_box_bottom  div.price  span.final_list_price").first().text().replaceAll("\\D+", "")));
        } catch(Exception ex){
        }
        try{
            price.setOriginalPrice(new BigDecimal(doc.select("div.price_box > div.price_box_bottom  div.price div.old_price > del").first().text().replaceAll("\\D+", "")));
        } catch(Exception ex){
        }
        Discount discount = new Discount();
        try{
            String discountString = doc.select("div.price_box > div.price_box_bottom div.price  span.discount-text > b").first().text().trim();
            if(discountString.indexOf('%')!=-1){
                discount.setDiscountType(DiscountType.PERCENT);
                discount.setDiscountValue(discountString.replaceAll("\\D+", ""));
            } else{
                discount.setDiscountValue(discountString.trim());
                discount.setDiscountType(DiscountType.PLUSBOOK);
            }
        } catch(Exception ex){
        }
        price.setDiscount(discount);
        book.setPrice(price);

        Rating rating = null;
        try {
            int ratingValue = Integer.parseInt(doc.select("div.rating div.rating-holder > div.rating-stars > div.rating-stars-main").first().attr("class").split(" ")[1].replaceAll("\\D+", ""));
            rating = new Rating();
            rating.setRating(ratingValue);
            int count = Integer.parseInt(doc.select("div.rating > span.rating-info").first().text().replace("értékelés", "").trim());
            if (count > 0) {
                rating.setCount(count);
            } else {
                rating = null;
            }
        } catch (Exception e) {
        }
        book.setRating(rating);

        ArrayList<DeliveryPoint> deliveryPoints = new ArrayList<>();
        try {
            for (Element e : doc.select("div.stockList > div.storeToSort")) {
                DeliveryPoint deliveryPoint = new DeliveryPoint();
                deliveryPoint.setAddress(e.select("div.stock_info_title").text().trim());
                String quantity = e.select("div.stock_info_quantity").text().trim();
                if (quantity != null && !quantity.equals("-")) {
                    deliveryPoint.setQuantity(quantity);
                }
                deliveryPoints.add(deliveryPoint);
            }
        } catch (Exception e) {
        }
        book.setDeliverypoints(deliveryPoints);

        Boolean antique_partner = null;
        try {
            antique_partner = !doc.select("div.price-box img[alt=Antikvár termék]").isEmpty();
        } catch (Exception e) {
        }
        book.setAntiquePartner(antique_partner);
        Boolean ebook = null;
        try {
            
            ebook = !doc.select("img[alt=e-Könyv]").isEmpty();
        } catch (Exception e) {
        }
        book.setEbook(ebook);

        ArrayList<SmallBook> bestsellers = new ArrayList<SmallBook>();
        try {
            for (Element e : doc.select("div#tab_content_best_booksTab article")) {
                bestsellers.add(SmallBookParser.parser(e));
            }
        } catch (Exception e) {
        }
        book.setBestsellers(bestsellers);
        
        ArrayList<SmallBook> publisher_news = new ArrayList<SmallBook>();
        try {
            for (Element e : doc.select("div#tab_content_publisher_booksTab article")) {
                publisher_news.add(SmallBookParser.parser(e));
            }
        } catch (Exception e) {
        }
        book.setPublisher_news(publisher_news);

        ArrayList<SmallBook> author_books = new ArrayList<SmallBook>();
        try {
            for (Element e : doc.select("div#tab_content_author_booksTab article")) {
                author_books.add(SmallBookParser.parser(e));
            }
        } catch (Exception e) {
        }
        book.setAuthor_books(author_books);
        
        Integer loyalityPoints = null;
        try {
            String s = doc.select("div.discount div:containsOwn(A termék megvásárlásával kapható:) > span.value").first().text().trim();
            Pattern pattern = Pattern.compile("([^(pont)]+) pont");
            Matcher matcher = pattern.matcher(s);

            loyalityPoints = Integer.parseInt(matcher.group(1).trim());
        } catch (Exception e) {
        }
        book.setLoyaltyPoints(loyalityPoints);

        return book;
    }
}
