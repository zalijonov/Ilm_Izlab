package uz.alijonovz.ilmizlab.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import uz.alijonovz.ilmizlab.model.BaseResponse

open class BaseRepository {

    var compositeDisposable = CompositeDisposable()
    fun <T> sendCall(
        call: Observable<BaseResponse<T>>,
        error: MutableLiveData<String>,
        success: MutableLiveData<T>,
        progress: MutableLiveData<Boolean>
    ) {

        compositeDisposable.add(
            call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progress.value = true
                }
                .doFinally {
                    progress.value = false
                }
                .subscribeWith(object : DisposableObserver<BaseResponse<T>>() {
                    override fun onNext(t: BaseResponse<T>) {
                        if (t.success)
                            success.value = t.data
                        else
                            error.value = t.message
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }

                })
        )

    }
}