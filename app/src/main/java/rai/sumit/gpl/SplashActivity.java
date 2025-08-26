package rai.sumit.gpl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView appNameText;
    private String appName = "Nickify"; // Change this to your app name
    private int index = 0;
    private Handler handler = new Handler();
    private long delay = 150; // typing speed (ms)

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            if (index <= appName.length()) {
                appNameText.setText(appName.substring(0, index));
                index++;
                handler.postDelayed(this, delay);
            } else {
                // Delay before moving to MainActivity
                handler.postDelayed(() -> {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appNameText = findViewById(R.id.app_name);

        // Start typing animation
        handler.postDelayed(characterAdder, delay);
    }
}
