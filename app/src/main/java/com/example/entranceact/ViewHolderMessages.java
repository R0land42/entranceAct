package com.example.entranceact;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderMessages extends RecyclerView.ViewHolder {
    TextView message, username, time;

    public ViewHolderMessages(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.itemMessage);
        username = itemView.findViewById(R.id.itemMessageUserName);
        time = itemView.findViewById(R.id.itemMessageTime);
    }
}
