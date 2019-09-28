package com.example.tourmatebase03;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventList extends AppCompatActivity {
    private static final String TAG = EventList.class.getSimpleName();

    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private FirebaseUser currentUser;
    private DatabaseReference eventRef;
    private List<Event> eventList = new ArrayList<>();


    private EditText eventnameET,eventDestinationET,eventBudgetET;
    private Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_event_list );

        eventnameET = findViewById( R.id.eventNameET );
        eventDestinationET = findViewById( R.id.eventDestinationET );
        eventBudgetET = findViewById( R.id.eventBudgetET );
        addbtn = findViewById( R.id.addEvent );

        rootRef = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userRef = rootRef.child(currentUser.getUid());
        eventRef = userRef.child("Events");

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Event event = d.getValue(Event.class);
                    eventList.add(event);
                }

                for (Event e : eventList){
                    Log.e(TAG, "name: "+e.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void addEvent(View view) {

        String name = eventnameET.getText().toString();
        String destination = eventDestinationET.getText().toString();
        int budget = Integer.parseInt(eventBudgetET.getText().toString());
        String id = eventRef.push().getKey();
        Event event = new Event(id, name, destination, budget);

        Event.eventList.add(event);

        startActivity(new Intent(EventList.this,EventListActivity.class));
        eventRef.child(id).setValue(event);
    }
}
