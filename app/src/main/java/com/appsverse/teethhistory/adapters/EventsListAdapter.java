package com.appsverse.teethhistory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appsverse.teethhistory.R;
import com.appsverse.teethhistory.repository.EventModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    private final List<EventModel> eventModels;
    private final LayoutInflater inflater;

    private ItemClickListener itemClickListener;

    public static int selectedPos = RecyclerView.NO_POSITION;

    public EventsListAdapter(Context context, List<EventModel> eventModels) {
        this.eventModels = eventModels;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventModel event = eventModels.get(position);

        holder.dateTV.setText(new SimpleDateFormat("dd.MM.yyyy").format(event.getDate()));

        setWarrantyTV(holder, event);

        holder.actionTV.setText(holder.itemView.getContext().getResources().getStringArray(R.array.actions)[event.getAction()]);

        switch (event.getAction()) {
            case EventModel.CLEANED:
                holder.icon.setImageResource(R.drawable.ic_cleaned);
                break;
            case EventModel.EXTRACTED:
                holder.icon.setImageResource(R.drawable.ic_extracted);
                break;
            case EventModel.FILLED:
                holder.icon.setImageResource(R.drawable.ic_filled);
                break;
            case EventModel.GROWN:
                holder.icon.setImageResource(R.drawable.ic_grown);
                break;
            case EventModel.IMPLANTED:
                holder.icon.setImageResource(R.drawable.ic_implanted);
                break;
            default:
                holder.icon.setImageResource(R.drawable.ic_other);
        }

        holder.itemView.setSelected(selectedPos == position);
    }

    private void setWarrantyTV(@NonNull ViewHolder holder, EventModel event) {

        Date warrantyLastDate = getWarrantyLastDate(event);
        long warrantyDaysLeft = TimeUnit.DAYS.convert(warrantyLastDate.getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);

        if (event.getAction() == EventModel.GROWN) {
            holder.warrantyTV.setVisibility(View.GONE);
        } else {
            holder.warrantyTV.setVisibility(View.VISIBLE);
        }

        if (warrantyDaysLeft % 100 < 1) {
            holder.warrantyTV.setText(holder.itemView.getContext().getString(R.string.warranty_expired));
        } else if (warrantyDaysLeft % 100 == 1) {
            holder.warrantyTV.setText(warrantyDaysLeft + holder.itemView.getContext().getString(R.string.days_of_warranty_left_1));
        } else if (warrantyDaysLeft % 100 > 1 && warrantyDaysLeft % 100 < 5) {
            holder.warrantyTV.setText(warrantyDaysLeft + holder.itemView.getContext().getString(R.string.days_of_warranty_left_2_4));
        }else if (warrantyDaysLeft % 100 > 4 && warrantyDaysLeft % 100 < 21) {
            holder.warrantyTV.setText(warrantyDaysLeft + holder.itemView.getContext().getString(R.string.days_of_warranty_left_5_20));
        } else {

            if (warrantyDaysLeft % 10 == 1) {
                holder.warrantyTV.setText(warrantyDaysLeft + holder.itemView.getContext().getString(R.string.days_of_warranty_left_1));
            } else if (warrantyDaysLeft % 10 > 1 && warrantyDaysLeft % 10 < 5) {
                holder.warrantyTV.setText(warrantyDaysLeft + holder.itemView.getContext().getString(R.string.days_of_warranty_left_2_4));
            } else {
                holder.warrantyTV.setText(warrantyDaysLeft + holder.itemView.getContext().getString(R.string.days_of_warranty_left_5_20));
            }
        }
    }

    private Date getWarrantyLastDate(EventModel event) {
        Date referenceDate = event.getDate();
        Calendar c = Calendar.getInstance();
        c.setTime(referenceDate);
        c.add(Calendar.MONTH, event.getWarranty());
        return c.getTime();
    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView dateTV, warrantyTV, actionTV, optionsMenu;
        final ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.holder_icon);
            dateTV = itemView.findViewById(R.id.item_event_date);
            warrantyTV = itemView.findViewById(R.id.item_warranty);
            actionTV = itemView.findViewById(R.id.item_event_action);
            optionsMenu = itemView.findViewById(R.id.itemEventOptions);
            optionsMenu.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAbsoluteAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
