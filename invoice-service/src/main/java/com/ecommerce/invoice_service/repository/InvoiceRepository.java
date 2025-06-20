package com.ecommerce.invoice_service.repository;

import com.ecommerce.invoice_service.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
