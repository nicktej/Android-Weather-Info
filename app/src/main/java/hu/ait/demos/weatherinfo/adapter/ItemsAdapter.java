package hu.ait.demos.weatherinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import hu.ait.demos.weatherinfo.MainActivity;
import hu.ait.demos.weatherinfo.R;
import hu.ait.demos.weatherinfo.WeatherDetailsActivity;
import hu.ait.demos.weatherinfo.data.Item;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    private List<Item> itemsList;
    private Context context;
    private int lastPosition = -1;

    public ItemsAdapter(List<Item> itemsList, Context context) {
        this.itemsList = itemsList;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.tvName.setText(itemsList.get(position).getCity());
        setAnimation(viewHolder.itemView, position);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CITY_NAME = "CITY_NAME";
                Intent intentStart = new Intent(context,
                        WeatherDetailsActivity.class);
                intentStart.putExtra(CITY_NAME, viewHolder.tvName.getText());
                context.startActivity(intentStart);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void addItem(Item item) {
        itemsList.add(item);
        notifyDataSetChanged();
    }

    public void updateItem(int index, Item item) {
        itemsList.set(index, item);
        notifyItemChanged(index);

    }

    public void removeItem(int index) {
        ((MainActivity)context).deleteItem(itemsList.get(index));
        itemsList.remove(index);
        notifyItemRemoved(index);
    }

    public void removeAllItem() {
        ((MainActivity)context).deleteAllItem();
        notifyItemRangeRemoved(0, itemsList.size());
        itemsList.clear();
    }


    public void swapItems(int oldPosition, int newPosition) {
        if (oldPosition < newPosition) {
            for (int i = oldPosition; i < newPosition; i++) {
                Collections.swap(itemsList, i, i + 1);
            }
        } else {
            for (int i = oldPosition; i > newPosition; i--) {
                Collections.swap(itemsList, i, i - 1);
            }
        }
        notifyItemMoved(oldPosition, newPosition);
    }

    public Item getItem(int i) {
        return itemsList.get(i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
