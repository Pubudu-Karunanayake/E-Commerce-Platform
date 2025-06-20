package com.ecommerce.invoice_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Invoices")
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;
    private Integer orderId;
    private Integer customerId;
    private Integer itemId;
    private String itemName;
    private Integer quantity;
    private Float unitPrice;
    private Float amount;
    private Float discount = 0.00f;
    private Float total;

}
