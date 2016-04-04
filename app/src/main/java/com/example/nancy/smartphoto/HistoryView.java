package com.example.nancy.smartphoto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

public class HistoryView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Logged Points");
        setSupportActionBar(toolbar);

        //Set up the cards

        //Get the list of locations
        final LocationData ld = new LocationData();
        ld.add(new Location(49.3017049, -123.1417003, "Stanley Park")); //Stanley Park
        ld.add(new Location(49.2268144, -123.0004924, "Movie Theatre"));
        ld.add(new Location(49.2606052, -123.2459939, "UBC"));
        ld.add(new Location(49.1748301,-123.1540775, "Skating Oval"));

        //Get the street view for each location
        int listImages[] = new int[]{R.drawable.camera, R.drawable.camera,
                R.drawable.camera, R.drawable.camera, R.drawable.camera};

        ArrayList<Card> cards = new ArrayList<Card>();
        int numcard = 4;

        for (int i = 0; i < numcard; i++) {
            // Create a Card
            Card card = new Card(this);


            // Create a CardHeader
            CardHeader header = new CardHeader(this);
            // Add Header to card
            header.setTitle("POI: " + i);
            card.setTitle("" + i);
            card.addCardHeader(header);

            //Add the picture
            CardThumbnail thumb = new CardThumbnail(this);
            thumb.setDrawableResource(listImages[i]);
            card.addCardThumbnail(thumb);

            //Click listener for the card
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(HistoryView.this,"Clickable card", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(HistoryView.this, MapView.class);

                    String title = card.getTitle();
                    //int liindex = Integer.parseInt(title);
                    //Toast.makeText(HistoryView.this, liindex, Toast.LENGTH_LONG).show();

                    //Location location = ld.locationdata.get(liindex);

                    //Pass the location to Map
                    //intent.putExtra("card_lat", location.getLat());
                    //intent.putExtra("card_long", location.getLong());

                    //Toast.makeText(HistoryView.this,(int)location.getLat(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(HistoryView.this, (int)location.getLong(), Toast.LENGTH_LONG).show();

                    //startActivity(intent);
                }
            });

            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, cards);

        CardListView listView = (CardListView) this.findViewById(R.id.myList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
        //End of cards

    }

    // Settings on toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SettingsView.class));
                break;
            default:
                break;
        }

        return true;
    }

}
