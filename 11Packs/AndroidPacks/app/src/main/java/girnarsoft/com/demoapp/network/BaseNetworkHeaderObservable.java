package girnarsoft.com.demoapp.network;

import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import girnarsoft.com.demoapp.utils.LoadingDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public abstract class BaseNetworkHeaderObservable<T> implements Observer<Result<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Result<T> t) {
        if (null != t && null != t.response() && null != t.response().body()) {
            success(t.response(), t.response().body());
        } else {
            failure(new RuntimeException("Unexpected response " + t));
        }
        LoadingDialog.dismissDialog();

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }


    public abstract void success(Response response, T t);

    public abstract void failure(Throwable t);
}