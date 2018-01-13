package com.example.jasonpather.bigtunes;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class EditCategoriesActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private AlertDialog alert11;
    private List<String> masterCategoriesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);
        
        displayCategories();
        setupAddButton();
    }

    public void setupAddButton() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(EditCategoriesActivity.this);
        builder1.setCancelable(true);

        final EditText input = new EditText(EditCategoriesActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder1.setView(input);

        builder1.setPositiveButton(
                "Add",
                (dialog, id1) -> {
                    MainActivity.masterCategories.add(new Category(input.getText().toString()));
                    masterCategoriesString.add(input.getText().toString());
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                });

        builder1.setNegativeButton(
                "Cancel",
                (dialog, id12) -> dialog.cancel());


        alert11 = builder1.create();

    }

    public void addButtonClick(View view) {
        alert11.show();
    }

    private void displayCategories() {
        ListView categoriesList = findViewById(R.id.categoriesList);

        masterCategoriesString = MainActivity.masterCategories.stream().map(x -> x.getText()).collect(Collectors.toList());

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, masterCategoriesString);

        categoriesList.setAdapter(adapter);

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditCategoriesActivity.this);
                builder1.setCancelable(true);


                final EditText input = new EditText(EditCategoriesActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(masterCategoriesString.get(position));
                input.setSelectAllOnFocus(true);

                builder1.setView(input);


                builder1.setPositiveButton(
                        "Ok",
                        (dialog, id1) -> {
                            MainActivity.masterCategories.get(position).setText(input.getText().toString());
                            masterCategoriesString.set(position, input.getText().toString());
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        (dialog, id12) -> dialog.cancel());

                builder1.setNeutralButton(
                        "Delete category",
                        (dialog, id13) -> {
                            MainActivity.masterCategories.remove(position);
                            masterCategoriesString.remove(position);
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                        });


                AlertDialog alert11 = builder1.create();
                alert11.show();
            }

        });



    }


}
