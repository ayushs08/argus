package comayushs08.github.argus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UserDetails extends AppCompatActivity {

    FloatingActionButton fabStoreDetails;
    EditText etUserName, etUserBloodGroup, etUserAddress, etUserConditions, etUserMedication;
    private String userName, userBloodGroup, userAddress, userConditions, userMedication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        etUserName = findViewById(R.id.userName);
        etUserBloodGroup = findViewById(R.id.userBloodGroup);
        etUserConditions = findViewById(R.id.userConditions);
        etUserMedication = findViewById(R.id.userMedications);
        etUserAddress = findViewById(R.id.userAddress);

        userName = etUserName.getText().toString();
        userBloodGroup = etUserBloodGroup.getText().toString();
        userConditions = etUserConditions.getText().toString();
        userMedication = etUserMedication.getText().toString();
        userAddress = etUserAddress.getText().toString();

        fabStoreDetails = findViewById(R.id.fabStoreDetails);

        fabStoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", userName);
                editor.putString("userBloodGroup", userBloodGroup);
                editor.putString("userConditions", userConditions);
                editor.putString("userMedication", userMedication);
                editor.putString("userAddress", userAddress);
                editor.apply();
                Toast.makeText(UserDetails.this, "Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UserDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
