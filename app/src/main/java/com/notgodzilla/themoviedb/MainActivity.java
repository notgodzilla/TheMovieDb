package com.notgodzilla.themoviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText searchBox;

    public static final String SEARCH_QUERY_EXTRA = "search_query_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.searchBox = (EditText) findViewById(R.id.search_box);
        this.searchButton = (Button) findViewById(R.id.search_button);
        setSearchButtonListener();

    }

    private void setSearchButtonListener() {
        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchResultsIntent = new Intent(getApplicationContext(), SearchResultsActivity.class);
                searchResultsIntent.putExtra(SEARCH_QUERY_EXTRA, searchBox.getText().toString());
                startActivity(searchResultsIntent);
            }
        });
    }


}
