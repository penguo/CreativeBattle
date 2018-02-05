package com.afordev.creativebattle.Manager;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.afordev.creativebattle.Data.Card;
import com.afordev.creativebattle.Data.UserData;
import com.afordev.creativebattle.GameActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by penguo on 2018-02-01.
 */

public class GameSystem {

    private ArrayList<Card> myDeck, myHand, myField, opponentField, myDrawedDeck;

    private FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = mFirebase.getReference();
    private TextView tvLog;
    private StringBuffer logs;
    private Context mContext;
    private String server, mySide;
    private Random random = new Random();

    private int myHp, opponentHp;
    private int costNow, costMax;

    private UserData userRed, userBlue;

    public GameSystem(Context mContext, String server, String side, TextView tvLog) {
        this.mContext = mContext;
        this.server = server;
        this.mySide = side;
        this.tvLog = tvLog;
        logs = new StringBuffer();
        if (!side.equals("red") && !side.equals("blue")) {
            addErrorLog("Fatal error! Your side is wrong.", null);
        } else {
            addLog("New game start!");
        }
        myDeck = new ArrayList<>();
        myDeck.add(new Card("Dummy1","Dummy Data." + mySide,1,2,3,0,0));
        myDeck.add(new Card("Dummy2","Dummy Data." + mySide,5,4,1,0,0));
        myDeck.add(new Card("Dummy3","Dummy Data." + mySide,3,7,8,0,0));
        myDeck.add(new Card("Dummy4","Dummy Data." + mySide,9,1,2,0,0));
        myDeck.add(new Card("Dummy5","Dummy Data." + mySide,2,2,1,0,0));
        myDeck.add(new Card("Dummy6","Dummy Data." + mySide,1,5,6,0,0));
        myHand = new ArrayList<>();
        myField = new ArrayList<>();
        opponentField = new ArrayList<>();
        userRed = new UserData(-1, "", 0);
        userBlue = new UserData(-1, "", 0);
    }

    public boolean execCommand(String command) {
        String[] strings = command.split(" ");
        String[] st;
        Card card;
        switch (strings[0]) {
            case ("/update"):
                switch (strings[1]) {
                    case ("card"):
                        int pos;
                        try {
                            pos = Integer.parseInt(strings[3]);
                        } catch (Exception e) {
                            addErrorLog(command, "casting strings[3] to Integer.");
                            return false;
                        }
                        switch (strings[4]) {
                            case ("set"):
                                if (mySide.equals(strings[2])) {
                                    switch (strings[5]) {
                                        case ("name"):
                                            myField.get(pos).setName(strings[6]);
                                            return true;
                                        case ("explain"):
                                            myField.get(pos).setExplain(strings[6]);
                                            return true;
                                        case ("cost"):
                                            try {
                                                myField.get(pos).setCost(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("hp"):
                                            try {
                                                myField.get(pos).setHp(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("atk"):
                                            try {
                                                myField.get(pos).setAtk(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("stamina"):
                                            try {
                                                myField.get(pos).setStamina(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        default:
                                            addErrorLog(command, "strings[5] is wrong command.");
                                            return false;
                                    }
                                } else {
                                    switch (strings[5]) {
                                        case ("name"):
                                            opponentField.get(pos).setName(strings[6]);
                                            return true;
                                        case ("explain"):
                                            opponentField.get(pos).setExplain(strings[6]);
                                            return true;
                                        case ("cost"):
                                            try {
                                                opponentField.get(pos).setCost(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("hp"):
                                            try {
                                                opponentField.get(pos).setHp(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("atk"):
                                            try {
                                                opponentField.get(pos).setAtk(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("stamina"):
                                            try {
                                                opponentField.get(pos).setStamina(Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        default:
                                            addErrorLog(command, "strings[5] is wrong command.");
                                            return false;
                                    }
                                }
                            case ("add"):
                                if (mySide.equals(strings[2])) {
                                    switch (strings[5]) {
                                        case ("cost"):
                                            try {
                                                myField.get(pos).setCost(myField.get(pos).getCost() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("hp"):
                                            try {
                                                myField.get(pos).setHp(myField.get(pos).getHp() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("atk"):
                                            try {
                                                myField.get(pos).setAtk(myField.get(pos).getAtk() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("stamina"):
                                            try {
                                                myField.get(pos).setStamina(myField.get(pos).getStamina() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        default:
                                            addErrorLog(command, "strings[5] is wrong command.");
                                            return false;
                                    }
                                } else {
                                    switch (strings[5]) {
                                        case ("cost"):
                                            try {
                                                opponentField.get(pos).setCost(opponentField.get(pos).getCost() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("hp"):
                                            try {
                                                opponentField.get(pos).setHp(opponentField.get(pos).getHp() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("atk"):
                                            try {
                                                opponentField.get(pos).setAtk(opponentField.get(pos).getAtk() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("stamina"):
                                            try {
                                                opponentField.get(pos).setStamina(opponentField.get(pos).getStamina() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        default:
                                            addErrorLog(command, "strings[5] is wrong command.");
                                            return false;
                                    }
                                }
                            case ("sub"):
                                if (mySide.equals(strings[2])) {
                                    switch (strings[5]) {
                                        case ("cost"):
                                            try {
                                                myField.get(pos).setCost(myField.get(pos).getCost() - Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("hp"):
                                            try {
                                                myField.get(pos).setHp(myField.get(pos).getHp() - Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("atk"):
                                            try {
                                                myField.get(pos).setAtk(myField.get(pos).getAtk() - Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("stamina"):
                                            try {
                                                myField.get(pos).setStamina(myField.get(pos).getStamina() - Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        default:
                                            addErrorLog(command, "strings[5] is wrong command.");
                                            return false;
                                    }
                                } else {
                                    switch (strings[5]) {
                                        case ("cost"):
                                            try {
                                                opponentField.get(pos).setCost(opponentField.get(pos).getCost() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("hp"):
                                            try {
                                                opponentField.get(pos).setHp(opponentField.get(pos).getHp() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("atk"):
                                            try {
                                                opponentField.get(pos).setAtk(opponentField.get(pos).getAtk() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        case ("stamina"):
                                            try {
                                                opponentField.get(pos).setStamina(opponentField.get(pos).getStamina() + Integer.parseInt(strings[6]));
                                                return true;
                                            } catch (Exception e) {
                                                addErrorLog(command, "casting strings[6] to Integer.");
                                                return false;
                                            }
                                        default:
                                            addErrorLog(command, "strings[5] is wrong command.");
                                            return false;
                                    }
                                }
                            default:
                                addErrorLog(command, "strings[4] is wrong command.");
                                return false;
                        }
                    case ("player"):
                        switch (strings[2]) {
                            case ("set"):
                            case ("add"):
                            case ("sub"):
                        }
                        addErrorLog(command, "Not ready.");
                        return false;
                    default:
                        addErrorLog(command, "strings[1] is wrong command.");
                        return false;
                }
            case ("/summon"):
                st = strings[2].split(",");
                try {
                    card = new Card(st[0],
                            st[1],
                            Integer.parseInt(st[2]),
                            Integer.parseInt(st[3]),
                            Integer.parseInt(st[4]),
                            Integer.parseInt(st[5]),
                            Integer.parseInt(st[6]));
                } catch (Exception e) {
                    addErrorLog(command, "casting st[2]~st[6] to Integer.");
                    return false;
                }
                if (mySide.equals(strings[1])) {
                    myField.add(card);
                    return true;
                } else {
                    opponentField.add(card);
                    return true;
                }
            case ("/attack"):
                int fromPos;
                int toPos;
                try {
                    fromPos = Integer.parseInt(strings[2]);
                    toPos = Integer.parseInt(strings[3]);
                } catch (Exception e) {
                    addErrorLog(command, null);
                    return false;
                }
                if (mySide.equals(strings[1])) {
                    attackToOne(myField.get(fromPos), opponentField.get(toPos));
                    return true;
                } else {
                    attackToOne(opponentField.get(fromPos), myField.get(toPos));
                    return true;
                }
            case ("/endturn"):
                return false;
            case ("/drawcard"):
                if (mySide.equals(strings[1])) {
                    int i = random.nextInt(myDeck.size());
                    myHand.add(myDeck.get(i));
                    myDrawedDeck.add(myDeck.get(i));
                    myDeck.remove(i);
                    return true;
                } else {
                    return true;
                }
            case ("/game"):
                switch (strings[1]) {
                    case ("start"):
                        if (userRed.getUserNo() != -1 && userBlue.getUserNo() != -1) {
                            ((GameActivity) mContext).setStartGame();
                            return true;
                        } else {
                            addErrorLog(command, "Need 2 Player.");
                            return false;
                        }
                    case ("clear"):
                        mDatabase.child("game").child(server).setValue(null);
                        addLog(server + " server's game is cleared.");
                        addLog("Please rejoin server.");
                        return true;
                    case ("proceed"):
                        if (mySide.equals(strings[2])) {
                            switch (strings[3]) {
                                case ("phase1"):
                                    ((GameActivity) mContext).proceedPhase1();
                                    return true;
                                case ("phase2"):
                                    ((GameActivity) mContext).proceedPhase2();
                                    return true;
                                default: {
                                    addErrorLog(command, "strings[3] is wrong command.");
                                    return false;
                                }
                            }
                        } else {
                            return true;
                        }
                    default: {
                        addErrorLog(command, "strings[1] is wrong command.");
                        return false;
                    }
                }
            case ("/connect"):
                switch (strings[1]) {
                    case ("red"):
                        if (userRed.getUserNo() == -1) {
                            st = strings[2].split(",");
                            try {
                                userRed = new UserData(Integer.parseInt(st[0]), st[1], Integer.parseInt(st[2]));
                                if (userBlue.getUserNo() != -1) {
                                    addLog("Red and Blue is ready.");
                                    ((GameActivity)mContext).insertCommand("/game start");
                                } else {
                                    addLog("Wait other player...");
                                }
                                return true;
                            } catch (Exception e) {
                                addErrorLog(command, "casting st[0], st[2] to Integer.");
                                return false;
                            }
                        } else {
                            addLog("Fatal Error: Another User join as red.");
                        }
                    case ("blue"):
                        try {
                            st = strings[2].split(",");
                            if (userBlue.getUserNo() == -1) {
                                userBlue = new UserData(Integer.parseInt(st[0]), st[1], Integer.parseInt(st[2]));
                                if (userRed.getUserNo() != -1) {
                                    addLog("Red and Blue is ready.");
                                    ((GameActivity)mContext).insertCommand("/game start");
                                } else {
                                    addLog("Wait other player...");
                                }
                                return true;

                            } else if (userBlue.getUserNo() == Integer.parseInt(st[0])) {
                                addLog("Fatal Error: Another User join as red.");
                            }
                        } catch (Exception e) {
                            addErrorLog(command, "casting st[0], st[2] to Integer.");
                            return false;
                        }
                    default:
                        addErrorLog(command, "strings[1] is wrong command.");
                        return false;
                }
        }
        addErrorLog(command, null);
        return false;
    }

    public void attackToOne(Card from, Card to) {
        to.setHp(to.getHp() - from.getAtk());
        from.setHp(from.getHp() - to.getAtk());
        from.setStamina(from.getStamina() - 1);
    }

    public void addErrorLog(String command, String explain) {
        String st;
        if (explain == null || explain.equals("")) {
            st = command;
        } else {
            st = command + ": " + explain;
        }
        logs.append("\n");
        logs.append("execCommand Error: " + st);
        Log.e("GameSystem execCommand", st);
        tvLog.setText(logs.toString());
    }

    public void addLog(String log) {
        logs.append("\n");
        logs.append(log);
        tvLog.setText(logs.toString());
    }

    public ArrayList<Card> getMyHand() {
        return myHand;
    }

    public ArrayList<Card> getMyField() {
        return myField;
    }

    public ArrayList<Card> getOpponentField() {
        return opponentField;
    }

    public UserData getUserRed() {
        return userRed;
    }

    public UserData getUserBlue() {
        return userBlue;
    }
}
