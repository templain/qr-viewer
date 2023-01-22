package com.qrcode.viewer.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.qrcode.viewer.domain.dto.create.CreateQrcodeCommand;
import com.qrcode.viewer.domain.dto.create.CreateQrcodeResult;
import com.qrcode.viewer.domain.ports.input.service.QrcodeApplicationService;

@Validated
@Service
class QrcodeApplicationServiceImpl implements QrcodeApplicationService {
    private QrcodeCreateCommandHandler qrcodeCreateCommandHandler;

    @Autowired
    public QrcodeApplicationServiceImpl(QrcodeCreateCommandHandler qrcodeCreateCommandHandler) {
        this.qrcodeCreateCommandHandler = qrcodeCreateCommandHandler;
    }

    @Override
    public CreateQrcodeResult createQrcode(CreateQrcodeCommand createQrcodeCommand) {
        return qrcodeCreateCommandHandler.createQrcode(createQrcodeCommand);
    }
}