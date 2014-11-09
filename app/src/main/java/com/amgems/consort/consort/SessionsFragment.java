package com.amgems.consort.consort;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SessionsFragment extends Fragment {

    private RecyclerView mSessionsRecyclerView;
    private SessionsAdapter mAdapter;

    // Visible default constructor to make Fragment happy
    public SessionsFragment() {
    }

    public static Fragment newInstance(ArrayList<Integer> sessionIds) {
        SessionsFragment fragment = new SessionsFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(MainMenuActivity.EXTRAS_SESSION_LIST, sessionIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sessions, container, false);

        mSessionsRecyclerView = (RecyclerView) rootView.findViewById(R.id.session_list);
        mSessionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Integer> sessionIds = getArguments().getIntegerArrayList(MainMenuActivity.EXTRAS_SESSION_LIST);
        if (sessionIds == null) {
            throw new IllegalArgumentException("EXTRAS_SESSION_LIST cannot be null");
        }
        mAdapter = new SessionsAdapter(getResources(), sessionIds);
        mAdapter.setOnItemClickListener(new SessionsAdapter.OnItemClickListener() {
            @Override
            public void onClick(Integer data, int pos) {
                Intent activityIntent = new Intent(getActivity(), GameSessionActivity.class);
                startActivity(activityIntent);
            }
        });

        mSessionsRecyclerView.setAdapter(mAdapter);
    }
}
