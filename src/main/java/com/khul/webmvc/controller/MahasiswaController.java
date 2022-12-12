package com.khul.webmvc.controller;

import com.khul.webmvc.entity.LookupEntity;
import com.khul.webmvc.model.JurusanModel;
import com.khul.webmvc.model.MahasiswaModel;
import com.khul.webmvc.service.JurusanService;
import com.khul.webmvc.service.LookupService;
import com.khul.webmvc.service.MahasiswaService;
import com.khul.webmvc.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {
    private final MahasiswaService mahasiswaService;
    private final JurusanService jurusanService;
    private LookupService lookupService;

    @Autowired
    public MahasiswaController(MahasiswaService mahasiswaService, JurusanService jurusanService, LookupService lookupService) {
        this.mahasiswaService = mahasiswaService;
        this.jurusanService = jurusanService;
        this.lookupService = lookupService;
    }

    @GetMapping
    public ModelAndView index(){
        ModelAndView view = new ModelAndView("mahasiswa/index.html");
        List<MahasiswaModel> result = mahasiswaService.getAll();
        view.addObject("dataList", result);
        return view;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView view = new ModelAndView("mahasiswa/form.html");
        view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
        view.addObject("agamaList", lookupService.getByGroup(Constants.AGAMA));
        view.addObject("jurusanList", jurusanService.getAll());
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        view.addObject("mahasiswa", new MahasiswaModel());
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid @ModelAttribute("mahasiswa") MahasiswaModel request, BindingResult result){
        ModelAndView view = new ModelAndView("mahasiswa/form.html");
        if (result.hasErrors()){
            view.addObject("mahasiswa", request);
        }

        this.mahasiswaService.save(request);
        return new ModelAndView("redirect:/mahasiswa");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        MahasiswaModel mahasiswa = mahasiswaService.getById(id);
        if (mahasiswa == null){
            return new ModelAndView("redirect:/mahasiswa");
        }

        //List<JurusanModel> jurusan = jurusanService.get();
        ModelAndView view = new ModelAndView("mahasiswa/edit.html");
        view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
        view.addObject("agamaList", lookupService.getByGroup(Constants.AGAMA));
        view.addObject("jurusanList", jurusanService.getAll());
        // untuk order
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        // data yang akan diedit
        view.addObject("mahasiswa", mahasiswa);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid @ModelAttribute("mahasiswa") MahasiswaModel request, BindingResult result){
        if (result.hasErrors()){
            ModelAndView view = new ModelAndView("mahasiswa/edit.html");
            view.addObject("mahasiswa", request);
            return view;
        }
        this.mahasiswaService.update(request.getId(),request);
        return new ModelAndView("redirect:/mahasiswa");
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") String id){
        MahasiswaModel mahasiswa = mahasiswaService.getById(id);
        if (mahasiswa == null){
            return new ModelAndView("redirect:/mahasiswa");
        }
        ModelAndView view = new ModelAndView("mahasiswa/detail.html");
        view.addObject("genderList", lookupService.getByGroup(Constants.GENDER));
        view.addObject("agamaList", lookupService.getByGroup(Constants.AGAMA));
        view.addObject("jurusanList", jurusanService.getAll());
        // untuk order
        view.addObject("byPosition", Comparator.comparing(LookupEntity::getPosition));
        // data yang akan diedit
        view.addObject("mahasiswa", mahasiswa);
        return view;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@ModelAttribute MahasiswaModel request){
        MahasiswaModel mahasiswa = mahasiswaService.getById(request.getId());
        if (mahasiswa == null) {
            return new ModelAndView("redirect:/mahasiswa");
        }

        this.mahasiswaService.delete(request.getId());
        return new ModelAndView("redirect:/mahasiswa");
    }

}
