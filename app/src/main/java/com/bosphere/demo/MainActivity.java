package com.bosphere.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.bosphere.fadingedgelayout.FadingEdgeLayout;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_FADE_LENGTH = 400; // dp

    private FadingEdgeLayout mFadingEdgeLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private Adapter mAdapter;
    private boolean mEnableTop = false;
    private boolean mEnableLeft = false;
    private boolean mEnableBottom = false;
    private boolean mEnableRight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFadingEdgeLayout = (FadingEdgeLayout) findViewById(R.id.fading_edge_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);

        SeekBar sb = (SeekBar) findViewById(R.id.sb_size);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float percent = progress / 100f;
                int len = (int) (MAX_FADE_LENGTH * percent);
                int lenPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, len,
                        getResources().getDisplayMetrics());
                mFadingEdgeLayout.setFadeSizes(lenPx, lenPx, lenPx, lenPx);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        sb.setProgress(20);
    }

    public void onClickedOrientation(View view) {
        switch (view.getId()) {
            case R.id.rb_horizontal:
                mLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
                break;
            case R.id.rb_vertical:
                mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                break;
        }
    }

    public void onClickedSwitch(View view) {
        SwitchCompat sw = (SwitchCompat) view;
        switch (sw.getId()) {
            case R.id.sw_top:
                mEnableTop = sw.isChecked();
                mFadingEdgeLayout.setFadeEdges(mEnableTop, mEnableLeft, mEnableBottom, mEnableRight);
                break;
            case R.id.sw_left:
                mEnableLeft = sw.isChecked();
                mFadingEdgeLayout.setFadeEdges(mEnableTop, mEnableLeft, mEnableBottom, mEnableRight);
                break;
            case R.id.sw_bottom:
                mEnableBottom = sw.isChecked();
                mFadingEdgeLayout.setFadeEdges(mEnableTop, mEnableLeft, mEnableBottom, mEnableRight);
                break;
            case R.id.sw_right:
                mEnableRight = sw.isChecked();
                mFadingEdgeLayout.setFadeEdges(mEnableTop, mEnableLeft, mEnableBottom, mEnableRight);
                break;
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View square = new View(parent.getContext());
            square.setBackgroundColor(Color.MAGENTA);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(200, 200);
            params.leftMargin = params.topMargin = params.rightMargin = params.bottomMargin = 20;
            square.setLayoutParams(params);
            return new ViewHolder(square);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 80;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
