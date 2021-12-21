package com.example.entranceact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapterForMessages extends RecyclerView.Adapter<ViewHolderMessages> {
    ArrayList<String> messages;
    ArrayList<String> userNames;
    ArrayList<String> timeMessages;
    LayoutInflater inflater;

    public DataAdapterForMessages(Context context, ArrayList<String> messages, ArrayList<String> userNames, ArrayList<String> timeMessages) {
        this.messages = messages;
        this.userNames = userNames;
        this.timeMessages = timeMessages;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolderMessages onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_message, parent, false);
        return new ViewHolderMessages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMessages holder, int position) {
        String msg = messages.get(position);
        String usr = userNames.get(position);
        String time = timeMessages.get(position);
        holder.message.setText(msg);
        holder.username.setText(usr);
        holder.time.setText(time);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
