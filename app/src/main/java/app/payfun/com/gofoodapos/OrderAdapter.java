package app.payfun.com.gofoodapos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jonathan on 2018. 1. 19..
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<HashMap<String,String>> itemList;

    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;


    Context mContext;
    private OnItemClicked onClick;


    public interface OnItemClicked {
        void onItemClick(int position);
    }


    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public OrderAdapter() {
        itemList =new ArrayList<>();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager){
        this.mLinearLayoutManager=linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new SmallViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    public void addAll(List<HashMap<String,String>> lst){
        itemList.clear();
        itemList.addAll(lst);
        notifyDataSetChanged();
    }

    public void addItemMore(List<HashMap<String,String>> lst){
        itemList.addAll(lst);
        notifyItemRangeChanged(0,itemList.size());
    }


    static int selected_position = 0;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SmallViewHolder) {

            final HashMap<String,String> singleItem = (HashMap<String,String>) itemList.get(position);




            ((SmallViewHolder) holder).tv_menu_name.setText(singleItem.get("menu_name") + " (" + singleItem.get("menu_eng_name") + ")");
            ((SmallViewHolder) holder).tv_menu_price.setText(Define.formatToMoney(singleItem.get("price")));
            ((SmallViewHolder) holder).tv_value.setText(singleItem.get("value"));


            ((SmallViewHolder) holder).bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ActivityMain.order_list.remove(position);
                    addAll(ActivityMain.order_list);
                    ((ActivityMain)mContext).remake();
                    ((ActivityMain)mContext).totalSum();


                }
            });


            ((SmallViewHolder) holder).bt_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int value = Integer.valueOf(ActivityMain.order_list.get(position).get("value"));
                    --value;
                    if(value == 0)
                    {
                        ActivityMain.order_list.remove(position);
                        addAll(ActivityMain.order_list);
                        ((ActivityMain)mContext).remake();
                    }
                    else
                    {
                        ActivityMain.order_list.get(position).put("value", String.valueOf(value));
                        addAll(ActivityMain.order_list);
                    }
                    ((ActivityMain)mContext).totalSum();

                }
            });




            ((SmallViewHolder) holder).bt_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int value = Integer.valueOf(ActivityMain.order_list.get(position).get("value"));
                    ++value;
                    ActivityMain.order_list.get(position).put("value", String.valueOf(value));
                    addAll(ActivityMain.order_list);
                    ((ActivityMain)mContext).totalSum();

                }
            });



        }
    }






    public Bitmap rotateImage(Bitmap getOrigin, File getImagFile)
    {
        Bitmap rotateBitmap = getOrigin;

        try {
            ExifInterface exif = new ExifInterface(getImagFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.e("Jonathan", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
            }
            rotateBitmap = Bitmap.createBitmap(rotateBitmap, 0, 0, rotateBitmap.getWidth(), rotateBitmap.getHeight(), matrix, true); // rotating bitmap


        } catch (IOException e) {
            e.printStackTrace();
        }

        return rotateBitmap;

    }



    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    itemList.add(null);
                    notifyItemInserted(itemList.size() - 1);
                }
            });
        } else {
            itemList.remove(itemList.size() - 1);
            notifyItemRemoved(itemList.size());
        }
    }

    class SmallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_menu_name;
        public TextView tv_menu_price;
        public TextView tv_value;

        public Button bt_delete;
        public Button bt_minus;
        public Button bt_plus;


        public SmallViewHolder(View v) {
            super(v);


            tv_menu_name = (TextView)v.findViewById(R.id.tv_menu_name);
            tv_menu_price = (TextView)v.findViewById(R.id.tv_menu_price);
            tv_value = (TextView)v.findViewById(R.id.tv_value);

            bt_delete = (Button)v.findViewById(R.id.bt_delete);
            bt_minus = (Button)v.findViewById(R.id.bt_minus);
            bt_plus = (Button)v.findViewById(R.id.bt_plus);


        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

            onClick.onItemClick(selected_position);

        }
    }

}