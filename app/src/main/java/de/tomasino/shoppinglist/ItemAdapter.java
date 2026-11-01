
package de.tomasino.shoppinglist;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ArrayList<EinkaufsItem> einkaufsListe;

    // Rückruf an die MainActivity zum Speichern
    private final Runnable onListChanged;

    public ItemAdapter(
            ArrayList<EinkaufsItem> einkaufsListe,
            Runnable onListChanged
    ) {
        this.einkaufsListe = einkaufsListe;
        this.onListChanged = onListChanged;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        EinkaufsItem item = einkaufsListe.get(position);
        holder.textView.setText(item.getText());

        // Standarddarstellung wiederherstellen
        holder.textView.setPaintFlags(
                holder.textView.getPaintFlags()
                        & ~Paint.STRIKE_THRU_TEXT_FLAG
        );

        holder.textView.setTextColor(Color.WHITE);
        holder.textView.setAlpha(1.0f);

        holder.buttonDone.setAlpha(1.0f);
        holder.buttonDelete.setAlpha(1.0f);
        holder.buttonUnavailable.setAlpha(1.0f);

        // Darstellung für erledigte Artikel
        if (item.isErledigt()) {

            holder.textView.setPaintFlags(
                    holder.textView.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG
            );

            holder.textView.setAlpha(0.5f);
            holder.buttonDone.setAlpha(0.5f);
            holder.buttonDelete.setAlpha(0.5f);
            holder.buttonUnavailable.setAlpha(0.5f);
        }

        // Darstellung für nicht verfügbare Artikel
        if (item.isNichtVerfuegbar()) {

            holder.textView.setTextColor(Color.RED);

            holder.textView.setPaintFlags(
                    holder.textView.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG
            );

            if (!item.isErledigt()) {
                holder.textView.setAlpha(0.6f);
                holder.buttonDone.setAlpha(0.6f);
                holder.buttonDelete.setAlpha(0.6f);
                holder.buttonUnavailable.setAlpha(0.6f);
            }
        }

        // Artikel als erledigt markieren oder Markierung entfernen
        holder.buttonDone.setOnClickListener(v -> {

            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {

                EinkaufsItem currentItem = einkaufsListe.get(pos);

                currentItem.setErledigt(
                        !currentItem.isErledigt()
                );

                notifyItemChanged(pos);

                // Änderung dauerhaft speichern
                onListChanged.run();
            }
        });

        // Artikel als nicht verfügbar markieren
        holder.buttonUnavailable.setOnClickListener(v -> {

            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {

                EinkaufsItem currentItem = einkaufsListe.get(pos);

                currentItem.setNichtVerfuegbar(
                        !currentItem.isNichtVerfuegbar()
                );

                notifyItemChanged(pos);

                // Änderung dauerhaft speichern
                onListChanged.run();
            }
        });

        // Artikel aus der Einkaufsliste löschen
        holder.buttonDelete.setOnClickListener(v -> {

            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {

                einkaufsListe.remove(pos);
                notifyItemRemoved(pos);

                // Löschung dauerhaft speichern
                onListChanged.run();
            }
        });
    }

    @Override
    public int getItemCount() {
        return einkaufsListe.size();
    }

    // Verknüpfung der Listenelemente mit ihren Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button buttonDone;
        Button buttonUnavailable;
        Button buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewItem);
            buttonDone = itemView.findViewById(R.id.buttonDone);
            buttonUnavailable = itemView.findViewById(
                    R.id.buttonUnavailable
            );
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
