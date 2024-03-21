package project_java_group_5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ControllerCauThu {
    public static String getYearFromString(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date date = sdf.parse(dateString);
            // Tạo một đối tượng SimpleDateFormat mới để chỉ định định dạng của kết quả mong
            // muốn
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            return yearFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Định dạng ngày không hợp lệ";
        }
    }
}
