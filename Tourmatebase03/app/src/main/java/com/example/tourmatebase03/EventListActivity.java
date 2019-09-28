package com.example.tourmatebase03;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {


    private FloatingActionButton fab;
    private UserPreference userPreference;
    private RecyclerView eventRV;
    private EventAdapter adapter;
    private ArrayList<Event> eventList;
    private FirebaseDatabase efirebaseDatabase;
    DatabaseReference edatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_event_list2 );

        fab = findViewById( R.id.row_fab );
        eventList = new ArrayList<Event>(  );

        userPreference = new UserPreference(this);
        userPreference.setLoginStatus(true);

        eventRV = findViewById(R.id.recyclerViewRow);
        eventRV.setHasFixedSize(true);
        eventRV.setLayoutManager( new LinearLayoutManager( this ) );

        edatabaseReference = efirebaseDatabase.getInstance().getReference().child( "Events" );

        edatabaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Event event = dataSnapshot.getValue(Event.class);
                    eventList.add(event);
                }
                adapter = new EventAdapter(EventListActivity.this,eventList);
                eventRV.setAdapter( adapter );


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( EventListActivity.this, "Opps!!..Something went wrong", Toast.LENGTH_SHORT ).show();
            }
        } );



    }


    public void addEvent(View view) {

        startActivity(new Intent(EventListActivity.this, EventList.class));
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

            case R.id.SrRandomMonth:
                Toast.makeText(this, "Search By Month", Toast.LENGTH_SHORT).show();
                break;

            case R.id.SrRandomYear:
                Toast.makeText(this, "Search By Year", Toast.LENGTH_SHORT).show();
                break;

            case R.id.SrCurrentMonth:
                Toast.makeText(this, "Search By Current Month", Toast.LENGTH_SHORT).show();
                break;

            case R.id.SrCurrentYear:
                Toast.makeText(this, "Search By Current Month", Toast.LENGTH_SHORT).show();
                break;
            case R.id.totalReport:
                Toast.makeText(this, "Search By Total Report", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                //isloggedin = false;
                userPreference.setLoginStatus(false);
                startActivity(new Intent(EventListActivity.this,MainActivity.class));
                break;
        }
        return true;
    }


 }





