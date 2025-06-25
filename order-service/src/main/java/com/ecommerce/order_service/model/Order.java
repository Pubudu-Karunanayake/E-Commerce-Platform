package com.ecommerce.order_service.model;

import com.ecommerce.order_service.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "user_Id")
    private Integer userId;

    @Column(name = "item_Id")
    private Integer productId;

    @Column(name = "item_Name")
    private String productName;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "order_Date_&_time")
    private LocalDateTime orderDate;

    @Column(name = "invoice_Id")
    private Integer invoiceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status")
    private InvoiceStatus invoiceStatus;
}
