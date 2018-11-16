package es.source.code;


/**
 * Created by sail on 2018/10/6.
 */

public class AppGlobal {
    // IntentKey
    public static class IntentKey {
        public static final String FROM = "from";
        public static final String LOGIN_STATUS = "login_status";
        public static final String CURRENT_INDEX = "currentPageIndex";
        public static final String COLD_FOOD = "intent_cold_food";//0
        public static final String HOT_FOOD = "intent_hot_food";//1
        public static final String SEA_FOOD = "intent_sea_food";//2
        public static final String DRINK_FOOD = "intent_drink_food";//3
        public static final String CURRENT_CATEGORY = "intent_category";
        public static final String CURRENT_DETAILED_POSITION = "intent_datailed_position";
        public static final String USER_INFO = "user_info";
    }

    // IntentValue
    public static class IntentValue {
        public static final String FROM_VALUE = "FromEntry";
        public static final String LOGIN_SUCCESS = "LoginSuccess";
        public static final String REGISTER_SUCCESS = "RegisterSuccess";
        public static final String LOGIN_RETURN = "Return";
    }

    //正则表达式
    public static final String REGEX_ACCOUNT = "^[A-Za-z1-9_-]+$";
    public static final String REGEX_PASSWORD = "^[A-Za-z1-9_-]+$";


    //返回页面标识
    public static class ResultCode{
        public static final int MAIN_SCREEN=1;
        public static final int LOGIN_OR_REGISTER = 2;

    }

    //功能标识
    public static class FunctionTag{
        public static final String ORDER ="order";
        public static final String ORDER_LIST ="order_list";
        public static final String ACCOUNT ="account";
        public static final String HELP ="help";
        public static final String HELP_AGREE = "help_agree";
        public static final String HELP_ABOUT = "help_about";
        public static final String HELP_TEL = "help_tel";
        public static final String HELP_MESS = "help_mess";
        public static final String HELP_MAIL = "help_mail";

    }

    //sharedPreferences键
    public static class SPKey{
        public static final String RESOURCE = "resource";
        public static final String USER = "user";
        public static final String LOGIN_STATUS = "login_status";
    }

    //sharedPreferences值
    public static class SPValue{
        public static final int LOGIN_SUCCESS = 1;
        public static final int LOGIN_FAILURE = 0;
    }

    //菜评论
    public static final String REMARK = "好吃";

    //菜品保存
    public static class Store{
        public static final String ORDEREDFOODS = "store_Ordered_foods";
        public static final String PAYEDFOODS = "store_Payed_foods";
        public static final String COLD_FOOD = "store_cold_food";//0
        public static final String HOT_FOOD = "store_hot_food";//1
        public static final String SEA_FOOD = "store_sea_food";//2
        public static final String DRINK_FOOD = "store_drink_food";//3
        public static final String FOOD_CONTENT = "store_food_content";
    }

    //fragment标签
    public static class Lable{
        public static final int UNORDER_LABLE = 1;
        public static final int ORDERED_LABLE = 0;
        public static final String COLD_LABLE = "cold";
        public static final String HOT_LABLE = "hot";
        public static final String SEA_LABLE = "sea";
        public static final String DRINK_LABLE = "drink";
        public static final int BASE_LABLE = 2;
    }

    //mail
    public static class Mail{
        public static final String Name = "saaaaaail@163.com";
        public static final String PASSWORD = "yf39330204";
        public static final String MAILADDRESS = "649014081@qq.com";
        public static final String AUTHORIZATION = "yf123456";//授权码
        public static final String HOST_KEY = "mail.smtp.host";
        public static final String AUTH_KEY = "mail.smtp.auth";
        //public static final String HOST_VALUE = "smtp.163.com";
        public static final String HOST_VALUE = "220.181.12.14";
        public static final String AUTH_VALUE = "true";
    }

    //Message
    public static class Message{
        public static final int GET_FOOD_MSG = 1;
        public static final int STOP_FOOD_MSG = 0;
        public static final int GET_FOOD_SUCCESS = 10;

    }

    //Bundle Key
    public static class BundleKey{
        public static final String FOOD_CONTENT = "bundle_food_content";
    }


}
