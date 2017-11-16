package app.learnkannada.com.learnkannadakannadakali;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaam on 24-10-2017.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> implements Filterable {

    private List<String> values, kanValues, mFilteredList;
    private Context context;
    private String mCategory;

    private MediaPlayer mediaPlayer;
    private Uri downloadUri;

    //Firebase details
    private StorageReference storageReference;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textInEng, textInKan;
        public ImageView imageView;
        public Button exampleButton;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            textInEng = (TextView) layout.findViewById(R.id.textID);
            textInKan = (TextView) layout.findViewById(R.id.textInKanID);
            if(mCategory.equals("dayCourse"))
                exampleButton = (Button)layout.findViewById(R.id.exampleButtonID);
                imageView = (ImageView)layout.findViewById(R.id.dayImageID);
        }
    }

    public void add(int position, String item) {
        mFilteredList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mFilteredList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListViewAdapter(Context mContext, List<String> myDataset, String category, List<String> kanInput) {
        context = mContext;
        values = myDataset;
        mFilteredList = myDataset;
        mCategory = category;
        kanValues = kanInput;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(mCategory.equals("dayCourse")) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.row_layout_day, parent, false);
        }
        else if(mCategory.equals("homeCourse")){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.row_layout_course, parent, false);
        }
        else {
            if (mCategory.equals("numbers")) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout_image, parent, false);
            } else {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                v = inflater.inflate(R.layout.row_layout, parent, false);
            }
        }
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = mFilteredList.get(position);
        holder.textInEng.setText(name);
        if(!mCategory.equals("homeCourse"))
            holder.textInKan.setText(kanValues.get(position));

        //onClickListener code begins
        if(!mCategory.equals("dayCourse")&& !mCategory.equals("homeCourse")) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //playOnline(position);
                }
            });
        }
        else if(mCategory.equals("homeCourse")) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DayActivity.class);
                    i.putExtra("position", name);
                    context.startActivity(i);
                }
            });
        }
        else if(mCategory.equals("dayCourse")) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        playOffline(position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.exampleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ExampleActivity.class);
                    i.putExtra("position", name);
                    context.startActivity(i);
                }
            });
        }

    }

    private void playOffline(int position) throws IOException {
       // Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        Integer id=context.getResources().getIdentifier(mFilteredList.get(position).toLowerCase(),"raw",context.getPackageName());
        mediaPlayer = MediaPlayer.create(context,id);
        mediaPlayer.start();
    }

    /*private void playOnline(int position) {
        Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();

        storageReference = FirebaseStorage.getInstance().getReference().child("Categories").child(mCategory)
                .child(values.get(position).toLowerCase()+".mp3");
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadUri = uri;
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(downloadUri.toString());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Resource not found",Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();

                if (charString.isEmpty()) {
                    mFilteredList = values;
                } else {

                    ArrayList<String> filteredList = new ArrayList<>();

                    for (String tempString : values) {
                        if (tempString.toLowerCase().contains(charString))
                            filteredList.add(tempString);
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
