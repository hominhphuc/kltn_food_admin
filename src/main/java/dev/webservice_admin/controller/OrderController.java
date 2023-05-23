package dev.webservice_admin.controller;

import dev.webservice_admin.model.ChiTietDonDatHang;
import dev.webservice_admin.model.DonDatHang;
import dev.webservice_admin.model.MatHang;

import dev.webservice_admin.service.ChiTietDonDatHangService;
import dev.webservice_admin.service.DonDatHangService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private DonDatHangService donDatHangService;

    @Autowired
    private ChiTietDonDatHangService chiTietDonDatHangService;

    @GetMapping
    public String orderPage() {
        return "order";
    }

    @GetMapping("/print")
    public String print(Model model, @RequestParam Long orderId) {
        double subTotal = 0.0;

        DonDatHang donDatHang = donDatHangService.findById(orderId);
        List<ChiTietDonDatHang> chiTietDonDatHangList = chiTietDonDatHangService.findAllByDonDatHang(orderId);

        for (ChiTietDonDatHang ctddh: chiTietDonDatHangList){
            subTotal += ctddh.getDonGia();
        }

        model.addAttribute("subTotal", subTotal);
        model.addAttribute("donDatHang", donDatHang);
        model.addAttribute("chiTietDonDatHang", chiTietDonDatHangList);
        return "order-print";
    }
}
