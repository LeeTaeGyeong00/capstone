//package com.example.bangbang_gotgot.member.util;
//
//
//import net.nurigo.sdk.NurigoApp;
//import net.nurigo.sdk.message.model.Message;
//import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
//import net.nurigo.sdk.message.response.SingleMessageSentResponse;
//import net.nurigo.sdk.message.service.DefaultMessageService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//public class SmsCertificationUtil {  // coolsms로 문자 보내는 uitl
//
//    @Value("${spring.coolsms.senderNumber}")
//    private String senderNumber;
//
//    @Value("${spring.coolsms.apiKey}")
//    private String apiKey;
//
//    @Value("${spring.coolsms.apiSecret}")
//    private String apiSecret;
//
//    DefaultMessageService messageService;
//
//
//    @PostConstruct // 빈의 초기화 프로세스를 제어
//    public void init() {
//        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
//    }
//
//    // 문자 보내는 형식
//    public SingleMessageSentResponse sendSms(String to, String verificationCode){
//        Message message = new Message();
//        message.setFrom(senderNumber);
//        message.setTo(to);
//        message.setText("[방방곡곡] 본인 확인 인증번호는 "+verificationCode+"입니다.");
//
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//        System.out.println(response);
//        return response;
//    }
//
//}
