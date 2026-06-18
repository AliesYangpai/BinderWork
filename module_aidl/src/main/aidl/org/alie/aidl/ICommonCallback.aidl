// ICommonCallback.aidl
package org.alie.aidl;

// Declare any non-default types here with import statements

interface ICommonCallback {
    void onSuccess(in String dataMsg);

    void onFail(in String msgMsg);
}