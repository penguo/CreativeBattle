package com.afordev.creativebattle;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afordev.creativebattle.Data.CardData;
import com.afordev.creativebattle.Data.ChatData;
import com.afordev.creativebattle.Data.ChoiceData;
import com.afordev.creativebattle.Data.CommandData;
import com.afordev.creativebattle.Data.UserData;
import com.afordev.creativebattle.Manager.GameSystem;
import com.afordev.creativebattle.Manager.ItemCard;
import com.afordev.creativebattle.Manager.ItemChoice;
import com.afordev.creativebattle.Manager.ItemPlayer;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = mFirebase.getReference();
    public GameSystem mGameSystem;

    private View gameUi;

    private ItemCard opponentCard0, opponentCard1, opponentCard2, myCard0, myCard1, myCard2, myHand0, myHand1, myHand2;
    private ItemChoice choice0, choice1, choice2;
    private ItemPlayer player, opponent;

    private ConstraintLayout layoutPhase0, layoutPhase1, layoutPhase2;
    private TextView tvPhase0;

    private LinearLayout layoutChat, layoutLog;
    private TextView tvChat, tvLog;
    private EditText etChat;
    private boolean isViewChatLayout, isViewLogLayout;
    private ImageButton btnSendChat, btnCloseChat, btnCloseLog;
    private ScrollView scrollViewChat, scrollViewLog;
    private StringBuffer logs;
    private ImageButton btnLog;

    private Button btnEndTurn, btnChat;

    private String serverName, mySide, opponentSide;
    private UserData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameUi = findViewById(R.id.game_ui);

        layoutChat = findViewById(R.id.game_layout_chat);
        etChat = findViewById(R.id.game_et_chat);
        tvChat = findViewById(R.id.game_tv_chat);
        scrollViewChat = findViewById(R.id.game_scrollview_chat);
        btnSendChat = findViewById(R.id.game_btn_chat_send);
        btnCloseChat = findViewById(R.id.game_btn_chat_close);

        layoutLog = findViewById(R.id.game_layout_log);
        tvLog = findViewById(R.id.game_tv_log);
        scrollViewLog = findViewById(R.id.game_scrollview_log);
        btnCloseLog = findViewById(R.id.game_btn_log_close);
        btnLog = findViewById(R.id.game_btn_log);

        layoutPhase0 = findViewById(R.id.game_layout_phase0);
        layoutPhase1 = findViewById(R.id.game_layout_phase1);
        layoutPhase2 = findViewById(R.id.game_layout_phase2);
        tvPhase0 = findViewById(R.id.game_tv_phase0);

        btnEndTurn = gameUi.findViewById(R.id.game_btn_endturn);
        btnChat = gameUi.findViewById(R.id.game_btn_chat);

        btnChat.setOnClickListener(this);
        btnSendChat.setOnClickListener(this);
        btnCloseChat.setOnClickListener(this);
        btnLog.setOnClickListener(this);
        btnCloseLog.setOnClickListener(this);
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
        user = getIntent().getParcelableExtra("user");
        player = new ItemPlayer(mGameSystem, findViewById(R.id.game_player), user);
        opponent = new ItemPlayer(mGameSystem, findViewById(R.id.game_player_opponent), null);
        mySide = getIntent().getStringExtra("side");
        serverName = getIntent().getStringExtra("serverName");
        logs = new StringBuffer();
        tvChat.setText(logs.toString());
        setLayoutChat(false);
        mGameSystem = new GameSystem(this, serverName, mySide, tvLog);
        opponentCard0 = new ItemCard(this, gameUi.findViewById(R.id.game_card_opponent_0), null, mySide);
        opponentCard1 = new ItemCard(this, gameUi.findViewById(R.id.game_card_opponent_1), null, mySide);
        opponentCard2 = new ItemCard(this, gameUi.findViewById(R.id.game_card_opponent_2), null, mySide);
        myCard0 = new ItemCard(this, gameUi.findViewById(R.id.game_card_my_0), null, mySide);
        myCard1 = new ItemCard(this, gameUi.findViewById(R.id.game_card_my_1), null, mySide);
        myCard2 = new ItemCard(this, gameUi.findViewById(R.id.game_card_my_2), null, mySide);
        myHand0 = new ItemCard(this, gameUi.findViewById(R.id.game_card_my_hand_0), null, mySide);
        myHand1 = new ItemCard(this, gameUi.findViewById(R.id.game_card_my_hand_1), null, mySide);
        myHand2 = new ItemCard(this, gameUi.findViewById(R.id.game_card_my_hand_2), null, mySide);
        choice0 = new ItemChoice(this, gameUi.findViewById(R.id.game_choice_0), null, mySide);
        choice1 = new ItemChoice(this, gameUi.findViewById(R.id.game_choice_1), null, mySide);
        choice2 = new ItemChoice(this, gameUi.findViewById(R.id.game_choice_2), null, mySide);

        firebaseSet();
        mGameSystem.insertCommand("/connect " + mySide + " " + user.toString());
        if (mySide.equals("red")) {
            opponentSide = "blue";
        } else {
            opponentSide = "red";
        }
        layoutPhase0.setVisibility(View.VISIBLE);
        layoutPhase1.setVisibility(View.GONE);
        layoutPhase2.setVisibility(View.GONE);
        btnEndTurn.setVisibility(View.GONE);
        tvPhase0.setText("Wait Other Player...");
        btnEndTurn.setOnClickListener(this);
    }

    public void setStartGame() {
        if (mySide.equals("red")) {
            mGameSystem.insertCommand("/game proceed red phase1");
        }
    }

    public void phase0() {
        tvPhase0.setText("Opponent's Turn.");
        layoutPhase0.setVisibility(View.VISIBLE);
        layoutPhase1.setVisibility(View.GONE);
        layoutPhase2.setVisibility(View.GONE);
        btnEndTurn.setVisibility(View.GONE);
    }

    public void phase1() {
        layoutPhase0.setVisibility(View.GONE);
        layoutPhase1.setVisibility(View.VISIBLE);
        layoutPhase2.setVisibility(View.GONE);
        btnEndTurn.setVisibility(View.GONE);
        player.addTurn();
        choice0.setChoice(new ChoiceData("3장 뽑기", "랜덤 카드팩에서 3장을 뽑습니다.", "card", "+3", "/drawcard " + mySide + " 3"));
        choice0.setReady();
    }

    public void phase2() {
        layoutPhase0.setVisibility(View.GONE);
        layoutPhase1.setVisibility(View.GONE);
        layoutPhase2.setVisibility(View.VISIBLE);
        btnEndTurn.setVisibility(View.VISIBLE);

        myHand0.setCard(new CardData("전설의", "투명 드래곤", "드래곤중에서도 최강의 투명드래곤이 울부짓었따\n" +
                "투명드래곤은 졸라짱쎄서 드래곤중에서 최강이엇따", 10, 50, 50, "", ""));
        myHand1.setCard(new CardData("맷집좋은", "아이언 골렘", "골렘골렘!!", 2, 100, 5, "", ""));
        myHand2.setCard(new CardData("카카오", "라이언", "라이언도 사자다!", 1, 3, 20, "", ""));
    }

    public void phase2CheckClick() {
        myHand0.onRefresh();
        myHand1.onRefresh();
        myHand2.onRefresh();
        myCard0.onRefresh();
        myCard1.onRefresh();
        myCard2.onRefresh();
        opponentCard0.onRefresh();
        opponentCard1.onRefresh();
        opponentCard2.onRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.game_btn_chat_send):
                if (etChat.getText().toString().equals("")) {
                } else if (etChat.getText().toString().charAt(0) == '/') {
                    CommandData commandData = new CommandData(etChat.getText().toString());
                    mDatabase.child("game").child(serverName).child("command").push().setValue(commandData);
                } else {
                    ChatData chatData = new ChatData(user.getName(), etChat.getText().toString());
                    mDatabase.child("game").child(serverName).child("message").push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                }
                etChat.setText("");
                break;
            case (R.id.game_btn_chat):
                setLayoutChat(true);
                break;
            case (R.id.game_btn_log):
                setLayoutLog(true);
                break;
            case (R.id.game_btn_chat_close):
                setLayoutChat(false);
                break;
            case (R.id.game_btn_log_close):
                setLayoutLog(false);
                break;
            case (R.id.game_btn_endturn):
                mGameSystem.insertCommand("/endturn " + mySide);
                break;
        }
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

    public void setLayoutLog(boolean isView) {
        isViewLogLayout = isView;
        if (isViewLogLayout) {
            layoutLog.setVisibility(View.VISIBLE);
        } else {
            layoutLog.setVisibility(View.GONE);
        }
    }

    public void addChat(String st) {
        logs.append("\n");
        logs.append(st);
        tvChat.setText(logs.toString());
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

    public ItemCard getCard(String type, int pos) {
        switch (type) {
            case ("my"):
                switch (pos) {
                    case (0):
                        return myCard0;
                    case (1):
                        return myCard1;
                    case (2):
                        return myCard2;
                    default:
                        mGameSystem.addErrorLog("getCard position error.", null);
                        return null;
                }
            case ("opponent"):
                switch (pos) {
                    case (0):
                        return opponentCard0;
                    case (1):
                        return opponentCard1;
                    case (2):
                        return opponentCard2;
                    default:
                        mGameSystem.addErrorLog("getCard position error.", null);
                        return null;
                }
            case ("hand"):
                switch (pos) {
                    case (0):
                        return myHand0;
                    case (1):
                        return myHand1;
                    case (2):
                        return myHand2;
                    default:
                        mGameSystem.addErrorLog("getCard type error.", null);
                        return null;
                }
            default:
                mGameSystem.addErrorLog("getCard position error.", null);
                return null;
        }
    }

    public ItemChoice getChoice(int pos) {
        switch (pos) {
            case (0):
                return choice0;
            case (1):
                return choice1;
            case (2):
                return choice2;
            default:
                mGameSystem.addErrorLog("getChoice position error.", null);
                return null;
        }
    }
}
