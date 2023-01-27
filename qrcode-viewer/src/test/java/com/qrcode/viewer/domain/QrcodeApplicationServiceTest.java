package com.qrcode.viewer.domain;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qrcode.viewer.domain.dto.create.CreateQrcodeCommand;
import com.qrcode.viewer.domain.dto.create.CreateQrcodeResult;
import com.qrcode.viewer.domain.ports.input.service.QrcodeApplicationService;

@SpringBootTest
public class QrcodeApplicationServiceTest {
    @Autowired
    private QrcodeApplicationService orderApplicationService;

    @Test
    public void testCreateQrcode() {
        CreateQrcodeCommand createQrcodeCommand = CreateQrcodeCommand.builder()
            .version(5)
            .correction("M")
            .margin(0)
            .size(1)
            .url("http://www.google.com")
            .build();
        CreateQrcodeResult createQrcodeResult = orderApplicationService.createQrcode(createQrcodeCommand);
        assertEquals(37, createQrcodeResult.getImage().getWidth());
        assertEquals(37, createQrcodeResult.getImage().getHeight());
    }
}
