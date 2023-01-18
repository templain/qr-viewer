package com.qrcode.viewer.application.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/qrcode", method = RequestMethod.GET)
    public String submit(@RequestParam("url")String url, Model model) {
        model.addAttribute("url", url);
        return "result";
    }

    @ResponseBody
    @RequestMapping(value = "/output", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getOutput(@RequestParam(name = "url", required = true)String url) throws IOException {
        CreateQrcodeResult createQrcodeResult = qrcodeApplicationService.createQrcode(CreateQrcodeCommand.builder().url(url).build());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(createQrcodeResult.getImage(), "png", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return IOUtils.toByteArray(is);
    }
}