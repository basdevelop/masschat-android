package com.flowerbell.masschat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.domain.BusinessNameCard;
import com.flowerbell.masschat.ui.activity.ChatMessageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by wsli on 2017/7/26.
 */

public class PeripheralFriendsAdapter extends RecyclerView.Adapter<PeripheralFriendsAdapter.FriendItemViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    public static List<BusinessNameCard> _currentNameCardList;

    public PeripheralFriendsAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        _currentNameCardList = new ArrayList<>();
    }

    @Override
    public FriendItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendItemViewHolder(mLayoutInflater.inflate(R.layout.item_nearby, parent, false));
    }

    @Override
    public void onBindViewHolder(FriendItemViewHolder holder, int position) {
        BusinessNameCard name_card = _currentNameCardList.get(position);
        holder.mNameView.setText(name_card.getName());

        String signature = name_card.getTittle()+"-"+name_card.getCompany();
        holder.mSignature.setText(signature);
    }

    @Override
    public int getItemCount() {
        return _currentNameCardList.size();
    }

    public void refreshNameCardList(List<BusinessNameCard> cards_list) {
        notifyItemRangeRemoved(0, _currentNameCardList.size());
        _currentNameCardList.clear();
        _currentNameCardList.addAll(cards_list);
        notifyItemRangeInserted(0, _currentNameCardList.size());
    }

    public class FriendItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_name)
        TextView mNameView;

        @BindView(R.id.tv_signature)
        TextView mSignature;

        FriendItemViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (ListViewCompat.NO_POSITION == position){
                return;
            }

            BusinessNameCard name_card = _currentNameCardList.get(position);
            Intent intent = new Intent(mContext, ChatMessageActivity.class);
            intent.putExtra("target", name_card.getMacAddress());
            mContext.startActivity(intent);
        }
    };
}
