package shop.mtcoding.blogv2._core.util;

// 오버로딩
public class Script {

    // 경고창 + 뒤로가기
    public static String back(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }

    // 이동
    public static String href(String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("location.href='" + url + "';");
        sb.append("</script>");
        return sb.toString();
    }

    // 경고창 + 이동
    public static String href(String msg, String url) { // msg가 매개변수 앞에 있는 이유는 일관성 때문이다.
        // (위에 String msg로 매개변수가 한개있는 함수가 먼저 정의되었기 때문에)
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + msg + "');");
        sb.append("location.href='" + url + "';");
        sb.append("</script>");
        return sb.toString();
    }

}
