package com.moutamid.cheffdarbariadminn.ui;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.cheffdarbariadminn.R;
import com.moutamid.cheffdarbariadminn.activities.NavigationDrawerActivity;
import com.moutamid.cheffdarbariadminn.databinding.FragmentAddJobBinding;
import com.moutamid.cheffdarbariadminn.models.JobsAdminModel;
import com.moutamid.cheffdarbariadminn.notifications.FcmNotificationsSender;
import com.moutamid.cheffdarbariadminn.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddJobFragment extends Fragment {
    private static final String TAG = "AddJobFragment";

    private FragmentAddJobBinding b;

    public JobsAdminModel jobsAdminModel = new JobsAdminModel();
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAddJobBinding.inflate(inflater, container, false);
        View root = b.getRoot();
        if (!isAdded())  return b.getRoot();
        linearLayoutManager = new LinearLayoutManager(requireContext());
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        jobsAdminModel.time = Constants.NULL;
        jobsAdminModel.date = Constants.NULL;
        jobsAdminModel.job_open = true;

        b.occasionTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_occasion_type,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.occasionTypeTv.setText(menuItem.getTitle().toString());
                        jobsAdminModel.occasion_type = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.cityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_cities,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.cityTv.setText(menuItem.getTitle().toString());
                        jobsAdminModel.city = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.dishTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_dishes,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.dishTypeTv.setText(menuItem.getTitle().toString());
                        jobsAdminModel.dish_type = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.staffTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_staff,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.staffTv.setText(menuItem.getTitle().toString());
                        jobsAdminModel.staff_required = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        b.burnersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.getMenuInflater().inflate(
                        R.menu.popup_burners,
                        popupMenu.getMenu()
                );
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        b.burnersTv.setText(menuItem.getTitle().toString());
                        jobsAdminModel.number_of_gas_burners = menuItem.getTitle().toString();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        b.breakfastAddBtn.setOnClickListener(v -> {
            String item = b.breakfastEditText.getText().toString();

            if (item.isEmpty())
                return;
            b.breakFastTextView.setText(b.breakFastTextView.getText().toString() + "-" + item + "\n");
            jobsAdminModel.breakfast_items = b.breakFastTextView.getText().toString();
            b.breakfastEditText.setText("");
        });
        b.breakFastClear.setOnClickListener(v -> {
            b.breakFastTextView.setText("");
            jobsAdminModel.breakfast_items = "";
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
        });
        b.lunchAddBtn.setOnClickListener(v -> {
            String item = b.lunchEditText.getText().toString();

            if (item.isEmpty())
                return;
            b.lunchTextView.setText(b.lunchTextView.getText().toString() + "-" + item + "\n");
            jobsAdminModel.lunch_items = b.lunchTextView.getText().toString();
            b.lunchEditText.setText("");
        });
        b.lunchClear.setOnClickListener(v -> {
            b.lunchTextView.setText("");
            jobsAdminModel.lunch_items = "";
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
        });
        b.dinnerAddBtn.setOnClickListener(v -> {
            String item = b.dinnerEditText.getText().toString();

            if (item.isEmpty())
                return;
            b.dinnerTextView.setText(b.dinnerTextView.getText().toString() + "-" + item + "\n");
            jobsAdminModel.dinner_items = b.dinnerTextView.getText().toString();
            b.dinnerEditText.setText("");
        });
        b.dinnerClear.setOnClickListener(v -> {
            b.dinnerTextView.setText("");
            jobsAdminModel.dinner_items = "";
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
        });
        b.snacksAddBtn.setOnClickListener(v -> {
            String item = b.snacksEditText.getText().toString();

            if (item.isEmpty())
                return;
            b.snacksTextView.setText(b.snacksTextView.getText().toString() + "-" + item + "\n");
            jobsAdminModel.snack_items = b.snacksTextView.getText().toString();
            b.snacksEditText.setText("");
        });
        b.snacksClear.setOnClickListener(v -> {
            b.snacksTextView.setText("");
            jobsAdminModel.snack_items = "";
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
        });
        b.dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long today;
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.clear();
                today = MaterialDatePicker.todayInUtcMilliseconds();
                final MaterialDatePicker.Builder singleDateBuilder = MaterialDatePicker.Builder.datePicker();
                singleDateBuilder.setTitleText("Select Date");
                singleDateBuilder.setSelection(today);
                final MaterialDatePicker materialDatePicker = singleDateBuilder.build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        jobsAdminModel.date = materialDatePicker.getHeaderText();
                        b.dateEt.setText(materialDatePicker.getHeaderText());
                    }
                });
                materialDatePicker.show(getFragmentManager(), "DATE");

            }
        });
        b.timeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mYear, mMonth, mDay, hour, mMinute;
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String current_hour = hourOfDay + "";
                                if (current_hour.length() == 1)
                                    current_hour = "0" + current_hour;

                                String current_minute = minute + "";
                                if (current_minute.length() == 1)
                                    current_minute = "0" + current_minute;

                                String am_pm = "";

                                Calendar datetime = Calendar.getInstance();
                                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                datetime.set(Calendar.MINUTE, minute);

                                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                                    am_pm = " AM";
                                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                                    am_pm = " PM";

                                String time = current_hour + ":" + current_minute;

                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                                    Date dateObj;
                                    dateObj = sdf.parse(time);
                                    time = new SimpleDateFormat("K:mm").format(dateObj);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                jobsAdminModel.time = time + am_pm;
                                b.timeEt.setText(jobsAdminModel.time);
                            }

                        }, hour, mMinute, false);
                timePickerDialog.show();
            }
        });
        b.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (affiliateCheckEntries())
                    return;
                jobsAdminModel.job_open = true;

                progressDialog.show();

                Constants.databaseReference()
                        .child(Constants.ADMIN_LAST_JOB_ID)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    last_id = snapshot.getValue(Long.class);
                                    last_id++;
                                } else {
                                    last_id = 1986;
                                }
                                jobsAdminModel.id = last_id + "";
                                Constants.databaseReference()
                                        .child(Constants.ADMIN_BOOKINGS)
                                        .push()
                                        .setValue(jobsAdminModel)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Constants.databaseReference()
                                                            .child(Constants.ADMIN_LAST_JOB_ID)
                                                            .setValue(last_id)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    uploadNotification();
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(requireContext(), NavigationDrawerActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    requireActivity().finish();
                                                                    startActivity(intent);
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });

        return root;
    }

    long last_id;

    private void uploadNotification() {
        new FcmNotificationsSender(
                "/topics/" + Constants.CHEF_NOTIFICATIONS,
                "New job",
                "Admin has added a new job in " + jobsAdminModel.city,
                requireActivity().getApplicationContext(),
                requireActivity())
                .SendNotifications();
    }

    public boolean affiliateCheckEntries() {
        String name = b.nameEt.getText().toString();
        String mobileNmbr = b.numberEt.getText().toString();
        String party_addres = b.partyAddressEt.getText().toString();
        String location = b.locationEt.getText().toString();
        String number_of_people = b.numberOfPeople.getText().toString();
        String no_of_dishes = b.noOfDishesEt.getText().toString();
        String payment = b.paymentEt.getText().toString();

        if (name.isEmpty()) {
            toast("Please enter your name");
            return true;
        } else jobsAdminModel.name = name;
        if (mobileNmbr.isEmpty()) {
            toast("Please enter your mobileNmbr");
            return true;
        } else jobsAdminModel.number = mobileNmbr;
        if (party_addres.isEmpty()) {
            toast("Please enter party address");
            return true;
        } else jobsAdminModel.party_address = party_addres;
        if (location.isEmpty()) {
            toast("Please enter location");
            return true;
        } else jobsAdminModel.location = location;
        if (number_of_people.isEmpty()) {
            toast("Please enter number of people");
            return true;
        } else jobsAdminModel.number_of_people = number_of_people;
        if (jobsAdminModel.date.equals(Constants.NULL)) {
            toast("Please enter date");
            return true;
        }
        if (jobsAdminModel.time.equals(Constants.NULL)) {
            toast("Please enter time");
            return true;
        }
        if (no_of_dishes.isEmpty()) {
            toast("Please enter your number of dishes");
            return true;
        } else jobsAdminModel.no_of_dishes = no_of_dishes;
        if (payment.isEmpty()) {
            toast("Please enter payment");
            return true;
        } else jobsAdminModel.payment = payment;

        if (b.tandoorCheckBoxAddJob.isChecked()) {
            jobsAdminModel.kitchen_tools_list.add(b.
                    tandoorCheckBoxAddJob.getText().toString());
        }
        if (b.otgCheckBoxAddJob.isChecked()) {
            jobsAdminModel.kitchen_tools_list.add(b.
                    otgCheckBoxAddJob.getText().toString());
        }
        if (b.microwaveCheckBoxAddJob.isChecked()) {
            jobsAdminModel.kitchen_tools_list.add(b.
                    microwaveCheckBoxAddJob.getText().toString());
        }
        if (b.mixerGrinderCheckBoxAddJob.isChecked()) {
            jobsAdminModel.kitchen_tools_list.add(b.
                    mixerGrinderCheckBoxAddJob.getText().toString());
        }
        if (b.foodProcessorCheckBoxAddJob.isChecked()) {
            jobsAdminModel.kitchen_tools_list.add(b.
                    foodProcessorCheckBoxAddJob.getText().toString());
        }
        if (b.handBlenderCheckBoxAddJob.isChecked()) {
            jobsAdminModel.kitchen_tools_list.add(b.
                    handBlenderCheckBoxAddJob.getText().toString());
        }
        if (b.utensilsCheckBoxAddJob.isChecked()) {
            jobsAdminModel.kitchen_tools_list.add(b.
                    utensilsCheckBoxAddJob.getText().toString());
        }

        if (b.northIndianCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    northIndianCheckBoxAddJob.getText().toString());
        }
        if (b.southIndianCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    southIndianCheckBoxAddJob.getText().toString());
        }
        if (b.chineseCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    chineseCheckBoxAddJob.getText().toString());
        }
        if (b.mexicanCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    mexicanCheckBoxAddJob.getText().toString());
        }
        if (b.continentalCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    continentalCheckBoxAddJob.getText().toString());
        }
        if (b.thaiCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    thaiCheckBoxAddJob.getText().toString());
        }
        if (b.barbecueCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    barbecueCheckBoxAddJob.getText().toString());
        }
        if (b.homeFoodCheckBoxAddJob.isChecked()) {
            jobsAdminModel.cuisines_list.add(b.
                    homeFoodCheckBoxAddJob.getText().toString());
        }
        /*String text = System.currentTimeMillis() + "";
        jobsAdminModel.id = text.substring(text.length() - 6);/*/

        return false;
    }

    public void toast(String mcg) {
        Toast.makeText(requireContext(), mcg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }
}