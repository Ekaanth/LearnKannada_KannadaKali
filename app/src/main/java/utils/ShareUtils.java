package utils;

/**
 * Created by raggitha on 05-Apr-18.
 */

public class ShareUtils {

   /* private void shareResult(int result) {
        View certificateView = getLayoutInflater().inflate(R.layout.layout_certificate, null);
        TextView userNameTextView = (TextView) certificateView.findViewById(R.id.userNameTextView);
        String userName = getString(R.string.user_name, "Mr");
        userNameTextView.setText(userName);
        String certBody = getString(R.string.certificateText, "EASY", result + "%");
        TextView certBodyTextView = (TextView) certificateView.findViewById(R.id.certificateBodyTextView);
        certBodyTextView.setText(certBody);
        Bitmap screenShotBitMap = getScreenShot(certificateView);
        String currentTimeStamp = (String) DateFormat.format("MMddyyhhmmss", new Date().getTime());
        storeAndShare(screenShotBitMap, "LearnKannadaSmartapp_" + currentTimeStamp + ".jpg", getString(R.string.resultShareBody));
    }

    private Bitmap getScreenShot(View view) {
        Log.d(THIS_ACTIVITY, "getScreenShot()");
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();
        Bitmap bitmap;
        if (screenView.getDrawingCache() != null)
            bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        else
            bitmap = loadLargeBitmapFromView(screenView);
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static Bitmap loadLargeBitmapFromView(View v) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(spec, spec);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public void storeAndShare(Bitmap bm, String fileName, String message) {
        Log.d(THIS_ACTIVITY, "storeAndShare()");
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QuizAppScreenshots";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
            shareImage(file, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareImage(File file, String message) {
        Log.d(THIS_ACTIVITY, "shareImage()");
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image*//*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Screenshot Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(QuizActivity.this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }*/
}
