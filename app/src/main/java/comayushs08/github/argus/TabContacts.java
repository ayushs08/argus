package comayushs08.github.argus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class TabContacts extends Fragment {

    final int pickerResult = 2015;
    ListView listView;
    ArrayList<Contacts> contactsList = new ArrayList<>();
    ContactsAdapter contactsAdapter;
    FloatingActionButton fab;
    TextView helperText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        fab = rootView.findViewById(R.id.fab);
        listView = rootView.findViewById(R.id.listView);
        helperText = rootView.findViewById(R.id.helperText);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(pickContact, pickerResult);
            }
        });

//        contactsList.add(new Contacts("Username", "9876543210"));

        hideHelper();

        contactsAdapter = new ContactsAdapter(TabContacts.this.getContext(), contactsList);

        listView.setAdapter(contactsAdapter);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == pickerResult && resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContext().getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String name = cursor.getString(nameIndex);
                String phone = cursor.getString(phoneIndex);
//                Toast.makeText(TabContacts.this.getContext(), name + " " + phone, Toast.LENGTH_SHORT).show();
                contactsList.add(new Contacts(name, phone));
                contactsAdapter.notifyDataSetChanged();
                hideHelper();
                hideFab();
        }

    }


    private void hideHelper() {
        if (!contactsList.isEmpty()) {
            helperText.setVisibility(View.INVISIBLE);
        }
    }

    private void hideFab() {
        if (contactsList.size() >= 4) {
            fab.hide();
        }
    }


}