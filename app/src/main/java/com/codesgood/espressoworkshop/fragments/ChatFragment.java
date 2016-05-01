package com.codesgood.espressoworkshop.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.codesgood.espressoworkshop.R;
import com.codesgood.espressoworkshop.activities.MainActivity;
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
    private final static String CHAT_PREFERENCES = "CHAT_PREFERENCES";
    private final static String PREF_USERNAME = "PREF_USERNAME";

    private EditText mNewMessage;
    private ArrayList<ChatMessage> mMessages = new ArrayList<>();
    private RecyclerView mRecycler;
    private EditText mUsernameText;
    private ChatAdapter mAdapter;
    private SharedPreferences mPreferences;
    private String mUsername;
    private AppCompatButton mEditButton;
    private FloatingActionButton mSendButton;
    private boolean mIsEditing;
    private MainActivity.BotListener mListener;

    public static ChatFragment getInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mPreferences = getActivity().getSharedPreferences(CHAT_PREFERENCES, Context.MODE_PRIVATE);
        mUsername = mPreferences.getString(PREF_USERNAME, null);

        mNewMessage = (EditText) view.findViewById(R.id.new_message);
        mRecycler = (RecyclerView) view.findViewById(R.id.chat_recycler);
        mUsernameText = (EditText) view.findViewById(R.id.username_text);
        mEditButton = (AppCompatButton) view.findViewById(R.id.edit_username_button);
        mSendButton = (FloatingActionButton) view.findViewById(R.id.send_button);

        mIsEditing = mUsername == null;

        mUsernameText.setEnabled(mIsEditing);
        mUsernameText.setText(mUsername);
        mEditButton.setText(getString(mIsEditing ? R.string.save : R.string.edit));

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSendButton.setOnClickListener(this);
        mEditButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:
                String newMessage = mNewMessage.getText().toString();
                mSendButton.setEnabled(false);
                if (NetworkService.isNetworkAvailable(getActivity())) {
                    mNewMessage.getText().clear();
                    mMessages.add(new ChatMessage("You", newMessage));

                    if (mAdapter == null) {
                        mAdapter = new ChatAdapter(mMessages);
                        mRecycler.setAdapter(mAdapter);
                        getActivity().supportInvalidateOptionsMenu();
                    } else {
                        mAdapter.notifyDataSetChanged();
                        mRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
                    }

                    notifyBotListener(true);
                    NetworkService.getAPI(getActivity()).getAnswerPersonality(mUsername != null ? mUsername : "random_user", newMessage, new Callback<Answer>() {
                        @Override
                        public void success(Answer answer, Response response) {
                            if (answer.getMessage() != null) {
                                mMessages.add(new ChatMessage("Bot", answer.getMessage().getMessage()));
                                mAdapter.notifyDataSetChanged();
                                mRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
                            }
                            mSendButton.setEnabled(true);
                            notifyBotListener(false);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e(TAG, error.getMessage());
                            mSendButton.setEnabled(true);
                            notifyBotListener(false);
                        }
                    });
                }
                break;
            case R.id.edit_username_button:
                if (mIsEditing) {
                    mUsername = mUsernameText.getText().toString();
                    mPreferences.edit().putString(PREF_USERNAME, mUsername).apply();
                } else {
                    mUsernameText.setSelected(true);
                }
                mIsEditing = !mIsEditing;
                mUsernameText.setEnabled(mIsEditing);
                mNewMessage.setSelected(true);
                mEditButton.setText(getString(mIsEditing ? R.string.save : R.string.edit));
                break;
            default:
                break;
        }
    }

    private void notifyBotListener(boolean sendingMessage) {
        if (mListener != null){
            if(sendingMessage)
                mListener.onMessageSent();
            else
                mListener.onMessageReceived();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.clear_chat_action).setVisible(!mMessages.isEmpty());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.clear_chat_action && mAdapter != null) {
            mMessages.clear();
            mAdapter.notifyDataSetChanged();
            getActivity().supportInvalidateOptionsMenu();
        }
        return false;
    }

    public void setListener(MainActivity.BotListener mListener) {
        this.mListener = mListener;
    }
}
