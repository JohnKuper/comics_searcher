package com.korobeinikov.comicsviewer.model;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
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
        public String modified;
        public String format;
        public int pageCount;
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
    public static class Thumbnail {
        public String path;
        public String extension;
    }

    @Parcel
    public static class Price {
        public double price;
    }

    public void swapResults(MarvelData marvelData) {
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
