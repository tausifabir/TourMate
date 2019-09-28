package com.example.tourmatebase03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private ArrayList<Event> eventList;
    private Context context;

    public EventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;

    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_row, viewGroup, false);
        return new EventViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        final Event event = eventList.get( i );
        eventViewHolder.eventName.setText( event.getName() );
        eventViewHolder.eventDestination.setText( event.getDestination() );
        eventViewHolder.eventBudget.setText( event.getBudget() );

    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventName,eventDestination,eventBudget;

        public EventViewHolder(@NonNull View itemView) {
            super( itemView );
            eventName = itemView.findViewById( R.id.row_eventNameTV );
            eventDestination = itemView.findViewById( R.id.row_eventDestinationTV );
            eventBudget = itemView.findViewById( R.id.row_eventBudgetTV );


        }
    }


}
