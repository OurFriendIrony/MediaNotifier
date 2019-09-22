package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.*;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.artist.Artist;
import uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabase;

import static uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition.WATCHED_FALSE;
import static uk.co.ourfriendirony.medianotifier.db.artist.ArtistDatabaseDefinition.WATCHED_TRUE;

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
            case R.layout.list_item_generic:
                view = getFindView(position, view);
                break;
            case R.layout.list_item_generic_toggle:
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
        view = inflater.inflate(R.layout.list_item_generic, null);

        TextView textId = (TextView) view.findViewById(R.id.list_item_generic_id);
        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        TextView textDate = (TextView) view.findViewById(R.id.list_item_generic_date);
        TextView textOverview = (TextView) view.findViewById(R.id.list_item_generic_overview);

        Artist artist = artists.get(position);

        textId.setText(artist.getIdAsString());
        textTitle.setText(artist.getTitle());
        textDate.setText("");
        textOverview.setText(artist.getOverview());

        return view;
    }

    @NonNull
    private View getChecklistView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_generic_toggle, null);
        final ArtistDatabase database = new ArtistDatabase(getContext());

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        TextView textSubTitle = (TextView) view.findViewById(R.id.list_item_generic_subtitle);
        TextView textDate = (TextView) view.findViewById(R.id.list_item_generic_date);
        TextView textOverview = (TextView) view.findViewById(R.id.list_item_generic_overview);

        final Artist artist = artists.get(position);

        textTitle.setText(artist.getTitle());
        textSubTitle.setHeight(0);
        textDate.setText("");
        textOverview.setText(artist.getOverview());

        SwitchCompat toggle = (SwitchCompat) view.findViewById(R.id.list_item_toggle);
        toggle.setChecked(!database.getArtistWatchedStatusAsBoolean(artist));

        toggle.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                database.updateArtistWatchedStatus(artist, (!isChecked) ? WATCHED_TRUE : WATCHED_FALSE);
            }
        });

        return view;
    }

    @NonNull
    private View getTitleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        Artist artist = artists.get(position);
        textTitle.setText(artist.getTitle());

        return view;
    }
}
