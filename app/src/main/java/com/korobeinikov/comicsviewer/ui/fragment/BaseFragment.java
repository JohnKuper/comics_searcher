package com.korobeinikov.comicsviewer.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.korobeinikov.comicsviewer.dagger.ComponentOwner;

import butterknife.Unbinder;

/**
 * Created by Dmitriy_Korobeinikov.
 */

public class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return ((ComponentOwner<T>) getActivity()).getComponent();
    }

    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }
}
