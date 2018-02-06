package com.afordev.creativebattle.Manager;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.afordev.creativebattle.Data.CardData;
import com.afordev.creativebattle.Data.ChoiceData;
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

    private ArrayList<CardData> myDeck, myDrawedDeck;

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
        myDeck.add(new CardData("", "Dummy1", "Dummy Data." + mySide, 1, 2, 3, "", ""));
        myDeck.add(new CardData("", "Dummy2", "Dummy Data." + mySide, 5, 4, 1, "", ""));
        myDeck.add(new CardData("", "Dummy3", "Dummy Data." + mySide, 3, 7, 8, "", ""));
        myDeck.add(new CardData("", "Dummy4", "Dummy Data." + mySide, 9, 1, 2, "", ""));
        myDeck.add(new CardData("", "Dummy5", "Dummy Data." + mySide, 2, 2, 1, "", ""));
        myDeck.add(new CardData("", "Dummy6", "Dummy Data." + mySide, 1, 5, 6, "", ""));
        userRed = new UserData(-1, "", 0);
        userBlue = new UserData(-1, "", 0);
    }

    public boolean execCommand(String command) {
        String[] strings = command.split(" ");
        String[] st;
        int position;
        ItemCard itemCard;
        ItemChoice itemChoice;
        switch (strings[0]) {
            case ("/update"):
                switch (strings[1]) {
                    case ("card"):
                        try {
                            position = Integer.parseInt(strings[3]);
                            if (position < 0 || position > 2) {
                                addErrorLog(command, "position must be 0~2.");
                                return false;
                            }
                        } catch (Exception e) {
                            addErrorLog(command, "casting strings[3] to Integer.");
                            return false;
                        }
                        switch (strings[2]) {
                            case ("hand"):
                                itemCard = ((GameActivity) mContext).getCard("hand", position);
                                break;
                            case ("red"):
                            case ("blue"):
                                if (mySide.equals(strings[2])) {
                                    itemCard = ((GameActivity) mContext).getCard("my", position);
                                } else {
                                    itemCard = ((GameActivity) mContext).getCard("opponent", position);
                                }
                                break;
                            default:
                                addErrorLog(command, "strings[2] is wrong command.");
                                return false;
                        }
                        switch (strings[4]) {
                            case ("set"):
                                switch (strings[5]) {
                                    case ("card"):
                                        st = strings[6].split(",");
                                        try {
                                            itemCard.setCard(new CardData(st[0], st[1], st[2], Integer.parseInt(st[3]), Integer.parseInt(st[4]), Integer.parseInt(st[5]), st[6], st[7]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting st[] to Integer.");
                                            return false;
                                        }
                                    case ("prefix"):
                                        itemCard.setPrefix(strings[6]);
                                        return true;
                                    case ("name"):
                                        itemCard.setName(strings[6]);
                                        return true;
                                    case ("explain"):
                                        itemCard.setExplain(strings[6]);
                                        return true;
                                    case ("cost"):
                                        try {
                                            itemCard.setCost(Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("hp"):
                                        try {
                                            itemCard.setHp(Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("atk"):
                                        try {
                                            itemCard.setAtk(Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("stamina"):
                                        try {
                                            itemCard.setStamina(Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("special1"):
                                        itemCard.setSpecial1(strings[6]);
                                        return true;
                                    case ("special2"):
                                        itemCard.setSpecial2(strings[6]);
                                        return true;
                                    default:
                                        addErrorLog(command, "strings[5] is wrong command.");
                                        return false;
                                }
                            case ("add"):
                                switch (strings[5]) {
                                    case ("cost"):
                                        try {
                                            itemCard.setCost(itemCard.getCost() + Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("hp"):
                                        try {
                                            itemCard.setHp(itemCard.getHp() + Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("atk"):
                                        try {
                                            itemCard.setAtk(itemCard.getAtk() + Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("stamina"):
                                        try {
                                            itemCard.setStamina(itemCard.getStamina() + Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    default:
                                        addErrorLog(command, "strings[5] is wrong command.");
                                        return false;
                                }
                            case ("sub"):
                                switch (strings[5]) {
                                    case ("cost"):
                                        try {
                                            itemCard.setCost(itemCard.getCost() - Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("hp"):
                                        try {
                                            itemCard.setHp(itemCard.getHp() - Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("atk"):
                                        try {
                                            itemCard.setAtk(itemCard.getAtk() - Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    case ("stamina"):
                                        try {
                                            itemCard.setStamina(itemCard.getStamina() - Integer.parseInt(strings[6]));
                                            return true;
                                        } catch (Exception e) {
                                            addErrorLog(command, "casting strings[6] to Integer.");
                                            return false;
                                        }
                                    default:
                                        addErrorLog(command, "strings[5] is wrong command.");
                                        return false;
                                }
                            default:
                                addErrorLog(command, "strings[4] is wrong command.");
                                return false;
                        }
                    case ("choice"):
                        try {
                            position = Integer.parseInt(strings[2]);
                            if (position < 0 || position > 2) {
                                addErrorLog(command, "position must be 0~2.");
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            addErrorLog(command, "casting strings[2] to Integer.");
                            return false;
                        }
                        itemChoice = ((GameActivity) mContext).getChoice(position);
                        switch (strings[3]) {
                            case ("set"):
                                switch (strings[4]) {
                                    case ("choice"):
                                        st = strings[5].split(",");
                                        itemChoice.setChoice(new ChoiceData(st[0], st[1], st[2], st[3]));
                                        return true;
                                    case ("command"):
                                }
                            default:
                                addErrorLog(command, "strings[3] is wrong command.");
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
            case ("/endturn"):
                return false;
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
                        // TODO proceed phase
                        if (mySide.equals(strings[2])) {
                            switch (strings[3]) {
                                case ("phase1"):
                                    return true;
                                case ("phase2"):
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
                                    ((GameActivity) mContext).insertCommand("/game start");
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
                                    ((GameActivity) mContext).insertCommand("/game start");
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

    public void attackToOne(CardData from, CardData to) {
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

    public UserData getUserRed() {
        return userRed;
    }

    public UserData getUserBlue() {
        return userBlue;
    }
}
