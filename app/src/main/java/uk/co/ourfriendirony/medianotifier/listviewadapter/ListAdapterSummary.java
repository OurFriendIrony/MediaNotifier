package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.mediaitem.MediaItem;
import uk.co.ourfriendirony.medianotifier.db.Database;

import static uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabaseDefinition.WATCHED_FALSE;
import static uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabaseDefinition.WATCHED_TRUE;

public class ListAdapterSummary extends ArrayAdapter {
    private final List<MediaItem> mediaItems;
    private final int defaultLayoutId;
    private final Database db;

    public ListAdapterSummary(Context context, int defaultLayoutId, List<MediaItem> mediaItems, Database db) {
        super(context, defaultLayoutId, mediaItems);
        this.defaultLayoutId = defaultLayoutId;
        this.mediaItems = mediaItems;
        this.db = db;
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

        MediaItem mediaItem = mediaItems.get(position);

        textId.setText(mediaItem.getId());
        textTitle.setText(mediaItem.getTitle());
        textDate.setText(mediaItem.getReleaseDateYear());
        textOverview.setText(mediaItem.getDescription());
        return view;
    }

    @NonNull
    private View getChecklistView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_generic_toggle, null);

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        TextView textSubTitle = (TextView) view.findViewById(R.id.list_item_generic_subtitle);
        TextView textDate = (TextView) view.findViewById(R.id.list_item_generic_date);
        TextView textOverview = (TextView) view.findViewById(R.id.list_item_generic_overview);

        final MediaItem mediaItem = mediaItems.get(position);

        textTitle.setText(mediaItem.getTitle());
        textSubTitle.setText(mediaItem.getSubtitle());
        textDate.setText(mediaItem.getReleaseDateFull());
        textOverview.setText(mediaItem.getDescription());

        SwitchCompat toggle = (SwitchCompat) view.findViewById(R.id.list_item_toggle);

        toggle.setChecked(!db.getWatchedStatusAsBoolean(mediaItem));
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.updateWatchedStatus(mediaItem, (!isChecked) ? WATCHED_TRUE : WATCHED_FALSE);
            }
        });

        return view;
    }

    @NonNull
    private View getTitleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        MediaItem mediaItem = mediaItems.get(position);
        textTitle.setText(mediaItem.getTitle());

        return view;
    }
}
