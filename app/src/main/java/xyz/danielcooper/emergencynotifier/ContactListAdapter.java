package xyz.danielcooper.emergencynotifier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dan on 18/01/2016.
 */
public class ContactListAdapter extends ArrayAdapter<Person> {
    private LayoutInflater inflater;

    public ContactListAdapter(Context context, ArrayList<Person> menuOptions, LayoutInflater inflater) {
        super(context, 0, menuOptions);

        this.inflater = inflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contacts_list_item, parent, false);
        }

        Person contact = getItem(position);

        ((TextView)convertView.findViewById(R.id.name)).setText(contact.getName());
        ((TextView)convertView.findViewById(R.id.phone_number)).setText(contact.getNumber());

        return convertView;
    }
}
