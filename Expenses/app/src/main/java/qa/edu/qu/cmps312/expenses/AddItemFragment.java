package qa.edu.qu.cmps312.expenses;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import qa.edu.qu.cmps312.expenses.Adapters.SpinnerCustomAdapter;
import qa.edu.qu.cmps312.expenses.Model.Category;
import qa.edu.qu.cmps312.expenses.SQLHelper.DatabaseOpenHelper;

/**
 * Created by sarahalhussaini on 11/28/16.
 */

//to avoid redundancy and because of layout similarities, I'm having a single layout for both
//items to add and having minor changes depending on the type selected

public class AddItemFragment extends Fragment {

    //private Context mContext;
    private onFragmentInteractionListener mListener;
    private ImageView mImgView;
    private Bitmap mPhoto = null;
    private TextView dateTV;
    private TextView amountTV;
    private String imgPath = "";
    private String selectedCategory = "";
    private char isRecurring = ' ';
    private String itemType = "";
    private ArrayList<Category> categories = new ArrayList<>();
    private float amount = 0.0f;
    private String dateStr;


    public AddItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mContext = getActivity();
//        Log.i("TAG",mContext.getPackageName());

        if (mListener == null) {
            try {
                mListener = (onFragmentInteractionListener) getActivity();
            } catch (ClassCastException e) {
                throw new ClassCastException(mListener.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }


    }

    public static AddItemFragment newInstance(String key, String value) {
        //passing the key as well because
        //I can't access the context methods from a static method


        //@@@@@@@@@@@@@@@@@@@@@try passing the context SARAAAH !!!------------>>>>>>>@@@@@@@%%%%%
        Bundle args = new Bundle();
        args.putString(key, value);
        AddItemFragment fragment = new AddItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        itemType = getArguments().getString(getString(R.string.ITEM_TYPE));
        //Log.i("TAG", itemType);
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);


        Spinner categorySpinner = (Spinner) view.findViewById(R.id.spinner);
        Button unclickableIncomeBtn = (Button) view.findViewById(R.id.unclickableIncomeBtn);
        Button unclickableExpenseBtn = (Button) view.findViewById(R.id.unclickableExpenseBtn);
        ImageButton addImageBtn = (ImageButton) view.findViewById(R.id.addImageBtn);
        Button dateBtn = (Button) view.findViewById(R.id.pickDateBtn);
        Button addAmountBtn = (Button) view.findViewById(R.id.addAmountBtn);
        Switch recurringSwitch = (Switch) view.findViewById(R.id.recurringSwitch);
        dateTV = (TextView) view.findViewById(R.id.dateTextv);
        amountTV = (TextView) view.findViewById(R.id.amountTextv);
        Button addItemBtn = (Button) view.findViewById(R.id.addItemBtn);
        mImgView = (ImageView) view.findViewById(R.id.imageV);

        if (getString(R.string.TYPE_EXPENSE).equals(itemType)) {

            itemType = getString(R.string.TYPE_EXPENSE);

        } else if (getString(R.string.TYPE_INCOME).equals(itemType)) { // if the item to add was an income

            itemType = getString(R.string.TYPE_INCOME);

            //minor changes to the expense layout
            unclickableExpenseBtn.setEnabled(false);
            unclickableExpenseBtn.setBackgroundColor(getResources().getColor(R.color.lightGrey));

            unclickableIncomeBtn.setEnabled(true);
            unclickableIncomeBtn.setBackgroundColor(getResources().getColor(R.color.white));
        }

        fillArrayList(itemType);
        SpinnerCustomAdapter spinnerAdapter = new SpinnerCustomAdapter(getActivity(), R.layout.spinner_row, R.id.spinnerTV, categories);

//        Log.i("TAG", String.valueOf(categorySpinner.getPrompt()));
        categorySpinner.setPrompt("Please select a category");

        //setting the adapter for category spinner
        categorySpinner.setAdapter(spinnerAdapter);

        // Log.i("TAG", String.valueOf(categorySpinner.getPrompt()));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = categories.get(i).getCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking whether I have been granted the camera permission
                //returns either GRANTED or DENIED
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    takeAPhoto();
                } else { //if DENIED --> request the permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}
                            , getResources().getInteger(R.integer.CAMERA_INTENT_REQ_CODE));
                }
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(AddItemFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        dateStr = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                        dateTV.setText(dateStr);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        addAmountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View npView = inflater.inflate(R.layout.number_picker, null);

                final NumberPicker np = (NumberPicker) npView.findViewById(R.id.numberPicker);
                //won't work if these values are not set
                np.setMaxValue(getResources().getInteger(R.integer.NUMBERPICKER_MAX));
                np.setMinValue(getResources().getInteger(R.integer.NUMBERPICKER_MIN));

                new AlertDialog.Builder(AddItemFragment.this.getActivity())
                        .setTitle("Amount")
                        .setView(npView)
                        .setPositiveButton("Set",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        amountTV.setText(String.valueOf(np.getValue()) + " QR");
                                        amount = np.getValue();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                })
                        .show();

            }
        });

        recurringSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRecurring = (b ? 't' : 'f');
            }
        });


        //Adding the item to DB
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPhoto != null) {
                    //ensure no duplicates
                    String imageName = String.valueOf(new Random(System.currentTimeMillis())) + ".png";
                    imgPath = saveToInternalSorage(mPhoto, imageName);
                    //loadImageFromStorage(imagePath,imageName);            //just to check if we can retrieve it from the memory
                }

                SharedPreferences preferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
                Float storedPreference = preferences.getFloat(getString(R.string.BALANCE), 0);

                //validation before adding
                if (itemType.equals(getString(R.string.TYPE_EXPENSE))) {
                    if (amount >= storedPreference) { //will show only if amount is greater than balance

                        AddItemFragment.showDialog(AddItemFragment.this.getActivity(), R.string.expenseGreaterErrTitle, R.string.expenseGreaterErrMsg);
                        return;
                    }
                }

                if (amount == Float.parseFloat(getString(R.string.amountHolder))) { //if no amount was entered

                    AddItemFragment.showDialog(AddItemFragment.this.getActivity(), R.string.amountZeroTitle, R.string.amountZeroMsg);
                    return;
                }

                if (selectedCategory.equals(categories.get(0).getCategory())) {

                    AddItemFragment.showDialog(AddItemFragment.this.getActivity(), R.string.categoryErrDialogTitle, R.string.categoryErrDialogMsg);
                    return;
                }

                if (dateStr.equals(R.string.dateHolder)) {
                    AddItemFragment.showDialog(AddItemFragment.this.getActivity(), R.string.noDateDialogTitle, R.string.noDateDialogMsg);
                    return;
                }

                ContentValues values = new ContentValues();

                values.put(DatabaseOpenHelper.AMOUNT, amountTV.getText().toString());
                values.put(DatabaseOpenHelper.DATE, dateTV.getText().toString());
                values.put(DatabaseOpenHelper.CATEGORY, selectedCategory);
                values.put(DatabaseOpenHelper.RECURRING, String.valueOf(isRecurring));
                values.put(DatabaseOpenHelper.TYPE, itemType);
                values.put(DatabaseOpenHelper.IMAGE_PATH, imgPath);
//                values.put(DatabaseOpenHelper.LATIT, lati);
//                values.put(DatabaseOpenHelper.LONG, longi);

                mListener.addItem(values);
                getFragmentManager().popBackStack(); //--> doesn't provide refresh

            }
        });


        return view;
    }

    private void fillArrayList(String itemType) {

        String[] strings = null;
        TypedArray drawables = null;
        if (getString(R.string.TYPE_EXPENSE).equals(itemType)) {

            strings = getResources().getStringArray(R.array.expense_categories);
            drawables = getResources().obtainTypedArray(R.array.expense_categories_drawables);


        } else if (getString(R.string.TYPE_INCOME).equals(itemType)) {

            strings = getResources().getStringArray(R.array.income_categories);
            drawables = getResources().obtainTypedArray(R.array.income_categories_drawables);

        }

        for (int i = 0; i < strings.length; i++) {
//            Log.i("TAG", String.valueOf(drawables.getResourceId(i, -1)));
//            Log.i("TAG", String.valueOf(strings[i]));
            categories.add(new Category(strings[i], drawables.getResourceId(i, -1)));
        }

    }

    public void takeAPhoto() {
        //an implicit intent to take an image
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //checking whether there exists an app to receive the intent
        if (i.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(i, R.integer.CAMERA_INTENT_REQ_CODE);
        } else {
            Toast.makeText(getActivity(), R.string.camera_toast_message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takeAPhoto();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == R.integer.CAMERA_INTENT_REQ_CODE && resultCode == Activity.RESULT_OK) {

            //the key "data" has the image captured
            mPhoto = (Bitmap) data.getExtras().get(getString(R.string.DATA));
            mImgView.setImageBitmap(mPhoto);
        }

    }

    private String saveToInternalSorage(Bitmap bitmapImage, String imageName) {
        ContextWrapper cw = new ContextWrapper(AddItemFragment.this.getActivity());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return mypath.getAbsolutePath();
    }

    public static void showDialog(Context context, int titleVal, int msgVal) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(titleVal);
        alertDialog.setMessage(msgVal);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

}
