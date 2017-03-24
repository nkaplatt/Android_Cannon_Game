package com.exeter.np326.cannongame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;

/**
 * A simple {@link ContentFragment} subclass.
 *
 */
public class ContentFragment extends WebViewFragment {
    private	static final String	KEY_FILE="file";

    /**
     * @param file
     * @return
     */
    protected static ContentFragment newInstance(String file) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FILE, file);
        fragment.setArguments(args);
        return(fragment);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle	savedInstanceState)	{
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater	inflater,
                             ViewGroup container,
                             Bundle	savedInstanceState)	{
        View result = super.onCreateView(inflater, container, savedInstanceState);
        getWebView().getSettings().setJavaScriptEnabled(true);
        getWebView().getSettings().setSupportZoom(true);
        getWebView().getSettings().setBuiltInZoomControls(true);
        getWebView().loadUrl(getPage());
        return(result);
    }

    /**
     * @return
     */
    private	String getPage() {
        return(getArguments().getString(KEY_FILE));
    }
}
