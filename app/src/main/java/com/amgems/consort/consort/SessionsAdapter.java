package com.amgems.consort.consort;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zac on 11/8/14.
 */
public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private final List<Integer> mSessionIds;
    private final Resources mResources;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mTitleCard;
        private TextView mTitleTextView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            mTitleCard = cardView;
            mTitleTextView = (TextView) cardView.findViewById(R.id.title_textview);
        }
    }

    public SessionsAdapter(Resources resources, List<Integer> sessionIds) {
        mResources = resources;
        mSessionIds = sessionIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView card = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_session, viewGroup, false);
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String title = mResources.getString(R.string.title_session_room, mSessionIds.get(i));
        viewHolder.mTitleTextView.setText(title);
    }

    @Override
    public int getItemCount() {
        return mSessionIds.size();
    }

}
