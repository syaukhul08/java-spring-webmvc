package com.khul.webmvc.controller;

import com.khul.webmvc.model.GedungModel;
import com.khul.webmvc.service.GedungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
        List<GedungModel> result = service.getAll();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("gedung/form.html");
        view.addObject("gedung", new GedungModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("gedung") GedungModel request, BindingResult result){
        ModelAndView view = new ModelAndView("gedung/form.html");

        if (Boolean.FALSE.equals(service.validCode(request))){
            FieldError fieldError = new FieldError("gedung", "code", "Code" + request.getCode() + " already exist");
            result.addError(fieldError);
        }

        if (Boolean.FALSE.equals(service.validName(request))){
            FieldError fieldError = new FieldError("gedung", "code", "Name with"+ request.getName() +" already exist");
            result.addError(fieldError);
        }

        if (result.hasErrors()){
            view.addObject("gedung", request);
            return view;
        }

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
        view.addObject("gedung", gedung);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("gedung") GedungModel request, BindingResult result){
        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("gedung/edit.html");
            view.addObject("gedung", request);
        }

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
