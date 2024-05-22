package com.example.bangbang_gotgot.article.util;

import org.springframework.beans.factory.annotation.Value; // properties에 있는 자료 가져오려면 import 변경!
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SmsUtil {

    @Value("${spring.coolsms.apiKey}")
    private String apiKey;
    @Value("${spring.coolsms.api.secret}")
    private String apiSecretKey;
    @Value("${spring.coolsms.senderNumber}")
    private String senderNumber;

    private DefaultMessageService messageService;


    @PostConstruct // 다른 리소스가 호출하지 않아도 수행하는 어노테이션
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    // 단일 메시지 발송 예제
    public SingleMessageSentResponse sendOne(String to, String verificationCode) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(senderNumber);
        message.setTo(to);
        message.setText("[방방곡곡] 아래의 인증번호를 입력해주세요\n" + verificationCode);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return response;
    }

}
