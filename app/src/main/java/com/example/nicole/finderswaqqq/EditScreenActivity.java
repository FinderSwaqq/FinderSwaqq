package com.example.nicole.finderswaqqq;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class EditScreenActivity extends AppCompatActivity {

    private Integer[] iconOptions = new Integer[7];
    private int lostItemId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        iconOptions[0] = R.drawable.remotee;
        iconOptions[1] = R.drawable.remote;
        iconOptions[2] = R.drawable.wallet;
        iconOptions[3] = R.drawable.remotee2;
        iconOptions[4] = R.drawable.remotee3;
        iconOptions[5] = R.drawable.remotee4;
        iconOptions[6] = R.drawable.ipod;


        Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LostItem lostItem = new LostItem();
                lostItem.name = ((EditText) findViewById(R.id.lost_item_edit_text)).getText().toString();

                int iconSelectedPosition = ((Spinner) findViewById(R.id.icon_selection)).getSelectedItemPosition();
                lostItem.image = iconOptions[iconSelectedPosition];
                lostItem.imagePosition = iconSelectedPosition;
                lostItem.itemId = lostItemId;
                Bundle bundle = new Bundle();
                bundle.putParcelable("lostItem", lostItem);

                Intent sendIntent = new Intent(EditScreenActivity.this, HomeActivity.class);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                sendIntent.putExtras(bundle);

                setResult(123, sendIntent);
                finish();
            }
        });

        Spinner iconSpinner = (Spinner)findViewById(R.id.icon_selection);
        iconSpinner.setAdapter(new IconSpinnerAdapter(this, R.layout.spinner_icon, iconOptions));

        if(getIntent().hasExtra("lostItem"))
        {
            LostItem lostitem = getIntent().getParcelableExtra("lostItem");

            ((EditText) findViewById(R.id.lost_item_edit_text)).setText(lostitem.name);

            Spinner spinner = (Spinner) findViewById(R.id.icon_selection);
            spinner.setSelection(lostitem.imagePosition);

            lostItemId = lostitem.itemId;

        }

    }

    private int getIndex(Spinner spinner, Integer myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i) == myString){
                index = i;
            }
        }
        return index;
    }

    public class IconSpinnerAdapter extends ArrayAdapter<Integer>{

        public IconSpinnerAdapter(Context context, int resource, Integer[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        private View getCustomView(int position, View convertView, ViewGroup parent)
        {
            View rootView = getLayoutInflater().inflate(R.layout.spinner_icon, parent, false);
            ImageView iv = (ImageView) rootView.findViewById(R.id.icon_image_view);
            iv.setImageResource(iconOptions[position]);
            return rootView;
        }
    }
}


