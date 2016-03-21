package com.lftechnology.hamropay.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.adapter.FriendProfileAdapter;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.models.User;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Search friends through contacts
 * Also look onto their profile {@link FriendProfileActivity}
 */
public class SearchListActivity extends BaseActivity {

    @Bind(R.id.img_cancel)
    ImageView imgCancel;
    // List view
    private ListView lv;

    // Listview Adapter
    FriendProfileAdapter profileAdapter;

    // Search EditText
    EditText inputSearch;

    private ArrayList<User> userNameList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        inputSearch.setText("");
    }

    private void init() {
        userNameList = getUser();

        // Adding items to listview
        profileAdapter = new FriendProfileAdapter(this, R.layout.search_list_item, userNameList);
        //Set adapter to ListView
        lv.setAdapter(profileAdapter);

        //enables filtering for the contents of the given ListView
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = (User) lv.getItemAtPosition(position);
                startActivity(FriendProfileActivity.launchActivity(SearchListActivity.this, selectedUser.getUserId()));

            }
        });

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                /** When user changed the Text
                 */
                profileAdapter.getFilter().filter(cs.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.img_cancel)
    public void OnClick() {
        inputSearch.getText().clear();
    }

    /**
     * TODO add functionality from database  Please go to {@link FriendProfileAdapter}
     *
     * @return
     */
    private ArrayList<User> getUser() {

        return new ArrayList<User>(DbManager.getInstance().getAllUsers());
    }
}