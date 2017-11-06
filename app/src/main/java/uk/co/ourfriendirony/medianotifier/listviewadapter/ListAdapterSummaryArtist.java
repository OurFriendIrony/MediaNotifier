package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.artist.Artist;
import uk.co.ourfriendirony.medianotifier.db.ArtistDatabase;

import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.WATCHED_FALSE;
import static uk.co.ourfriendirony.medianotifier.db.ArtistDatabaseDefinition.WATCHED_TRUE;

public class ListAdapterSummaryArtist extends ArrayAdapter {
    private final List<Artist> artists;
    private final int defaultLayoutId;
    private DateFormat yearFormatter = new SimpleDateFormat("yyyy");
    private DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy");

    public ListAdapterSummaryArtist(Context context, int defaultLayoutId, List<Artist> objects) {
        super(context, defaultLayoutId, objects);
        this.defaultLayoutId = defaultLayoutId;
        this.artists = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        switch (defaultLayoutId) {
            case R.layout.list_item_find:
                view = getFindView(position, view);
                break;
            case R.layout.list_item_artist:
                view = getChecklistView(position, view);
                break;
            default:
                view = getTitleView(position, view);
                break;
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = getFindView(position, view);

        return view;
    }

    @NonNull
    private View getFindView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_find, null);

        TextView textId = (TextView) view.findViewById(R.id.find_item_id);
        TextView textTitle = (TextView) view.findViewById(R.id.find_item_title);

        Artist artist = artists.get(position);
        textId.setText(artist.getIdAsString());
        textTitle.setText(artist.getTitle());

        return view;
    }

    @NonNull
    private View getChecklistView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_artist, null);
        final ArtistDatabase database = new ArtistDatabase(getContext());

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_title);

        final Artist artist = artists.get(position);

        textTitle.setText(artist.getTitle());

        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.button_toggle);
        toggle.setChecked(database.getArtistWatchedStatusAsBoolean(artist));

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    database.updateArtistWatchedStatus(artist, WATCHED_TRUE);
                } else {
                    database.updateArtistWatchedStatus(artist, WATCHED_FALSE);
                }
            }
        });

        return view;
    }

    @NonNull
    private View getTitleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);

        TextView textTitle = (TextView) view.findViewById(R.id.find_item_title);
        Artist artist = artists.get(position);
        textTitle.setText(artist.getTitle());

        return view;
    }
}
