package xyz.danielcooper.emergencynotifier;


import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

/**
 * Created by Dan on 11/01/2016.
 */
public class ContactsLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    Context context;

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

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
