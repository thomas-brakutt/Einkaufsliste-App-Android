package de.tomasino.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView editTextItem; // Eingabefeld mit Vorschlägen
    private Button buttonAdd;  // Button zum Hinzufügen
    private RecyclerView recyclerViewItems;  // RecyclerView für die Einkaufsliste

    private ArrayList<EinkaufsItem> einkaufsListe; // Datenquelle für RecyclerView
    private ItemAdapter adapter; // Adapter für RecyclerView

    private ArrayList<String> vorschlaege; // Liste der Vorschläge
    private ArrayAdapter<String> autoAdapter; // Adapter für AutoCompleteTextView

    private SharedPreferences prefs; // Speichert Einkaufsliste und Artikelvorschläge dauerhaft
    private static final String PREFS_NAME = "ShoppingPrefs";
    private static final String KEY_VORSCHLAEGE = "Vorschlaege";
    private static final String KEY_EINKAUFSLISTE = "Einkaufsliste";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar einrichten
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Views verknüpfen
        editTextItem = findViewById(R.id.editTextItem);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);

        // SharedPreferences laden
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> gespeicherteVorschlaege = prefs.getStringSet(KEY_VORSCHLAEGE, new HashSet<>());
        vorschlaege = new ArrayList<>(gespeicherteVorschlaege);

        // AutoCompleteTextView Adapter setzen
        autoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, vorschlaege);
        editTextItem.setAdapter(autoAdapter);
        editTextItem.setThreshold(1); // ab 1 Buchstaben Vorschläge anzeigen

        // Liste vorbereiten
        einkaufsListe = ladeEinkaufsliste();

// RecyclerView einrichten
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter(
                einkaufsListe,
                this::speichereEinkaufsliste
        );

        recyclerViewItems.setAdapter(adapter);


        // Button-Klick → neuen Eintrag hinzufügen
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = editTextItem.getText().toString().trim();
                if (!itemText.isEmpty()) {
                    // Neues EinkaufsItem hinzufügen
                    EinkaufsItem neuesItem = new EinkaufsItem(itemText);
                    einkaufsListe.add(neuesItem);
                    adapter.notifyItemInserted(einkaufsListe.size() - 1);
                    speichereEinkaufsliste();

                    // Vorschläge aktualisieren
                    if (!vorschlaege.contains(itemText)) {
                        vorschlaege.add(itemText);
                        autoAdapter.notifyDataSetChanged();

                        // Vorschläge dauerhaft speichern
                        SharedPreferences.Editor editor = prefs.edit();
                        Set<String> set = new HashSet<>(vorschlaege);
                        editor.putStringSet(KEY_VORSCHLAEGE, set);
                        editor.apply();
                    }

                    // Eingabefeld leeren
                    editTextItem.setText("");
                }
            }
        });
    }

    private void speichereEinkaufsliste() {
        JSONArray daten = new JSONArray();

        for (EinkaufsItem item : einkaufsListe) {
            JSONObject eintrag = new JSONObject();

            try {
                eintrag.put("text", item.getText());
                eintrag.put("erledigt", item.isErledigt());
                eintrag.put("nichtVerfuegbar", item.isNichtVerfuegbar());

                daten.put(eintrag);
            } catch (JSONException e) {
                Log.e("Einkaufsliste", "Fehler beim Speichern", e);
            }
        }

        prefs.edit()
                .putString(KEY_EINKAUFSLISTE, daten.toString())
                .apply();
    }

    private ArrayList<EinkaufsItem> ladeEinkaufsliste() {
        ArrayList<EinkaufsItem> geladeneListe = new ArrayList<>();

        String gespeichert = prefs.getString(
                KEY_EINKAUFSLISTE, "[]"
        );

        try {
            JSONArray daten = new JSONArray(gespeichert);

            for (int i = 0; i < daten.length(); i++) {
                JSONObject eintrag = daten.getJSONObject(i);

                String text = eintrag.optString("text", "").trim();

                if (text.isEmpty()) {
                    continue;
                }

                EinkaufsItem item = new EinkaufsItem(text);

                item.setErledigt(
                        eintrag.optBoolean("erledigt", false)
                );

                item.setNichtVerfuegbar(
                        eintrag.optBoolean("nichtVerfuegbar", false)
                );

                geladeneListe.add(item);
            }
        } catch (JSONException e) {
            Log.e("Einkaufsliste", "Fehler beim Laden", e);
        }

        return geladeneListe;
    }

}
