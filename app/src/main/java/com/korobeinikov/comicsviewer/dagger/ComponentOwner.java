package com.korobeinikov.comicsviewer.dagger;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public interface ComponentOwner<T> {
    T getComponent();
}
