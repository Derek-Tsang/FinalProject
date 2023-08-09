package algonquin.cst2335.finalproject.Controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/**

 The AppController class represents the application controller
 for managing network requests using the Volley library.
 It extends the Application class and provides methods for
 accessing the request queue and managing network requests.
 */
public class AppController extends Application {
    /**
     * The tag used for logging and identification purposes.
     */
    public static final String TAG = AppController.class.getSimpleName();

    /**
     * The singleton instance of the AppController class.
     */
    private static AppController mInstance;

    /**
     * The Volley RequestQueue for handling network requests.
     */
    private RequestQueue mRequestQueue;

    /**
     * Retrieves the singleton instance of the AppController class.
     *
     * @return The singleton instance of AppController.
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * Retrieves the singleton instance of the AppController class
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Retrieves the request queue used for network requests.
     *
     * @return The request queue.
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Adds a request to the request queue with a specified tag.
     * If the tag is empty, the default tag is used.
     *
     * @param req The request to be added.
     * @param tag The tag associated with the request.
     * @param <T> The type of the request.
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Adds a request to the request queue with the default tag.
     *
     * @param req The request to be added.
     * @param <T> The type of the request.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests with a specified tag.
     *
     * @param tag The tag associated with the requests to be canceled.
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
