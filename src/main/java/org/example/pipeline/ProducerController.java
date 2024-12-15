package org.example.pipeline;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@RestController("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/api/select")
    public void selectColor(
            @RequestHeader("user-agent") String userAgentName,
            @RequestParam(value = "color") String colorName,
            @RequestParam(value = "user") String userName) {
        Gson gson = new Gson();
        UserEventVO eventVO = UserEventVO.builder()
                .colorName(colorName)
                .userName(userName)
                .userAgent(userAgentName)
                .build();

        String colorJsonLog = gson.toJson(eventVO, UserEventVO.class);
        kafkaTemplate.send("select-color", colorJsonLog).addCallback(new ListenableFutureCallback<SendResult<String,String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info(result.toString());

            }
            @Override
            public void onFailure(Throwable ex) {
                log.error(ex.getMessage(),ex);
            }
        });
    }

}
