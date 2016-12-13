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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.deidentifier.arx.ARXResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.wnameless.workbookaccessor.WorkbookWriter;

import net.sf.rubycollect4j.RubyFile;
import tw.edu.ym.lab525.arx.DataPrivacy;
import tw.edu.ym.lab525.repository.PatientRepository;
import tw.edu.ym.lab525.service.MainService;
import tw.edu.ym.lab525.unit.TimeStamp;

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
      @RequestParam(value = "birthday") String birthday,
      @RequestParam(value = "nationality") String nationality) {

    if (name.equals("") || ssid.equals("") || gender.equals("")
        || disease.equals("") || birthday.equals("")) {
      map.addAttribute("errorMessage", "請勿輸入空值");
      return "patients";
    } else if (patientRepo.findBySsid(ssid) != null) {
      map.addAttribute("errorMessage", "身分證號重複");
      return "patients";
    } else {
      mainService.create(ssid, name, gender, disease, birthday, nationality);
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

  @RequestMapping(value = "/download", method = RequestMethod.POST)
  public void download(HttpServletResponse response,
      @RequestParam(value = "k") Integer k,
      @RequestParam(value = "l") Integer l) throws IOException {
    if (k == null) k = 2;
    if (l == null) l = 2;
    DataPrivacy dataPrivacy = new DataPrivacy(patientRepo.findAll());
    ARXResult result = dataPrivacy.getARXResult(k, l);

    WorkbookWriter writer = WorkbookWriter.openXLSX();
    Iterator<String[]> iterator = result.getOutput(false).iterator();
    while (iterator.hasNext()) {
      List<String> list = Arrays.asList(iterator.next());
      writer.addRow(list.get(0), list.get(1), list.get(2), list.get(3),
          list.get(4), list.get(5));
    }

    String fileName = k + "anonymity_" + l + "diversity_"
        + TimeStamp.simpleDateTime() + ".xls";
    InputStream is = new FileInputStream(writer.save(fileName));
    IOUtils.copy(is, response.getOutputStream());
    response.setHeader("Content-disposition",
        "attachment; filename=" + fileName);
    response.flushBuffer();
    RubyFile.delete(fileName);
  }

}
