package com.example.ahmed.chatapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.ChatRoom;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatRoomHolder>{
    private List<ChatRoom> chatRooms;
    private Context context;

    public ChatRoomsAdapter(List<ChatRoom> chatRooms, Context context) {
        this.chatRooms = chatRooms;
        this.context = context;
    }

    @Override
    public ChatRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatRoomHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.room_row,parent,false));
    }

    @Override
    public void onBindViewHolder(ChatRoomHolder holder, int position) {
        ChatRoom room =chatRooms.get(position);
        holder.chatroomName.setText(room.room_name);
        holder.chatroomDesc.setText(room.room_desc);
        Picasso.with(holder.chatlogo.getContext()).load("https://ahmedmh.000webhostapp.com/chat.png")
                .error(R.drawable.ic_launcher_background).into(holder.chatlogo);
    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }

    public class ChatRoomHolder extends RecyclerView.ViewHolder {

        ImageView chatlogo;
        TextView chatroomName,chatroomDesc;



        public ChatRoomHolder(View itemView) {
            super(itemView);
            chatlogo = (ImageView) itemView.findViewById(R.id.logo);
            chatroomName = (TextView) itemView.findViewById(R.id.roomTitleTXT);
            chatroomDesc = (TextView) itemView.findViewById(R.id.roomDescTXT);
        }
    }
}
