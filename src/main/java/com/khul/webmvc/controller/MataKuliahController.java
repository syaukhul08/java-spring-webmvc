package com.khul.webmvc.controller;

import com.khul.webmvc.model.MahasiswaModel;
import com.khul.webmvc.model.MataKuliahModel;
import com.khul.webmvc.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
        List<MataKuliahModel> result = service.getAll();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("matakuliah/form.html");
        view.addObject("matakuliah", new MataKuliahModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("matakuliah") MataKuliahModel request, BindingResult result){
        ModelAndView view = new ModelAndView("matakuliah/form.html");

        if (Boolean.FALSE.equals(service.validCode(request))){
            FieldError fieldError = new FieldError("matakuliah", "code", "Code" + request.getCode() + " already exist");
            result.addError(fieldError);
        }

        if (Boolean.FALSE.equals(service.validName(request))){
            FieldError fieldError = new FieldError("matakuliah", "code", "Name with"+ request.getName() +" already exist");
            result.addError(fieldError);
        }

        if (result.hasErrors()){
            view.addObject("matakuliah", request);
            return view;
        }

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
        view.addObject("matakuliah", mataKuliah);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("matakuliah") MataKuliahModel request, BindingResult result) {
        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("matakuliah/edit.html");
            view.addObject("matakuliah", request);
        }

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
