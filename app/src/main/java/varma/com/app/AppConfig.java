package varma.com.app;

public class AppConfig {
/*
	   public static String URL_LOGIN = "http://192.168.3.2/LogIn/login.php";
    public static String URL_REGISTER = "http://192.168.3.2/LogIn/register.php";

	public static String URL_REGISTERDEVICE = "http://192.168.3.2/FcmSimplifiedCoding/RegisterDevice.php";

    public static String URL_IMAGES = "http://192.168.3.2/myImages.php";
	public static String URL_DIS_UPDATE = "http://192.168.3.2/mhConversationsUpdate.php";
	public static String URL_DIS = "http://192.168.3.2/mhConversations.php";
	public static String URL_DIS_REPLY = "http://192.168.3.2/mhDiscussionViewReply.php";
*/

    public static String URL = "http://varmaserver.bitnamiapp.com";

    public static String URL_LOGIN = URL+"/varmaphp/LogIn/login.php";
    public static String URL_REGISTER = URL+"/varmaphp/LogIn/register.php";
    public static String URL_REGISTERDEVICE = URL+"/varmaphp/FcmSimplifiedCoding/RegisterDevice.php";
    public static String URL_IMAGES = URL+"/varmaphp/myImages.php";
    public static String URL_DIS_UPDATE = URL+"/varmaphp/mhConversationsUpdate.php";
    public static String URL_DIS = URL+"/varmaphp/mhConversations.php";
    public static String URL_DIS_REPLY = URL+"/varmaphp/mhDiscussionViewReply.php";

}
