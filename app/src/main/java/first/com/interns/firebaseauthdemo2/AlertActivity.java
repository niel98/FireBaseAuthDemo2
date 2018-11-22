package first.com.interns.firebaseauthdemo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlertActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonDanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        buttonDanger = (Button) findViewById(R.id.buttonDanger);
        buttonDanger.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == buttonDanger){
            startActivity(new Intent(this, MapsActivity.class));
        }

    }
}
