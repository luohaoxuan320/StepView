- 要实现如下效果

    ![期待的效果](http://upload-images.jianshu.io/upload_images/1760078-5661ca27655c0cc3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

    发现要把文字上面的原点居中在文字区域好难实现，突然想起来前两天折腾的RecycleView的ItemDecoration，感觉可以用它来实现，于是开始码代码。
    
    
```
    public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
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
        private int cicleRadious = PxUtils.dpToPx(15, getBaseContext());
        /**
         * 圆圈与顶部的距离
         */
        private int marginTop=PxUtils.dpToPx(10, getBaseContext());
        /**
         * 圆圈与线的距离
         */
        private int marginLine=PxUtils.dpToPx(5, getBaseContext());
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

  ```
  
  关键代码如下：
-   绘制圆圈和线，高亮当前的step
```
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
```

-   切换Step

```
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
```
- 高亮选中文字

```
        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            if (position == selPosition) {//高亮当前选中的文字颜色
                ((TextView)holder.itemView).setTextColor(Color.RED);
            }else{
                ((TextView)holder.itemView).setTextColor(Color.BLACK);
            }
        }
```



    
    layout_item_tv.xml
    
```
        <TextView xmlns:android="http://schemas.android.com/apk/res/android"
            android:gravity="bottom|center_horizontal"
            android:layout_width="80dp" android:layout_height="60dp">
        
        </TextView>
```
    

shape_cicle.xml

```
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="ring"
    android:innerRadiusRatio="5"
    android:thicknessRatio="10"
    android:useLevel="false"
    >
    <solid android:color="#333" />

</shape>
```
shape_cicle_sel.xml

```
<shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="ring"
    android:innerRadiusRatio="5"
    android:thicknessRatio="10"
    android:useLevel="false"
    >

    <solid android:color="@android:color/holo_red_light" />

</shape>
```

shape_line.xml

```
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid
        android:color="@android:color/holo_purple" />
    <size android:height="1dp" />
</shape>
```

shape_line_sel.xml

```
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid
        android:color="@android:color/holo_red_dark" />
    <size android:height="1dp" />
</shape>
```




- 实现的效果
![效果](http://upload-images.jianshu.io/upload_images/1760078-3c74132caa12d242.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

    是不是还不错。发现到尽头再滑动会有overScroll的效果，有点恶心，于是去掉它。
配置如下属性

```
    android:overScrollMode="never"
```
    
    
```
    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:overScrollMode="never"
        android:layout_height="60dp">
    
    </android.support.v7.widget.RecyclerView>
```




    当然，这个实现很简陋，只是做一个记录，后面会做进一步封装。
