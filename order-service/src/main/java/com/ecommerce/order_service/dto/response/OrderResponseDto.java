package com.ecommerce.order_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class OrderResponseDto {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private String productName;
    private Float unitPrice;
    private Integer amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderedDate;
    private Integer invoiceId;
}
