package hu.unideb.fejlxml.libri.parser;

import hu.unideb.fejlxml.libri.model.Discount;
import hu.unideb.fejlxml.libri.model.DiscountType;
import hu.unideb.fejlxml.libri.model.Price;
import hu.unideb.fejlxml.libri.model.Rating;
import hu.unideb.fejlxml.libri.model.SearchResultItem;
import hu.unideb.fejlxml.libri.model.SearchResults;
import hu.unideb.fejlxml.libri.model.Transport;
import java.io.IOException;
import java.math.BigDecimal;

import java.util.List;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SearchResultsParser {
    
    public static final int MAX_ITEMS = 60;

    private int maxItems = MAX_ITEMS;

    public SearchResultsParser() {
    }

    public SearchResultsParser(int maxItems) {
        setMaxItems(maxItems);
    }

    public int getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    private int getTotalItems(Document doc) throws IOException {
        int totalItems = 0;
        try {
            totalItems = Integer.parseInt(doc.select("header.productList_title > span.count").first().text().replaceAll("\\D+", ""));
        } catch (Exception e) {
            throw new IOException("Malformed document");
        }
        return totalItems;
    }

    private List<SearchResultItem> extractItems(Document doc) throws IOException {
        ArrayList<SearchResultItem> items = new ArrayList<>();
        for (Element element : doc.select("article.box-product-full")) {
            SearchResultItem item = new SearchResultItem();
            String uri = null;
            try {
                uri = element.select("a.book-title").first().attr("href").trim();
            } catch (Exception e) {
                throw new IOException("Malformed document");
            }
            item.setUri(uri);

            String title = null;
            try {
                title = element.select("a.book-title").first().text().trim();
            } catch (Exception e) {
                throw new IOException("Malformed document");
            }
            item.setTitle(title);

            ArrayList<String> authors = new ArrayList<>();
            try {
                for (Element e : element.select("div.author > a.authors")) {
                    authors.add(e.text().trim());
                }
            } catch (Exception e) {
            }
            item.setAuthors(authors);

            Price price = new Price();
            try {
                price.setActualPrice(new BigDecimal(element.select("div.list-price > div.price > span.act-price").first().text().replaceAll("\\D+", "")));
            } catch (Exception ex) {
            }
            try {
                price.setOriginalPrice(new BigDecimal(element.select("div.list-price > div.price > del").first().text().replaceAll("\\D+", "")));
            } catch (Exception ex) {
            }
            Discount discount = new Discount();
            try {
                String discountString = element.select("div.list-price > div.price > span.list-percent").first().text().trim();
                if (discountString.indexOf('%') != -1) {
                    discount.setDiscountType(DiscountType.PERCENT);
                    discount.setDiscountValue(discountString.replaceAll("\\D+", ""));
                } else {
                    discount.setDiscountValue(discountString.trim());
                    discount.setDiscountType(DiscountType.PLUSBOOK);
                }
            } catch (Exception ex) {
            }
            price.setDiscount(discount);
            item.setPrice(price);

            Integer loyalityPoints = null;
            try {
                String s = doc.select("div.tvPriceBox > div.lpk_point_value").first().text().trim();
                Pattern pattern = Pattern.compile("([^(pont)]+) pont");
                Matcher matcher = pattern.matcher(s);

                loyalityPoints = Integer.parseInt(matcher.group(1).trim());
            } catch (Exception e) {
            }
            item.setLoyaltyPoints(loyalityPoints);

            String productCode = null;
            try {
                productCode = element.attr("data-id").trim();
            } catch (Exception e) {
            }
            item.setProductCode(productCode);

            Transport transport = null;
            try {
                String s = element.select("div.transport div.orderable span").first().text().split(":")[1].trim();
                transport = new Transport();
                transport.setTransport(s);
            } catch (Exception e) {
            }
            item.setTransport(transport);
            Boolean ebook = null;
            try {
                ebook = !element.select("img[alt=e-Könyv]").isEmpty();
            } catch (Exception e) {
            }
            item.setEbook(ebook);
            Rating rating = null;
            try {
                int ratingValue = Integer.parseInt(element.select("div.rating-holder > div.rating-stars > div.rating-stars-main").first().attr("class").split(" ")[1].replaceAll("\\D+", ""));
                rating = new Rating();
                rating.setRating(ratingValue);
            } catch (Exception e) {
            }
            item.setRating(rating);
            items.add(item);
        }
        return items;
    }

    private Document getNextPage(Document doc) throws IOException {
        String nextPage = null;
        try {
            nextPage = doc.select("section.pagination > a.navLink:containsOwn(Következő)").first().attr("abs:href");
        } catch (Exception e) {
        }
        return nextPage != null ? Jsoup.connect(nextPage).userAgent("Mozilla").validateTLSCertificates(false).get() : null;
    }

    public SearchResults parse(Document doc) throws IOException {
        ArrayList<SearchResultItem> items = new ArrayList<>();
        int totalItems = getTotalItems(doc);
        loop:
        while (totalItems != 0 && doc != null) {
            for (SearchResultItem item : extractItems(doc)) {
                if (0 <= maxItems && maxItems <= items.size()) {
                    break loop;
                }
                items.add(item);
            }
            if (0 <= maxItems && maxItems <= items.size()) {
                break;
            }
            doc = getNextPage(doc);
        }
        return new SearchResults(totalItems, 1, items.size(), items);
    }

}
