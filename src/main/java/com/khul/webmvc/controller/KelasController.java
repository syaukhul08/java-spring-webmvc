package com.khul.webmvc.controller;

import com.khul.webmvc.entity.KelasEntity;
import com.khul.webmvc.entity.LookupEntity;
import com.khul.webmvc.model.*;
import com.khul.webmvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/kelas")
public class KelasController {
    private KelasService kelasService;
    private RuangService ruangService;
    private MataKuliahService mataKuliahService;
    private DosenService dosenService;
    private LookupService lookupService;

    @Autowired
    public KelasController(KelasService kelasService, RuangService ruangService, MataKuliahService mataKuliahService, DosenService dosenService, LookupService lookupService) {
        this.kelasService = kelasService;
        this.ruangService = ruangService;
        this.mataKuliahService = mataKuliahService;
        this.dosenService = dosenService;
        this.lookupService = lookupService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("kelas/index.html");
        List<KelasModel> result = kelasService.getAll();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("kelas/form.html");

        view.addObject("namaHariList", lookupService.getByGroup("HARI"));
        view.addObject("onlineList", lookupService.getByGroup("ONLINE"));
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));

//        List<RuangModel> resultRuang = ruangService.getAll();
//        List<MataKuliahModel> resultMatkul = mataKuliahService.getAll();
//        List<DosenModel> resultDosen = dosenService.getAll();

        view.addObject("ruangList", ruangService.getAll());
        view.addObject("mataKuliahList", mataKuliahService.getAll());
        view.addObject("dosenList", dosenService.getAll());
        view.addObject("kelas", new KelasModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("kelas") KelasModel request, BindingResult result){

        ModelAndView view = new ModelAndView("kelas/form.html");
        if (Boolean.FALSE.equals(kelasService.validCode(request))){
            FieldError fieldError = new FieldError("kelas","code","Code "+ request.getCode() +" already exist");
            result.addError(fieldError);
        }


        if (result.hasErrors()){
            view.addObject("hariList", lookupService.getByGroup("HARI"));
            view.addObject("onlineList", lookupService.getByGroup("online"));
            view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
            view.addObject("ruangList", ruangService.getAll());
            view.addObject("mataKuliahList", mataKuliahService.getAll());
            view.addObject("dosenList", dosenService.getAll());
            view.addObject("kelas", request);
            return view;
        }

        this.kelasService.save(request);
        return new ModelAndView("redirect:/kelas");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        KelasModel kelas = kelasService.getById(id);
        if (kelas == null){
            return new ModelAndView("redirect:/kelas");
        }

//        List<RuangModel> ruang = ruangService.getAll();
//        List<MataKuliahModel> mataKuliah = mataKuliahService.getAll();
//        List<DosenModel> dosen = dosenService.getAll();

        ModelAndView view = new ModelAndView("kelas/edit.html");
        view.addObject("data", kelas);
        view.addObject("ruangList", ruangService.getAll());
        view.addObject("mataKuliahList", mataKuliahService.getAll());
        view.addObject("dosenList", dosenService.getAll());
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("kelas") KelasModel request, BindingResult result){
        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("kelas/edit.html");
            view.addObject("kelas",request);
            return view;
        }

        this.kelasService.update(request.getId(), request);
        return new ModelAndView("redirect:/kelas");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id){
        KelasModel kelas = kelasService.getById(id);
        if (kelas == null){
            return new ModelAndView("redirect:/kelas");
        }

        ModelAndView view = new ModelAndView("kelas/detail.html");
        view.addObject("data", kelas);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute KelasModel request){
        KelasModel kelas = kelasService.getById(request.getId());
        if (kelas == null){
            return new ModelAndView("redirect:/kelas");
        }
        this.kelasService.delete(request.getId());
        return new ModelAndView("redirect:/kelas");
    }
}
