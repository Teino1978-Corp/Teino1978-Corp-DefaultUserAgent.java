// http://matsuhilog.blogspot.jp/2013/05/performance-analytics-in-android.html
// https://code.google.com/p/codenameone/issues/detail?id=294
public static String getDefaultUserAgent(Context context) {
	String ua;
	if (Build.VERSION.SDK_INT >= 17) {
		ua = Util.getDefaultUserAgent(context);
	} else if (Build.VERSION.SDK_INT >= 16) {
		ua = Util.getUserAgent(context);
	} else {
		try {
			Constructor<WebSettings> constructor = WebSettings.class.getDeclaredConstructor(
				Context.class, WebView.class);
			constructor.setAccessible(true);
			try {
				WebSettings settings = constructor.newInstance(context, null);
				ua = settings.getUserAgentString();
			} finally {
				constructor.setAccessible(false);
			}
		} catch (Exception e) {
			ua = new WebView(context).getSettings().getUserAgentString();
		}
	}
	return ua;
}

class Util {
	@SuppressLint("NewApi")
	static String getDefaultUserAgent(Context context) {
		return WebSettings.getDefaultUserAgent(context);
	}

	static String getUserAgent(Context context) {
		String userAgent;
		try {
			@SuppressWarnings("unchecked")
			Class<? extends WebSettings> clz = (Class<? extends WebSettings>) Class
				.forName("android.webkit.WebSettingsClassic");
			Class<?> webViewClassicClz = (Class<?>) Class
				.forName("android.webkit.WebViewClassic");
			Constructor<? extends WebSettings> constructor = clz.getDeclaredConstructor(
				Context.class, webViewClassicClz);
			constructor.setAccessible(true);
			try {
				WebSettings settings = constructor.newInstance(context, null);
				userAgent = settings.getUserAgentString();
			} finally {
				constructor.setAccessible(false);
			}
		} catch (Exception e) {
			userAgent = new WebView(context).getSettings().getUserAgentString();
		}
		return userAgent;
	}
}
