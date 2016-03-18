package com.lftechnology.hamropay.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lftechnology.hamropay.MessageModel;
import com.lftechnology.hamropay.R;
import com.lftechnology.hamropay.db.models.User;
import com.lftechnology.hamropay.events.ChatEvents;
import com.lftechnology.hamropay.utils.GeneralUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link android.support.v7.widget.RecyclerView.Adapter} for sticker view
 */
public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    public static int[] stickerList = new int[]{R.drawable.bholi_la,
            R.drawable.bhukka_lairacha,
            R.drawable.chito_pathauna,
            R.drawable.dhanyabaad,
            R.drawable.dhewa_maru,
            R.drawable.huncha,
            R.drawable.iloveu,
            R.drawable.kati_paisa, R.drawable.good_morning,
            R.drawable.good_night,
            R.drawable.nai,
            R.drawable.paisa_payo,
            R.drawable.paisa_chaiyo,
            R.drawable.paisa_sakyo,
            R.drawable.taat_paltyo};

    Context context;
    ChatEvents chatEvents;

    //This will be used if there are stickers of multiple category
    int stickerType = 0X1;

    StickerAdapter(Context context, int stickerType) {
        this.context = context;
        chatEvents = (ChatEvents) context;
        this.stickerType = stickerType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sticker_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(stickerList[position]).into(holder.ivSticker);
    }

    @Override
    public int getItemCount() {
        return stickerList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_sticker)
        ImageView ivSticker;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageModel messageModel = new MessageModel(MessageModel.MessageTypes.STICKER,
                            stickerType,
                            getPosition(),
                            GeneralUtils.getFormattedDate(), null, User.TYPE_NORMAL, false);

                    chatEvents.sendSticker(messageModel);
                }
            });
        }


    }
}
