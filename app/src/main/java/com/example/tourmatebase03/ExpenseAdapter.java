package com.example.tourmatebase03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    List<Event> eventList;
    private Context context;



    public ExpenseAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;



    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( context )
                .inflate( R.layout.expense_row,viewGroup,false );

        ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder( view);
        return expenseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder expenseViewHolder, int i) {

        final Event event = eventList.get( i );

        expenseViewHolder.expenseTV.setText( event.getName() );
        expenseViewHolder.dateTV.setText( event.getDestination() );
        expenseViewHolder.expensemenuTV.setText( String.valueOf(event.getBudget()) );


        expenseViewHolder.expensemenuTV.setOnClickListener(new View.OnClickListener() {
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

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView expenseTV, dateTV,expensemenuTV;


        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseTV = itemView.findViewById( R.id.Expenserow_amount );
            dateTV = itemView.findViewById( R.id.Expenserow_date );
            expensemenuTV = itemView.findViewById( R.id.Expenserow_menu );

        }
    }
}
