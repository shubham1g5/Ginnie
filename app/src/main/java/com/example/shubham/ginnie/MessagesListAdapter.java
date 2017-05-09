package com.example.shubham.ginnie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shubham.ginnie.model.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Message> messages = new ArrayList<>();
    private static Context context;


    public MessagesListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = (MessageViewHolder) holder;
        Message message = messages.get(position);
        messageViewHolder.bind(message);
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    void showMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.message_author_textview)
        TextView authorTextView;


//        @BindView(R.id.message_date_textview)
//        TextView dateTextView;

        @BindView(R.id.message_message_textview)
        TextView messageTextView;


        MessageViewHolder(View view) {
            super(view);
        }

        void bind(Message message) {
            ButterKnife.bind(this, itemView);
            authorTextView.setText(message.getAuthor());
            messageTextView.setText(message.getMessage());
        }
    }
}
