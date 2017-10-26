package app.learnkannada.com.learnkannadakannadakali;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.RawRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by vaam on 24-10-2017.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<String> values;
    private Context context;

    private MediaPlayer mediaPlayer;

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
    public ListViewAdapter(Context mContext, List<String> myDataset) {
        context = mContext;
        values = myDataset;
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
                //Toast.makeText(context,values.get(position),Toast.LENGTH_LONG).show();
                mediaPlayer = new MediaPlayer();
                Integer integer = context.getResources().getIdentifier(values.get(position).toLowerCase(),"raw",context.getPackageName());
                mediaPlayer = MediaPlayer.create(context,integer);
                if(mediaPlayer!=null)
                    mediaPlayer.start();
                else
                    Toast.makeText(context,"ERROR!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
