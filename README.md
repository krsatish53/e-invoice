# ZATCA (Fatoora) QR-Code Implementation

An unofficial package to help developers to implement ZATCA (Fatoora) QR code required for e-invoicing in KSA

## Installation

```python
<dependency>
 <groupId>com.payneteasy</groupId>
 <artifactId>ber-tlv</artifactId>
 <version>1.0-11</version>
</dependency>
```

## Generate QR code

```python
  String qrCode = QrUtil.generateQrCode("Jeddah Tower", "31011111311113", LocalDateTime.now().toString(), BigDecimal.valueOf(115), BigDecimal.valueOf(15));
  System.out.println("Qr code: " + qrCode);
# AQxKZWRkYWggVG93ZXICDjMxMDExMTExMzExMTEzAx0yMDIxLTExLTIyVDIyOjI3OjQzLjYyMDgyOTM1NwQDMTE1BQIxNQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==
```
