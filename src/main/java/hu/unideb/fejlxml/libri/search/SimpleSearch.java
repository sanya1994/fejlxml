package hu.unideb.fejlxml.libri.search;

import hu.unideb.fejlxml.libri.jaxb.JAXBUtil;
import hu.unideb.fejlxml.libri.model.SearchResults;
import hu.unideb.fejlxml.libri.parser.SearchResultsParser;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

public class SimpleSearch extends SearchResultsParser {

    private static final String SEARCH_URI = "https://www.libri.hu/talalati_lista/";

    public SimpleSearch() {
    }

    public SimpleSearch(int maxItems) {
        super(maxItems);
    }

    public SearchResults doSearch(HashMap<String, String> gets) throws IOException {
        Document doc = Jsoup.connect(SEARCH_URI).userAgent("Mozilla").
                data("szerzo", URLDecoder.decode(gets.get("szerzo"),"UTF-8")).
                data("cim", URLDecoder.decode(gets.get("cim"),"UTF-8")).
                data("keyword", URLDecoder.decode(gets.get("keyword"),"UTF-8")).
                data("s_cim", URLDecoder.decode(gets.get("s_cim"),"UTF-8")).
                data("book_target_id", URLDecoder.decode(gets.get("book_target_id"),"UTF-8")).
                data("book_era_id", URLDecoder.decode(gets.get("book_era_id"),"UTF-8")).
                data("book_location_id", URLDecoder.decode(gets.get("book_location_id"),"UTF-8")).
                data("book_lang_id", URLDecoder.decode(gets.get("book_lang_id"),"UTF-8")).
                data("kiado", URLDecoder.decode(gets.get("kiado"),"UTF-8")).
                data("isbn", URLDecoder.decode(gets.get("isbn"),"UTF-8")).
                data("ar1", URLDecoder.decode(gets.get("ar1"),"UTF-8")).
                data("ar2", URLDecoder.decode(gets.get("ar2"),"UTF-8")).
                data("ev1", URLDecoder.decode(gets.get("ev1"),"UTF-8")).
                data("ev2", URLDecoder.decode(gets.get("ev2"),"UTF-8")).
                data("ext_id", URLDecoder.decode(gets.get("ext_id"),"UTF-8")).
                data("transport_id", URLDecoder.decode(gets.get("transport_id"),"UTF-8")).
                data("reszletes", "1").validateTLSCertificates(false).get();
        return parse(doc);
    }
}
