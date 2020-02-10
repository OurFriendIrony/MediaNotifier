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
import uk.co.ourfriendirony.medianotifier._objects.Item;
import uk.co.ourfriendirony.medianotifier.db.Database;

import static uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabaseDefinition.WATCHED_FALSE;
import static uk.co.ourfriendirony.medianotifier.db.movie.MovieDatabaseDefinition.WATCHED_TRUE;

public class ListAdapterSummary extends ArrayAdapter {
    private final List<Item> items;
    private final int defaultLayoutId;
    private final Database db;

    public ListAdapterSummary(Context context, int defaultLayoutId, List<Item> items, Database db) {
        super(context, defaultLayoutId, items);
        this.defaultLayoutId = defaultLayoutId;
        this.items = items;
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

        Item item = items.get(position);

        textId.setText(item.getId());
        textTitle.setText(item.getTitle());
        textDate.setText(item.getReleaseDateYear());
        textOverview.setText(item.getDescription());
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

        final Item item = items.get(position);

        textTitle.setText(item.getTitle());
        textSubTitle.setText(item.getSubtitle());
        textDate.setText(item.getReleaseDateFull());
        textOverview.setText(item.getDescription());

        SwitchCompat toggle = (SwitchCompat) view.findViewById(R.id.list_item_toggle);

        toggle.setChecked(!db.getWatchedStatusAsBoolean(item));
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                db.updateWatchedStatus(item, (!isChecked) ? WATCHED_TRUE : WATCHED_FALSE);
            }
        });

        return view;
    }

    @NonNull
    private View getTitleView(int position, View view) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(defaultLayoutId, null);

        TextView textTitle = (TextView) view.findViewById(R.id.list_item_generic_title);
        Item item = items.get(position);
        textTitle.setText(item.getTitle());

        return view;
    }
}
