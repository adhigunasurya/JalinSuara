package com.jalinsuara.android;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;
 
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    static SearchRecentSuggestions getBridge(Context ctxt) {
        return(new SearchRecentSuggestions(ctxt, "com.app.SearchInterfaceDemo",DATABASE_MODE_QUERIES));
    }
         
    public SearchSuggestionProvider() {
            super();
            setupSuggestions("com.app.SearchInterfaceDemo", DATABASE_MODE_QUERIES);
    }
}
