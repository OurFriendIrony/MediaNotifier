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
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;

public class TVShowSeasonListAdapter extends ArrayAdapter {
    private final List<TVSeason> tvShowSeasons;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public TVShowSeasonListAdapter(Context context, int textViewResourceId, List<TVSeason> seasons) {
        super(context, textViewResourceId, seasons);
        tvShowSeasons = seasons;
    }

    public List<TVSeason> getTvShowSeasons() {
        return tvShowSeasons;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(String.valueOf(this.getClass()),"***************************"+String.valueOf(position));
        TVSeason tvShowSeason = tvShowSeasons.get(position);
        Log.v(String.valueOf(this.getClass()),"***************************"+tvShowSeason.getSeasonNumber()+" "+tvShowSeason.getAirDate());
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.find_item_seasons, null);
        Log.v(String.valueOf(this.getClass()),"*************************** READYYYY "+R.id.find_item_season_img+ " "+R.id.find_item_season_title);
        ImageView imageView = (ImageView) v.findViewById(R.id.find_item_season_img);
        TextView textTitle = (TextView) v.findViewById(R.id.find_item_season_title);

        textTitle.setText(String.valueOf(tvShowSeason.getSeasonNumber()));
        imageView.setImageResource(R.drawable.circle_off);

        return v;
    }
}
