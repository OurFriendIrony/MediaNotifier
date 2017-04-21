package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabase;
import uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition;

import static uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition.WATCHED_FALSE;
import static uk.co.ourfriendirony.medianotifier.db.TVShowDatabaseDefinition.WATCHED_TRUE;
import static uk.co.ourfriendirony.medianotifier.general.StringHandler.pad;

public class ListAdapterTVEpisode extends ArrayAdapter {
    private final List<TVEpisode> tvShowEpisodes;
    private final Boolean includeTitle;

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
    TVShowDatabase database = new TVShowDatabase(new TVShowDatabaseDefinition(getContext()));

    public ListAdapterTVEpisode(Context context, int defaultLayoutId, List<TVEpisode> objects, Boolean includeTitle) {
        super(context, defaultLayoutId, objects);
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
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_tv_episode, null);

        TextView showTitleView = (TextView) v.findViewById(R.id.list_item_notification_show_title);
        if (includeTitle) {
            TextView seperatorBar = (TextView) v.findViewById(R.id.list_item_tv_separatorbar);
            seperatorBar.setBackgroundColor(getContext().getResources().getColor(R.color.black));
        } else {
            showTitleView.setHeight(0);
        }

        TextView episodeTitleView = (TextView) v.findViewById(R.id.list_item_notification_episode_title);
        TextView textNumber = (TextView) v.findViewById(R.id.list_item_notification_number);
        TextView textDate = (TextView) v.findViewById(R.id.list_item_notification_date);
        TVEpisode tvEpisode = tvShowEpisodes.get(position);

        String dateString = "";
        Date date = tvEpisode.getAirDate();
        if (date != null)
            dateString = dateFormat.format(date);

        showTitleView.setText(tvEpisode.getTitle());
        episodeTitleView.setText(tvEpisode.getName());
        textNumber.setText("S" + pad(tvEpisode.getSeasonNumber(), 2) + " E" + pad(tvEpisode.getEpisodeNumber(), 2));
        textDate.setText(dateString);

        ToggleButton toggle = (ToggleButton) v.findViewById(R.id.button_toggle);
        toggle.setChecked(database.getEpisodeWatchedStatus(tvEpisode));

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    database.updateTVEpisodeWatchedStatus(tvEpisode, WATCHED_TRUE);
                } else {
                    database.updateTVEpisodeWatchedStatus(tvEpisode, WATCHED_FALSE);
                }
            }
        });

        return v;
    }
}
