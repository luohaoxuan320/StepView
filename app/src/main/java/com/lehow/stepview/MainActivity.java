package com.lehow.stepview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this
                , LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new MAdapter());
        recyclerView.addItemDecoration(new MItemDecoration());
    }

    /**
     * 选中的position
     */
    int selPosition = 0;
    public void btnNext(View view) {
        int preSel = selPosition;
        selPosition = (selPosition + 1) % 4;
        //刷新前面选中的状态，和当前选中的状态
        recyclerView.getAdapter().notifyItemChanged(selPosition);
        recyclerView.getAdapter().notifyItemChanged(preSel);
    }


    class MViewHolder extends RecyclerView.ViewHolder{

        public MViewHolder(View itemView) {
            super(itemView);
        }
    }

    class MAdapter extends RecyclerView.Adapter<MViewHolder> {
        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.layout_item_tv, parent, false);
            textView.setText("AAAA");

            return new MViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            if (position == selPosition) {//高亮当前选中的文字颜色
                ((TextView)holder.itemView).setTextColor(Color.RED);
            }else{
                ((TextView)holder.itemView).setTextColor(Color.BLACK);
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }



    class MItemDecoration extends RecyclerView.ItemDecoration{

        /**
         * 圆圈的半径
         */
        private int cicleRadious = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        /**
         * 圆圈与顶部的距离
         */
        private int marginTop= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        /**
         * 圆圈与线的距离
         */
        private int marginLine= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics());
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            Log.i("TEST", "onDrawOver: ");
            int childCount = parent.getChildCount();
            Drawable drawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_cicle);
            Drawable drawableSel = ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_cicle_sel);
            Drawable drawableLine = ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_line);
            Drawable drawableLineSel=ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_line_sel);
            for (int i=0;i<childCount;i++) {
                View childAt = parent.getChildAt(i);
                int left = childAt.getLeft();
                int right = childAt.getRight();

                if (i == selPosition) {//选中的圆圈颜色
                    drawableSel.setBounds(left+childAt.getWidth()/2-cicleRadious,marginTop,right-childAt.getWidth()/2+cicleRadious,marginTop+cicleRadious*2);
                    drawableSel.draw(c);
                }else{
                    //圆圈的半径40px，距离顶部30px
                    drawable.setBounds(left+childAt.getWidth()/2-cicleRadious,marginTop,right-childAt.getWidth()/2+cicleRadious,marginTop+cicleRadious*2);
                    drawable.draw(c);
                }



                if (i > 0) {
                    View preView = parent.getChildAt(i - 1);
                    if (i == selPosition) {//选中的线的颜色
                        drawableLineSel.setBounds(preView.getRight()-preView.getWidth()/2+(cicleRadious+marginLine),marginTop+cicleRadious-drawableLine.getIntrinsicHeight()/2,childAt.getLeft()+childAt.getWidth()/2-(cicleRadious+10),marginTop+cicleRadious+drawableLine.getIntrinsicHeight());
                        drawableLineSel.draw(c);
                    }else{
                        //直线距离左右两边10px
                        drawableLine.setBounds(preView.getRight()-preView.getWidth()/2+(cicleRadious+marginLine),marginTop+cicleRadious-drawableLine.getIntrinsicHeight()/2,childAt.getLeft()+childAt.getWidth()/2-(cicleRadious+10),marginTop+cicleRadious+drawableLine.getIntrinsicHeight());
                        drawableLine.draw(c);
                    }
                }

            }
        }

    }
}
