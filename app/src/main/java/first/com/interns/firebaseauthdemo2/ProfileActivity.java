package first.com.interns.firebaseauthdemo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogOut;
    private TextView textviewSkip;

    private DatabaseReference databaseReference;

    private EditText editTextName;
    private EditText editTextPhone;
    private Button buttonSave;

    public int click = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Contacts");

        editTextName = (EditText) findViewById(R.id.editTextname);

        editTextPhone = (EditText) findViewById(R.id.editTextphone);

        buttonSave = (Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Welcome " + user.getEmail());

        textviewSkip = (TextView) findViewById(R.id.textviewSkip);

        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);

        buttonLogOut.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        textviewSkip.setOnClickListener(this);

    }

    private void saveContacts(){
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        //new code
        if (!TextUtils.isEmpty(name)){

            ContactsInfo contactsInfo = new ContactsInfo(name, phone);

            FirebaseUser user = firebaseAuth.getCurrentUser();

            databaseReference.child(user.getUid()).setValue(contactsInfo);

            Toast.makeText(this, "Contact Information saved successfully...", Toast.LENGTH_LONG).show();

        }

        else {

            Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(phone)){

            Toast.makeText(this, "Phone number cannot be blank", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View view) {

        if (view == buttonLogOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == buttonSave){
            saveContacts();
            click++;
        }

        //if the user saves five contacts, go to the alert activity
        if (click == 5){
            startActivity(new Intent(this, AlertActivity.class));
            finish();
        }

       if (view == textviewSkip){
            startActivity(new Intent(this, AlertActivity.class));
            finish();
       }

    }
}
