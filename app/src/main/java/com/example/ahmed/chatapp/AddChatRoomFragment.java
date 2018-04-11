package com.example.ahmed.chatapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsProgress;
import com.fourhcode.forhutils.FUtilsValidation;

import Model.ChatRoom;
import Model.MainResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import webServices.WebService;

public class AddChatRoomFragment extends DialogFragment{

TextView roomName,roomDesc;
Button addbtn;

    public AddChatRoomFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_add_chat_room,container,false);
       roomName = (TextView) view.findViewById(R.id.ET_roomFrg);
       roomDesc = (TextView) view.findViewById(R.id.ET_roomDESCFrg);
       addbtn = (Button) view.findViewById(R.id.btnAddFrag);

       addbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!FUtilsValidation.isEmpty((EditText) roomName,"Please Enter Room Name")
                       && !FUtilsValidation.isEmpty((EditText) roomDesc,"Please Enter Room Desc")){
                   ChatRoom chatRoom = new ChatRoom();
                   chatRoom.room_name=roomName.getText().toString();
                   chatRoom.room_desc=roomDesc.getText().toString();
                   addChatRoom(chatRoom);
               }
           }
       });

       getDialog().setTitle("Add New Chat Room");
        return view;
    }
    public void addChatRoom(ChatRoom chatRoom){
        WebService.getInstance().getApi().addChatRooms(chatRoom).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.body().status==1){
                    Toast.makeText(getContext(),response.body().message,Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getActivity(),ChatRoomsActivity.class));
                }else{
                    Toast.makeText(getContext(),response.body().message,Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
               Toast.makeText(getContext(),"check internet connection",Toast.LENGTH_LONG).show();
            }
        });
    }

}
