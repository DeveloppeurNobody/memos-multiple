package rxjava

import io.reactivex.Observable
import io.reactivex.ObservableEmitter

fun main() {
    ObservableEssai().observableWithRecursionReturnAllObjects()
}

class ObservableEssai {

    fun observableWithRecursionReturnAllObjects() {
        Observable.create<Int> { it -> getIntegers(1, 5, maxPagesNum, it) }

    }

    fun getIntegers(pageStart: Int, pageSize: Int, maxPagesNum: Int, subscriber: ObservableEmitter<Int>) {
        Observable.range(pageStart, pageSize)
            .doOnComplete {
                var newPageStart: Int = pageStart + pageSize;
                if (pageStart >= maxPagesNum) {
                    subscriber.onComplete();
                } else {
                    getIntegers(newPageStart, pageSize, maxPagesNum, subscriber);
                }
            }
            .subscribe(subscriber::onNext);
    }

    companion object {
        private const val maxPagesNum: Int = 15;
    }
}