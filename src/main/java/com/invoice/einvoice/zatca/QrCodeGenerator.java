package com.invoice.einvoice.zatca;

import java.util.List;

public interface QrCodeGenerator {
   String generateQrCode(List<TLVMessage> var1) throws Exception;
}
