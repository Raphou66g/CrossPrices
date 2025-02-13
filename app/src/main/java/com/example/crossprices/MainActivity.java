package com.example.crossprices;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout MenuA, MenuB, SoloA, SoloB;
    TextInputLayout MenuAInputLayout, MenuBInputLayout, SoloAInputLayout, SoloBInputLayout;
    List<TextInputLayout> textInputs;
    EditText menuA, menuB, soloA, soloB;
    List<EditText> editTexts;
    HashMap<EditText, TextInputLayout> textInputLayouts;
    float menuAPrice, menuBPrice, soloAPrice, soloBPrice;
    List<Float> floats;
    Button clearAll;
    TextView menuASoloB, menuBSoloA, difference;

    private void defineVars() {
        //ConstraintLayout
        MenuA = findViewById(R.id.MenuA);
        MenuB = findViewById(R.id.MenuB);
        SoloA = findViewById(R.id.SoloA);
        SoloB = findViewById(R.id.SoloB);

        //TextInputLayout
        MenuAInputLayout = findViewById(R.id.MenuAInputLayout);
        MenuBInputLayout = findViewById(R.id.MenuBInputLayout);
        SoloAInputLayout = findViewById(R.id.SoloAInputLayout);
        SoloBInputLayout = findViewById(R.id.SoloBInputLayout);

        //EditText
        menuA = findViewById(R.id.MenuAPrice);
        menuB = findViewById(R.id.MenuBPrice);
        soloA = findViewById(R.id.SoloAPrice);
        soloB = findViewById(R.id.SoloBPrice);

        //TextView
        menuASoloB = findViewById(R.id.MenuASoloB);
        menuBSoloA = findViewById(R.id.MenuBSoloA);
        difference = findViewById(R.id.Difference);

        //Button
        clearAll = findViewById(R.id.clearAll);
        buttonActions();

        //List
        editTexts = new ArrayList<>();
        editTexts.add(menuA);
        editTexts.add(menuB);
        editTexts.add(soloA);
        editTexts.add(soloB);
        textInputs = new ArrayList<>();
        textInputs.add(MenuAInputLayout);
        textInputs.add(MenuBInputLayout);
        textInputs.add(SoloAInputLayout);
        textInputs.add(SoloBInputLayout);

        //Float
        floats = new ArrayList<>();
        floats.add(menuAPrice);
        floats.add(menuBPrice);
        floats.add(soloAPrice);
        floats.add(soloBPrice);

        //HashMap
        textInputLayouts = new HashMap<>();
        textInputLayouts.put(menuA, MenuAInputLayout);
        textInputLayouts.put(menuB, MenuBInputLayout);
        textInputLayouts.put(soloA, SoloAInputLayout);
        textInputLayouts.put(soloB, SoloBInputLayout);

    }

    private void buttonActions() {
        clearAll.setOnClickListener(v -> {
            menuA.setText("");
            menuB.setText("");
            soloA.setText("");
            soloB.setText("");
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
        float mAsB = menuAPrice + soloBPrice;
        float mBsA = menuBPrice + soloAPrice;
        float dif = Math.abs(mAsB - mBsA);

        String AB = String.format("%s = %.2f", getText(R.string.menu_a_solo_b), mAsB);
        String BA = String.format("%s = %.2f", getText(R.string.menu_b_solo_a), mBsA);
        String diff = String.format("%s %.2f", getText(R.string.difference), dif);

        menuASoloB.setText(AB);
        menuBSoloA.setText(BA);
        difference.setText(diff);

        if (mAsB > mBsA) {
            return "BA";
        } else if (mAsB < mBsA) {
            return "AB";
        } else if (mAsB == mBsA && notZero()) {
            return "C";
        } else {
            return "N";
        }
    }

    private void displayCombo() {
        String comboResult = calculate();
        switch (comboResult) {
            case "BA":
                menuBSoloA.setTextColor(getResources().getColor(R.color.green, null));
                menuASoloB.setTextColor(getResources().getColor(R.color.red, null));
                difference.setTextColor(getResources().getColor(R.color.green, null));
                MenuB.setBackgroundColor(getResources().getColor(R.color.green, null));
                SoloA.setBackgroundColor(getResources().getColor(R.color.green, null));
                MenuA.setBackgroundColor(getResources().getColor(R.color.red, null));
                SoloB.setBackgroundColor(getResources().getColor(R.color.red, null));
                break;
            case "AB":
                menuBSoloA.setTextColor(getResources().getColor(R.color.red, null));
                menuASoloB.setTextColor(getResources().getColor(R.color.green, null));
                difference.setTextColor(getResources().getColor(R.color.green, null));
                MenuA.setBackgroundColor(getResources().getColor(R.color.green, null));
                SoloB.setBackgroundColor(getResources().getColor(R.color.green, null));
                MenuB.setBackgroundColor(getResources().getColor(R.color.red, null));
                SoloA.setBackgroundColor(getResources().getColor(R.color.red, null));
                break;
            case "C":
                menuBSoloA.setTextColor(getResources().getColor(R.color.cyan, null));
                menuASoloB.setTextColor(getResources().getColor(R.color.cyan, null));
                difference.setTextColor(getResources().getColor(R.color.cyan, null));
                MenuA.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                MenuB.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                SoloA.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                SoloB.setBackgroundColor(getResources().getColor(R.color.cyan, null));
                break;
            default:
                menuBSoloA.setTextColor(getResources().getColor(R.color.textColor, null));
                menuASoloB.setTextColor(getResources().getColor(R.color.textColor, null));
                difference.setTextColor(getResources().getColor(R.color.textColor, null));
                MenuA.setBackground(null);
                MenuB.setBackground(null);
                SoloA.setBackground(null);
                SoloB.setBackground(null);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        defineVars();

        textInputs.forEach(textInputLayout -> {
            textInputLayout.setEndIconVisible(true);
            textInputLayout.setEndIconOnClickListener(v -> {
                Objects.requireNonNull(textInputLayout.getEditText()).setText("");
            });
        });

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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}