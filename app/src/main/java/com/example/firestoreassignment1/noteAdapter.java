package com.example.firestoreassignment1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {


    private List<Note> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemClickListener2 itemClickListener2;


    noteAdapter(Context context, List<Note> data, ItemClickListener onClick, ItemClickListener2 onClick2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = onClick;
        this.itemClickListener2 = onClick2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.note_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.username.setText(mData.get(position).getUsername());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(holder.getAdapterPosition(), mData.get(position).id);

            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener2.onItemClick2(holder.getAdapterPosition(), mData.get(position).id);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView username;
        public ImageView delete;
        public CardView card;

        ViewHolder(View itemView) {
            super(itemView);
            this.username = itemView.findViewById(R.id.et_note);
            this.delete = itemView.findViewById(R.id.delete);
            this.card = itemView.findViewById(R.id.card2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

    }

    Note getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, String id);
    }

    public interface ItemClickListener2{
        void onItemClick2(int position, String id);
    }
}