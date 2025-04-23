package com.example.chatbot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText searchEditText;
    TextView chatbotResponse;
    Button getAdviceButton, brainstormButton, moreButton, sendButton;
    String[] hints = {
            "Ask anything...",
            "Search about Java",
            "How to fix an error?",
            "Find coding help",
            "Get AI suggestions"
    };
    int hintIndex = 0;
    Handler handler = new Handler();
    Runnable hintChanger;

    FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        searchEditText = findViewById(R.id.searchEditText);
        getAdviceButton=findViewById(R.id.getAdviceButton);
        brainstormButton=findViewById(R.id.brainstormButton);
        moreButton=findViewById(R.id.moreButton);
        sendButton=findViewById(R.id.sendButton);
        chatbotResponse = findViewById(R.id.chatbotResponse);



//        hintChanger = new Runnable() {
//            @Override
//            public void run() {
//                hintIndex = (hintIndex + 1) % hints.length;
//                searchEditText.setHint(hints[hintIndex]);
//                handler.postDelayed(this, 3000);
//            }
//        };

//        handler.postDelayed(hintChanger, 3000);
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        handler.removeCallbacks(hintChanger);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = searchEditText.getText().toString().trim();
                if (!message.isEmpty()) {
                    new Thread(() -> {
                        String reply = ClientChatbot.sendMessageToChatbot(message);
                        runOnUiThread(() -> chatbotResponse.setText(reply));
                    }).start();
                }
            }
        });

        getAdviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("Get Advice");
                getAdviceButton.setVisibility(View.GONE);
                brainstormButton.setVisibility(View.GONE);
                moreButton.setVisibility(View.GONE);
            }
        });

        brainstormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("Brainstrom");
                brainstormButton.setVisibility(View.GONE);
                getAdviceButton.setVisibility(View.GONE);
                moreButton.setVisibility(View.GONE);
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("more");
                getAdviceButton.setVisibility(View.GONE);
                brainstormButton.setVisibility(View.GONE);
                moreButton.setVisibility(View.GONE);
            }
        });


    }
}