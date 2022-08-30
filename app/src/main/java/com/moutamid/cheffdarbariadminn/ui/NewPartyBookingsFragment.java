package com.moutamid.cheffdarbariadminn.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.cheffdarbariadminn.R;
import com.moutamid.cheffdarbariadminn.databinding.FragmentNewPartyBookingsBinding;
import com.moutamid.cheffdarbariadminn.models.AffiliateAddBookingModel;

import java.util.ArrayList;


public class NewPartyBookingsFragment extends Fragment {

    private FragmentNewPartyBookingsBinding b;
    LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentNewPartyBookingsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded())  return b.getRoot();
        linearLayoutManager = new LinearLayoutManager(requireContext());
        /*Constants.databaseReference()
                .child(Constants.auth().getUid())
                .child(Constants.NEW_PARTY_BOOKINGS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                AffiliateAddBookingModel model = dataSnapshot.getValue(AffiliateAddBookingModel.class);
                                tasksArrayList.add(model);

                            }

                            initRecyclerView();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/


        return root;
    }
    private ArrayList<AffiliateAddBookingModel> tasksArrayList = new ArrayList<AffiliateAddBookingModel>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.myBookingsRecyclerview;
        //conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        //        LinearLayoutManager layoutManagerUserFriends = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//    int numberOfColumns = 3;
        //int mNoOfColumns = calculateNoOfColumns(getApplicationContext(), 50);
        //  recyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
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

/*public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
    int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
    return noOfColumns;
}*/

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_party_bookings_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {

//            holder.title.setText("");

        }

        @Override
        public int getItemCount() {
//            if (tasksArrayList == null)
            return 10;
//            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

//            TextView title;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
//                title = v.findViewById(R.id.titleTextview);

            }
        }

    }

}