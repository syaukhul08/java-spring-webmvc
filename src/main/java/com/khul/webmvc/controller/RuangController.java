package com.khul.webmvc.controller;

import com.khul.webmvc.model.GedungModel;
import com.khul.webmvc.model.RuangModel;
import com.khul.webmvc.service.GedungService;
import com.khul.webmvc.service.RuangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
        List<RuangModel> result = ruangService.getAll();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("ruang/form.html");
        List<GedungModel> result = gedungService.getAll();
        view.addObject("ruang", new RuangModel());
        view.addObject("gedungList", gedungService.getAll());
        //view.addObject("gedungList", result);
        return view;
    }


    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("ruang") RuangModel request, BindingResult result){

        ModelAndView view = new ModelAndView("ruang/form.html");

        if (Boolean.FALSE.equals(ruangService.validCode(request))){
            FieldError fieldError = new FieldError("jurusan", "code", "Code "+ request.getCode() + " already exist");
            result.addError(fieldError);
        }

        if (Boolean.FALSE.equals(ruangService.validName(request))){
            FieldError fieldError = new FieldError("jurusan","code", "Name with "+request.getName() + " already exist");
            result.addError(fieldError);
        }

        if (result.hasErrors()){
            view.addObject("ruang", request);
            return view;
        }

        this.ruangService.save(request);
        return new ModelAndView("redirect:/ruang");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        RuangModel ruang = ruangService.getById(id);
        if (ruang == null){
            return new ModelAndView("redirect:/ruang");
        }

        List<GedungModel> gedung = gedungService.getAll();
        ModelAndView view = new ModelAndView("ruang/edit.html");
        //view.addObject("data", ruang);
        view.addObject("ruang", ruang);
        view.addObject("gedungList", gedung);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("ruang") RuangModel request, BindingResult result){

        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("ruang/edit.html");
            view.addObject("ruang", request);
            return view;
        }

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
