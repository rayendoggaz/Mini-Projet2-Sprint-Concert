package com.rayen.concerts.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rayen.concerts.entities.Concert;
import com.rayen.concerts.service.ConcertService;

@Controller
public class ConcertController {

    @Autowired
    ConcertService concertService;

    @RequestMapping("/listeConcerts")
    public String listeConcerts(ModelMap modelMap,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "2") int size) {
        Page<Concert> concerts = concertService.getAllConcertsParPage(page, size);
        modelMap.addAttribute("concerts", concerts);
        modelMap.addAttribute("pages", new int[concerts.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeConcerts";
    }


    @RequestMapping("/showCreate")
    public String showCreate() {
        return "createConcert";
    }

    @RequestMapping("/saveConcert")
    public String saveConcert(@ModelAttribute("concert") Concert concert,
                              @RequestParam("date") String date,
                              ModelMap modelMap) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date concertDate = dateFormat.parse(date);
        concert.setDateConcert(concertDate);

        Concert savedConcert = concertService.saveConcert(concert);
        String msg = "Concert enregistré avec l'ID " + savedConcert.getIdConcert();
        modelMap.addAttribute("msg", msg);
        return "createConcert";
    }

    @RequestMapping("/supprimerConcert")
    public String supprimerConcert(@RequestParam("id") Long id,
                                    ModelMap modelMap,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "2") int size) {
        concertService.deleteConcertById(id);
        Page<Concert> concerts = concertService.getAllConcertsParPage(page, size);
        modelMap.addAttribute("concerts", concerts);
        modelMap.addAttribute("pages", new int[concerts.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listeConcerts";
    }


    @RequestMapping("/modifierConcert")
    public String editerConcert(@RequestParam("id") Long id, ModelMap modelMap) {
        Concert concert = concertService.getConcert(id);
        modelMap.addAttribute("concert", concert);
        return "editerConcert";
    }

    @RequestMapping("/updateConcert")
    public String updateConcert(@ModelAttribute("concert") Concert concert,
                                @RequestParam("date") String date,
                                ModelMap modelMap) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date concertDate = dateFormat.parse(date);
        concert.setDateConcert(concertDate);

        concertService.updateConcert(concert);
        List<Concert> concerts = concertService.getAllConcerts();
        modelMap.addAttribute("concerts", concerts);
        return "listeConcerts";
    }
}
