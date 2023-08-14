package com.jtzj.tools.ocr.controller;

import com.jtzj.tools.ocr.model.ApiResponse;
import com.jtzj.tools.ocr.model.ResponseState;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("ocr")
public class OcrController {
    @Autowired
    private ITesseract tesseract;

    @SneakyThrows
    @RequestMapping("text")
    public ApiResponse ocrText(@RequestParam(name = "url") String url) {
        ApiResponse apiResponse = new ApiResponse();
        log.debug("接收到文字提取ocr请求{}", url);

        try (InputStream inputStream = new ByteArrayInputStream(IOUtils.toByteArray(new URL(url)))) {
            String text = ocrText(inputStream);
            apiResponse.setCode(ResponseState.Success);
            Map<String, String> retData = new LinkedHashMap<>(1);
            retData.put("result", text);
            apiResponse.setData(retData);
            return apiResponse;
        } catch (Exception ex) {
            apiResponse.setCode(ResponseState.Failure);
            apiResponse.setMsg(ex.getMessage());
            return apiResponse;
        }
    }

    @SneakyThrows
    @PostMapping("text/stream")
    public ApiResponse ocrTextStream(@RequestParam("file") MultipartFile file) {
        ApiResponse apiResponse = new ApiResponse();
        log.debug("接收到文字提取ocr请求(流模式)");
        try (InputStream inputStream = file.getInputStream()) {
            String text = ocrText(inputStream);
            apiResponse.setCode(ResponseState.Success);
            Map<String, String> retData = new LinkedHashMap<>(1);
            retData.put("result", text);
            apiResponse.setData(retData);
            return apiResponse;
        } catch (Exception ex) {
            apiResponse.setCode(ResponseState.Failure);
            apiResponse.setMsg(ex.getMessage());
            return apiResponse;
        }
    }

    private String ocrText(InputStream inputStream) throws TesseractException, IOException {
        try {
            BufferedImage img = ImageIO.read(inputStream);
            String result = tesseract.doOCR(img);
            log.info("扫描文本结果:{}", "成功");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
