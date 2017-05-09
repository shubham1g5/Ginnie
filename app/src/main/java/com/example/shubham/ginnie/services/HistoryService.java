package com.example.shubham.ginnie.services;

import com.example.shubham.ginnie.data.MessagesRepository;
import com.example.shubham.ginnie.model.Message;

import java.util.List;

import io.reactivex.Observable;

public class HistoryService implements ServiceInterface{

    private final Message mMessage;
    private final MessagesRepository mMessagesRepository;

    public HistoryService(MessagesRepository messagesRepository, Message message) {
        mMessage = message;
        mMessagesRepository = messagesRepository;
    }

    public Observable<List<Message>> getResponse() {
        return mMessagesRepository.getMyMessages(10);
    }

    @Override
    public String getDescription() {
        return "Gives the last 10 commands by you";
    }
}
