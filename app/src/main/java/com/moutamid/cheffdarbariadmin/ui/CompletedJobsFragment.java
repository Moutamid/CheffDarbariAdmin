package com.moutamid.cheffdarbariadmin.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.cheffdarbariadmin.R;
import com.moutamid.cheffdarbariadmin.databinding.FragmentCompletedJobsBinding;
import com.moutamid.cheffdarbariadmin.models.JobsAdminModel2;
import com.moutamid.cheffdarbariadmin.utils.Constants;

import java.util.ArrayList;

public class CompletedJobsFragment extends Fragment {

    private FragmentCompletedJobsBinding b;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCompletedJobsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded())   return b.getRoot();

        Constants.databaseReference()
                .child(Constants.COMPLETED_JOBS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                JobsAdminModel2 adminModel = dataSnapshot.getValue(JobsAdminModel2.class);
                                tasksArrayList.add(adminModel);
                            }
                            initRecyclerView();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), error.toException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        linearLayoutManager = new LinearLayoutManager(requireContext());
        return root;
    }
    LinearLayoutManager linearLayoutManager;

    private ArrayList<JobsAdminModel2> tasksArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.completedJobsRecyclerView;
        //conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        //        LinearLayoutManager layoutManagerUserFriends = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//    int numberOfColumns = 3;
        //int mNoOfColumns = calculateNoOfColumns(getApplicationContext(), 50);
        //  recyclerView.setLayoutManager(new GridLayoutManager(this, mNoOfColumns));
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_jobs_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            JobsAdminModel2 model = tasksArrayList.get(position);

            holder.name.setText(Html.fromHtml(Constants.BOLD_START + "Customer Name: "  + Constants.BOLD_END+ model.name));

            holder.id.setText(Html.fromHtml(Constants.BOLD_START + "Job Id: " + Constants.BOLD_END + model.id));

            holder.staff_required.setText(Html.fromHtml(Constants.BOLD_START + "Staff Required: " + Constants.BOLD_END + model.staff_required));
            holder.payment.setText(Html.fromHtml(Constants.BOLD_START + "Payment: " + Constants.BOLD_END + "₹"+model.payment));
            holder.occasion.setText(Html.fromHtml(Constants.BOLD_START + "Occasion Type: "  + Constants.BOLD_END+ model.occasion_type));
            holder.party_date.setText(Html.fromHtml(Constants.BOLD_START + "Party Date: " + Constants.BOLD_END + model.date));
            holder.number_of_people.setText(Html.fromHtml(Constants.BOLD_START + "Number of people: " + Constants.BOLD_END + model.number_of_people));
            holder.time.setText(Html.fromHtml(Constants.BOLD_START + "Time: " + Constants.BOLD_END + model.time));
            holder.number_of_dishes.setText(Html.fromHtml(Constants.BOLD_START + "No of dishes: " + Constants.BOLD_END + model.no_of_dishes));
            holder.cuisines.setText(Html.fromHtml(Constants.BOLD_START + "Cuisines: " + Constants.BOLD_END + model.cuisines_list.toString()));
            holder.party_address.setText(Html.fromHtml(Constants.BOLD_START + "Party Address: " + Constants.BOLD_END + model.party_address));

            holder.nameChef.setText(Html.fromHtml(Constants.BOLD_START + "Chef name: " + Constants.BOLD_END + model.nameChef));
            holder.uidChef.setText(Html.fromHtml(Constants.BOLD_START + "Chef id: " + Constants.BOLD_END + model.uidChef));
            holder.numberChef.setText(Html.fromHtml(Constants.BOLD_START + "Chef number: " + Constants.BOLD_END + model.numberChef));
            holder.expertInChef.setText(Html.fromHtml(Constants.BOLD_START + "Chef expert in: " + Constants.BOLD_END + model.expertInChef));
            holder.highestQualificationChef.setText(Html.fromHtml(Constants.BOLD_START + "Chef highest qualification: " + Constants.BOLD_END + model.highestQualificationChef));
            holder.experienceYearsChef.setText(Html.fromHtml(Constants.BOLD_START + "Chef experience in years: " + Constants.BOLD_END + model.experienceYearsChef));

        }

        @Override
        public int getItemCount() {
            if (tasksArrayList == null)
                return 0;
            return tasksArrayList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {
            TextView name, id, staff_required, payment,
                    occasion, party_date, number_of_people, time,
                    number_of_dishes, cuisines, party_address,
                    nameChef,
                    uidChef,
                    numberChef,
                    expertInChef,
                    highestQualificationChef,
                    experienceYearsChef;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                uidChef = v.findViewById(R.id.uid_chef_completed_jobs_item);
                nameChef = v.findViewById(R.id.name_chef_completed_jobs_item);
                numberChef = v.findViewById(R.id.number_chef_completed_jobs_item);
                expertInChef = v.findViewById(R.id.expert_in_chef_completed_jobs_item);
                highestQualificationChef = v.findViewById(R.id.highest_qualification_completed_jobs_item);
                experienceYearsChef = v.findViewById(R.id.experience_years_completed_jobs_item);
                name = v.findViewById(R.id.name_completed_jobs_item);
                id = v.findViewById(R.id.id_number_completed_jobs_item);
                staff_required = v.findViewById(R.id.staff_required_completed_jobs_item);
                payment = v.findViewById(R.id.payment_completed_jobs_item);
                occasion = v.findViewById(R.id.occasion_completed_jobs_item);
                party_date = v.findViewById(R.id.party_date_completed_jobs_item);
                number_of_people = v.findViewById(R.id.number_of_people_completed_jobs_item);
                time = v.findViewById(R.id.time_completed_jobs_item);
                number_of_dishes = v.findViewById(R.id.number_of_dishes_completed_jobs_item);
                cuisines = v.findViewById(R.id.cuisines_completed_jobs_item);
                party_address = v.findViewById(R.id.party_address_completed_jobs_item);
            }
        }

    }
}