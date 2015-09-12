package tw.ccmos.demo.ultimaterecyclerview;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    List<String> dataSet = new ArrayList<>();
    boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setup();
    }

    private void setup() {
        //RecyclerView Setup
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int top = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                int height = layoutManager.getChildCount();
                int total = layoutManager.getItemCount();

                if (top + height > total - 1) {
                    if (!loading) {
                        loading = true;

                        new DelayTask().execute();
                    }
                }
            }
        });

        //SwipeRefreshLayout Setup
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadData(true);
    }

    private void loadData(boolean clear) {
        if (clear) dataSet.clear();

        for (int i = 1; i <= 15; i++) {
            dataSet.add(String.format("%d at %d", i, new Date().getTime()));
        }

        adapter.notifyDataSetChanged();
    }

    class DelayTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            startLoadMore();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            stopLoadMore();

            loadData(false);
            loading = false;
        }
    }

    private void stopLoadMore() {
        dataSet.remove(dataSet.size() - 1);
        adapter.notifyDataSetChanged();
    }

    private void startLoadMore() {
        dataSet.add("");
        adapter.notifyDataSetChanged();
    }
}
