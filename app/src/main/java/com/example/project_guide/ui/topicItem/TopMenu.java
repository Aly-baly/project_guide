package com.example.project_guide.ui.topicItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.project_guide.R;

import java.util.ArrayList;

public class TopMenu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mech_top);
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.subTopic1:
                intent = new Intent(this, TopicActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        startActivity(intent);
    }

}
