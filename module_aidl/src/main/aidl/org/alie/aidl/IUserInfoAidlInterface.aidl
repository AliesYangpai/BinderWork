// IUserInfoAidlInterface.aidl
package org.alie.aidl;
import org.alie.aidl.IUserInfo;
// Declare any non-default types here with import statements

interface IUserInfoAidlInterface {
    int add(int a,int b);
    int getScore(in IUserInfo iUserInfo);
    int getNewScore(in List<IUserInfo> list);
    List<IUserInfo> getUserInfoList();
}