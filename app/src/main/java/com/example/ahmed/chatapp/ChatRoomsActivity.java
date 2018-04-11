package com.example.ahmed.chatapp;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsProgress;

import java.util.List;

import Model.ChatRoom;
import Model.MainResponse;
import Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Session;
import webServices.WebService;

public class ChatRoomsActivity extends AppCompatActivity {
private FUtilsProgress progress;
    List<ChatRoom> chatRooms;
    ChatRoomsAdapter roomsAdapter;
private Call<List<ChatRoom>> getChatRoomsCall;
RecyclerView recyclerView;
Toolbar toolbar;
FloatingActionButton floatingActionButton;
    TextView toolbar_title,toolbar_desc,toolbar_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rooms);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
         toolbar_title=(TextView) findViewById(R.id.toolbar_title);
         toolbar_desc=(TextView) findViewById(R.id.toolbar_Desc);
         toolbar_logout=(TextView) findViewById(R.id.toolbar_logout);
         floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

         toolbar_logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Session.getInstance().logoutAndGoTologin(ChatRoomsActivity.this);
             }
         });

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        progress = FUtilsProgress.newProgress(this,layout);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatRoomsActivity.this);

        recyclerView.setLayoutManager(linearLayoutManager);

        User user = Session.getInstance().getUser();
        if(user!=null){
            toolbar_title.setText("Welcome "+user.username);
            if(user.isAdmin){
                toolbar_desc.setText("Admin Login");
                floatingActionButton.setVisibility(View.VISIBLE);
                ItemTouchHelper itemTouchHelper =new ItemTouchHelper(swipChatRoomCallBack);
                itemTouchHelper.attachToRecyclerView(recyclerView);
            }else{
                toolbar_desc.setText(user.email);
                floatingActionButton.setVisibility(View.GONE);
            }
        }

        getChatRooms();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddChatRoomFragment addChatRoomFragment = new AddChatRoomFragment();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                addChatRoomFragment.show(fm,"add");
            }
        });
    }

    public void getChatRooms() {
    progress.showTransparentProgress();
    getChatRoomsCall = WebService.getInstance().getApi().getAllChatRooms();
        getChatRoomsCall.enqueue(new Callback<List<ChatRoom>>() {
            @Override
            public void onResponse(Call<List<ChatRoom>> call, Response<List<ChatRoom>> response) {
                chatRooms = response.body();
                roomsAdapter = new ChatRoomsAdapter(chatRooms,ChatRoomsActivity.this);
                recyclerView.setAdapter(roomsAdapter);
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<ChatRoom>> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Check your internet !",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getChatRoomsCall.cancel();
    }

    ItemTouchHelper.SimpleCallback swipChatRoomCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        final int postion=viewHolder.getAdapterPosition();
        int chatRoomId = Integer.parseInt(chatRooms.get(postion).id);
        WebService.getInstance().getApi().deleteChatRoom(chatRoomId).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.body().status==1){
                    Toast.makeText(ChatRoomsActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                    chatRooms.remove(postion);
                    roomsAdapter.notifyItemRemoved(postion);
                }else {
                    Toast.makeText(ChatRoomsActivity.this,response.body().message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Toast.makeText(ChatRoomsActivity.this,"check internet connection",Toast.LENGTH_LONG).show();
            }
        });
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
                View itemview=viewHolder.itemView;
                Paint p = new Paint();
                if(dX<=0){
                    p.setColor(Color.parseColor("#ED1220"));
                    c.drawRect((float)itemview.getRight()+dX,(float)itemview.getTop(),
                            (float)itemview.getRight(),(float)itemview.getBottom(),p);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}
