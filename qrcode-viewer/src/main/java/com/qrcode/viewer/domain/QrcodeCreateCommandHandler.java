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
            ErrorCorrectionLevel errorCorrectionLevel;
            switch(createQrcodeCommand.getCorrection()) {
                case "L":
                    errorCorrectionLevel = ErrorCorrectionLevel.L;
                    break;
                case "M":
                    errorCorrectionLevel = ErrorCorrectionLevel.M;
                    break;
                case "Q":
                    errorCorrectionLevel = ErrorCorrectionLevel.Q;
                    break;
                case "H":
                    errorCorrectionLevel = ErrorCorrectionLevel.H;
                    break;
                default:
                    log.warn("Unsupported error correction level: {}", createQrcodeCommand.getCorrection());
                    throw new QrcodeApplicationServiceException("Unsupported error correction level: " + createQrcodeCommand.getCorrection());
            }
            if(createQrcodeCommand.getVersion() < 1 || createQrcodeCommand.getVersion() > 40) {
                log.warn("Invalid version: {}", createQrcodeCommand.getVersion());
                throw new QrcodeApplicationServiceException("Invalid version:" + createQrcodeCommand.getVersion());
            }
            int cellSize = 21 + (createQrcodeCommand.getVersion() - 1) * 4;
            int width =  (cellSize + createQrcodeCommand.getMargin() * 2) * createQrcodeCommand.getSize();
            int height = width;
      
            Hashtable<EncodeHintType,Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
            hints.put(EncodeHintType.MARGIN, createQrcodeCommand.getMargin());
            hints.put(EncodeHintType.QR_VERSION, createQrcodeCommand.getVersion());
      
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
