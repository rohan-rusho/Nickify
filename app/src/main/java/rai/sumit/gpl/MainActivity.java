package rai.sumit.gpl;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnGenerate, btnShare;
    private LinearLayout resultContainer;
    private TextView tvResultHint;

    private String[] prefixes = {"Pro", "Dark", "Epic", "Noob", "Shadow", "Mr", "Gamer", "Xx", "Ultra"};
    private String[] suffixes = {"99", "_XD", "007", "_YT", "_HD", "King", "X", "Master", "77", "_Pro"};

    private String selectedUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnShare = findViewById(R.id.btnShare);
        resultContainer = findViewById(R.id.resultContainer);
        tvResultHint = findViewById(R.id.tvResultHint);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                }

                resultContainer.removeAllViews(); // clear old results
                Random random = new Random();

                // Hide bottom instruction
                tvResultHint.setVisibility(View.GONE);

                // Generate 5 usernames
                for (int i = 0; i < 5; i++) {
                    String prefix = prefixes[random.nextInt(prefixes.length)];
                    String suffix = suffixes[random.nextInt(suffixes.length)];
                    int choice = random.nextInt(3);

                    String username;
                    if (choice == 0) {
                        username = prefix + name;
                    } else if (choice == 1) {
                        username = name + suffix;
                    } else {
                        username = prefix + name + suffix;
                    }

                    // Create TextView for username
                    TextView tv = new TextView(MainActivity.this);
                    tv.setText(username);
                    tv.setTextSize(20f);
                    tv.setPadding(16, 16, 16, 16);
                    tv.setBackgroundResource(R.drawable.rounded_result);
                    tv.setTextColor(getResources().getColor(android.R.color.white));
                    tv.setGravity(android.view.Gravity.CENTER);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 10, 0, 10);
                    tv.setLayoutParams(params);

                    // Fade-in animation
                    Animation fadeIn = new AlphaAnimation(0, 1);
                    fadeIn.setDuration(500);
                    tv.startAnimation(fadeIn);

                    // Tap to copy
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedUsername = tv.getText().toString();
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("username", selectedUsername);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(MainActivity.this, "Copied: " + selectedUsername, Toast.LENGTH_SHORT).show();
                            btnShare.setVisibility(View.VISIBLE);
                        }
                    });

                    resultContainer.addView(tv);
                }

                resultContainer.setVisibility(View.VISIBLE);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedUsername.isEmpty()) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my username: " + selectedUsername);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                } else {
                    Toast.makeText(MainActivity.this, "Tap a username first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
