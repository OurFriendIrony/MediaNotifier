package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVEpisode;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;

public class TVShowEpisodeListAdapter extends ArrayAdapter {
    private final List<TVEpisode> tvShowEpisodes;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

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
        v = inflater.inflate(R.layout.find_item_episodes, null);

        TVEpisode tvShowEpisode = tvShowEpisodes.get(position);


        ImageView imageView = (ImageView) v.findViewById(R.id.find_item_episode_img);
        TextView textTitle = (TextView) v.findViewById(R.id.find_item_episode_title);

        textTitle.setText(tvShowEpisode.getName());
        imageView.setImageResource(R.drawable.circle_off);

        return v;
    }
}
