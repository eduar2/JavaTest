package com.pichincha.prueba.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Date Fecha;
    private String Cliente;
    private String NumeroCuenta;
    private String Tipo;
    private BigDecimal SaldoInicial;
    private Boolean Estado;
    private BigDecimal Movimiento;
    private BigDecimal SaldoDisponible;
}
