package comayushs08.github.argus;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabHome extends Fragment {

    SharedPreferences sharedPreferences;

    TextView tvUserName, tvUserBloodGroup, tvUserConditions, tvUserMedications, tvUserAddress;
    String mUsername = "Name: ", mUserBloodGroup = "Blood Group: ", mUserConditions = "Medical Conditions: ",
            mUserMedications = "On-going Medications: ", mUserAddress = "Address: ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        tvUserName = rootView.findViewById(R.id.tvUserName);
        tvUserBloodGroup = rootView.findViewById(R.id.tvUserBloodGroup);
        tvUserConditions = rootView.findViewById(R.id.tvUserConditions);
        tvUserMedications = rootView.findViewById(R.id.tvUserMedicaitons);
        tvUserAddress = rootView.findViewById(R.id.tvUserAddress);

        mUsername += sharedPreferences.getString("userName", "");
        mUserBloodGroup += sharedPreferences.getString("userBloodGroup", "");
        mUserConditions += sharedPreferences.getString("userConditions", "");
        mUserMedications += sharedPreferences.getString("userConditions", "");
        mUserAddress += sharedPreferences.getString("userAddress", "");

        tvUserName.setText(mUsername);
        tvUserBloodGroup.setText(mUserBloodGroup);
        tvUserConditions.setText(mUserConditions);
        tvUserMedications.setText(mUserConditions);
        tvUserAddress.setText(mUserAddress);

        return rootView;
    }
}