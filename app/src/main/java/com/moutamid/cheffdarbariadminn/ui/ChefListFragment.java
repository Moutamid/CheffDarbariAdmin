package com.moutamid.cheffdarbariadminn.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.cheffdarbariadminn.R;
import com.moutamid.cheffdarbariadminn.databinding.FragmentChefListBinding;
import com.moutamid.cheffdarbariadminn.models.ChefUserModel;
import com.moutamid.cheffdarbariadminn.utils.Constants;

import java.util.ArrayList;

public class ChefListFragment extends Fragment {
    private static final String TAG = "ChefListFragment";
    private FragmentChefListBinding b;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentChefListBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        Log.d(TAG, "onCreateView: ");
        if (!isAdded()) return b.getRoot();
        Log.d(TAG, "onCreateView: added");
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Constants.databaseReference()
                .child(Constants.USERS)
                .child(Constants.CHEF)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d(TAG, "onDataChange: ");
                        if (!isAdded()) return;
                        if (snapshot.exists()) {
                            Log.d(TAG, "onDataChange: snapshot exist");
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ChefUserModel model = dataSnapshot.getValue(ChefUserModel.class);
                                Log.d(TAG, "onDataChange: model: " + model);
                                tasksArrayList.add(model);
                            }

                            progressDialog.dismiss();
                            initRecyclerView();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(requireContext(), error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return root;
    }

    private ArrayList<ChefUserModel> tasksArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.chefRecyclerView;
        conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        //        LinearLayoutManager layoutManagerUserFriends = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//    int numberOfColumns = 3;
        //int mNoOfColumns = calculateNoOfColumns(getApplicationContext(), 50);
        //  recyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setReverseLayout(true);
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

//    if (adapter.getItemCount() != 0) {

//        noChatsLayout.setVisibility(View.GONE);
//        chatsRecyclerView.setVisibility(View.VISIBLE);

//    }

    }

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chef_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            ChefUserModel model = tasksArrayList.get(position);
            Log.d(TAG, "onBindViewHolder: " + model.email);
            holder.title.setText(model.name);

        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

            TextView title;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                title = v.findViewById(R.id.chef_name);

            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }
}