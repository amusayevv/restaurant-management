package az.edu.ada.wm2.backend.controller;

import az.edu.ada.wm2.backend.service.QRCodeGeneratorService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/table")
public class TableController {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";

    private final QRCodeGeneratorService qrCodeGenerator;

    @Autowired
    public TableController(QRCodeGeneratorService qrCodeGenerator) {
        this.qrCodeGenerator = qrCodeGenerator;
    }

    @GetMapping("/{tableId}")
    public String getQRCode(Model model, @PathVariable String tableId) {
        String url = "http://localhost:5173/?table=" + tableId;

        byte[] image = new byte[0];
        try {
            image = qrCodeGenerator.getQRCodeImage(url, 250, 250);
            qrCodeGenerator.generateQRCodeImage(url, 250, 250, QR_CODE_IMAGE_PATH);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("url", url);

        model.addAttribute("qrcode", qrcode);

        return "qrcode";
    }
}