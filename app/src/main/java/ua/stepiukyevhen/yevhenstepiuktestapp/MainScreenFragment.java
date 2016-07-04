package ua.stepiukyevhen.yevhenstepiuktestapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class MainScreenFragment extends Fragment {

    EditText editName;
    EditText editText;
    Button sendButton;
    RecyclerView list;

    Random rand = new Random();
    ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findViewsById();
        setupRecyclerView();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    //Normally I use ButterKnife or Android Data Binding, but not this time :D
    private void findViewsById() {
        editName = (EditText) getActivity().findViewById(R.id.name);
        editText = (EditText) getActivity().findViewById(R.id.text);
        sendButton = (Button) getActivity().findViewById(R.id.send_button);
        list = (RecyclerView) getActivity().findViewById(R.id.list);
    }

    private void setupRecyclerView() {
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListAdapter();
        list.setAdapter(adapter);
    }

    private void sendMessage() {
        if (editName.getText().toString().equals("") ||
                editText.getText().toString().equals("")) {

            Toast.makeText(getContext(), "Please input fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rand.nextBoolean()) {
            adapter.addItem(
                    new Message(editName.getText().toString(),
                            editText.getText().toString()));
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Error")
                    .setMessage("Something goes wrong")
                    .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendMessage();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing :D
                        }
                    })
                    .show();
        }
    }

    private void saveList() {
        SharedPreferences.Editor editor = getActivity()
                .getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE)
                .edit();

        Gson gson = new Gson();
        editor
                .putString(MainActivity.LIST, gson.toJson(adapter.getItems()))
                .apply();
    }

    private ArrayList<Message> getListFromJson(String list) {
        Gson gson = new Gson();
        Message[] items = gson.fromJson(list, Message[].class);
        return new ArrayList<Message>(Arrays.asList(items));
    }

    @Override
    public void onPause() {
        saveList();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = getActivity()
                .getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE);

        String messagesList = prefs.getString(MainActivity.LIST, null);
        if (messagesList != null) {
            adapter.replaceItems(getListFromJson(messagesList));
        }
        String name = prefs.getString(MainActivity.NAME, null);
        if (name != null) {
            editName.setText(name);
        }
    }
}
