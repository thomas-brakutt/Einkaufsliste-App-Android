package de.tomasino.shoppinglist;

import android.content.res.ColorStateList;
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

    private ArrayList<EinkaufsItem> einkaufsListe;

    public ItemAdapter(ArrayList<EinkaufsItem> einkaufsListe) {
        this.einkaufsListe = einkaufsListe;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EinkaufsItem item = einkaufsListe.get(position);
        holder.textView.setText(item.getText());

        // Standardzustand zurücksetzen
        holder.textView.setPaintFlags(holder.textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        holder.textView.setTextColor(Color.WHITE);
        holder.textView.setAlpha(1.0f);
        holder.buttonDone.setAlpha(1.0f);
        holder.buttonDelete.setAlpha(1.0f);
        holder.buttonUnavailable.setAlpha(1.0f);

        // erledigt-Status
        if (item.isErledigt()) {
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView.setAlpha(0.5f);
            holder.buttonDone.setAlpha(0.5f);
            holder.buttonDelete.setAlpha(0.5f);
            holder.buttonUnavailable.setAlpha(0.5f);
        }

        // nicht verfügbar-Status
        if (item.isNichtVerfuegbar()) {
            holder.textView.setTextColor(Color.RED);
            holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (!item.isErledigt()) {
                holder.textView.setAlpha(0.6f);
                holder.buttonDone.setAlpha(0.6f);
                holder.buttonDelete.setAlpha(0.6f);
                holder.buttonUnavailable.setAlpha(0.6f);
            }
        }

        // Button Klicks
        holder.buttonDone.setOnClickListener(v -> {
            item.setErledigt(!item.isErledigt());
            notifyItemChanged(position);
        });

        holder.buttonUnavailable.setOnClickListener(v -> {
            item.setNichtVerfuegbar(!item.isNichtVerfuegbar());
            notifyItemChanged(position);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                einkaufsListe.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }


    @Override
    public int getItemCount() {
        return einkaufsListe.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button buttonDone, buttonUnavailable, buttonDelete;
        ColorStateList defaultTextColors; // <- neu

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewItem);
            buttonDone = itemView.findViewById(R.id.buttonDone);
            buttonUnavailable = itemView.findViewById(R.id.buttonUnavailable);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

            // ursprüngliche Textfarbe merken (z. B. weiß oder schwarz je nach Theme)
            defaultTextColors = textView.getTextColors();
        }
    }

}
