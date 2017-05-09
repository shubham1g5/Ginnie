package com.example.shubham.ginnie;

import android.text.TextUtils;

import com.bumptech.glide.util.Util;
import com.example.shubham.ginnie.common.BasePresenter;
import com.example.shubham.ginnie.common.BasePresenterView;
import com.example.shubham.ginnie.common.FragmentScoped;
import com.example.shubham.ginnie.common.IO;
import com.example.shubham.ginnie.common.UI;
import com.example.shubham.ginnie.common.Utils;
import com.example.shubham.ginnie.data.MessagesRepository;
import com.example.shubham.ginnie.model.Message;
import com.example.shubham.ginnie.services.HelpService;
import com.example.shubham.ginnie.services.HistoryService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@FragmentScoped
public class GinniePresenter extends BasePresenter<GinniePresenter.View> {

    private static final String YOU = "You";
    private static final String GINNIE = "Ginnie";

    private final Scheduler mUiScheduler;
    private final Scheduler mIoScheduler;
    private final MessagesRepository mMessagesRepository;
    private View mView;

    @Inject
    GinniePresenter(@UI Scheduler uiScheduler, @IO Scheduler ioScheduler, MessagesRepository messagesRepository) {
        mUiScheduler = uiScheduler;
        mIoScheduler = ioScheduler;
        mMessagesRepository = messagesRepository;
    }

    @Override
    protected void onRegister(View view) {
        mView = view;

        addToUnsubscribe(mMessagesRepository.getMessages()
                .subscribeOn(mIoScheduler)
                .observeOn(mUiScheduler)
                .subscribe(messages -> {
                    view.showMessages(messages);
                    view.showEmptyView(messages.size() == 0);
                }));

        addToUnsubscribe(view.onMessageEntered()
                .map(message -> new Message(YOU, message, Utils.getTime()))
                .doOnNext(message -> mMessagesRepository.saveMessage(message))
                .subscribe(message -> {
                    processMessage(message);
                }));
    }

    private void processMessage(Message message) {
        String response;

        switch (new MessageProcessor(message).getMessageType()) {
            case HELP:
                new HelpService(message).getResponse().subscribe(s -> {
                    saveResponse(s);
                });
                break;
            case HISTORY:
                new HistoryService(mMessagesRepository, message).getResponse().subscribe(messages -> {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < messages.size(); i++) {
                        sb.append(messages.get(i).getMessage());
                        sb.append("\n");
                    }
                    saveResponse(sb.toString());
                });
                break;
            case YOUTUBE:
                String url = message.getMessage().replace("play","");
                url = url.trim();
                mView.openYoutube(url);
                break;
            case TIME:
                saveResponse(new SimpleDateFormat("h:mm a").format(Utils.getTime()));
                break;
            case DAY:
                saveResponse(new SimpleDateFormat("EEEEEEE").format(Utils.getTime()));
                break;
            case INVALID:
                saveResponse("Master, I don't understand your language");
                break;
            case UNKNOWN:
                saveResponse("Master, I don't have that kind of powers!");
                break;
            default:
                throw new IllegalStateException("Invalid message type " + message.toString());
        }


    }

    private void saveResponse(String response) {
        mMessagesRepository.saveMessage(new Message(GINNIE, response, Utils.getTime()));
    }

    interface View extends BasePresenterView {

        Observable<String> onMessageEntered();

        void showMessages(List<Message> messages);

        void showEmptyView(boolean visible);

        void openYoutube(String url);
    }
}
