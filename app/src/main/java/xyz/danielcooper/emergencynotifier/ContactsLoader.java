package xyz.danielcooper.emergencynotifier;


import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Dan on 11/01/2016.
 */
public class ContactsLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    Context context;

    public static final String TAG = "ContactsLoader";
    public static final String QUERY_KEY = "QueryKey";

    public ContactsLoader(Context context) { context = context; }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String query = args.getString(QUERY_KEY);
        Uri uri = Uri.withAppendedPath(
                ContactsContract.CommonDataKinds.Contactables.CONTENT_FILTER_URI, query);

        String selection =
                ContactsContract.CommonDataKinds.Contactables.HAS_PHONE_NUMBER + " = " + 1;

        String sortBy = ContactsContract.CommonDataKinds.Contactables.LOOKUP_KEY;

        return new CursorLoader(
                context,
                uri,
                null,
                selection,
                null,
                sortBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (context == null) {
            throw new IllegalArgumentException("context");
        }

        ArrayList<Person> contacts = ((SelectContactsActivity)context).Contacts;

        if(contacts == null) {
            throw new IllegalArgumentException("contacts");
        }

        if (data.getCount() == 0) {
            return;
        }

        int nameColumnIndex = data.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DISPLAY_NAME);
        int phoneColumnIndex = data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int typeColumnIndex = data.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.MIMETYPE);
        int lookupColumnIndex = data.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.LOOKUP_KEY);

        data.moveToFirst();

        String lookupKey = "";
        String displayName = "";

        do {
            Person contact = new Person();

            String currentLookupKey = data.getString(lookupColumnIndex);
            if (!lookupKey.equals(currentLookupKey)) {
                displayName = data.getString(nameColumnIndex);
                lookupKey = currentLookupKey;
            }

            contact.setName(displayName);

            String mimeType = data.getString(typeColumnIndex);

            if (mimeType.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
               contact.setNumber(data.getString(phoneColumnIndex));

                contacts.add(contact);
            }
        } while (data.moveToNext());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
