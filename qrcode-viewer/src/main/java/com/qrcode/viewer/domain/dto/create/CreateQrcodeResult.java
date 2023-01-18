package com.qrcode.viewer.domain.dto.create;

import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateQrcodeResult {
    @NotNull
    private final BufferedImage image;
}
