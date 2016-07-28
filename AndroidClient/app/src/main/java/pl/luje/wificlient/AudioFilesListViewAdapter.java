package pl.luje.wificlient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AudioFilesListViewAdapter extends ArrayAdapter<String> {
    public AudioFilesListViewAdapter(Context context, ArrayList<String> elements) {
        super(context, 0, elements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);
        }
        TextView row = (TextView) convertView.findViewById(R.id.row);
        row.setText(title);
        return convertView;
    }
}
