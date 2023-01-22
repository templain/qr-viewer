package com.qrcode.viewer.application.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qrcode.viewer.domain.dto.create.CreateQrcodeCommand;
import com.qrcode.viewer.domain.dto.create.CreateQrcodeResult;
import com.qrcode.viewer.domain.ports.input.service.QrcodeApplicationService;

@Controller
public class QrcodeController {
    private final QrcodeApplicationService qrcodeApplicationService;

    @Autowired
    public QrcodeController(QrcodeApplicationService qrcodeApplicationService) {
        this.qrcodeApplicationService = qrcodeApplicationService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(Model model) {
        String[] corrections = {"L", "M", "Q", "H"};
        model.addAttribute("versions", IntStream.rangeClosed(1, 40).toArray());
        model.addAttribute("corrections", corrections);
        model.addAttribute("sizeNum", 5);
        model.addAttribute("marginMax", 8);
        return "index";
    }
    
    @ResponseBody
    @RequestMapping(value = "/output", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getOutput(@RequestParam(name = "url", required = false)String url,
                            @RequestParam(name = "size", required = false)String size,
                            @RequestParam(name = "margin", required = false)String margin,
                            @RequestParam(name = "version", required = false)String version,
                            @RequestParam(name = "correction", required = false)String correction) throws IOException {
        try {
            CreateQrcodeResult createQrcodeResult = qrcodeApplicationService.createQrcode(
                CreateQrcodeCommand.builder()
                .url(url)
                .size(Integer.parseInt(size))
                .margin(Integer.parseInt(margin))
                .version(Integer.parseInt(version))
                .correction(correction)
                .build()
            );
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(createQrcodeResult.getImage(), "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return IOUtils.toByteArray(is);
        } catch(Exception e) {
            try {
                File file = new ClassPathResource("static/images/error.png").getFile();
                FileInputStream inputStream = new FileInputStream(file);
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while(true) {
                    int len = inputStream.read(buffer);
                    if(len < 0) {
                        break;
                    }
                    bout.write(buffer, 0, len);
                }
                inputStream.close();
                return bout.toByteArray();
            } catch (IOException e1) {
                return null;
            }
        }
    }
}