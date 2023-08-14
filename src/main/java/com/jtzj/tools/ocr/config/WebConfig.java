package com.jtzj.tools.ocr.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public ITesseract tesseract(OcrConfig ocrConfig) {
        ITesseract instance = new Tesseract();
        //设置语言库所在的文件夹位置，最好是绝对的，不然加载不到就直接报错了
        instance.setDatapath(ocrConfig.getDataPath());
        //设置使用的语言库类型：chi_sim 中文简体
        instance.setLanguage(ocrConfig.getLanguage());
        return instance;
    }
}
