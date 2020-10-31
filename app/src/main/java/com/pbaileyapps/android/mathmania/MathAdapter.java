package com.pbaileyapps.android.mathmania;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MathAdapter extends RecyclerView.Adapter<MathAdapter.MathViewHolder> {
    private ArrayList<String> subjectList;
    private subjectSelector selectorInstance;
    private Context mContext;
    interface subjectSelector{
        public void selectSubject(int position);
    }
    public MathAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setSubjectList(ArrayList<String> subjectList) {
        this.subjectList = subjectList;
    }

    public void setSelectorInstance(subjectSelector selectorInstance) {
        this.selectorInstance = selectorInstance;
    }

    @NonNull
    @Override
    public MathAdapter.MathViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        v.setBackgroundColor(Color.YELLOW);
        return new MathViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MathAdapter.MathViewHolder holder, final int position) {
        if(subjectList.isEmpty()){
            holder.textView.setText("No Subjects");
        }
        else{
            String subject = subjectList.get(position);
            holder.textView.setText(subject);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectorInstance.selectSubject(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }
    public class MathViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public MathViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.subject_view);
        }
    }
}
