package com.qrcode.viewer.domain.ports.input.service;
import com.qrcode.viewer.domain.dto.create.CreateQrcodeCommand;
import com.qrcode.viewer.domain.dto.create.CreateQrcodeResult;

import javax.validation.Valid;

public interface QrcodeApplicationService {
    CreateQrcodeResult createQrcode(@Valid CreateQrcodeCommand createQrcodeCommand );
}
