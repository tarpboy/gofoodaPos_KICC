package app.payfun.com.gofoodapos.Popup;

import java.util.HashMap;

public interface OnEventOkListener {
	void onOk();
	void onOk(String addMenu);
	void onOk(HashMap<String, String> addOrigin);
}
