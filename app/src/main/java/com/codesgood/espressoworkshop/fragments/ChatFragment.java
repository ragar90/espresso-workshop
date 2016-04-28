package com.codesgood.espressoworkshop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codesgood.espressoworkshop.R;
import com.codesgood.espressoworkshop.adapters.ChatAdapter;
import com.codesgood.espressoworkshop.models.ChatMessage;
import com.codesgood.espressoworkshop.network.NetworkService;
import com.codesgood.espressoworkshop.network.models.Answer;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChatFragment extends Fragment implements View.OnClickListener {

    public final static String TAG = ChatFragment.class.getName();

    private EditText mNewMessage;
    private ArrayList<ChatMessage> mMessages = new ArrayList<>();
    private RecyclerView mRecycler;
    private ChatAdapter mAdapter;

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mNewMessage = (EditText) view.findViewById(R.id.new_message);
        mRecycler = (RecyclerView) view.findViewById(R.id.chat_recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        view.findViewById(R.id.send_message).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message:
                String newMessage = mNewMessage.getText().toString();
                if (NetworkService.isNetworkAvailable(getActivity())) {
                    mNewMessage.getText().clear();
                    mMessages.add(new ChatMessage("User", newMessage));

                    if (mAdapter == null) {
                        mAdapter = new ChatAdapter(mMessages);
                        mRecycler.setAdapter(mAdapter);
                    } else {
                        mAdapter.notifyDataSetChanged();
                        mRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
                    }

                    NetworkService.getAPI(getActivity()).getAnswer("en", newMessage, new Callback<Answer>() {
                        @Override
                        public void success(Answer answer, Response response) {
                            Log.d(TAG, answer.getMsg());
                            mMessages.add(new ChatMessage("Bot", answer.getResponse()));
                            mAdapter.notifyDataSetChanged();
                            mRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(TAG, error.getMessage());
                        }
                    });
                }
                break;
            default:
                break;
        }
    }
}
