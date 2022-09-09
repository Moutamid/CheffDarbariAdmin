package com.moutamid.cheffdarbariadminn.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.cheffdarbariadminn.R;
import com.moutamid.cheffdarbariadminn.databinding.FragmentAcceptedJobsBinding;
import com.moutamid.cheffdarbariadminn.models.JobsAdminModel2;
import com.moutamid.cheffdarbariadminn.utils.Constants;

import java.util.ArrayList;

public class AcceptedJobsFragment extends Fragment {
    private static final String TAG = "AcceptedJobsFragment";
    LinearLayoutManager linearLayoutManager;

    private FragmentAcceptedJobsBinding b;
    private ProgressDialog progressDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAcceptedJobsBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded()) return b.getRoot();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        linearLayoutManager = new LinearLayoutManager(requireContext());
        Constants.databaseReference()
                .child(Constants.ACCEPTED_JOBS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!isAdded()) return;
                        if (snapshot.exists()) {
                            tasksArrayList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                JobsAdminModel2 adminModel = dataSnapshot.getValue(JobsAdminModel2.class);
                                tasksArrayList.add(adminModel);
                            }
                            progressDialog.dismiss();
                            initRecyclerView();
                        }else {
                            progressDialog.dismiss();

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

    private ArrayList<JobsAdminModel2> tasksArrayList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private void initRecyclerView() {

        conversationRecyclerView = b.acceptedJobsRecyclerview;
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

    }

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_jobs_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            JobsAdminModel2 model = tasksArrayList.get(position);

            holder.name.setText(Html.fromHtml(Constants.BOLD_START + "Customer Name: " + Constants.BOLD_END + model.name));

            holder.id.setText(Html.fromHtml(Constants.BOLD_START + "Job Id: " + Constants.BOLD_END + model.id));

            holder.staff_required.setText(Html.fromHtml(Constants.BOLD_START + "Staff Required: " + Constants.BOLD_END + model.staff_required));
            holder.payment.setText(Html.fromHtml(Constants.BOLD_START + "Payment: " + Constants.BOLD_END + "₹"+model.payment));
            holder.occasion.setText(Html.fromHtml(Constants.BOLD_START + "Occasion Type: " + Constants.BOLD_END + model.occasion_type));
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
            try {
                holder.post.setText(Html.fromHtml(Constants.BOLD_START + "Post: " + Constants.BOLD_END + model.post));
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // REMOVE CURRENT JOB MODEL FROM ACCEPTED CHILD
                    Constants.databaseReference()
                            .child(Constants.ACCEPTED_JOBS)
                            .child(model.push_key)
                            .removeValue();

                    // MAKING JOB OPEN STATUS TRUE
                    Constants.databaseReference()
                            .child(Constants.ADMIN_BOOKINGS)
                            .child(model.push_key)
                            .child("job_open")
                            .setValue(true);

                    // REMOVING JOB DATA FROM CHEF'S CHILD
                    Constants.databaseReference()
                            .child(Constants.USERS)
                            .child(Constants.CHEF)
                            .child(model.uidChef)
                            .child(Constants.ACCEPTED_JOBS)
                            .child(model.push_key)
                            .removeValue();

                    Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
                }
            });

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
                    experienceYearsChef,
                    post;
            Button rejectBtn;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                uidChef = v.findViewById(R.id.uid_chef_accepted_jobs_item);
                nameChef = v.findViewById(R.id.name_chef_accepted_jobs_item);
                numberChef = v.findViewById(R.id.number_chef_accepted_jobs_item);
                expertInChef = v.findViewById(R.id.expert_in_chef_accepted_jobs_item);
                highestQualificationChef = v.findViewById(R.id.highest_qualification_accepted_jobs_item);
                experienceYearsChef = v.findViewById(R.id.experience_years_accepted_jobs_item);
                name = v.findViewById(R.id.name_accepted_jobs_item);
                id = v.findViewById(R.id.id_number_accepted_jobs_item);
                staff_required = v.findViewById(R.id.staff_required_accepted_jobs_item);
                payment = v.findViewById(R.id.payment_accepted_jobs_item);
                occasion = v.findViewById(R.id.occasion_accepted_jobs_item);
                party_date = v.findViewById(R.id.party_date_accepted_jobs_item);
                number_of_people = v.findViewById(R.id.number_of_people_accepted_jobs_item);
                time = v.findViewById(R.id.time_accepted_jobs_item);
                number_of_dishes = v.findViewById(R.id.number_of_dishes_accepted_jobs_item);
                cuisines = v.findViewById(R.id.cuisines_accepted_jobs_item);
                party_address = v.findViewById(R.id.party_address_accepted_jobs_item);
                rejectBtn = v.findViewById(R.id.reject_button_accepted_job);
                post = v.findViewById(R.id.post_accepted_jobs_item);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }
}