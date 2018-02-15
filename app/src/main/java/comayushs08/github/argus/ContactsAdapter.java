package comayushs08.github.argus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayush on 16/11/17.
 */

public class ContactsAdapter extends ArrayAdapter<Contacts> {

    public ContactsAdapter(@NonNull Context context, ArrayList<Contacts> contactsList) {
        super(context, 0, contactsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.contact_row, parent, false);
        }

        Contacts currentContact = getItem(position);

        TextView userName = listItemView.findViewById(R.id.userName);
        userName.setText(currentContact.getContactName());

        TextView userPhone = listItemView.findViewById(R.id.userPhone);
        userPhone.setText(currentContact.getContactPhone());


        return listItemView;

    }
}
