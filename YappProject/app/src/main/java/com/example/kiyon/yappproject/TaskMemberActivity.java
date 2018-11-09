package com.example.kiyon.yappproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kiyon.yappproject.adapter.AttendTeamMemberRVAdapter;
import com.example.kiyon.yappproject.adapter.MemberListRVAdapter;
import com.example.kiyon.yappproject.model.RoomList.UserResponseResult;

import java.util.ArrayList;

import static com.example.kiyon.yappproject.AddTaskActivity.USER_DATA;

public class TaskMemberActivity extends AppCompatActivity implements MemberListRVAdapter.ItemClickListener {
    RecyclerView recyclerView1,recyclerView2;
    MemberListRVAdapter memberListRVAdapter;
    ArrayList<UserResponseResult> userResponseResults;
    UserResponseResult[] memberArray;
    ArrayList<UserResponseResult> list = new ArrayList<>();

    public static Intent newIntent (Context context, ArrayList<UserResponseResult> list) {
        Intent intent = new Intent(context, TaskMemberActivity.class);
        intent.putExtra(USER_DATA,list);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_member);

        //팀원 전체
        recyclerView2 = findViewById(R.id.recyclerview2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        memberListRVAdapter = new MemberListRVAdapter(TaskMemberActivity.this);
        memberListRVAdapter.setClickListener(this);
        recyclerView2.setAdapter(memberListRVAdapter);

        userResponseResults = new ArrayList<>();

        Intent intent = getIntent();
        list = (ArrayList<UserResponseResult>) intent.getSerializableExtra(USER_DATA);

        memberArray = new UserResponseResult[list.size() + 1];

        memberListRVAdapter.setData(list);

        //선탣된 팀원
        recyclerView1 = findViewById(R.id.recyclerview1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    public void onClickTaskMember(View v) {
        switch (v.getId()) {
            case R.id.backBtn:      //<- 버튼 눌렀을 때
                Intent intent = new Intent(TaskMemberActivity.this, AddTaskActivity.class);
                intent.putExtra("add_member",userResponseResults);
                intent.putExtra("member",list);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(int pos, boolean check, UserResponseResult person) {
        userResponseResults = new ArrayList<>();

        if(check) {
            memberArray[pos] = person;
        }else {
            memberArray[pos] = null;
        }
        for(UserResponseResult user : memberArray) {
            if (user != null) {
                userResponseResults.add(user);
            }
        }

        AttendTeamMemberRVAdapter attendTeamMemberRVAdapter = new AttendTeamMemberRVAdapter(userResponseResults,TaskMemberActivity.this);
        recyclerView1.setAdapter(attendTeamMemberRVAdapter);

    }

}