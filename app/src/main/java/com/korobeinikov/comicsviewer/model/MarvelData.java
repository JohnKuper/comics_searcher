package com.korobeinikov.comicsviewer.model;

import java.util.ArrayList;

/**
 * Created by Dmitriy_Korobeinikov.
 * Copyright (C) 2017 SportingBet. All rights reserved.
 */
public class MarvelData {

    public ArrayList<Result> results;

    public static class Result {
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

    public static class Thumbnail {
        public String path;
        public String extension;
    }

    public static class Price {
        public double price;
    }
}
