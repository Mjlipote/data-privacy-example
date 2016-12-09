/*
 *
 * @author Ming-Jheng Li
 *
 *
 * Copyright 2016 Ming-Jheng Li
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package tw.edu.ym.lab525.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tw.edu.ym.lab525.repository.PatientRepository;
import tw.edu.ym.lab525.service.MainService;

@RequestMapping("/patients")
@Controller
public class MainController {

  @Autowired
  PatientRepository patientRepo;

  @Autowired
  MainService mainService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String view(ModelMap map) {
    map.addAttribute("patients", mainService.view());
    return "patients";
  }

  @RequestMapping(value = "/lookup", method = RequestMethod.GET)
  public String lookup(ModelMap map,
      @RequestParam(value = "ssid") String ssid) {
    if (patientRepo.findBySsid(ssid) == null) {
      map.addAttribute("errorMessage", "找不到這個病人");
      return "patients";
    } else {
      map.addAttribute("patients", mainService.lookup(ssid));
      return "patients";
    }
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String create(ModelMap map, @RequestParam(value = "ssid") String ssid,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "gender") String gender,
      @RequestParam(value = "disease") String disease,
      @RequestParam(value = "zipcode") Integer zipcode,
      @RequestParam(value = "age") Integer age) {

    if (name.equals("") || ssid.equals("") || gender.equals("")
        || disease.equals("") || zipcode == null || age == null) {
      map.addAttribute("errorMessage", "請勿輸入空值");
      return "patients";
    } else if (patientRepo.findBySsid(ssid) != null) {
      map.addAttribute("errorMessage", "身分證號重複");
      return "patients";
    } else {
      mainService.create(ssid, name, gender, disease, zipcode, age);
      return "redirect:/patients";
    }
  }

  @RequestMapping(value = "/{ssid}", method = RequestMethod.GET)
  public String read(ModelMap map, @PathVariable("ssid") String ssid) {

    map.addAttribute("patients", mainService.read(ssid));

    return "patient-info";
  }

  @RequestMapping(value = "/{ssid}", method = RequestMethod.PUT)
  public String update(ModelMap map, @PathVariable("ssid") String ssid,
      @RequestParam(value = "disease") String disease) {
    mainService.update(ssid, disease);
    return "redirect:/patients";
  }

  @RequestMapping(value = "/{ssid}", method = RequestMethod.DELETE)
  public String delete(ModelMap map, @PathVariable("ssid") String ssid) {
    mainService.delete(ssid);
    return "redirect:/patients";
  }

}
