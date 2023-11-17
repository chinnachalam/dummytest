package com.learning.dummytest.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*"{
    "status": 0,
    "time": 1700072286278,
    "maxAge": 0,
    "model": {
    "id": "I-6FXMQN",
    "status": "PENDING",
    "upiId": "im.201023906157@indus",
    "amount": 5000.00,
    "cardInfo": {
    "name": "HARISH PARIHAR",
    "bankAccountNo": "201023906157",
    "ifsc": "1"
    },
    "payUser": {
    "phone": "111111",
    "email": "test@gmail.com",
    "name": "desc"
    },
    "deeplink": "upi://pay?pa=im.201023906157@indus&pn=HARISH PARIHAR&tid=6FXMQN&tn=6FXMQN&am=5000.00&cu=INR",
    "enableUpay": false,
    "orderNo": "DS58021511152347588099678",
    "created": 1700072278883,
    "supportDeepLink": false
    }
    }"*/
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping()
    public String getPayment() {
        System.out.println("******************* getPayment() *********************");
        return "{\n" +
            "    \"status\": 0,\n" +
            "    \"time\": 1700072404082,\n" +
            "    \"maxAge\": 0,\n" +
            "    \"model\": {\n" +
            "        \"id\": \"I-6FXMQN\",\n" +
            "        \"status\": \"PENDING\",\n" +
            "        \"upiId\": \"im.201023906157@indus\",\n" +
            "        \"amount\": 5000.00,\n" +
            "        \"cardInfo\": {\n" +
            "            \"name\": \"HARISH PARIHAR\",\n" +
            "            \"bankAccountNo\": \"201023906157\",\n" +
            "            \"ifsc\": \"1\"\n" +
            "        },\n" +
            "        \"payUser\": {\n" +
            "            \"phone\": \"111111\",\n" +
            "            \"email\": \"test@gmail.com\",\n" +
            "            \"name\": \"desc\"\n" +
            "        },\n" +
            "        \"deeplink\": \"upi://pay?pa=im.201023906157@indus&pn=HARISH PARIHAR&tid=6FXMQN&tn=6FXMQN&am=5000.00&cu=INR\",\n" +
            "        \"enableUpay\": false,\n" +
            "        \"orderNo\": \"DS58021511152347588099678\",\n" +
            "        \"created\": 1700072278883,\n" +
            "        \"supportDeepLink\": false\n" +
            "    }\n" +
            "}";
    }

    @PostMapping
    public String postPayment() {
        System.out.println("*************** postPayment called ********************");
        return "{\n" +
            "    \"status\": 0,\n" +
            "    \"time\": 1700072404082,\n" +
            "    \"maxAge\": 0,\n" +
            "    \"model\": {\n" +
            "        \"id\": \"I-6FXMQN\",\n" +
            "        \"status\": \"PENDING\",\n" +
            "        \"upiId\": \"im.201023906157@indus\",\n" +
            "        \"amount\": 5000.00,\n" +
            "        \"cardInfo\": {\n" +
            "            \"name\": \"HARISH PARIHAR\",\n" +
            "            \"bankAccountNo\": \"201023906157\",\n" +
            "            \"ifsc\": \"1\"\n" +
            "        },\n" +
            "        \"payUser\": {\n" +
            "            \"phone\": \"111111\",\n" +
            "            \"email\": \"test@gmail.com\",\n" +
            "            \"name\": \"desc\"\n" +
            "        },\n" +
            "        \"deeplink\": \"upi://pay?pa=im.201023906157@indus&pn=HARISH PARIHAR&tid=6FXMQN&tn=6FXMQN&am=5000.00&cu=INR\",\n" +
            "        \"enableUpay\": false,\n" +
            "        \"orderNo\": \"DS58021511152347588099678\",\n" +
            "        \"created\": 1700072278883,\n" +
            "        \"supportDeepLink\": false\n" +
            "    }\n" +
            "}";
    }

}
