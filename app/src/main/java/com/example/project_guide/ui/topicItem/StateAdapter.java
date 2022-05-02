package com.example.project_guide.ui.topicItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_guide.R;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    interface OnStateClickListener{
        void onStateClick(Chapter chapter, int position);
    }

    private final OnStateClickListener onClickListener;
    private final LayoutInflater inflater;
    private final List<Chapter> listData;

    StateAdapter(OnStateClickListener onClickListener, Context context, List<Chapter> listData) {
        this.onClickListener = onClickListener;
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chapter chapter = listData.get(position);
        holder.iconView.setImageResource(chapter.getIcon());
        holder.titleView.setText(chapter.getTitle());
        holder.descriptionView.setText(chapter.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // вызываем метод слушателя, передавая ему данные
                onClickListener.onStateClick(chapter, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final TextView titleView, descriptionView;

        ViewHolder(View view) {
            super(view);
            iconView = view.findViewById(R.id.image_icon);
            titleView = view.findViewById(R.id.textView_title);
            descriptionView = view.findViewById(R.id.textView_description);
        }
    }
}