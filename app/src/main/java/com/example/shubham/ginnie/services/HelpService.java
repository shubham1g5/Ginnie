package com.example.shubham.ginnie.services;

import android.text.TextUtils;

import com.example.shubham.ginnie.model.Message;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/*
Defines All availabe commands
 */
public class HelpService implements ServiceInterface {

    private final Message mMessage;

    private static ArrayList<String> COMMANDS = new ArrayList<>();

    static {
        COMMANDS.add("help");
        COMMANDS.add("history");
        COMMANDS.add("what time");
        COMMANDS.add("what day");
        COMMANDS.add("play <youtube url>");
    }

    public HelpService(Message mMessage) {
        this.mMessage = mMessage;
    }

    public Observable<String> getResponse() {
        return Observable.just(TextUtils.join("\n", COMMANDS));
    }

    @Override
    public String getDescription() {
        return "List all available commands";
    }
}
