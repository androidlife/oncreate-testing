package com.lftechnology.hamropay.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lftechnology.hamropay.GlideConfigurator;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.db.DbManager;
import com.lftechnology.hamropay.db.models.Transaction;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.model.Constants;
import com.lftechnology.hamropay.utils.Extras;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * View profile of a specific friend
 */
public class FriendProfileActivity extends BaseActivity {

    @Bind(R.id.btn_send_message)
    Button btnSendMessage;

    @Bind(R.id.rl_request_money)
    RelativeLayout rlRequestMoney;

    @Bind(R.id.rl_send_money)
    RelativeLayout rlSendMoney;

    @Bind(R.id.txt_send_money)
    TextView txtVwSendMoney;

    @Bind(R.id.img_profile_image)
    ImageView imgProfileImage;

    @Bind(R.id.txt_share_transaction)
    TextView txtShareTransaction;

    User selectedUser;

    public static String SELECTED_USER_ID = "selectedUserId";
    private long selectedUserId = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_profile;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedUserId = getIntent().getLongExtra(SELECTED_USER_ID, 0);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedUser == null && selectedUserId > 0) {
            selectedUser = DbManager.getInstance().getSelectedUserById(selectedUserId);
            setToolBarTitle(selectedUser.getUserName());
            setTextFromUserType(selectedUser.getUserName(), selectedUser.getType());
            GlideConfigurator.load(this, Constants.ASSETS_IMAGE_DIRECTORY + selectedUser.getLocalPhoto())
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(imgProfileImage);
        }
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

    Intent intent;

    @OnClick({R.id.btn_send_message, R.id.rl_request_money, R.id.rl_send_money})
    public void onClickView(View view) {
        intent = new Intent();
        int transactionType = 0;
        switch (view.getId()) {
            case R.id.btn_send_message:
                if (selectedUser.getType() == User.TYPE_NOT_JOINED) {
                    Bundle params = new Bundle();
                    params.putParcelable(Extras.SELECTED_USER, Parcels.wrap(User.class, selectedUser));
                    intent = new Intent(FriendProfileActivity.this, InviteFriend.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
                return;

            case R.id.rl_request_money:
                transactionType = Transaction.TYPE_RECEIVE_MONEY;
                break;
            case R.id.rl_send_money:
                transactionType = Transaction.TYPE_SEND_MONEY;
                break;
        }


    }

    public static Intent launchActivity(Context context, long userId) {
        Intent intent = new Intent(context, FriendProfileActivity.class);
        intent.putExtra(SELECTED_USER_ID, userId);
        return intent;
    }

    /**
     * set text and make button visible/invisible according to the user type {@link User#getType()}
     *
     * @param userName name of the user as String
     * @param userType type of the user as Integer
     */

    private void setTextFromUserType(String userName, int userType) {

        switch (userType) {
            case User.TYPE_NOT_JOINED:
                txtShareTransaction.setText(getResources().getString(R.string.share_transaction) + " " + userName);
                rlRequestMoney.setVisibility(View.GONE);
                rlSendMoney.setVisibility(View.GONE);
                btnSendMessage.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                btnSendMessage.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGrey));
                btnSendMessage.setText(getResources().getString(R.string.invite) + " " + selectedUser.getUserName());

                break;
            case User.TYPE_SERVICE:
                txtShareTransaction.setText(getResources().getString(R.string.make_transaction) + " " + userName);
                rlRequestMoney.setVisibility(View.GONE);
                txtVwSendMoney.setText(getResources().getString(R.string.make_payment));
                break;

            case User.TYPE_STORE:
                txtShareTransaction.setText(getResources().getString(R.string.make_transaction) + " " + userName);
                rlRequestMoney.setVisibility(View.GONE);
                txtVwSendMoney.setText(getResources().getString(R.string.make_payment));
                break;

            case User.TYPE_NORMAL:
                txtShareTransaction.setText(getResources().getString(R.string.share_transaction) + " " + userName);
                break;

            default:
                break;

        }

    }

}
