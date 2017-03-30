package uk.co.ourfriendirony.medianotifier.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.movie.MDMovieSummary;
import uk.co.ourfriendirony.medianotifier.clients.MovieDatabaseClient;
import uk.co.ourfriendirony.medianotifier.general.MyMovieAdapter;

public class MovieLookupActivity extends AppCompatActivity {
    ListView simpleList;
    List<MDMovieSummary> movies = new ArrayList<>();
    MovieDatabaseClient client = new MovieDatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookup);

        TextView textView = (TextView) findViewById(R.id.title_lookup);
        textView.setText(R.string.title_lookup_movie);

        EditText editText = (EditText) findViewById(R.id.input_lookup);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    try {
                        movies = client.queryMovie(textView.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (movies.size() > 0) {
                        MyMovieAdapter adapter = new MyMovieAdapter(textView.getContext(), R.layout.list_item, movies);
                        simpleList = (ListView) findViewById(R.id.list_lookup);
                        simpleList.setAdapter(adapter);
                    } else {
                        Toast.makeText(textView.getContext(), R.string.lookup_no_results, Toast.LENGTH_LONG).show();
                    }
                    handled = true;
                }
                return handled;
            }
        });
    }
}
