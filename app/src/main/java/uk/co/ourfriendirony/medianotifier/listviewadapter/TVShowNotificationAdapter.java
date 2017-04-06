package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;

import static uk.co.ourfriendirony.medianotifier.general.StringHandler.pad;

public class TVShowNotificationAdapter extends ArrayAdapter {
    private final List<TVEpisode> tvEpisodes;

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

    public TVShowNotificationAdapter(Context context, int textViewResourceId, List<TVEpisode> objects) {
        super(context, textViewResourceId, objects);
        tvEpisodes = objects;
    }

    public List<TVEpisode> getTvEpisodes() {
        return tvEpisodes;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_notification, null);

        TextView showTitleView = (TextView) v.findViewById(R.id.list_item_notification_show_title);
        TextView episodeTitleView = (TextView) v.findViewById(R.id.list_item_notification_episode_title);
        TextView textNumber = (TextView) v.findViewById(R.id.list_item_notification_number);
        TVEpisode tvEpisode = tvEpisodes.get(position);

        String year = "";
        Date date = tvEpisode.getAirDate();
        if (date != null)
            year = dateFormat.format(date);

        showTitleView.setText(tvEpisode.getTitle());
        episodeTitleView.setText(tvEpisode.getName());
        textNumber.setText("S" + pad(tvEpisode.getSeasonNumber(), 2) + " E" + pad(tvEpisode.getEpisodeNumber(), 2));

        return v;
    }

}
