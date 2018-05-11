package com.firebase.ginggingi.memoappwithserver.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ginggingi.memoappwithserver.ConnServer.DeleteMemoData;
import com.firebase.ginggingi.memoappwithserver.DataModels.MemoDataModel;
import com.firebase.ginggingi.memoappwithserver.MainActivity;
import com.firebase.ginggingi.memoappwithserver.MemoUpdateActivity;
import com.firebase.ginggingi.memoappwithserver.R;

import java.util.ArrayList;

/**
 * Created by GingGingI on 2018-04-11.
 */

public class MRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<MemoDataModel> listViewItemList = new ArrayList<>();
    protected MainActivity mActivity;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mActivity = (MainActivity) parent.getContext();
        View v = LayoutInflater.from(parent.getContext()). inflate(R.layout.listitem_memo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.Title.setText(listViewItemList.get(position).getTitle());
        holder.Date.setText(listViewItemList.get(position).getTime());
        holder.Content.setText(listViewItemList.get(position).getContent());
        holder.DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MemoUpdateActivity.class);
                intent.putExtra("idx", listViewItemList.get(position).getIdx());
                intent.putExtra("Title", listViewItemList.get(position).getTitle());
                intent.putExtra("Date", listViewItemList.get(position).getTime());
                intent.putExtra("Content", listViewItemList.get(position).getContent());
                mActivity.startActivityForResult(intent, 1);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("채팅방지우기");
                builder.setMessage("채팅방을 지우시겠습니까?");
                builder.setPositiveButton("지우기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteMemoData DelMemo = new DeleteMemoData();
                        DelMemo.InitData(listViewItemList.get(position).getIdx(), mActivity);
                        DelMemo.DeleteData();
                    } // onClick
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mActivity, "취소", Toast.LENGTH_SHORT).show();
                    } // onClick
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listViewItemList != null) ? listViewItemList.size() : 0;
    }

    public void setItems(ArrayList<MemoDataModel> itemlist) {
        listViewItemList = itemlist;
    }
}
    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Title;
        public TextView Date;
        public TextView Content;
        public Button DetailBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.Listitem_TitleView);
            Date = (TextView)itemView.findViewById(R.id.Listitem_DateView);
            Content = (TextView)itemView.findViewById(R.id.Listitem_ContentView);
            DetailBtn = (Button) itemView.findViewById(R.id.Listitem_DetailBtn);
        }
}
