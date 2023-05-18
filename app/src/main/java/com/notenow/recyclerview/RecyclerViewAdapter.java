package com.notenow.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notenow.R;
import com.notenow.db.DBManager;
import com.notenow.model.Note;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    //context of class
    private Context context;
    //Note model instance with list
    private List<Note> notes;

    //Constructer of Adapter class
    public RecyclerViewAdapter(Context context, List<Note> notes) {
        //get Notes Class Data w
        this.context = context;
        this.notes = notes;
    }

    public void removeAllItem() {
        notes.clear();
        notifyDataSetChanged();
    }
    //theree key methods
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //set card view in our recycler widgets
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tvRate.setText((notes.get(position).getRank() + "★"));
        holder.tvTitle.setText(notes.get(position).getTitle());
        String temp = notes.get(position).getContent();
        if (temp.length() >= 45)
            temp = temp.substring(0, 45).trim() + "...";
        holder.tvContent.setText(temp);
        holder.tvTime.setText(notes.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setFilter(List<Note> listNotes) {
        // This method updates the RecyclerView according to listNotes List
        notes = new ArrayList<>();
        notes.addAll(listNotes);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView tvRate;
        TextView tvTitle;
        TextView tvContent;
        TextView tvTime;
        Context ctx;
        //Constructer with super
        public ItemViewHolder(View itemView) {
            super(itemView);
            this.ctx = itemView.getContext();
            tvRate = (TextView) itemView.findViewById(R.id.note_rate);
            tvTitle = (TextView) itemView.findViewById(R.id.note_title);
            tvContent = (TextView) itemView.findViewById(R.id.note_content);
            tvTime = (TextView) itemView.findViewById(R.id.note_time);
        }
    }
}
