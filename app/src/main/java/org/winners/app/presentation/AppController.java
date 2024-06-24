package org.winners.app.presentation;

public interface AppController {

    String PHONE_IDENTITY_CERT_TAG_NAME = "101. 휴대폰 본인인증";
    String PHONE_IDENTITY_CERT_PATH = "cert/phone-identity";

    String CLIENT_USER_SIGN_TAG_NAME = "201. 사용자 회원 서명";
    String CLIENT_USER_SIGN_PATH = "sign/user/client";

    String BUSINESS_USER_SIGN_TAG_NAME = "202. 사업자 회원 서명";
    String BUSINESS_USER_SIGN_PATH = "sign/user/business";

    String CLIENT_USER_TAG_NAME = "301. 사용자 회원";
    String CLIENT_USER_PATH = "user/business";

    String BUSINESS_USER_TAG_NAME = "302. 사업자 회원";
    String BUSINESS_USER_PATH = "user/business";

}
