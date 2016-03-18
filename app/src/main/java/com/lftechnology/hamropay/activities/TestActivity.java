package com.lftechnology.hamropay.activities;

import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.activities.base.BaseActivity;
import com.lftechnology.hamropay.messagemodels.PaymentRequestModel;
import com.lftechnology.hamropay.widgets.CircleLogoLoader;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class TestActivity extends BaseActivity {


    CircleLogoLoader loader;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = (CircleLogoLoader) findViewById(R.id.circle_logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loader.animateProgress();
            }
        }, 1000);

//        circularImageView = (CircleImageView) findViewById(R.id.profile_image);
//        //circularImageView.setBackgroundResource(R.drawable.bajeko_sekuwa);
//        circularImageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.bajeko_sekuwa));

        //circularImageView.setBorderColor(Color.parseColor("ff0000"));
      /*  circularImageView.setBorderWidth(4);
        circularImageView.setBorderColor(Color.BLUE);*/
        //.setBackgroundResource(R.drawable.ic_send_gray);
        //messageModel();
        //initPermission();
    }

//    private void insertNewTransaction() {
//        DbManager.getInstance().insertNewTransaction();
//    }

    private void messageModel() {
        MessageModel someMessageModel = new MessageModel(MessageModel.MessageTypes.NORMAL_TEXT_MESSAGE, true, "Test", "test1",
                "This is message", "receiver", 10, 11, 12, "Some bank", "Card Number", "Some uri", "Additionnal notes", PaymentRequestModel.PaymentStatus.ACCEPTED);
        ArrayList<MessageModel> arr = new ArrayList<>();
        arr.add(someMessageModel);
        someMessageModel = new MessageModel(MessageModel.MessageTypes.NORMAL_TEXT_MESSAGE, true, "Test", "test1",
                "This is message", "receiver", 10, 11, 12, "Some bank", "Card Number", "Some uri", "Additionnal notes", PaymentRequestModel.PaymentStatus.ACCEPTED);
        arr.add(someMessageModel);

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(arr, new TypeToken<List<MessageModel>>() {
        }.getType());
        Timber.d("Element string =%s ", element.toString());
        Timber.d("Element arr string =%s", element.getAsJsonArray().toString());

        String jsonString = element.toString();

        ArrayList<MessageModel> convertedArr = gson.fromJson(jsonString, new TypeToken<List<MessageModel>>() {
        }.getType());
        for (MessageModel messageModel : convertedArr) {
            Timber.d("ConvertedArr msg =%s", messageModel.messageTypes.toString());
        }

        long transactionId = 1456301702292L;
        //DbManager.getInstance().updateTransactionDetail(transactionId, arr);
        //this.finish();
    }
}
