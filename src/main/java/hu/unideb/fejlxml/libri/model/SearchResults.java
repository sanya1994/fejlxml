package hu.unideb.fejlxml.libri.model;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@javax.xml.bind.annotation.XmlType(namespace = "http://www.inf.unideb.hu/Libri")
public class SearchResults {

	@XmlAttribute(required = true)
	private int itemsTotal;

	@XmlAttribute(required = true)
	private int from;

	@XmlAttribute(required = true)
	private int to;

	@XmlElement(name = "item")
	private ArrayList<SearchResultItem> items;

	public SearchResults() {
	}

	public SearchResults(int itemsTotal, int from, int to, ArrayList<SearchResultItem> items) {
		this.itemsTotal = itemsTotal;
		this.from = from;
		this.to = to;
		this.items = items;
	}

	public int getItemsTotal() {
		return itemsTotal;
	}

	public void setItemsTotal(int itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public ArrayList<SearchResultItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<SearchResultItem> items) {
		this.items = items;
	}
        
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
