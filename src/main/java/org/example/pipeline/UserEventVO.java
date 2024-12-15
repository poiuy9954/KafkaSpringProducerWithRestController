package org.example.pipeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
public class UserEventVO {

    private String userName;
    private String colorName;
    private String userAgent;
    private String timeStamp;

    @Builder
    public UserEventVO(String userName, String colorName, String userAgent) {
        this.userName = userName;
        this.colorName = colorName;
        this.userAgent = userAgent;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZZ");
        this.timeStamp = formatter.format(new Date());
    }
}
