package com.khul.webmvc.controller;

import com.khul.webmvc.model.GedungModel;
import com.khul.webmvc.service.GedungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gedung")
public class GedungController {

    private GedungService service;

    @Autowired
    public GedungController(GedungService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("gedung/index.html");
        List<GedungModel> result = service.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("gedung/add.html");
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute GedungModel request){
        this.service.save(request);
        return new ModelAndView("redirect:/gedung");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        GedungModel gedung = service.getById(id);
        if (gedung == null){
            return new ModelAndView("redirect:/gedung");
        }
        ModelAndView view = new ModelAndView("gedung/edit.html");
        view.addObject("data", gedung);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute GedungModel request){
        this.service.update(request.getId(), request);
        return new ModelAndView("redirect:/gedung");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id){
        GedungModel gedung = service.getById(id);
        if (gedung == null){
            return new ModelAndView("redirect:/gedung");
        }
        ModelAndView view = new ModelAndView("gedung/detail.html" );
        view.addObject("data", gedung);
        return view;
    }
    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute GedungModel request){
        GedungModel gedung = service.getById(request.getId());
        if (gedung == null){
            return new ModelAndView("redirect:/gedung");
        }

        this.service.delete(request.getId());
        return new ModelAndView("redirect:/gedung");
    }



}
