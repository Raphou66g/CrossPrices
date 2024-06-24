package com.example.crossprices;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout MenuA, MenuB, SoloA, SoloB;
    EditText menuA, menuB, soloA, soloB;
    List<EditText> editTexts;
    float menuAPrice, menuBPrice, soloAPrice, soloBPrice;
    List<Float> floats;
    Button clearMenuA, clearMenuB, clearSoloA, clearSoloB, clearAll;
    TextView combo;

    private void defineVars() {
        //ConstraintLayout
        MenuA = findViewById(R.id.MenuA);
        MenuB = findViewById(R.id.MenuB);
        SoloA = findViewById(R.id.SoloA);
        SoloB = findViewById(R.id.SoloB);

        //EditText
        menuA = findViewById(R.id.MenuAPrice);
        menuB = findViewById(R.id.MenuBPrice);
        soloA = findViewById(R.id.SoloAPrice);
        soloB = findViewById(R.id.SoloBPrice);
        combo = findViewById(R.id.Combo);

        //Button
        clearMenuA = findViewById(R.id.clearMenuA);
        clearMenuB = findViewById(R.id.clearMenuB);
        clearSoloA = findViewById(R.id.clearSoloA);
        clearSoloB = findViewById(R.id.clearSoloB);
        clearAll = findViewById(R.id.clearAll);
        buttonActions();

        //List
        editTexts = new ArrayList<>();
        editTexts.add(menuA);
        editTexts.add(menuB);
        editTexts.add(soloA);
        editTexts.add(soloB);

        //Float
        floats = new ArrayList<>();
        floats.add(menuAPrice);
        floats.add(menuBPrice);
        floats.add(soloAPrice);
        floats.add(soloBPrice);

    }

    private void buttonActions() {
        clearMenuA.setOnClickListener(v -> {
            menuA.setText("");
            displayCombo();
        });
        clearMenuB.setOnClickListener(v -> {
            menuB.setText("");
            displayCombo();
            });
        clearSoloA.setOnClickListener(v -> {
            soloA.setText("");
            displayCombo();
        });
        clearSoloB.setOnClickListener(v -> {
            soloB.setText("");
            displayCombo();
        });
        clearAll.setOnClickListener(v -> {
            menuA.setText("");
            menuB.setText("");
            soloA.setText("");
            soloB.setText("");
            displayCombo();
            combo.setText("");
        });
    }

    private void zeroed() {
        if (menuA.getText().toString().isEmpty()) {
            menuAPrice = 0;
        } else {
            menuAPrice = Float.parseFloat(menuA.getText().toString());
        }

        if (menuB.getText().toString().isEmpty()) {
            menuBPrice = 0;
        } else {
            menuBPrice = Float.parseFloat(menuB.getText().toString());
        }

        if (soloA.getText().toString().isEmpty()) {
            soloAPrice = 0;
        } else {
            soloAPrice = Float.parseFloat(soloA.getText().toString());
        }

        if (soloB.getText().toString().isEmpty()) {
            soloBPrice = 0;
        } else {
            soloBPrice = Float.parseFloat(soloB.getText().toString());
        }
    }

    private boolean notZero() {
        return menuAPrice != 0 && menuBPrice != 0 && soloAPrice != 0 && soloBPrice != 0;
    }

    private String calculate() {
        zeroed();
        if (menuAPrice + soloBPrice > menuBPrice + soloAPrice) {
            return "BA";
        } else if (menuAPrice + soloBPrice < menuBPrice + soloAPrice) {
            return "AB";
        } else if (menuAPrice + soloBPrice == menuBPrice + soloAPrice && notZero()) {
            return "C";
        } else {
            return "N";
        }
    }

    private void displayCombo() {
        String comboResult = calculate();
        switch (comboResult) {
            case "BA":
                combo.setText(R.string.menu_b_solo_a);
                MenuB.setBackgroundColor(getResources().getColor(R.color.green, null));
                SoloA.setBackgroundColor(getResources().getColor(R.color.green, null));
                MenuA.setBackgroundColor(getResources().getColor(R.color.red, null));
                SoloB.setBackgroundColor(getResources().getColor(R.color.red, null));
                break;
            case "AB":
                combo.setText(R.string.menu_a_solo_b);
                MenuA.setBackgroundColor(getResources().getColor(R.color.green, null));
                SoloB.setBackgroundColor(getResources().getColor(R.color.green, null));
                MenuB.setBackgroundColor(getResources().getColor(R.color.red, null));
                SoloA.setBackgroundColor(getResources().getColor(R.color.red, null));
                break;
            case "C":
                combo.setText(R.string.no_combo);
                MenuA.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                MenuB.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                SoloA.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                SoloB.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                break;
            default:
                combo.setText("");
                MenuA.setBackgroundColor(getResources().getColor(R.color.back, null));
                MenuB.setBackgroundColor(getResources().getColor(R.color.back, null));
                SoloA.setBackgroundColor(getResources().getColor(R.color.back, null));
                SoloB.setBackgroundColor(getResources().getColor(R.color.back, null));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        defineVars();

        editTexts.forEach(editText -> editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(".") || s.toString().startsWith(",")) {
                    s.insert(0, "0");
                }
                displayCombo();
            }
        }));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}