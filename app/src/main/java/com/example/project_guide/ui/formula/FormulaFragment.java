package com.example.project_guide.ui.formula;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_guide.R;
import com.example.project_guide.databinding.FragmentFormulaBinding;
import com.example.project_guide.databinding.FragmentHomeBinding;
import com.example.project_guide.databinding.FragmentProfileBinding;
import com.example.project_guide.ui.favorite.TopicAdapter;
import com.example.project_guide.ui.favorite.TopicItem;
import com.example.project_guide.ui.home.HomeViewModel;

import java.util.ArrayList;

public class FormulaFragment extends Fragment {

    private FragmentFormulaBinding binding;

    private ArrayList<TopicItem> topicItems = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_formula, container, false);
//        binding = FragmentProfileBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TopicAdapter(topicItems, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        topicItems.add(new TopicItem(R.drawable.formula_force_of_gravity, "Force of gravity","0","0"));
        topicItems.add(new TopicItem(R.drawable.formula_newton_2law, "Newton second Law","1","0"));
        topicItems.add(new TopicItem(R.drawable.formula_newton_3law, "Newton third Law","2","0"));
        topicItems.add(new TopicItem(R.drawable.formula_acceleration_of_free_fall, "Acceleration of free fall","3","0"));
        topicItems.add(new TopicItem(R.drawable.formula_density_of_the_substance, "Density of the substance","4","0"));
        topicItems.add(new TopicItem(R.drawable.formula_law_of_universal_gravitation, "The law of universal gravitation","5","0"));
        topicItems.add(new TopicItem(R.drawable.formula_pressure, "Pressure","6","0"));
        topicItems.add(new TopicItem(R.drawable.formula_potential_energy_of_the_body, "Potential energy of the body","7","0"));
        topicItems.add(new TopicItem(R.drawable.formula_wavelength, "Wavelength","8","0"));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
