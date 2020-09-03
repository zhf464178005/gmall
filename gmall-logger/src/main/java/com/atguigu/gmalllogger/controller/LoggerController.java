package com.atguigu.gmalllogger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.common.constant.KafkaTopicConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoggerController {

    private final Logger logger = LoggerFactory.getLogger(LoggerController.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("log")
    @ResponseBody
    public String doLog(@RequestParam("log") String log){
        System.out.println(log);
        //1,给日志数据添加时间戳
        log = addTsToLog(log);
        //2,日志落盘
        saveLogToFile(log);
        //3，日志发送到kafka
        sendLogToKafka(log);
        return "ok";
    }

    private void sendLogToKafka(String log) {
        if (log.contains("startup")){
            kafkaTemplate.send(KafkaTopicConstant.TOPIC_STARTUP, log);
        }else {
            kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT,log);
        }
    }

    private void saveLogToFile(String log) {
        logger.info(log);
    }

    private String addTsToLog(String log) {
        JSONObject jsonObject = JSON.parseObject(log);
        jsonObject.put("ts",System.currentTimeMillis());
        return jsonObject.toJSONString();
    }
}
