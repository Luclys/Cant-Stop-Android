package com.luclys.cantstopmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.luclys.cantstopmobile.R;
import com.luclys.cantstopmobile.adapters.AdapterDicesPathChoice;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listViewDiceOptions = findViewById(R.id.diceOptions);

        // Temp Data to test
        ArrayList<Integer> listDiceRoll = new ArrayList<>();
        listDiceRoll.add(6);
        listDiceRoll.add(1);
        listDiceRoll.add(2);
        listDiceRoll.add(3);
        ArrayList<Integer> climberPos = new ArrayList<>();
        climberPos.add(2);
        climberPos.add(12);
        climberPos.add(6);

        listViewDiceOptions.setAdapter(new AdapterDicesPathChoice(getApplicationContext(), listDiceRoll, climberPos));
    }
}
