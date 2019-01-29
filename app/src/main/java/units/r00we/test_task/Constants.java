package units.r00we.test_task;

public class Constants {
    // Github Api
    public static final String BASE_URL = "https://api.github.com/";
    public static final String USER = "ReactiveX";
    public static final String REPOSITORY = "RxAndroid";
    // Github анулирует токен если выложить его в открытый доступ.
    // Поэтому перед запуском приложения нужно сходить на https://github.com/settings/tokens
    // получить его и вписать в TOKEN
    public static final String TOKEN = "";

    //Comments settings
    public static final int NEED_COLLAPSED_SIZE = 3;
    public static final int SIZE_IF_COLLAPSED = 2;

}
