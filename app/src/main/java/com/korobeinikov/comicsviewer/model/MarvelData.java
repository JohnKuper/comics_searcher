package com.korobeinikov.comicsviewer.model;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Dmitriy_Korobeinikov.
 */
@Parcel
public class MarvelData {

    private static final int ITEMS_PER_PAGE = 20;

    public int offset;
    public int total;
    public ArrayList<ComicInfo> results = new ArrayList<>();

    @Parcel
    public static class ComicInfo {

        public int id;
        public String title;
        public String description;
        public String format;
        public Thumbnail thumbnail;
        public ArrayList<Price> prices;

        public Price getFirstPrice() {
            if (prices != null && !prices.isEmpty()) {
                return prices.get(0);
            }
            return null;
        }
    }

    @Parcel
    public static class Price {
        public double price;
    }

    public void updateResults(MarvelData marvelData) {
        results.clear();
        merge(marvelData);
    }

    public void merge(MarvelData marvelData) {
        offset = marvelData.offset;
        total = marvelData.total;
        results.addAll(marvelData.results);
    }

    public void clear() {
        offset = 0;
        total = 0;
        results.clear();
    }

    public int getNextOffset() {
        return offset += ITEMS_PER_PAGE;
    }

    public boolean hasMoreData() {
        return offset + ITEMS_PER_PAGE < total;
    }
}
