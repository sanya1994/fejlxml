package hu.unideb.fejlxml.libri.service;

import hu.unideb.fejlxml.libri.model.Book;
import hu.unideb.fejlxml.libri.search.ProductCodeSearch;
import java.io.IOException;

import org.restlet.data.Status;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class BookResource extends ServerResource {

    @Get("json|xml")
    public Book represent() throws IOException {
        String productCode = getAttribute("productCode");
        Book book = new ProductCodeSearch().doSearch(productCode);
        if (book == null) {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return book;
    }

}
