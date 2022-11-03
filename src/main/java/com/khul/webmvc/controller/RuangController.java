package com.khul.webmvc.controller;

import com.khul.webmvc.model.GedungModel;
import com.khul.webmvc.model.RuangModel;
import com.khul.webmvc.service.GedungService;
import com.khul.webmvc.service.RuangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/ruang")
public class RuangController {

    private RuangService ruangService;
    private GedungService gedungService;

    @Autowired
    public RuangController(RuangService ruangService, GedungService gedungService) {
        this.ruangService = ruangService;
        this.gedungService = gedungService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("ruang/index.html");
        List<RuangModel> result = ruangService.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("ruang/add.html");
        List<GedungModel> result = gedungService.get();
        view.addObject("gedungList", result);
        return view;
    }


    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute RuangModel request){
        this.ruangService.save(request);
        return new ModelAndView("redirect:/ruang");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        RuangModel ruang = ruangService.getById(id);
        if (ruang == null){
            return new ModelAndView("redirect:/ruang");
        }

        List<GedungModel> gedung = gedungService.get();
        ModelAndView view = new ModelAndView("ruang/edit.html");
        view.addObject("data", ruang);
        view.addObject("gedungList", gedung);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute RuangModel request){
        this.ruangService.update(request.getId(), request);
        return new ModelAndView("redirect:/ruang");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id){
        RuangModel ruang = ruangService.getById(id);
        if (ruang == null){
            return new ModelAndView("redirect:/ruang");
        }

        ModelAndView view = new ModelAndView("ruang/detail.html");
        view.addObject("data", ruang);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute RuangModel request){
        RuangModel ruang = ruangService.getById(request.getId());
        if (ruang == null){
            return new ModelAndView("redirect:/ruang");
        }
        this.ruangService.delete(request.getId());
        return new ModelAndView("redirect:/ruang");
    }
}
