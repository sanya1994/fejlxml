package hu.unideb.fejlxml.libri.service;

import hu.unideb.fejlxml.libri.model.SearchResults;
import hu.unideb.fejlxml.libri.search.SimpleSearch;
import java.io.IOException;
import java.util.HashMap;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class SearchResource extends ServerResource {

    @Get("json|xml")
    public SearchResults represent() throws IOException {
        HashMap<String, String> gets = new HashMap();
        gets.put("szerzo", getQueryValue("szerzo") != null ? getQueryValue("szerzo") : "");
        gets.put("cim", getQueryValue("cim") != null ? getQueryValue("cim") : "");
        gets.put("keyword", getQueryValue("keyword") != null ? getQueryValue("keyword") : "");
        gets.put("s_cim", getQueryValue("s_cim") != null ? getQueryValue("s_cim") : "");
        gets.put("book_target_id", getQueryValue("book_target_id") != null ? getQueryValue("book_target_id") : "");
        gets.put("book_era_id", getQueryValue("book_era_id") != null ? getQueryValue("book_era_id") : "");
        gets.put("book_location_id", getQueryValue("book_location_id") != null ? getQueryValue("book_location_id") : "");
        gets.put("book_lang_id", getQueryValue("book_lang_id") != null ? getQueryValue("book_lang_id") : "");
        gets.put("kiado", getQueryValue("kiado") != null ? getQueryValue("kiado") : "");
        gets.put("isbn", getQueryValue("isbn") != null ? getQueryValue("isbn") : "");
        gets.put("ar1", getQueryValue("ar1") != null ? getQueryValue("ar1") : "");
        gets.put("ar2", getQueryValue("ar2") != null ? getQueryValue("ar2") : "");
        gets.put("ev1", getQueryValue("ev1") != null ? getQueryValue("ev1") : "");
        gets.put("ev2", getQueryValue("ev2") != null ? getQueryValue("ev2") : "");
        gets.put("ext_id", getQueryValue("ext_id") != null ? getQueryValue("ext_id") : "");
        gets.put("transport_id", getQueryValue("transport_id") != null ? getQueryValue("transport_id") : "");

        return new SimpleSearch().doSearch(gets);
    }

}
