package com.kolis.test_catalog_app.ui.home;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.kolis.test_catalog_app.R;
import com.kolis.test_catalog_app.data.DressModel;
import com.kolis.test_catalog_app.data.DressRepositoryImpl;

import java.util.ArrayList;

public class DressListAdapter extends RecyclerView.Adapter<DressListAdapter.DressViewHolder> {
    private ArrayList<DressModel> dressList = new ArrayList<>();
    public NavController controller;

    @NonNull
    @Override
    public DressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DressListAdapter.DressViewHolder holder, int position) {
        holder.bind(dressList.get(position));
    }

    @Override
    public int getItemCount() {
        return dressList.size();
    }

    public void setModelsList(ArrayList<DressModel> modelList) {
        dressList = modelList;
        notifyDataSetChanged();
    }

    class DressViewHolder extends RecyclerView.ViewHolder {

        ImageView pictureIV;
        ImageView isLikedIV;
        TextView productNameTV;
        TextView newPriceTV;
        TextView oldPriceTV;
        TextView numberOfVotesTV;
        TextView timeRemainingTV;
        RatingBar ratingBar;

        public DressViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureIV = itemView.findViewById(R.id.product_photo);
            isLikedIV = itemView.findViewById(R.id.likePhoto);
            productNameTV = itemView.findViewById(R.id.productName);
            newPriceTV = itemView.findViewById(R.id.priceActual);
            oldPriceTV = itemView.findViewById(R.id.priceOld);
            numberOfVotesTV = itemView.findViewById(R.id.numberOfMarks);
            timeRemainingTV = itemView.findViewById(R.id.timeRemaining);
            ratingBar = itemView.findViewById(R.id.rating);
        }

        public void bind(DressModel model) {

            //заглушка. Фото не отправляется на сервер и не получается с сервера.
            pictureIV.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(),
                    model.getTestImageResource(), null));
            isLikedIV.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(),
                    model.isLiked() ? R.drawable.like : R.drawable.dislike, null));
            isLikedIV.setOnClickListener(v -> {
                model.setLiked(!model.isLiked());
                isLikedIV.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(),
                        model.isLiked() ? R.drawable.like : R.drawable.dislike, null));
            });
            productNameTV.setText(model.getName());
            if (model.getNewPrice() >= model.getOldPrice()) {
                newPriceTV.setText("$ " + String.format(java.util.Locale.US, "%.2f", model.getNewPrice()));
                oldPriceTV.setVisibility(View.INVISIBLE);
                timeRemainingTV.setVisibility(View.GONE);
            } else {
                newPriceTV.setText("$ " + String.format(java.util.Locale.US, "%.2f", model.getNewPrice()));
                oldPriceTV.setText("$ " + String.format(java.util.Locale.US, "%.2f", model.getOldPrice()));
                oldPriceTV.setPaintFlags(oldPriceTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                oldPriceTV.setVisibility(View.VISIBLE);
                if (model.getTimeTill() > System.currentTimeMillis()) {
                    timeRemainingTV.setText(getRemainingTimeText(model.getTimeTill() - System.currentTimeMillis(),
                            timeRemainingTV));
                    timeRemainingTV.setVisibility(View.VISIBLE);
                } else {
                    timeRemainingTV.setVisibility(View.GONE);
                }
            }
            long votes = model.getNumberOfVotes();
            numberOfVotesTV.setText("(" + (int) votes + ")");
            ratingBar.setRating(model.getAvgMark());
            itemView.setOnClickListener(v -> {
                HomeFragmentDirections.ActionNavigationHomeToNavigationWatchDress action = HomeFragmentDirections.actionNavigationHomeToNavigationWatchDress(model);
                controller.navigate(action);
            });
        }
    }

    private String getRemainingTimeText(Long timeInMillis, View v) {
        Long days = timeInMillis / (1000 * 60 * 60 * 24);
        Long hours = (timeInMillis - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        Long minutes = (timeInMillis - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        if (days > 0) {
            return v.getResources().getQuantityString(R.plurals.time_remaining, days.intValue(),
                    days.intValue(), hours.intValue(), minutes.intValue());
        } else {
            //done due to limitations of plurals in English
            return v.getResources().getString(R.string.time_remain_zero_days,
                    hours.intValue(), minutes.intValue());
        }
    }
}
