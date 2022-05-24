package com.example.notekeeper.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.R;
import com.example.notekeeper.activity.NoteActivity;
import com.example.notekeeper.model.NoteInfo;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final Context mContext;
    private final List<NoteInfo> mNoteInfos;
    private final LayoutInflater layoutInflater;

    public NoteAdapter(Context context, List<NoteInfo> noteInfos) {
        mContext = context;
        mNoteInfos = noteInfos;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_note_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteInfo noteInfo=mNoteInfos.get(position);
        holder.mTextCourse.setText(noteInfo.getCourse().getTitle());
        holder.mTextTitle.setText(noteInfo.getText());
     //   holder.mCurrentPosition=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, NoteActivity.class);
                intent.putExtra(NoteActivity.NOTE_POSITION,holder.getAdapterPosition());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextCourse;
        public final TextView mTextTitle;
//        public int mCurrentPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextCourse = itemView.findViewById(R.id.text_cource);
            mTextTitle = itemView.findViewById(R.id.text_title);
        }
    }
}
