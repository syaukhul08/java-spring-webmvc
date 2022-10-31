package com.khul.webmvc.controller;

import com.khul.webmvc.model.FakultasModel;
import com.khul.webmvc.service.FakultasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/fakultas")
public class FakultasController {
    private FakultasService service;

    @Autowired
    public FakultasController(FakultasService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("fakultas/index.html");
        List<FakultasModel> result = service.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("fakultas/add.html");
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute FakultasModel request) {
        this.service.save(request);
        return new ModelAndView("redirect:/fakultas");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id) {
        FakultasModel fakultas = this.service.getById(id);
        if (fakultas == null) {
            return new ModelAndView("redirect:/fakultas");
        }

        ModelAndView view = new ModelAndView("fakultas/edit.html");
        view.addObject("data", fakultas);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute FakultasModel request) {
        this.service.update(request.getId(), request);
        return new ModelAndView("redirect:/fakultas");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id) {
        FakultasModel fakultas = this.service.getById(id);
        if (fakultas == null) {
            return new ModelAndView("redirect:/fakultas");
        }

        ModelAndView view = new ModelAndView("fakultas/detail.html");
        view.addObject("data", fakultas);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute FakultasModel request) {
        FakultasModel fakultas = service.getById(request.getId());
        if (fakultas == null) {
            return new ModelAndView("redirect:/fakultas");
        }
        this.service.delete(request.getId());
        return new ModelAndView("redirect:/fakultas");
    }
}
