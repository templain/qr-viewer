package com.qrcode.viewer.domain.dto.create;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateQrcodeCommand {
    @NotNull
    private final String url;
    @NotNull
    private final int size;
    @NotNull
    private final int version;
    @NotNull
    private final String correction;
}
