package com.pbnj.pbnj.Adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbnj.pbnj.Models.Message;
import com.pbnj.pbnj.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by amgregoi on 7/21/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder>
{


    private List<Message> mMessages;

    public MessageAdapter()
    {
        mMessages = new ArrayList<>();
        mMessages.add(new Message("MrChef9", "I'm learning so much rn :D"));
        mMessages.add(new Message("NomNomBoi", "I just ate all the vegetables from the last step"));
        mMessages.add(new Message("FoodieGirl1972", "Omg this looks good"));
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View lView = LayoutInflater.from(parent.getContext())
                                   .inflate(R.layout.item_message, parent, false);
        MessageHolder lHolder = new MessageHolder(lView);
        return lHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position)
    {
        holder.onBind(position);
    }

    @Override
    public int getItemCount()
    {
        return mMessages.size();
    }

    /**************************************************
     *
     * View holders
     *
     **************************************************/
    public class MessageHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.textViewMessageMessage) TextView mMessageView;

        public MessageHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position)
        {
            Message lMessage = mMessages.get(position);
            int lLength = lMessage.senderName.length();

            SpannableStringBuilder str = new SpannableStringBuilder(String.format(Locale.getDefault(), "%s  %s", lMessage.senderName, lMessage.message));
            str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD_ITALIC), 0, lLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mMessageView.setText(str);
        }
    }
}
