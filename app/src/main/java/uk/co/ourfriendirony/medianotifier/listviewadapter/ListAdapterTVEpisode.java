package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.*;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier._objects.tv.TVEpisode;
import uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabase;

import static uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition.WATCHED_FALSE;
import static uk.co.ourfriendirony.medianotifier.db.tv.TVShowDatabaseDefinition.WATCHED_TRUE;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.pad;

public class ListAdapterTVEpisode extends ArrayAdapter {
    private final List<TVEpisode> tvShowEpisodes;
    private final int defaultLayoutId;
    private final Boolean includeTitle;
    private final TVShowDatabase database = new TVShowDatabase(getContext());
    private DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yy");

    public ListAdapterTVEpisode(Context context, int defaultLayoutId, List<TVEpisode> objects, Boolean includeTitle) {
        super(context, defaultLayoutId, objects);
        this.defaultLayoutId = defaultLayoutId;
        this.tvShowEpisodes = objects;
        this.includeTitle = includeTitle;
    }

    public List<TVEpisode> getTvShowEpisodes() {
        return tvShowEpisodes;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        view = getFindView(position, view);
        return view;
    }

    @NonNull
    private View getFindView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_generic, null);
        final TVEpisode tvEpisode = tvShowEpisodes.get(position);

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        TextView textSubTitle = (TextView) view.findViewById(R.id.list_item_generic_subtitle);
        TextView textOverview = (TextView) view.findViewById(R.id.list_item_generic_overview);
        TextView textDate = (TextView) view.findViewById(R.id.list_item_generic_date);

        if (!includeTitle) {
            textSubTitle.setHeight(0);
        }

        String dateString = "";
        Date date = tvEpisode.getAirDate();
        if (date != null)
            dateString = dateFormatter.format(date);

        String text = "s" + pad(tvEpisode.getSeasonNumber(), 2) + "e" + pad(tvEpisode.getEpisodeNumber(), 2) + ": " +tvEpisode.getName();
        textTitle.setText(text);
        textSubTitle.setText(database.getTitleById(tvEpisode.getId()));

        textOverview.setText(tvEpisode.getOverview());
        textDate.setText(dateString);

        SwitchCompat toggle = (SwitchCompat) view.findViewById(R.id.button_toggle);
        toggle.setChecked(!database.getEpisodeWatchedStatusAsBoolean(tvEpisode));

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                database.updateTVEpisodeWatchedStatus(tvEpisode, (!isChecked) ? WATCHED_TRUE : WATCHED_FALSE);
            }
        });

        return view;
    }
}
