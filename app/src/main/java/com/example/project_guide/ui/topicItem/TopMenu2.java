package com.example.project_guide.ui.topicItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_guide.R;

import java.util.ArrayList;

public class TopMenu2 extends AppCompatActivity{

    ArrayList<Chapter> list = new ArrayList<Chapter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kinem_top);
        // начальная инициализация списка
        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.list);
        StateAdapter.OnStateClickListener stateClickListener = new StateAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(Chapter chapter, int position) {
                Toast.makeText(getApplicationContext(), chapter.getTitle() + "Will be updated soon!",
                        Toast.LENGTH_SHORT).show();
            }
        };
        // создаем адаптер
        StateAdapter adapter = new StateAdapter(stateClickListener, this, list);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData() {
        Chapter topic1 = new Chapter("Dynamics of Point Particle", R.drawable.ic_formula, "Newton’s Laws of Motion, Inertial Reference Frames. Galilean Principle of Relativity, Force. Mass, Space and Time");
        Chapter topic2 = new Chapter("Work and Mechanical Energy", R.drawable.ic_formula, "Energy. Work of a Force, Kinetic and Potential Energy");
        Chapter topic3 = new Chapter("Dynamics of Rigid Body", R.drawable.ic_formula, "Center of Mass of a Body, Torque. Angular Momentum. Rotary Inertia, Steiner Theorem, Rotational Kinetic Energy. Rotational Work");
        Chapter topic4 = new Chapter("Mechanical Conservation Laws", R.drawable.ic_formula, "Collision of Tow Bodies, Space Velocities, Gyroscopes");
        Chapter topic5 = new Chapter("Relativistic Mechanics", R.drawable.ic_formula, "Basic Postulates of Relativity, . Lorentz Transformations, Interval, Corollaries of the Lorentz Transformations, Dynamics of Relativity");


        list.add(topic1);
        list.add(topic2);
        list.add(topic3);
        list.add(topic4);
        list.add(topic5);
    }
}
