package com.moutamid.cheffdarbariadmin.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moutamid.cheffdarbariadmin.R;
import com.moutamid.cheffdarbariadmin.databinding.FragmentAddJobBinding;
import com.moutamid.cheffdarbariadmin.models.JobsAdminModel;
import com.moutamid.cheffdarbariadmin.utils.Constants;

public class AddJobFragment extends Fragment {

    private FragmentAddJobBinding b;

    public JobsAdminModel jobsAdminModel = new JobsAdminModel();
    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentAddJobBinding.inflate(inflater, container, false);
        View root = b.getRoot();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

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
        b.lunchAddBtn.setOnClickListener(v -> {
            String item = b.lunchEditText.getText().toString();

            if (item.isEmpty())
                return;
            b.lunchTextView.setText(b.lunchTextView.getText().toString() + "-" + item + "\n");
            jobsAdminModel.lunch_items = b.lunchTextView.getText().toString();
            b.lunchEditText.setText("");
        });
        b.dinnerAddBtn.setOnClickListener(v -> {
            String item = b.dinnerEditText.getText().toString();

            if (item.isEmpty())
                return;
            b.dinnerTextView.setText(b.dinnerTextView.getText().toString() + "-" + item + "\n");
            jobsAdminModel.dinner_items = b.dinnerTextView.getText().toString();
            b.dinnerEditText.setText("");
        });
        b.snacksAddBtn.setOnClickListener(v -> {
            String item = b.snacksEditText.getText().toString();

            if (item.isEmpty())
                return;
            b.snacksTextView.setText(b.snacksTextView.getText().toString() + "-" + item + "\n");
            jobsAdminModel.snack_items = b.snacksTextView.getText().toString();
            b.snacksEditText.setText("");
        });

        b.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (affiliateCheckEntries())
                    return;
                progressDialog.show();
                Constants.databaseReference()
                        .child(Constants.ADMIN_BOOKINGS)
                        .push()
                        .setValue(jobsAdminModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                                    requireActivity().recreate();
                                }else {
                                    Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        return root;
    }

    public boolean affiliateCheckEntries() {
        String name = b.nameEt.getText().toString();
        String mobileNmbr = b.numberEt.getText().toString();
        String party_addres = b.partyAddressEt.getText().toString();
        String location = b.locationEt.getText().toString();
        String number_of_people = b.numberOfPeople.getText().toString();
        String date = b.dateEt.getText().toString();
        String time = b.timeEt.getText().toString();
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
        if (date.isEmpty()) {
            toast("Please enter date");
            return true;
        } else jobsAdminModel.date = date;
        if (time.isEmpty()) {
            toast("Please enter time");
            return true;
        } else jobsAdminModel.time = time;
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
                    italianCheckBoxAddJob.getText().toString());
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

        return false;
    }

    public void toast(String mcg) {
        Toast.makeText(requireContext(), mcg, Toast.LENGTH_SHORT).show();
    }

}