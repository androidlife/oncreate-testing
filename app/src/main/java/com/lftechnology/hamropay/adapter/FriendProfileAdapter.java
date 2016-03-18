package com.lftechnology.hamropay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.models.User;

import java.util.ArrayList;
import java.util.HashMap;

import timber.log.Timber;


/**
 * holds the value for the profile of user
 */
public class FriendProfileAdapter extends ArrayAdapter<User> {

    private Context context;
    private ArrayList<User> userInfoList = new ArrayList<>();
    private ArrayList<User> filteredData = new ArrayList<>();
    private LayoutInflater inflater;
    private ItemFilter mFilter = new ItemFilter();

    private HashMap<Integer, String> userNameMap;

    public FriendProfileAdapter(Context context, int textViewResourceId, ArrayList<User> userInfo) {
        super(context, textViewResourceId, userInfo);
        this.context = context;
        this.userInfoList = new ArrayList<>();
        userInfoList.addAll(userInfo);
        this.filteredData = new ArrayList<>();
        filteredData.addAll(userInfo);
        userNameMap = new HashMap<>();
        for (int i = 0; i < userInfoList.size(); i++) {
            userNameMap.put(i, userInfoList.get(i).getUserName());
        }
        getFilter();
    }

    @Override
    public User getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_list_item, null);
        }

        TextView txtUserName = (TextView) convertView.findViewById(R.id.search_value);
        txtUserName.setText(filteredData.get(position).getUserName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    /**
     * Implementing search method in the ArrayList
     *
     * @see http://www.mysamplecode.com/2012/07/android-listview-custom-layout-filter.html
     */
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {

            String filterString = constraint.toString();

            Timber.d("Search value :: %s", filterString);

            FilterResults results = new FilterResults();

            final ArrayList<User> list = userInfoList;

            int count = list.size();
            final ArrayList<User> nlist = new ArrayList<>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = userNameMap.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                    Timber.d("Search value :: %s", filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        /**
         * TODO Database add functionality from database pass {@link User} in ArrayList
         *
         * @param constraint
         * @param results
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData.clear();
            Timber.d("Result is null =%b", results == null);
            Timber.d("Result value is null=%b", results.values == null);
            if (results.values instanceof ArrayList) {
                Timber.d("Is instace of ArrayList");
            }
            filteredData.addAll((ArrayList<User>) results.values);
            notifyDataSetChanged();
           /* filteredData = (ArrayList<String>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0; i < filteredData.size(); i++) {
                add(filteredData.get(i));
                Timber.d("Search value :: %s", filteredData.get(i).toString());
                notifyDataSetInvalidated();
            }
*/
        }
    }
}

