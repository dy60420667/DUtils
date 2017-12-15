package dy.utils.libhttp.rxbus.rxdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import dy.utils.libhttp.rxbus.RxBusHelper;
import dy.utils.libhttp.rxbus.common.ErrorBean;
import io.reactivex.disposables.CompositeDisposable;

public class SecondActivity extends Activity {
    CompositeDisposable disposables = new CompositeDisposable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_second);
//		Button button = (Button) findViewById(R.id.b);
		RxBusHelper.doOnMainThread(String.class, disposables, new RxBusHelper.OnEventListener<String>() {
			@Override
			public void onEvent(String s) {
				Log.i("xx","收到消息了"+s);
			}

			@Override
			public void onError(ErrorBean errorBean) {

			}
		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		disposables.dispose();
	}
}