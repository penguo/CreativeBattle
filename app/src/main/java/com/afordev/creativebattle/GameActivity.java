package com.afordev.creativebattle;

import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afordev.creativebattle.Data.ChatData;
import com.afordev.creativebattle.Data.CommandData;
import com.afordev.creativebattle.Data.UserData;
import com.afordev.creativebattle.Manager.GameSystem;
import com.afordev.creativebattle.Manager.FieldRcvAdapter;
import com.afordev.creativebattle.Manager.HandRcvAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rcvHand, rcvMyField, rcvOpponentField;
    private FloatingActionButton fabChat;
    private LinearLayout layoutChat;
    private TextView tvChat, tvLog, tvMyInfo, tvOpponentInfo;
    private EditText etChat;
    private boolean isViewChatLayout;
    private FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = mFirebase.getReference();
    private StringBuffer logs;
    private ScrollView scrollViewChat, scrollViewLog;
    private FieldRcvAdapter myFieldRcvAdaper, opponentFieldRcvAdapter;
    private HandRcvAdapter handRcvAdapter;
    private GameSystem mGameSystem;
    private ImageButton btnSendChat, btnCloseChat;
    private String serverName, mySide;
    private UserData user;
    private Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rcvHand = findViewById(R.id.game_rcv_hand);
        rcvMyField = findViewById(R.id.game_rcv_myfield);
        rcvOpponentField = findViewById(R.id.game_rcv_opponentfield);
        fabChat = findViewById(R.id.game_fab_chat);
        layoutChat = findViewById(R.id.game_layout_chat);
        etChat = findViewById(R.id.game_et_chat);
        tvChat = findViewById(R.id.game_tv_chat);
        scrollViewChat = findViewById(R.id.game_scrollview_chat);
        tvLog = findViewById(R.id.game_tv_log);
        scrollViewLog = findViewById(R.id.game_scrollview_log);
        btnSendChat = findViewById(R.id.game_btn_chat_send);
        btnCloseChat = findViewById(R.id.game_btn_chat_close);
        tvMyInfo = findViewById(R.id.game_tv_my_info);
        tvOpponentInfo = findViewById(R.id.game_tv_opponent_info);
        btn1 = findViewById(R.id.game_btn_1);
        btn2 = findViewById(R.id.game_btn_2);

        rcvHand.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvMyField.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcvOpponentField.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fabChat.setOnClickListener(this);
        btnSendChat.setOnClickListener(this);
        btnCloseChat.setOnClickListener(this);
        tvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                scrollViewChat.fullScroll(View.FOCUS_DOWN);
            }
        });
        tvLog.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                scrollViewLog.fullScroll(View.FOCUS_DOWN);
            }
        });

        setReady();
    }

    public void setReady() {
        waitPhase();
        user = getIntent().getParcelableExtra("user");
        mySide = getIntent().getStringExtra("side");
        serverName = getIntent().getStringExtra("serverName");
        logs = new StringBuffer();
        tvChat.setText(logs.toString());
        setLayoutChat(false);
        mGameSystem = new GameSystem(this, serverName, mySide, tvLog);
        handRcvAdapter = new HandRcvAdapter(this, mGameSystem.getMyHand());
        myFieldRcvAdaper = new FieldRcvAdapter(this, mGameSystem.getMyField(), true);
        opponentFieldRcvAdapter = new FieldRcvAdapter(this, mGameSystem.getOpponentField(), false);
        rcvHand.setAdapter(handRcvAdapter);
        rcvMyField.setAdapter(myFieldRcvAdaper);
        rcvOpponentField.setAdapter(opponentFieldRcvAdapter);
        firebaseSet();
        insertCommand("/connect " + mySide + " " + user.toString());
        if (mySide.equals("red")) {
            tvMyInfo.setText("red" + "- Player: " + mGameSystem.getUserRed().getName());
        } else {
            tvMyInfo.setText("blue" + "- Player: " + mGameSystem.getUserBlue().getName());
        }
    }

    public void setStartGame() {
        if (mySide.equals("red")) {
            tvOpponentInfo.setText("blue" + "- Player: " + mGameSystem.getUserBlue().getName());
        } else {
            tvOpponentInfo.setText("red" + "- Player: " + mGameSystem.getUserRed().getName());
        }
    }

    public void waitPhase() {
        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn1.setOnClickListener(null);
        btn2.setOnClickListener(null);
    }

    public void proceedPhase1() {
        mGameSystem.addLog(mySide + "");
        btn1.setVisibility(View.GONE);
        btn1.setText("Card Upgrade");
        btn1.setOnClickListener(new View.OnClickListener() { //card upgrade
            @Override
            public void onClick(View view) {
                insertCommand("/");
                insertCommand("/game proceed " + mySide + " phase2");
            }
        });
        btn2.setVisibility(View.VISIBLE);
        btn2.setText("Card Draw");
        btn2.setOnClickListener(new View.OnClickListener() { //card draw
            @Override
            public void onClick(View view) {
                insertCommand("/drawcard " + mySide);
                insertCommand("/game proceed " + mySide + " phase2");
            }
        });

    }

    public void proceedPhase2() {
        btn1.setVisibility(View.GONE);
        btn1.setText("Card Upgrade");
        btn1.setOnClickListener(new View.OnClickListener() { //card upgrade
            @Override
            public void onClick(View view) {
                insertCommand("/game proceed " + mySide + " phase2");
            }
        });
        btn2.setVisibility(View.VISIBLE);
        btn2.setText("Turn End");
        btn2.setOnClickListener(new View.OnClickListener() { //card draw
            @Override
            public void onClick(View view) {
                if(mySide.equals("red")){
                    insertCommand("/game proceed blue phase1");
                }else{
                    insertCommand("/game proceed red phase1");
                }
                waitPhase();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.game_fab_chat):
                setLayoutChat(true);
                break;
            case (R.id.game_btn_chat_send):
                if (etChat.getText().toString().charAt(0) == '/') {
                    CommandData commandData = new CommandData(etChat.getText().toString());
                    mDatabase.child("game").child(serverName).child("command").push().setValue(commandData);
                } else {
                    ChatData chatData = new ChatData(user.getName(), etChat.getText().toString());
                    mDatabase.child("game").child(serverName).child("message").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                }
                etChat.setText("");
                break;
            case (R.id.game_btn_chat_close):
                setLayoutChat(false);
                break;
        }
    }

    public void insertCommand(String command) {
        CommandData commandData = new CommandData(command);
        mDatabase.child("game").child(serverName).child("command").push().setValue(commandData);
    }

    public void setLayoutChat(boolean isView) {
        isViewChatLayout = isView;
        if (isViewChatLayout) {
            layoutChat.setVisibility(View.VISIBLE);
            etChat.setText("");
        } else {
            layoutChat.setVisibility(View.GONE);
        }
    }

    public void addChat(String st) {
        logs.append("\n");
        logs.append(st);
        tvChat.setText(logs.toString());
    }

    public void refreshField() {
        myFieldRcvAdaper.onRefresh();
        opponentFieldRcvAdapter.onRefresh();
    }

    public void firebaseSet() {
        mDatabase.child("game").child(serverName).child("message").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                addChat(chatData.getName() + ": " + chatData.getMessage()); // stringBuffer에 추가합니다.
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
        });
        mDatabase.child("game").child(serverName).child("command").addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CommandData commandData = dataSnapshot.getValue(CommandData.class);
                if (mGameSystem.execCommand(commandData.getCommand())) { // true일 경우
                    refreshField();
                }
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
        });
    }
}
