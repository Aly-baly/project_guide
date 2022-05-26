package com.example.project_guide.ui.gallery;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_guide.R;
import com.example.project_guide.databinding.FragmentGalleryBinding;
import com.example.project_guide.databinding.FragmentProfileBinding;
import com.example.project_guide.ui.favorite.FavAdapter;
import com.example.project_guide.ui.favorite.FavDB;
import com.example.project_guide.ui.favorite.FavItem;
import com.example.project_guide.ui.favorite.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private final static String TAG = "favorite";
    private RecyclerView recyclerView;
    private FavDB favDB;
    private List<FavItem> favItemList = new ArrayList<>();
    private FavAdapter favAdapter;

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        favDB = new FavDB(getActivity());

        recyclerView = root.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadData();

        return root;
    }

    private void loadData() {
        if(favItemList!=null){
            favItemList.clear();
        }
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();
        try {
            while (cursor.moveToNext()){
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                @SuppressLint("Range") int image = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)));
                FavItem favItem = new FavItem(title, id, image);
                favItemList.add(favItem);
//                Toast.makeText(getActivity(),"Added to memorize",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "DATA ADDED");
            }
        }finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            Log.d(TAG, "Database close");
            db.close();
        }

        favAdapter = new FavAdapter(getActivity(), favItemList);
        recyclerView.setAdapter(favAdapter);
    }


//    private FragmentGalleryBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        GalleryViewModel galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);
//
//        binding = FragmentGalleryBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}