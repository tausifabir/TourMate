package com.example.tourmatebase03;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourmatebase03.Weather.WeatherActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity implements EventAdapter.EventListener {


    private FloatingActionButton fab;
    private UserPreference userPreference;
    private RecyclerView eventRV;
    private EventAdapter adapter;
    private List<Event> eventList;
    private FirebaseDatabase efirebaseDatabase;
    private DatabaseReference eventRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_event_list);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fab = findViewById( R.id.row_fab );


        userPreference = new UserPreference(this);
        userPreference.setLoginStatus(true);

        eventRV = findViewById(R.id.recyclerViewRow);
        eventRV.setHasFixedSize(true);
        eventRV.setLayoutManager( new LinearLayoutManager( this ) );

        eventList = new ArrayList<Event>();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(user.getUid());
        DatabaseReference eventRef = userRef.child("Events");


        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Event event = dataSnapshot1.getValue(Event.class);
                    Log.e("event", "event: "+event);
                    eventList.add(event);
                }
                if(adapter == null){
                    adapter = new EventAdapter(eventList,EventListActivity.this);
                    final LinearLayoutManager llm = new LinearLayoutManager(EventListActivity.this);
                    eventRV.setLayoutManager(llm);
                    eventRV.setAdapter(adapter);
                }else{
                    adapter.swapList(eventList);
                }

                /*Log.e("eventList", "onDataChange: "+eventList.size());
                adapter = new EventAdapter(eventList, EventListActivity.this);
                eventRV.setAdapter( adapter );
                adapter.swapList(eventList);
                eventRV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(EventListActivity.this,ExpenseList.class));
                    }
                });*/


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( EventListActivity.this, "Opps!!..Something went wrong", Toast.LENGTH_SHORT ).show();
            }
        } );




    }




    public void addEvent(View view) {
        startActivity(new Intent(EventListActivity.this, AddEvent.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }




    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem islogout = menu.findItem(R.id.logout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.Map:
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show();

                break;


            case R.id.Forcast:
                Toast.makeText(this, "Forcast", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EventListActivity.this, WeatherActivity.class));
                break;




            case R.id.logout:
                //isloggedin = false;
                userPreference.setLoginStatus(false);
                startActivity(new Intent(EventListActivity.this,MainActivity.class));
                break;
        }
        return true;
    }



    @Override
    public void onEventDelete(String id) {




        /*eventRef.child(id).removeValue();*/
    }

    @Override
    public void onEventUpdate(String id) {

         EditText eventnameET,eventDestinationET,eventBudgetET;
         Button addbtn;

        eventnameET = findViewById( R.id.eventNameET );
        eventDestinationET = findViewById( R.id.eventDestinationET );
        eventBudgetET = findViewById( R.id.eventBudgetET );
        addbtn = findViewById( R.id.addEvent );
        String name = eventnameET.getText().toString();
        String destination = eventDestinationET.getText().toString();
        int budget = Integer.parseInt(eventBudgetET.getText().toString());
        Event event = new Event(id, name, destination, budget);



    }
}







