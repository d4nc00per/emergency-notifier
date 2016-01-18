package xyz.danielcooper.emergencynotifier;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Created by Dan on 03/01/2016.
 */
public class SelectContactsActivity extends AppCompatActivity {

    public static final int CONTACT_QUERY_LOADER = 0;
    public static final String QUERY_KEY = "QueryKey";
    public ArrayList<Person> Contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_contacts);

//        PermissionHelper.checkAndRequest(this, Manifest.permission.READ_CONTACTS);

        SetupContactList();

        SetupSearchBar();
    }

    private void SetupContactList() {

        ListView contactList = (ListView)findViewById(R.id.list);

        ContactListAdapter adapter = new ContactListAdapter(
                this,
                this.Contacts,
                LayoutInflater.from(getApplicationContext()));

        contactList.setAdapter(adapter);
    }

    private void SetupSearchBar() {
        Intent intent = getIntent();

        if (intent != null && intent.getAction().equals(Intent.ACTION_SEARCH))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Bundle bundle = new Bundle();
            bundle.putString(QUERY_KEY, query);

            ContactsLoader contactLoader = new ContactsLoader(this);

            getLoaderManager().restartLoader(CONTACT_QUERY_LOADER, bundle, contactLoader);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_contact_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

//    @Override
//    public void onRequestPermissionsResult(
//            int requestCode,
//            String permissions[],
//            int[] grantResults) {
//        switch (requestCode) {
//            case PermissionHelper.READ_CONTACTS_REQUEST: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
}
