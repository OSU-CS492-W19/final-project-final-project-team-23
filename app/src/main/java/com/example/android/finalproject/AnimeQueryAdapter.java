package com.example.android.finalproject;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.finalproject.data.ASearchQuery;

import org.w3c.dom.Text;

import java.util.List;

public class AnimeQueryAdapter extends RecyclerView.Adapter<AnimeQueryAdapter.QueryViewHolder> {
    private List<ASearchQuery> mSearchQueries;
    private OnQueryClickListener mQueryClickListener;

    public interface OnQueryClickListener {
        void onQueryClick(ASearchQuery query);
    }

    public AnimeQueryAdapter(OnQueryClickListener clickListener){
        mQueryClickListener = clickListener;
    }

    public void updateQueryList(List<ASearchQuery> queries){
        Log.d("DATABASE TESTING", "Query Adapter update - " + queries);
        mSearchQueries = queries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mSearchQueries != null){
            return mSearchQueries.size();
        }else{
            return 0;
        }
    }

    @Override
    public QueryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View queryView = inflater.inflate(R.layout.query_item, parent, false);
        return new QueryViewHolder(queryView);
    }

    @Override
    public void onBindViewHolder(QueryViewHolder holder, int pos){
        Log.d("DATABASE TESTING", " - onBindViewHolder - query text: " + mSearchQueries.get(pos).query);
        holder.bind(mSearchQueries.get(pos));
    }

    class QueryViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView mQueryText;

        public QueryViewHolder(View itemView){
            super(itemView);
            mQueryText = itemView.findViewById(R.id.tv_query_text);
            itemView.setOnClickListener(this);
        }

        public void bind(ASearchQuery searchQuery){
            Log.d("DATABASE TESTING",  "- bind - query Text: " + searchQuery.query);
            mQueryText.setText(searchQuery.query);
        }

        @Override
        public void onClick(View v){
            ASearchQuery searchQuery = mSearchQueries.get(getAdapterPosition());
            mQueryClickListener.onQueryClick(searchQuery);
        }
    }
}
