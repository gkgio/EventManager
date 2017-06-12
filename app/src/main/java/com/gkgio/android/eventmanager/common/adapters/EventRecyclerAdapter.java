package com.gkgio.android.eventmanager.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkgio.android.eventmanager.R;
import com.gkgio.android.eventmanager.common.Utils;
import com.gkgio.android.eventmanager.model.Event;
import com.gkgio.android.eventmanager.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Георгий on 11.06.2017.
 * gkgio
 */

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.CategoryItemViewHolder> {

    private final List<Event> eventList;
    private final WeakReference<Context> refContext;

    public EventRecyclerAdapter(Context context) {
        refContext = new WeakReference<>(context);
        eventList = new ArrayList<>();
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_recycler_item, parent, false);
        return new CategoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryItemViewHolder holder, final int position) {

        final Event event = eventList.get(position);
        final Context context = refContext.get();

        if (context != null) {
            holder.tvDate.setText(Utils.formatDateTime("EEE, d MMM yyyy", event.getDateStart()));
            holder.tvTitle.setText(event.getTitle());
            holder.tvDescription.setText(event.getDescription());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).editEvent(event, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setEvents(List<Event> events) {
        eventList.clear();
        eventList.addAll(events);
        notifyDataSetChanged();
    }

    public void setEvent(Event event) {
        eventList.add(event);
        notifyItemInserted(eventList.size() - 1);
        notifyDataSetChanged();
    }

    public void deleteEvent(int position) {
        eventList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, eventList.size());
    }

    public void updateEvent(Event event, int position) {
        eventList.clear();
        eventList.set(position, event);
        notifyDataSetChanged();
    }

    class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        final TextView tvDate;
        final TextView tvTitle;
        final TextView tvDescription;

        public CategoryItemViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}
