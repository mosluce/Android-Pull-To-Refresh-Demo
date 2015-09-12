package tw.ccmos.demo.ultimaterecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mosluce on 15/9/12.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<String> dataSet;

    public MyAdapter(List<String> ds) {
        dataSet = ds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.label.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.label)
        TextView label;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
