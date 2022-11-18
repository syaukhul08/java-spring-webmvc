package com.khul.webmvc.controller;

import com.khul.webmvc.model.MataKuliahModel;
import com.khul.webmvc.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/matakuliah")
public class MataKuliahController {

    private MataKuliahService service;

    @Autowired
    public MataKuliahController(MataKuliahService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("matakuliah/index.html");
        List<MataKuliahModel> result = service.get();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView update(){
        return new ModelAndView("matakuliah/form.html");
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute MataKuliahModel request){
        this.service.save(request);
        return new ModelAndView("redirect:/matakuliah");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        MataKuliahModel mataKuliah = this.service.getById(id);
        if (mataKuliah == null){
            return new ModelAndView("redirect:/matakuliah");
        }

        ModelAndView view = new ModelAndView("matakuliah/edit.html");
        view.addObject("data", mataKuliah);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute MataKuliahModel request) {
        this.service.update(request.getId(), request);
        return new ModelAndView("redirect:/matakuliah");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id) {
        MataKuliahModel mataKuliah = this.service.getById(id);
        if (mataKuliah == null) {
            return new ModelAndView("redirect:/matakuliah");
        }

        ModelAndView view = new ModelAndView("matakuliah/detail.html");
        view.addObject("data", mataKuliah);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute MataKuliahModel request){
        MataKuliahModel mataKul = service.getById(request.getId());
        if (mataKul == null){
            return new ModelAndView("redirect:/matakuliah");
        }
        this.service.delete(request.getId());
        return new ModelAndView("redirect:/matakuliah");
    }
}
