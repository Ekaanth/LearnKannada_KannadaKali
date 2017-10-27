package app.learnkannada.com.learnkannadakannadakali;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by vaam on 24-10-2017.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<String> values;
    private Context context;
    private String mCategory;

    private MediaPlayer mediaPlayer;
    private Uri downloadUri;

    //Firebase details
    private StorageReference storageReference;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView testText;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            testText = (TextView) layout.findViewById(R.id.textID);
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListViewAdapter(Context mContext, List<String> myDataset, String category) {
        context = mContext;
        values = myDataset;
        mCategory = category;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = values.get(position);
        holder.testText.setText(name);
        holder.testText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
