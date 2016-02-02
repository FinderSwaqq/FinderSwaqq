package com.example.nicole.finderswaqqq;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ImageAdapter _adapter;
    private ArrayList<LostItem> _items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("FinderSwaqq");

        if(savedInstanceState != null)
        {
            _items = savedInstanceState.getParcelableArrayList("lostItems");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(HomeActivity.this, EditScreenActivity.class);
                startActivityForResult(sendIntent, 123);
                //handle on click listeners with Edit and Add buttons
            }
        }); //set negative button text "Add"

        GridView gridview;
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //build alert dialog with AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                //set dialog title
                builder.setTitle("Options");
                //set dialog message "What would you like to do?"
                builder.setMessage("Edit or Find Device");
                //set positive button text "Edit"
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent sendIntent = new Intent(HomeActivity.this, EditScreenActivity.class);
                        LostItem lostitem = _items.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("lostItem", lostitem);

                        sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        sendIntent.putExtras(bundle);

                        startActivityForResult(sendIntent, 123);

                    }
                });
                builder.setNegativeButton("Find", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
        _adapter = new ImageAdapter(this);
        gridview.setAdapter(_adapter);

    }

    private boolean itemsContainItemId(int id)
    {
        for(LostItem item : _items)
        {
            if(item.itemId == id)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LostItem lostItem = (LostItem)data.getParcelableExtra("lostItem");
        if(itemsContainItemId(lostItem.itemId))
        {
            int position = lostItem.itemId - 1;
            _items.remove(position);
            _items.add(position, lostItem);
        }
        else
        {
            lostItem.itemId = _items.size() + 1;
            _items.add(lostItem);
        }
        _adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listItems", _items);
    }

    public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return _items.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View RelativeLayout = getLayoutInflater().inflate(R.layout.grid_item_layout, null);
        ImageView iv = (ImageView) RelativeLayout.findViewById(R.id.item_icon);
        iv.setImageResource(_items.get(position).image);
        //this is probably wrong^ we don't want only one picture right-- do we have to make multiple of these
        TextView tv = (TextView) RelativeLayout.findViewById(R.id.item_text);
        tv.setText(_items.get(position).name);
        //what the heck ^is it asking for

        return RelativeLayout;
    }

//    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.remote1, R.drawable.keys,
//            R.drawable.remote2, R.drawable.remote3,
//            R.drawable.greenbutton, R.drawable.greenbutton,
//            R.drawable.greenbutton, R.drawable.greenbutton,
//            R.drawable.greenbutton,R.drawable.greenbutton,
//            R.drawable.greenbutton, R.drawable.greenbutton,
//            R.drawable.greenbutton,R.drawable.greenbutton,
//            R.drawable.greenbutton, R.drawable.greenbutton,
//            R.drawable.greenbutton, R.drawable.greenbutton,
//            R.drawable.greenbutton, R.drawable.greenbutton,
//            R.drawable.greenbutton, R.drawable.greenbutton
//
//    };
}
}

