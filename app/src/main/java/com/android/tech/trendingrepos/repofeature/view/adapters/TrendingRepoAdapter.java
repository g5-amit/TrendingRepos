package com.android.tech.trendingrepos.repofeature.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tech.trendingrepos.R;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class TrendingRepoAdapter extends RecyclerView.Adapter<TrendingRepoAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TrendingRepoEntity> mListData;

    public TrendingRepoAdapter(Context activityContext, ArrayList<TrendingRepoEntity> trendingRepos) {
        mContext = activityContext;
        mListData = trendingRepos;
    }

    @NonNull
    @Override
    public TrendingRepoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_repo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingRepoAdapter.ViewHolder holder, int position) {
        TrendingRepoEntity trendingRepo = mListData.get(position);

        if(!TextUtils.isEmpty(trendingRepo.getAvatar())) {
            Glide.with(mContext)
                    .load(trendingRepo.getAvatar())
                    .apply(RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.avtar_placeolder)
                            .error(R.drawable.avtar_placeolder))
                    .into(holder.avtar);
        }

        if(!TextUtils.isEmpty(trendingRepo.getLanguageColor())){
            holder.lang_color.setBackgroundColor(Color.parseColor(trendingRepo.getLanguageColor()));
        }

        holder.author.setText(trendingRepo.getAuthor()== null?"":trendingRepo.getAuthor());
        holder.name.setText(trendingRepo.getName()== null?"":trendingRepo.getName());
        holder.desc_with_url.setText(
                String.format("%s%s", trendingRepo.getDescription() == null ? "" : trendingRepo.getDescription(), trendingRepo.getUrl() == null ? "" : "("+trendingRepo.getUrl()+")"));
        holder.lang.setText(trendingRepo.getLanguage()== null?"":trendingRepo.getLanguage());
        holder.star_value.setText(String.format("%s",trendingRepo.getStars()));
        holder.fork_value.setText(String.format("%s",trendingRepo.getForks()));

        holder.repogroupDetails.setVisibility(trendingRepo.isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            trendingRepo.isExpanded = !trendingRepo.isExpanded;
            holder.repogroupDetails.setVisibility(trendingRepo.isExpanded ? View.VISIBLE : View.GONE);
        });

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView avtar,lang_color;
        private TextView author,name,desc_with_url,lang,star_value,fork_value;
        private Group repogroupDetails;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            repogroupDetails = itemView.findViewById(R.id.repogroupDetails);

            avtar = itemView.findViewById(R.id.avtar);
            lang_color = itemView.findViewById(R.id.lang_color);

            author = itemView.findViewById(R.id.author);
            name = itemView.findViewById(R.id.name);
            desc_with_url = itemView.findViewById(R.id.desc_with_url);
            lang = itemView.findViewById(R.id.lang);
            star_value = itemView.findViewById(R.id.star_value);
            fork_value = itemView.findViewById(R.id.fork_value);

        }
    }
}
