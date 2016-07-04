package ua.stepiukyevhen.yevhenstepiuktestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<Message> items = new ArrayList<>();

    public void replaceItems(ArrayList<Message> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void addItem(Message item) {
        items.add(0, item);
        notifyDataSetChanged();
    }

    public ArrayList<Message> getItems() {
        return items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);

            author = (TextView) itemView.findViewById(R.id.author);
            text = (TextView) itemView.findViewById(R.id.text);
        }

        public void setData(Message item) {
            author.setText(item.getAuthor());
            text.setText(item.getText());
        }
    }
}
