package com.example.tourmatebase03;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {


    List<Event> eventList;
    private Context context;
    private EventListener listener;



    public EventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
        listener = (EventListener) context;


    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from( context )
                .inflate( R.layout.event_row,viewGroup,false );

        EventViewHolder eventViewHolder = new EventViewHolder( view);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {

        final Event event = eventList.get( i );

        eventViewHolder.eventName.setText( event.getName() );
        eventViewHolder.eventDestination.setText( event.getDestination() );
        eventViewHolder.eventBudget.setText( String.valueOf(event.getBudget()) );

        eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context,ExpenseList.class).putExtra("eventID",event.getId()));


            }
        });


        eventViewHolder.menuTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.event_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.item_edit:

                                break;
                            case R.id.item_delete:
                                listener.onEventDelete(event.getId());
                                break;
                        }
                        return false;
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {

        return eventList.size();
    }


    class EventViewHolder extends RecyclerView.ViewHolder  {

        TextView eventName, eventDestination, eventBudget,menuTV;


        public EventViewHolder(@NonNull View itemView) {
            super( itemView );
            eventName = itemView.findViewById( R.id.row_eventNameTV );
            eventDestination = itemView.findViewById( R.id.row_eventDestinationTV );
            eventBudget = itemView.findViewById( R.id.row_eventBudgetTV );
            menuTV = itemView.findViewById(R.id.row_menu);


        }



    }


    public void swapList(List<Event> eventList){
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    interface EventListener{
        void onEventDelete(String id);
        void onEventUpdate(String id);
    }




}