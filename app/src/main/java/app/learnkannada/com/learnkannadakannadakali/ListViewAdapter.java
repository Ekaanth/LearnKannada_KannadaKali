package app.learnkannada.com.learnkannadakannadakali;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

/**
 * Created by vaam on 24-10-2017.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<String> values, kanValues;
    private Context context;
    private String mCategory;

    private MediaPlayer mediaPlayer;
    private Uri downloadUri;

    //Firebase details
    private StorageReference storageReference;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textInEng, textInKan;
        public ImageView imageView;
        public View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            textInEng = (TextView) layout.findViewById(R.id.textID);
            textInKan = (TextView) layout.findViewById(R.id.textInKanID);

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
    public ListViewAdapter(Context mContext, List<String> myDataset, String category, List<String> kanInput) {
        context = mContext;
        values = myDataset;
        mCategory = category;
        kanValues = kanInput;
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
        holder.textInEng.setText(name);
        holder.textInKan.setText(kanValues.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try {
                    playOffline(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                playOnline(position);
            }
        });
    }

    private void playOffline(int position) throws IOException {
       // Toast.makeText(context,"playing...",Toast.LENGTH_SHORT).show();
        mediaPlayer = new MediaPlayer();
        Integer id=context.getResources().getIdentifier(values.get(position).toLowerCase(),"raw",context.getPackageName());
        mediaPlayer = MediaPlayer.create(context,id);
        mediaPlayer.start();
    }

    private void playOnline(int position) {
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
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
