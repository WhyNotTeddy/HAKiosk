package com.ha.kiosk;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

public class MainActivity extends AppCompatActivity {

    private GeckoView geckoView;
    private GeckoSession geckoSession;
    private static GeckoRuntime geckoRuntime;
    private String currentUrl;
    private TextView statusText;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideSystemUI();
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        geckoView = findViewById(R.id.geckoview);
        geckoView.setVisibility(View.GONE);

        SharedPreferences prefs = getSharedPreferences("HA_PREFS", MODE_PRIVATE);
        currentUrl = prefs.getString("ha_url", "http://192.168.0.37:8123");

        // Initialisation GeckoView
        geckoSession = new GeckoSession();
        if (geckoRuntime == null) {
            geckoRuntime = GeckoRuntime.create(this);
        }
        geckoSession.open(geckoRuntime);
        geckoView.setSession(geckoSession);

        // --- DÉMARRAGE DU COMPTE À REBOURS ---
        startCountdown();

        findViewById(R.id.btnSettings).setOnClickListener(v -> {
            if (countDownTimer != null) countDownTimer.cancel(); // On stoppe le chrono
            showUrlDialog();
        });
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                // On met à jour le texte chaque seconde
                int secondsLeft = (int) (millisUntilFinished / 1000);
                statusText.setText("Lancement dans " + secondsLeft + "s...");
            }

            @Override
            public void onFinish() {
                // Action finale : on lance Home Assistant
                launchHomeAssistant();
            }
        }.start();
    }

    private void launchHomeAssistant() {
        statusText.setVisibility(View.GONE);
        geckoView.setVisibility(View.VISIBLE);
        geckoSession.loadUri(currentUrl);
    }

    private void showUrlDialog() {
        final EditText input = new EditText(this);
        input.setText(currentUrl);
        new AlertDialog.Builder(this)
                .setTitle("Réglages")
                .setView(input)
                .setPositiveButton("Enregistrer et Lancer", (d, w) -> {
                    currentUrl = input.getText().toString();
                    getSharedPreferences("HA_PREFS", MODE_PRIVATE).edit().putString("ha_url", currentUrl).apply();
                    launchHomeAssistant();
                })
                .setNegativeButton("Annuler", (d, w) -> {
                    hideSystemUI();
                    // Optionnel : on peut relancer le chrono ici si on veut
                })
                .show();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideSystemUI();
    }
}