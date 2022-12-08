package com.khul.webmvc.controller;

import com.khul.webmvc.entity.KelasEntity;
import com.khul.webmvc.model.*;
import com.khul.webmvc.service.DosenService;
import com.khul.webmvc.service.KelasService;
import com.khul.webmvc.service.MataKuliahService;
import com.khul.webmvc.service.RuangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/kelas")
public class KelasController {
    private KelasService kelasService;
    private RuangService ruangService;
    private MataKuliahService mataKuliahService;
    private DosenService dosenService;

    @Autowired
    public KelasController(KelasService kelasService, RuangService ruangService, MataKuliahService mataKuliahService, DosenService dosenService) {
        this.kelasService = kelasService;
        this.ruangService = ruangService;
        this.mataKuliahService = mataKuliahService;
        this.dosenService = dosenService;
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
        List<RuangModel> resultRuang = ruangService.get();
        List<MataKuliahModel> resultMatkul = mataKuliahService.get();
        List<DosenModel> resultDosen = dosenService.get();
        view.addObject("ruangList", resultRuang);
        view.addObject("mataKuliahList", resultMatkul);
        view.addObject("dosenList", resultDosen);
        return view;
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute KelasModel request){
        this.kelasService.save(request);
        return new ModelAndView("redirect:/kelas");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id){
        KelasModel kelas = kelasService.getById(id);
        if (kelas == null){
            return new ModelAndView("redirect:/kelas");
        }

        List<RuangModel> ruang = ruangService.get();
        List<MataKuliahModel> mataKuliah = mataKuliahService.get();
        List<DosenModel> dosen = dosenService.get();
        ModelAndView view = new ModelAndView("kelas/edit.html");
        view.addObject("data", kelas);
        view.addObject("ruangList", ruang);
        view.addObject("mataKuliahList", mataKuliah);
        view.addObject("dosenList", dosen);
        return view;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute KelasModel request){
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
