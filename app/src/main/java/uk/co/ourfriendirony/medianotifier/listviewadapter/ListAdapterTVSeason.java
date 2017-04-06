package uk.co.ourfriendirony.medianotifier.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import uk.co.ourfriendirony.medianotifier.R;
import uk.co.ourfriendirony.medianotifier.autogen.tvshow.TVSeason;

public class ListAdapterTVSeason extends ArrayAdapter {
    private final List<TVSeason> tvShowSeasons;

    DateFormat dateFormat = new SimpleDateFormat("yyyy");

    public ListAdapterTVSeason(Context context, int textViewResourceId, List<TVSeason> seasons) {
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
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_item_tv_season, null);

        TextView textTitle = (TextView) v.findViewById(R.id.find_item_season_title);

        TVSeason tvShowSeason = tvShowSeasons.get(position);

        textTitle.setText(String.valueOf(tvShowSeason.getSeasonNumber()));

        return v;
    }
}
