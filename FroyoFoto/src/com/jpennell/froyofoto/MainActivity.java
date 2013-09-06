/*
 * project		FroyoFoto
 * 
 * package		com.jpennell.froyofoto
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 5, 2013
 */
package com.jpennell.froyofoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jpennell.library.Singleton;



// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {

    // Global Variables
    
    /** The image uri. */
    Uri imageUri;
    
    /** The value. */
    Boolean value;
    
    /** The to email. */
    TextView toEmail;
    
    /** The text subject. */
    TextView textSubject;
    
    /** The text message. */
    TextView textMessage;
    
    /** The send button. */
    Button sendButton;

    /**
     * Handle image.
     *
     * @param intent the intent
     */
    void handleImage(Intent intent) {
        imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            Log.i("IMAGEURI", String.valueOf(imageUri));

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageURI(imageUri);

            // Set Enabled
            value = true;
            setEnabled(value);

            // On Click Listener
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendEmail(imageUri);
                }
            });
        }
    }


    /**
     * Send email.
     *
     * @param imageUri the image uri
     */
    void sendEmail(Uri imageUri) {
    	//Setting singleton context
    	Singleton.getInstance().setContext(this);
    	
        String to = toEmail.getText().toString();
        String subject = textSubject.getText().toString();
        String message = textMessage.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[] {to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);
        email.putExtra(Intent.EXTRA_STREAM, imageUri);

        // Returns user back to app after the email is sent
        email.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // need this to prompts email client only
        email.setType("message/rfc822");

        try {
            startActivity(Intent.createChooser(email, "Choose an Email Client"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Singleton.getInstance().getContext(), "There are no email clients installed.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sets the enabled.
     *
     * @param value the new enabled
     */
    void setEnabled(Boolean value) {
        toEmail.setEnabled(value);
        textSubject.setEnabled(value);
        textMessage.setEnabled(value);
        sendButton.setEnabled(value);
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Setting Context
        Singleton.getInstance().setContext(this);

        // Declare Variables
        toEmail = (TextView) findViewById(R.id.address);
        textSubject = (TextView) findViewById(R.id.subject);
        textMessage = (TextView) findViewById(R.id.message);
        sendButton = (Button) findViewById(R.id.send);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleImage(intent);
            }
        } else {

            // Set Enabled
            value = false;
            setEnabled(value);

            // Alert
            AlertDialog.Builder alert = new AlertDialog.Builder(Singleton.getInstance().getContext());
            alert.setTitle("Loading Error");
            alert.setMessage("Oops...this application is to be used via the share option found in the camera app.");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alert.show();
        }
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}