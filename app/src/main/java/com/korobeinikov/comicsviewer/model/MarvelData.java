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
        public Thumbnail thumbnail;
    }

    private static class Thumbnail {
        public String path;
        public String extension;
    }
}
