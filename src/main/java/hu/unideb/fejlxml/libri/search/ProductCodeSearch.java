package hu.unideb.fejlxml.libri.search;

import hu.unideb.fejlxml.libri.model.Book;
import hu.unideb.fejlxml.libri.parser.BookParser;
import java.io.IOException;
import java.net.URLDecoder;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ProductCodeSearch {

    private static final String SEARCH_URI = "https://www.libri.hu/talalati_lista/";

    public ProductCodeSearch() {
    }

    public Book doSearch(String prouctCode) throws IOException {
        Document doc = Jsoup.connect(SEARCH_URI).userAgent("Mozilla").data("ext_id", URLDecoder.decode(prouctCode, "UTF-8")).data("reszletes", "1").validateTLSCertificates(false).get();
        int totalItems = 0;
        try {
            totalItems = Integer.parseInt(doc.select("header.productList_title > span.count").get(0).text().replaceAll("\\D+", ""));
        } catch (Exception e) {
            throw new IOException("Not found");
        }
        Element element = null;
        for(Element e : doc.select("article.box-product-full")){
            if(e.attr("data-id").trim().equals(URLDecoder.decode(prouctCode, "UTF-8"))){
                element = e;
                break;
            }
        }
        if(element==null){
            return null;
        }
        String uri = element.select("a.book-title").get(0).attr("abs:href").trim();
        if (uri.contains("www.libri.hu/muleiras/")) {
            doc = Jsoup.connect(uri).userAgent("Mozilla").validateTLSCertificates(false).get();
            for(Element e : doc.select("article.box-product-full")){
                uri = e.select("a.book-title").get(0).attr("abs:href").trim();
                Book book = new BookParser().parse(Jsoup.connect(uri).userAgent("Mozilla").validateTLSCertificates(false).get());
                if (!book.getProductCode().equals(URLDecoder.decode(prouctCode, "UTF-8"))) {
                    continue;
                }
                book.setUri(uri);            
                return book;
            }
        } else {
            Book book = new BookParser().parse(Jsoup.connect(uri).userAgent("Mozilla").validateTLSCertificates(false).get());
            book.setUri(uri);
            return book;
        }
        return null;
    }

}
