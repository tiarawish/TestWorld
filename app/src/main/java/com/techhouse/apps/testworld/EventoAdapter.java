package com.techhouse.apps.testworld;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class EventoAdapter extends FirestoreRecyclerAdapter<Evento, EventoAdapter.EventoHolder> {

    private onItemClickListener listener;

    public EventoAdapter(@NonNull FirestoreRecyclerOptions<Evento> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventoHolder holder, int position, @NonNull Evento model) {
        holder.textViewTitle.setText(model.getsName());
        holder.textViewDate.setText(model.getsDate());
        holder.textViewDescription.setText(model.getsDescription());
    }

    @NonNull
    @Override
    public EventoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item_layout, viewGroup, false);
        return new EventoHolder(v);
    }

    class EventoHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewDate;

        public EventoHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title2);
            textViewDescription = itemView.findViewById(R.id.text_view_description2);
            textViewDate = itemView.findViewById(R.id.text_view_priority2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface  onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int poistion);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
