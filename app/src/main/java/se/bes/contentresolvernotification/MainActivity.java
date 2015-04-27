package se.bes.contentresolvernotification;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MainActivity extends ActionBarActivity {

    private static final String AUTHORITY = "content://base.uri";

    private static final String SUB_URI = AUTHORITY + "/a/sub/uri";

    private static final String SUB_SUB_URI = SUB_URI + "/sub/uri";

    private static final String TAG = "UriTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler h = new Handler(Looper.getMainLooper());

        final Uri baseUri = Uri.parse(AUTHORITY);
        final Uri subUri = Uri.parse(SUB_URI);
        final Uri subSubUri = Uri.parse(SUB_SUB_URI);

        ContentResolver res = getContentResolver();

        res.registerContentObserver(baseUri, false, new ContentObserver(h) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d(TAG, baseUri + " onChange, notifyForDescendents=false");
            }
        });

        res.registerContentObserver(baseUri, true, new ContentObserver(h) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d(TAG, baseUri + " onChange, notifyForDescendents=true");
            }
        });

        res.registerContentObserver(subUri, false, new ContentObserver(h) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d(TAG, subUri + " onChange, notifyForDescendents=false");
            }
        });

        res.registerContentObserver(subUri, true, new ContentObserver(h) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d(TAG, subUri + " onChange, notifyForDescendents=true");
            }
        });

        res.registerContentObserver(subSubUri, false, new ContentObserver(h) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d(TAG, subSubUri + " onChange, notifyForDescendents=false");
            }
        });

        res.registerContentObserver(subSubUri, true, new ContentObserver(h) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d(TAG, subSubUri + " onChange, notifyForDescendents=true");
            }
        });

        new Thread() {
            public void run() {
                Log.d(TAG, "First test");
                Log.d(TAG, "Notifying on " + baseUri);
                getContentResolver().notifyChange(baseUri, null);
                SystemClock.sleep(700);

                Log.d(TAG, "Second test");
                Log.d(TAG, "Notifying on " + subUri);
                getContentResolver().notifyChange(subUri, null);
                SystemClock.sleep(700);

                Log.d(TAG, "Third test");
                Log.d(TAG, "Notifying on " + subSubUri);
                getContentResolver().notifyChange(subSubUri, null);
                SystemClock.sleep(700);

                Log.d(TAG, "End test");
            }
        }.start();
    }
}