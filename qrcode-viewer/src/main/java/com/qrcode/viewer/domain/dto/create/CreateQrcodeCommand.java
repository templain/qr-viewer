package com.qrcode.viewer.domain.dto.create;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateQrcodeCommand {
    @NotEmpty
    private final String url;
    @Min(1)
    private final int size;
    @Min(0)
    private final int margin;
    @Min(1)
    private final int version;
    @NotNull
    private final String correction;
}
