package com.example.jasonpather.bigtunes;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.stream.Collectors;

public class EditCategoriesActivity extends AppCompatActivity {

    private ArrayAdapter categoryDisplayAdapter;
    private AlertDialog addButtonDialog;
    private List<String> masterCategoriesString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        createCategoryDisplayAdapter();
        displayCategories();
        configureAddButton();
    }

    private void createCategoryDisplayAdapter() {
        masterCategoriesString = MainActivity.masterCategories.stream().map(x -> x.getText()).collect(Collectors.toList());
        categoryDisplayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, masterCategoriesString);
    }

    private void shortToast(String text)
    {
        Toast toast = Toast.makeText(EditCategoriesActivity.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void configureAddButton() {

        AlertDialog.Builder addButtonBuilder = new AlertDialog.Builder(EditCategoriesActivity.this);
        addButtonBuilder.setCancelable(true);

        LayoutInflater layoutInflater = LayoutInflater.from(EditCategoriesActivity.this);
        View addButtonView = layoutInflater.inflate(R.layout.add_dialog, null);
        addButtonBuilder.setView(addButtonView);

        final EditText addButtonInputText = addButtonView.findViewById(R.id.edittext);
        addButtonInputText.setInputType(InputType.TYPE_CLASS_TEXT);
        addButtonInputText.setSingleLine(false);
        addButtonInputText.setHorizontalScrollBarEnabled(false);

        addButtonBuilder.setPositiveButton(
                "Add",
                (dialog, id1) -> {
                    MainActivity.masterCategories.add(new Category(addButtonInputText.getText().toString()));
                    masterCategoriesString.add(addButtonInputText.getText().toString());
                    categoryDisplayAdapter.notifyDataSetChanged();
                    dialog.cancel();
                    shortToast("Category added!");
                });

        addButtonBuilder.setNegativeButton(
                "Cancel",
                (dialog, id12) -> dialog.cancel());

        addButtonDialog = addButtonBuilder.create();
    }

    public void addButtonClick(View view) {
        addButtonDialog.show();
    }

    public void doneButtonClick(View view) {
        finish();
    }

    private void displayCategories() {
        ListView categoriesListView = findViewById(R.id.categoriesList);
        categoriesListView.setAdapter(categoryDisplayAdapter);
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                AlertDialog.Builder editCategoryBuilder = new AlertDialog.Builder(EditCategoriesActivity.this);
                editCategoryBuilder.setCancelable(true);

                LayoutInflater layoutInflater = LayoutInflater.from(EditCategoriesActivity.this);
                View editCategoryView = layoutInflater.inflate(R.layout.edit_dialog, null);
                editCategoryBuilder.setView(editCategoryView);

                final EditText editCategoryInput = editCategoryView.findViewById(R.id.edittext);
                editCategoryInput.setInputType(InputType.TYPE_CLASS_TEXT);
                editCategoryInput.setSingleLine(false);
                editCategoryInput.setHorizontalScrollBarEnabled(false);
                editCategoryInput.setText(masterCategoriesString.get(position));
                editCategoryInput.setSelectAllOnFocus(true);

                editCategoryBuilder.setPositiveButton(
                        "Ok",
                        (dialog, id1) -> {
                            MainActivity.masterCategories.get(position).setText(editCategoryInput.getText().toString());
                            masterCategoriesString.set(position, editCategoryInput.getText().toString());
                            categoryDisplayAdapter.notifyDataSetChanged();
                            dialog.cancel();
                            shortToast("Category edited!");
                        });

                editCategoryBuilder.setNegativeButton(
                        "Cancel",
                        (dialog, id12) -> dialog.cancel());

                editCategoryBuilder.setNeutralButton(
                        "Delete",
                        (dialog, id13) -> {
                            MainActivity.masterCategories.remove(position);
                            masterCategoriesString.remove(position);
                            categoryDisplayAdapter.notifyDataSetChanged();
                            dialog.cancel();
                            shortToast("Category deleted!");
                        });

                AlertDialog editCategoryDialog = editCategoryBuilder.create();
                editCategoryDialog.show();
            }
        });
    }
}
