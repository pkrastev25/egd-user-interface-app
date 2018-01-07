package com.egd.userinterface.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Helper class for a {@link AsyncTask} which handles workload off the main working thread.
 * Provides an interface for usage which helps identify the result of the asynchronous operation.
 *
 * @author Petar Krastev
 * @since 5.11.2017
 */
public class AsyncTaskUtil extends AsyncTask<Void, Void, Object> {

    /**
     * Represents the class name, used only for debugging.
     */
    private static final String TAG = AsyncTaskUtil.class.getSimpleName();

    private IAsyncTaskListener mListener;
    private Exception mException;

    /**
     * Creates an instance of {@link AsyncTaskUtil}. An explicit call to {@link #execute(Object[])}
     * is needed to start the asynchronous task.
     *
     * @param listener Instance of {@link IAsyncTaskListener}
     */
    public AsyncTaskUtil(@NonNull IAsyncTaskListener listener) {
        mListener = listener;
    }

    /**
     * Creates an instance of {@link AsyncTaskUtil}. The task is automatically started.
     *
     * @param listener Instance of {@link IAsyncTaskListener}
     */
    public static void doInBackground(@NonNull IAsyncTaskListener listener) {
        new AsyncTaskUtil(listener).execute();
    }

    /**
     * Called after the asynchronous task has finished work. If no exception was thrown,
     * {@link IAsyncTaskListener#onSuccess(Object)} is called,
     * {@link IAsyncTaskListener#onError(Exception)} otherwise.
     *
     * @param result The result of the asynchronous task
     */
    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (mException != null) {
            mListener.onError(mException);
        } else {
            mListener.onSuccess(result);
        }

        mException = null;
        mListener = null;
    }

    /**
     * Called right after {@link #execute(Object[])} is started. Performs the task and
     * catches any errors during execution.
     *
     * @param params Ignored, {@link IAsyncTaskListener#onExecuteTask()} takes care of invoking the request
     * @return The result of the asynchronous task, null if the task failed to complete
     */
    @Nullable
    @Override
    protected Object doInBackground(Void... params) {
        try {
            return mListener.onExecuteTask();
        } catch (Exception e) {
            Log.e(TAG, "AsyncTaskUtil.doInBackground() failed!", e);
            mException = e;
        }

        return null;
    }

    /**
     * Helper interface, must be implemented by all classes who wish to use the
     * {@link AsyncTaskUtil}.
     *
     * @param <TResult> The result type of the asynchronous operation
     */
    public interface IAsyncTaskListener<TResult> {
        /**
         * Provides an asynchronous operation.
         *
         * @return Result of the asynchronous operation
         * @throws Exception Something went wrong with the request
         */
        TResult onExecuteTask() throws Exception;

        /**
         * Provide logic for dealing with the result of the asynchronous operation.
         *
         * @param result The result of the asynchronous operation
         */
        void onSuccess(TResult result);

        /**
         * Provide logic for dealing with errors occurred during the
         * execution of the asynchronous task!
         *
         * @param error Exception occurred during the execution of the asynchronous task
         */
        void onError(Exception error);
    }
}
