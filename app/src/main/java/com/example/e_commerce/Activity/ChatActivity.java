package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_commerce.Adapter.MessageAdapter;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.Model.Messages;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;





public class ChatActivity extends AppCompatActivity {

    private ImageView backButton;

    private String messageReceiverEmail = "admin@gmai.com";
    private String messageReceiverID = "Nmr4dlivPsPUAGvNn9KvJtsKIgF3";

    private String messageSenderEmail, messageSenderID;
    public ArrayList<Messages> messagesList;




    private TextView userName, userLastSeen;
    private CircleImageView userImage;

    private Toolbar ChatToolBar;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;



    private ImageButton SendMessageButton;
    private EditText MessageInputText;



    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;


    private String saveCurrentTime, saveCurrentDate;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();


        RootRef = FirebaseDatabase.getInstance().getReference();
        messageSenderEmail =  getIntent().getStringExtra("UserEmail");

        messagesList = new ArrayList<Messages>();

        InitializeControllers();


        addEventBackButton();


        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendMessage();
            }
        });

    }

    private void InitializeControllers() {

//        ChatToolBar = (Toolbar) findViewById(R.id.chat_toolbar);
//        setSupportActionBar(ChatToolBar);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowCustomEnabled(true);


        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar, null);
//        actionBar.setCustomView(actionBarView);

        userName = (TextView) findViewById(R.id.custom_profile_name);
        userLastSeen = (TextView) findViewById(R.id.custom_user_last_seen);
        userImage = (CircleImageView) findViewById(R.id.custom_profile_image);

        SendMessageButton = (ImageButton) findViewById(R.id.send_message_btn);
        MessageInputText = (EditText) findViewById(R.id.input_message);

        messageAdapter = new MessageAdapter(messagesList);
        userMessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());
    }





    //    private void DisplayLastSeen()
//    {
//        RootRef.child("Users").child(messageReceiverID)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot)
//                    {
//                        if (dataSnapshot.child("userState").hasChild("state"))
//                        {
//                            String state = dataSnapshot.child("userState").child("state").getValue().toString();
//                            String date = dataSnapshot.child("userState").child("date").getValue().toString();
//                            String time = dataSnapshot.child("userState").child("time").getValue().toString();
//
//                            if (state.equals("online"))
//                            {
//                                userLastSeen.setText("online");
//                            }
//                            else if (state.equals("offline"))
//                            {
//                                userLastSeen.setText("Last Seen: " + date + " " + time);
//                            }
//                        }
//                        else
//                        {
//                            userLastSeen.setText("offline");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//    }
//
    @Override
    public void onStart()
    {
        super.onStart();

        RootRef.child("Messages").child(messageSenderID).child(messageReceiverID)
                .addChildEventListener(new ChildEventListener() {

                                           @Override
                                           public void onChildAdded(DataSnapshot dataSnapshot, String s)
                                           {

                                               Messages messages = dataSnapshot.getValue(Messages.class);

                                               messagesList.add(messages);

                                               messageAdapter.notifyDataSetChanged();

                                               userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());


                                           }

                                           @Override
                                           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                           }

                                           @Override
                                           public void onChildRemoved(DataSnapshot dataSnapshot) {

                                           }

                                           @Override
                                           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {

                                           }

                                       }
                );
    }
    //
//
//
    private void addEventBackButton() {
        ImageView backButton = (ImageView) findViewById(R.id.back_arrow_chat);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //
    private void SendMessage()
    {
        String messageText = MessageInputText.getText().toString();


        if (TextUtils.isEmpty(messageText))
        {
            Toast.makeText(this, "first write your message...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String messageSenderRef = "Messages/" + messageSenderID + "/" + messageReceiverID;
            String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderID;



            DatabaseReference userMessageKeyRef = RootRef.child("Messages")
                    .child(messageSenderID).child(messageReceiverID).push();



            String messagePushID = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);
            messageTextBody.put("to", messageReceiverID);
            messageTextBody.put("messageID", messagePushID);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);


            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
            messageBodyDetails.put( messageReceiverRef + "/" + messagePushID, messageTextBody);



            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ChatActivity.this, "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    MessageInputText.setText("");
                }
            });
        }
    }

}