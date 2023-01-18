package com.qrcode.viewer.domain;

import com.qrcode.viewer.domain.dto.create.CreateQrcodeCommand;
import com.qrcode.viewer.domain.dto.create.CreateQrcodeResult;
import com.qrcode.viewer.domain.exception.QrcodeApplicationServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Slf4j
@Component
public class QrcodeCreateCommandHandler {
    public CreateQrcodeResult createQrcode(CreateQrcodeCommand createQrcodeCommand) {
        BufferedImage image;
        try {
            BarcodeFormat format = BarcodeFormat.QR_CODE;
            int width = 160;
            int height = 160;
      
            Hashtable<EncodeHintType,ErrorCorrectionLevel> hints = new Hashtable<EncodeHintType,ErrorCorrectionLevel>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
      
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(createQrcodeCommand.getUrl(), format, width, height, hints);
            image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        }
        catch (WriterException e) {
            log.error("Failed to write image.");
            throw new QrcodeApplicationServiceException("Failed to write image.");
        }
        return CreateQrcodeResult.builder()
            .image(image)
            .build();
    }
}
