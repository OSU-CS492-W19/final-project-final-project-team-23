package com.example.android.finalproject;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.finalproject.data.SingleSearchResult;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AnimeSearchAdapter extends RecyclerView.Adapter<AnimeSearchAdapter.SearchResultViewHolder> {
    private List<SingleSearchResult> mSeries;
    OnSearchItemClickListener mSeachItemClickListener;

    public interface OnSearchItemClickListener {
        void onSearchItemClick(SingleSearchResult result);
    }

    AnimeSearchAdapter(OnSearchItemClickListener searchItemClickListener) {
        mSeachItemClickListener = searchItemClickListener;
    }

    public void updateSearchResults(List<SingleSearchResult> result) {
        mSeries = result;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mSeries != null) {
            return mSeries.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.bind(mSeries.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mSearchResultTV;
        private TextView mSeriesTypeTV;
        private TextView mSeriesAirdateTV;
        private ImageView mSeriesImageIV;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.tv_search_result);
            mSeriesTypeTV = itemView.findViewById(R.id.tv_series_type);
            mSeriesImageIV = itemView.findViewById(R.id.iv_series_image);
            mSeriesAirdateTV = itemView.findViewById(R.id.tv_series_airdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SingleSearchResult searchResult = mSeries.get(getAdapterPosition());
                    mSeachItemClickListener.onSearchItemClick(searchResult);
                }
            });
        }

        public void bind(SingleSearchResult series) {
            mSearchResultTV.setText(series.name);
            mSeriesTypeTV.setText(series.type);
            mSeriesAirdateTV.setText(series.airDate);
            Glide.with(mSeriesImageIV.getContext()).load(series.image).into(mSeriesImageIV);
        }
    }

}