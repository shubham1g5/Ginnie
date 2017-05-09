package com.example.shubham.ginnie;

import com.example.shubham.ginnie.model.Message;
import com.example.shubham.ginnie.services.HelpService;
import com.example.shubham.ginnie.services.HistoryService;

import static com.example.shubham.ginnie.MESSAGE_TYPE.DAY;
import static com.example.shubham.ginnie.MESSAGE_TYPE.HELP;
import static com.example.shubham.ginnie.MESSAGE_TYPE.HISTORY;
import static com.example.shubham.ginnie.MESSAGE_TYPE.INVALID;
import static com.example.shubham.ginnie.MESSAGE_TYPE.TASK;
import static com.example.shubham.ginnie.MESSAGE_TYPE.TIME;
import static com.example.shubham.ginnie.MESSAGE_TYPE.UNKNOWN;
import static com.example.shubham.ginnie.MESSAGE_TYPE.YOUTUBE;

public class MessageProcessor {

    private final Message mMessage;

    public MessageProcessor(Message mMessage) {
        this.mMessage = mMessage;
    }

    public MESSAGE_TYPE getMessageType() {
        String message = mMessage.getMessage();
        message = message.trim();
        message = message.toLowerCase();
        if (message.isEmpty()) {
            return INVALID;
        } else if (message.startsWith("help")) {
            return HELP;
        } else if (message.startsWith("history")) {
            return HISTORY;
        } else if (message.endsWith("time")) {
            return TIME;
        } else if (message.endsWith("day")) {
            return DAY;
        } else if (message.contains("task")) {
            return TASK;
        } else if (message.contains("play") || message.contains("song")) {
            return YOUTUBE;
        } else {
            return UNKNOWN;
        }
    }
}
