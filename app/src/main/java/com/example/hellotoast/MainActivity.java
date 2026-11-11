package com.example.hellotoast;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hellotoast.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Key used to keep the count when the screen rotates
    private static final String KEY_COUNT = "count";

    private int count = 0;

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Restore the count after a rotation (the activity is recreated)
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(KEY_COUNT, 0);
        }
        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNT, count);
    }

    // Toast button: shows a short message
    public void show_toast(View view) {
        Toast.makeText(this, "Hello Toast", Toast.LENGTH_SHORT).show();
    }

    // Count button: adds 1 to the count
    public void increase_count(View view) {
        count++;
        updateUI();
    }

    // Zero button: resets the count to 0
    public void show_zero(View view) {
        count = 0;
        updateUI();
    }

    // Shows the current count and sets the button colors to match it
    private void updateUI() {
        mainBinding.textDisplayCount.setText(String.valueOf(count));

        // Count button: green when the count is odd, blue when it is even
        int countColor = (count % 2 != 0) ? R.color.green : R.color.blue;
        mainBinding.buttonCount.setBackgroundColor(ContextCompat.getColor(this, countColor));

        // Zero button: gray when the count is 0, orange (active) otherwise
        int zeroColor = (count == 0) ? R.color.gray : R.color.orange;
        mainBinding.buttonZero.setBackgroundColor(ContextCompat.getColor(this, zeroColor));
    }
}
