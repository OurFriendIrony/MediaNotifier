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

public class TVShowEpisodeListAdapter extends ArrayAdapter {
    private final List<TVEpisode> tvShowEpisodes;

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

    public TVShowEpisodeListAdapter(Context context, int textViewResourceId, List<TVEpisode> episodes) {
        super(context, textViewResourceId, episodes);
        tvShowEpisodes = episodes;
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
        v = inflater.inflate(R.layout.list_item_episode, null);

        TextView textTitle = (TextView) v.findViewById(R.id.list_item_notification_episode_title);
        TextView textNumber = (TextView) v.findViewById(R.id.list_item_notification_number);
        TextView textDate = (TextView) v.findViewById(R.id.list_item_notification_date);
        TVEpisode tvShowEpisode = tvShowEpisodes.get(position);

        String dateString = "";
        Date date = tvShowEpisode.getAirDate();
        if (date != null)
            dateString = dateFormat.format(date);

        textTitle.setText(tvShowEpisode.getName());
        textNumber.setText(tvShowEpisode.getEpisodeNumber() + ". ");
        textDate.setText(dateString);

        return v;
    }
}
