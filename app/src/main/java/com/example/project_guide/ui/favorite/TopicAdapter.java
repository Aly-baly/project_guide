package com.example.project_guide.ui.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_guide.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder>{

    private ArrayList<TopicItem> topicItems;
    private Context context;
    private FavDB favDB;

    public TopicAdapter(ArrayList<TopicItem> coffeeItems, Context context) {
        this.topicItems = coffeeItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final TopicItem topicItem = topicItems.get(position);

        readCursorData(topicItem, holder);
        holder.imageView.setImageResource(topicItem.getImageResource());
        holder.titleTextView.setText(topicItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return topicItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        Button favBtn;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            favBtn = itemView.findViewById(R.id.favBtn);

            //add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
//                    int position = getAbsoluteAdapterPosition();
                    TopicItem topicItem = topicItems.get(position);

                    if(topicItem.getFavStatus().equals("0")){
                        topicItem.setFavStatus("1");
                        favDB.insertIntoTheDatabase(topicItem.getTitle(), topicItem.getImageResource(),
                                topicItem.getKey_id(), topicItem.getFavStatus());
                        Toast.makeText(context, "Added to memorize", Toast.LENGTH_SHORT).show();
                        favBtn.setBackgroundResource(R.drawable.ic_favorite);
                    }else {
                        topicItem.setFavStatus("0");
                        favDB.remove_fav(topicItem.getKey_id());
                        Toast.makeText(context, "Remove from memorize", Toast.LENGTH_SHORT).show();
                        favBtn.setBackgroundResource(R.drawable.ic_not_favorite);
                    }
                }
            });
        }
    }


    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(TopicItem topicItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(topicItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                topicItem.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_not_favorite);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }
}
