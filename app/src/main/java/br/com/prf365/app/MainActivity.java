package br.com.prf365.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private LinearLayout root;
    private TextView progressText;
    private TextView levelText;
    private ProgressBar progressBar;
    private SharedPreferences preferences;
    private int completedDays;

    private final int dark = Color.rgb(17, 24, 39);
    private final int blue = Color.rgb(37, 99, 235);
    private final int white = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("PRF365", MODE_PRIVATE);
        completedDays = preferences.getInt("completedDays", 0);

        buildScreen();
    }

    private void buildScreen() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(dark);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 30, 24, 30);

        scroll.addView(root);
        setContentView(scroll);

        addText("🦅 PRF 365", 30, white, true);
        addText("SUA JORNADA COMEÇA AQUI", 12,
                Color.LTGRAY, true);

        addSpace(25);

        addText("PAINEL DE PREPARAÇÃO", 14,
                Color.LTGRAY, true);

        addSpace(10);

        addText("365 dias para construir sua aprovação.",
                23, white, true);

        addSpace(20);

        progressText = addText("", 18, white, true);

        progressBar = new ProgressBar(
                this, null,
                android.R.attr.progressBarStyleHorizontal
        );

        progressBar.setMax(365);
        root.addView(progressBar, params());

        addSpace(15);

        levelText = addText("", 16,
                Color.rgb(96, 165, 250), true);

        updateProgress();

        addSpace(20);

        Button completeButton = new Button(this);
        completeButton.setText("MARCAR DIA COMO CONCLUÍDO");
        completeButton.setTextColor(white);
        completeButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(blue)
        );

        root.addView(completeButton, params());

        completeButton.setOnClickListener(v -> {
            if (completedDays < 365) {
                completedDays++;
                preferences.edit()
                        .putInt("completedDays", completedDays)
                        .apply();
                updateProgress();
            }
        });

        addSpace(30);

        addSection("SEU PLANO DE ESTUDOS");

        addSubject("01", "Língua Portuguesa",
                "Interpretação, gramática e redação.");

        addSubject("02", "Raciocínio Lógico",
                "Lógica, proposições e matemática.");

        addSubject("03", "Direito Constitucional",
                "Constituição Federal e direitos fundamentais.");

        addSubject("04", "Direito Administrativo",
                "Administração pública e atos administrativos.");

        addSubject("05", "Direito Penal",
                "Princípios, crimes e legislação penal.");

        addSubject("06", "Direito Processual Penal",
                "Investigação e procedimentos.");

        addSubject("07", "Legislação de Trânsito",
                "Código de Trânsito Brasileiro.");

        addSubject("08", "Informática",
                "Sistemas, redes e segurança da informação.");

        addSubject("09", "Física",
                "Mecânica e conceitos fundamentais.");

        addSubject("10", "Ética e Cidadania",
                "Ética no serviço público e cidadania.");

        addSpace(25);

        addSection("SUA MISSÃO");

        addText(
                "Estude no seu ritmo. Cada dia concluído "
                + "representa mais um passo na sua preparação.",
                16, Color.LTGRAY, false
        );

        addSpace(20);

        addText("PRF 365 | VERSÃO 1.0",
                12, Color.GRAY, true);
    }

    private void addSubject(String number,
                            String name, String description) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 16, 18, 16);
        card.setBackgroundColor(
                Color.rgb(31, 41, 55)
        );

        LinearLayout.LayoutParams cardParams = params();
        cardParams.setMargins(0, 0, 0, 12);
        root.addView(card, cardParams);

        TextView title = new TextView(this);
        title.setText(number + "  |  " + name);
        title.setTextColor(white);
        title.setTextSize(18);
        title.setTypeface(null, Typeface.BOLD);

        card.addView(title);

        TextView detail = new TextView(this);
        detail.setText(description);
        detail.setTextColor(Color.LTGRAY);
        detail.setTextSize(14);
        detail.setPadding(0, 8, 0, 0);

        card.addView(detail);
    }

    private void addSection(String title) {
        addText(title, 17,
                Color.rgb(96, 165, 250), true);
        addSpace(15);
    }

    private TextView addText(String value, int size,
                             int color, boolean bold) {

        TextView text = new TextView(this);
        text.setText(value);
        text.setTextColor(color);
        text.setTextSize(size);

        if (bold) {
            text.setTypeface(null, Typeface.BOLD);
        }

        root.addView(text);
        return text;
    }

    private void addSpace(int height) {
        View space = new View(this);
        root.addView(space,
                new LinearLayout.LayoutParams(1, height));
    }

    private LinearLayout.LayoutParams params() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private void updateProgress() {
        progressText.setText(
                "Dias concluídos: " + completedDays + " / 365"
        );

        progressBar.setProgress(completedDays);

        int percentage = (completedDays * 100) / 365;

        if (completedDays < 60) {
            levelText.setText(
                    "NÍVEL 1 | FUNDAMENTAÇÃO • " + percentage + "%"
            );
        } else if (completedDays < 180) {
            levelText.setText(
                    "NÍVEL 2 | DESENVOLVIMENTO • " + percentage + "%"
            );
        } else if (completedDays < 300) {
            levelText.setText(
                    "NÍVEL 3 | APROFUNDAMENTO • " + percentage + "%"
            );
        } else {
            levelText.setText(
                    "NÍVEL 4 | RETA FINAL • " + percentage + "%"
            );
        }
    }
                        
